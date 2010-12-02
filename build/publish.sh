#!/bin/bash

################# BEGIN CONFIGURATION #################

DESTINATION=/home/data/httpd/download.eclipse.org/technology/bpel
JOB_NAME=bpel-0.5
JOBDIR=/shared/jobs/${JOB_NAME}
WORKSPACE=${JOBDIR}/workspace/
BUILD_ID=$(find ${JOBDIR}/builds -maxdepth 1 -type d | sort | tail -1); BUILD_ID=${BUILD_ID/${JOBDIR}\/builds\/}
BUILD_NUMBER=$(cat ${JOBDIR}/nextBuildNumber); BUILD_NUMBER=$(( ${BUILD_NUMBER}-1 ))
JOB_URL=https://hudson.eclipse.org/hudson/job/${JOB_NAME}

################# END CONFIGURATION #################

# where to create the stuff to publish
STAGINGDIR=/tmp${WORKSPACE}/results/${JOB_NAME}

JOBNAMEREDUX=${JOB_NAME/.aggregate}; JOBNAMEREDUX=${JOBNAMEREDUX/tycho-}

# releases get named differently than snapshots
if [[ ${RELEASE} == "Yes" ]]; then
	ZIPSUFFIX="${BUILD_ID}-H${BUILD_NUMBER}"
else
	ZIPSUFFIX="SNAPSHOT"
fi

# define target update zip filename
SNAPNAME="${JOB_NAME}-Update-${ZIPSUFFIX}.zip"
# define target sources zip filename
SRCSNAME="${JOB_NAME}-Sources-${ZIPSUFFIX}.zip"
# define suffix to use for additional update sites
SUFFNAME="-Update-${ZIPSUFFIX}.zip"

# cleanup from last time
rm -fr /tmp${WORKSPACE}/results; mkdir -p ${STAGINGDIR}

# check for aggregate zip or overall zip
z=""
if [[ -d ${WORKSPACE}/sources/site/target ]]; then
	siteZip=${WORKSPACE}/sources/site/target/site_assembly.zip
	if [[ ! -f ${WORKSPACE}/sources/site/target/site_assembly.zip ]]; then
		siteZip=${WORKSPACE}/sources/site/target/site.zip
	fi
	z=$siteZip
elif [[ -d ${WORKSPACE}/site/target ]]; then
	siteZip=${WORKSPACE}/site/target/site_assembly.zip
	if [[ ! -f ${WORKSPACE}/site/target/site_assembly.zip ]]; then
		siteZip=${WORKSPACE}/site/target/site.zip
	fi
	z=$siteZip
fi

# note the job name, build number, SVN rev, and build ID of the latest snapshot zip
mkdir -p ${STAGINGDIR}/logs
METAFILE="${BUILD_ID}-H${BUILD_NUMBER}.txt"
if [[ ${SVN_REVISION} ]]; then
	METAFILE="${BUILD_ID}-H${BUILD_NUMBER}-r${SVN_REVISION}.txt"
	echo "SVN_REVISION = ${SVN_REVISION}" > ${STAGINGDIR}/logs/${METAFILE}
else
	echo -n "" > ${STAGINGDIR}/logs/${METAFILE}
fi
echo "JOB_NAME = ${JOB_NAME}" >> ${STAGINGDIR}/logs/${METAFILE}
echo "BUILD_NUMBER = ${BUILD_NUMBER}" >> ${STAGINGDIR}/logs/${METAFILE}
echo "BUILD_ID = ${BUILD_ID}" >> ${STAGINGDIR}/logs/${METAFILE}
echo "WORKSPACE = ${WORKSPACE}" >> ${STAGINGDIR}/logs/${METAFILE}
echo "HUDSON_SLAVE = $(uname -a)" >> ${STAGINGDIR}/logs/${METAFILE}

#echo "$z ..."
if [[ $z != "" ]] && [[ -f $z ]] ; then
	# unzip into workspace for publishing as unpacked site
	mkdir -p ${STAGINGDIR}/all/repo
	unzip -u -o -q -d ${STAGINGDIR}/all/repo $z

	# copy into workspace for access by bucky aggregator (same name every time)
	rsync -aq $z ${STAGINGDIR}/all/${SNAPNAME}
fi
z=""

# generate list of zips in this job
METAFILE=zip.list.txt
mkdir -p ${STAGINGDIR}/logs
echo "ALL_ZIPS = \\" >> ${STAGINGDIR}/logs/${METAFILE}
for z in $(find ${STAGINGDIR} -name "*Update*.zip") $(find ${STAGINGDIR} -name "*Sources*.zip"); do
	# list zips in staging dir
	echo "${z##${STAGINGDIR}/},\\"  >> ${STAGINGDIR}/logs/${METAFILE}
done
echo ""  >> ${STAGINGDIR}/logs/${METAFILE}

# get full build log and filter out Maven test failures
mkdir -p ${STAGINGDIR}/logs
bl=${STAGINGDIR}/logs/BUILDLOG.txt
wget -q ${JOB_URL}/${BUILD_NUMBER}/consoleText -O ${bl}
fl=${STAGINGDIR}/logs/FAIL_LOG.txt
# ignore warning lines and checksum failures
sed -ne "/\[WARNING\]\|CHECKSUM FAILED/ ! p" ${bl} | sed -ne "/<<< FAI/,+9 p" | sed -e "/AILURE/,+9 s/\(.\+AILURE.\+\)/\n----------\n\n\1/g" > ${fl}
sed -ne "/\[WARNING\]\|CHECKSUM FAILED/ ! p" ${bl} | sed -ne "/ FAI/ p" | sed -e "/AILURE \[/ s/\(.\+AILURE \[.\+\)/\n----------\n\n\1/g" >> ${fl}
sed -ne "/\[WARNING\]\|CHECKSUM FAILED/ ! p" ${bl} | sed -ne "/ SKI/ p" | sed -e "/KIPPED \[/ s/\(.\+KIPPED \[.\+\)/\n----------\n\n\1/g" >> ${fl}
fc=$(sed -ne "/FAI\|LURE/ p" ${fl} | wc -l)
if [[ $fc != "0" ]]; then
	echo "" >> ${fl}; echo -n "FAI" >> ${fl}; echo -n "LURES FOUND: "$fc >> ${fl};
fi 
fc=$(sed -ne "/KIPPED/ p" ${fl} | wc -l)
if [[ $fc != "0" ]]; then
	echo "" >> ${fl}; echo -n "SKI" >> ${fl}; echo -n "PS FOUND: "$fc >> ${fl};
fi 
el=${STAGINGDIR}/logs/ERRORLOG.txt
# ignore warning lines and checksum failures
sed -ne "/\[WARNING\]\|CHECKSUM FAILED/ ! p" ${bl} | sed -ne "/<<< ERR/,+9 p" | sed -e "/RROR/,+9 s/\(.\+RROR.\+\)/\n----------\n\n\1/g" > ${el}
sed -ne "/\[WARNING\]\|CHECKSUM FAILED/ ! p" ${bl} | sed -ne "/\[ERR/,+2 p"   | sed -e "/ROR\] Fai/,+2 s/\(.\+ROR\] Fai.\+\)/\n----------\n\n\1/g" >> ${el}
ec=$(sed -ne "/ERR\|RROR/ p" ${el} | wc -l) 
if [[ $ec != "0" ]]; then
	echo "" >> ${el}; echo -n "ERR" >> ${el}; echo "ORS FOUND: "$ec >> ${el};
fi

# publish to download.*.org, unless errors found - avoid destroying last-good update site
if [[ $ec == "0" ]] && [[ $fc == "0" ]]; then
	# publish build dir (including update sites/zips/logs/metadata
	if [[ -d ${STAGINGDIR} ]]; then
		pushd ${STAGINGDIR} 2>&1 >/dev/null
		list=index.html
		echo "<html><head><title>Directory listing</title></head><body>" > $list
		find . -type f | sort | grep -v "/all/repo/" | grep -v index.html | sed "s#^\(.\+\)\$#<li><a href=\1>\1</a>#g" >> $list
		echo "</body></html>" >> $list
		popd 2>&1 >/dev/null
		list=""
		if [[ ! -d ${DESTINATION}/ ]]; then # remote dir, generate index
			pushd ${STAGINGDIR} 2>&1 >/dev/null
			list=/tmp/${BUILD_ID}-H${BUILD_NUMBER}.list.txt
			echo "<html><head><title>Directory listing</title></head><body>" > $list
			find . -type f | sort | grep -v "/all/repo/" | sed "s#^\.\(.\+\)\$#<li><a href=${BUILD_ID}-H${BUILD_NUMBER}\1>${BUILD_ID}-H${BUILD_NUMBER}\1</a>#g" >> $list
			echo "</body></html>" >> $list
			popd 2>&1 >/dev/null
		fi

		echo "<meta http-equiv=\"refresh\" content=\"0;url=${BUILD_ID}-H${BUILD_NUMBER}\">" > /tmp/latestBuild.html
		if [[ $1 == "trunk" ]]; then
			date; rsync -arzq --delete ${STAGINGDIR}/* $DESTINATION/builds/nightly/trunk/${BUILD_ID}-H${BUILD_NUMBER}/
			date; rsync -arzq --delete /tmp/latestBuild.html $DESTINATION/builds/nightly/trunk/
			if [[ $list ]]; then date; rsync -arzq --delete $list $DESTINATION/builds/nightly/trunk/index.html; fi
		else
			date; rsync -arzq --delete /tmp/latestBuild.html $DESTINATION/builds/nightly/${JOBNAMEREDUX}/ 
			date; rsync -arzq --delete ${STAGINGDIR}/* $DESTINATION/builds/nightly/${JOBNAMEREDUX}/${BUILD_ID}-H${BUILD_NUMBER}/
			if [[ $list ]]; then date; rsync -arzq --delete $list $DESTINATION/builds/nightly/${JOBNAMEREDUX}/index.html; fi
		fi
		rm -f /tmp/latestBuild.html $list
	fi

	if [[ $1 == "trunk" ]]; then
		date; rsync -arzq --delete ${STAGINGDIR}/all/repo/* $DESTINATION/updates/nightly/trunk/
	else
		date; rsync -arzq --delete ${STAGINGDIR}/all/repo/* $DESTINATION/updates/nightly/${JOBNAMEREDUX}/
	fi
fi
date

# generate md5sums in a single file 
if [[ ${RELEASE} == "Yes" ]] || [[ $2 == "RELEASE" ]] || [[ $1 == "RELEASE" ]]; then
	md5sumsFile=${STAGINGDIR}/all/${JOB_NAME}-md5sums-${BUILD_ID}-H${BUILD_NUMBER}.txt
	echo "# Update Site Zips" > ${md5sumsFile}
	echo "# ----------------" >> ${md5sumsFile}
	md5sum $(find . -name "*Update*.zip" | egrep -v "aggregate-Sources|nightly-Update") >> ${md5sumsFile}
	echo "  " >> ${md5sumsFile}
	echo "# Source Zips" >> ${md5sumsFile}
	echo "# -----------" >> ${md5sumsFile}
	md5sum $(find . -name "*Source*.zip" | egrep -v "aggregate-Sources|nightly-Update") >> ${md5sumsFile}
	echo " " >> ${md5sumsFile}
fi

# if destination is local, we can also generate index.html with more information
if [[ -d $DESTINATION/ ]]; then # local dir - we can generate index.html with more info
	if [[ $1 == "trunk" ]]; then
		pushd $DESTINATION/builds/nightly/trunk/ 2>&1 >/dev/null
	else
		pushd $DESTINATION/builds/nightly/${JOBNAMEREDUX}/ 2>&1 >/dev/null
	fi
	list=index.html
	rm -fr $list
	echo "<html><head><title>Directory listing</title></head><body>" > $list
	find . -type f | sort | grep -v "/all/repo/" | grep -v index.html | sed "s#^\(.\+\)\$#<li><a href=\1>\1</a>#g" >> $list
	echo "</body></html>" >> $list
	popd 2>&1 >/dev/null
fi

