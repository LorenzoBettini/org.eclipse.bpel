#!/bin/sh
#
# The eclipse directory from which eclipse will be started for building and testing. 
# This eclipse instance will be the build host
# the AntRunner application is started within this eclipse installation
#
# 
# Reset path
PATH=/bin:/usr/bin
export PATH
# classpath is evil
export CLASSPATH=""

#
#
# ----------------- change these ------------------------------
# or use the arguments to this script
#
export ECLIPSE_HOME="${ECLIPSE_HOME:-/usr/eclipse}"
export JAVA_HOME="${JAVA_HOME:-/usr/java/jdk1.5.0_11}"
export BUILD_DIRECTORY="${BUILD_DIRECTORY:-$HOME/builds/org.eclipse.bpel}"
# --------------------------------------------------------------
export STARTUP_JAR="${ECLIPSE_HOME}/startup.jar"
#
function Usage {
    echo "usage: `basename $0`  [--java-home=dir] [--eclipse-home=dir] [--skip-build]" >&2
    echo "        [--skip-site] [--build-directory=dir]" >&2
    echo " " >&2
    echo "      JAVA_HOME=${JAVA_HOME}" >&2
    echo "   ECLIPSE_HOME=${ECLIPSE_HOME}" >&2
    echo "BUILD_DIRECTORY=${BUILD_DIRECTORY}" >&2
    exit 2
}

skipBuild=0
skipSite=0
#
#  arguments
#
while [ $# -gt 0 ]; do
    case $1 in
        --eclipse-home)
           export ECLIPSE_HOME="$2"
           shift
           ;;    
        --eclipse-home=*)
            export ECLIPSE_HOME="${1/--eclipse-home=}"
            ;;

        --java-home)
            export JAVA_HOME="$2"
            shift
            ;;

        --java-home=*)
            export JAVA_HOME="${1/--java-home=}"
            ;;
            
        --skip-build) 
            skipBuild=1
            ;;

        --skip-site)
            skipSite=1
            ;;

        --build-directory)
            export BUILD_DIRECTORY="$2"
            shift
			;;

        --build-directory=*)            
            export BUILD_DIRECTORY="${1/--build-directory=}"
            ;;

        --help| -h|-he|-hel|-help) 
        	Usage
        	exit 1
        	;;
        --)
        	shift
        	break
        	;;        
   esac
   shift
done
#
#
#
# Where the build will happen
export BUILD_ID="`date +'%Y%m%d'`"
#
echo "Build-ID: ${BUILD_ID}"
#
# this location, adjust for cygdrive pathing
cd `dirname $0`
CWD=`pwd | sed -e 's/^\/cygdrive\/\(.\)/\1:/g'`

# Java things
export JAVA_CMD="${JAVA_HOME}/bin/java"

# ant args like verbose for example ... (-v)
# export ANT_ARGS="-v -debug"

# reset ant command line args
export ANT_CMD_LINE_ARGS="-noclasspath"

# Find the build.xml in the org.eclipse.pde.build_* plugin
export BUILD_XML=""
for d in `find ${ECLIPSE_HOME}/plugins -name 'org.eclipse.pde.build_*.*.*' -a -type d`
do
	f="${d}/scripts/build.xml"
	
	if [ ! -f "${f}" ]
	then
		continue
	fi	
	if [ ! -z "${BUILD_XML}" ]
	then
		echo "warning: Hmmmm, looks like you have more then 1 version of this. Using the last one"		
	fi
	echo "Build-File: ${f}"	
	BUILD_XML=${f}	
done
#
# http://wiki.eclipse.org/index.php/Equinox_Launcher
#
if [ ! -f "${STARTUP_JAR}" ]
then
	for f in `find ${ECLIPSE_HOME}/plugins -name 'org.eclipse.equinox.launcher_*.*.*.jar' -a -type f`
	do		
		echo "Equinox-Launcher-JAR: ${f}"	
		STARTUP_JAR=${f}
	done
fi

# basic idiot checks
#
if [ ! -f "${BUILD_XML}" ] 
then
	echo "error: Somehow I cannot find build.xml in ${ECLIPSE_HOME}/plugins/org.eclipse.pde.build_* ..."
	echo "error: please check your eclipse installation and make sure that this plugin exists and is"
	echo "error: in fact a directory. Also, check your eclipse installation ..."	
	exit 2
fi
if [ ! -f "${JAVA_CMD}" ]
then
	echo "error: Somehow JAVA_CMD=${JAVA_CMD} is not a file that I can run"
	exit 2
fi
#
#
echo "+------------------------------------------------------------------+"
echo "Current-Path: ${CWD}"
echo "  Using-Java: ${JAVA_CMD}"
echo "Eclipse-Home: ${ECLIPSE_HOME}"
echo "  Build-File: ${BUILD_XML}"
echo " Startup-JAR: ${STARTUP_JAR}"
echo "+------------------------------------------------------------------+"
#
#
if [ $skipBuild -eq 0 ]
then
    echo "Running build ..."
	${JAVA_CMD} -cp \
	"${STARTUP_JAR}" \
	org.eclipse.core.launcher.Main \
	-application org.eclipse.ant.core.antRunner \
	-buildfile "${BUILD_XML}" \
	-data "${BUILD_DIRECTORY}/workspace-build" \
	-Dconfigs="*,*,*" \
	-Dbuilder="${CWD}" \
	-DbuildId="${BUILD_ID}" \
	-DbuildDirectory="${BUILD_DIRECTORY}" \
	-DbaseLocation="${ECLIPSE_HOME}" ${ANT_ARGS}
else 
    echo "Skipping build ..."
fi
#
#
echo "+--------------------------------------------------------+"
#
if [ $? -eq 0 -a $skipSite -eq 0 ]
then
	echo "Attempting to build site ..."
	cd $BUILD_DIRECTORY
	for n in `/bin/ls -1d *${BUILD_ID}*`
	do
	     echo $n
	     if [ ! -d $n ]
	     then 
	     	continue
	     fi	 
	     loc=$n
	     break	     
	done
	#
	if [ ! -d "$loc" ]
	then 
	    echo "I cannot find the build, cannot make the site ... sorry"
	    exit 4
	fi
    echo "Will use $loc as the build since buildId=${BUILD_ID}"
	siteDir="$BUILD_DIRECTORY/site-$loc"	
	test -d $siteDir || mkdir $siteDir
	cd $siteDir
	featureZip="${BUILD_DIRECTORY}/${loc}/org.eclipse.bpel.feature-${BUILD_ID}.zip"
	echo "Trying to unzip $featureZip into $siteDir"
	unzip $featureZip 
	if [ $? -ne 0 ]
	then 
	    echo "Failed to unzip the BPEL feature ..."
	    exit 5
	fi
	#
	test -d plugins && echo "Removing features from $siteDir" && rm -rf plugins	
	test -d features && echo "Removing features from $siteDir" && rm -rf features
	test -d eclipse && mv eclipse/* . && rm -rf eclipse
	siteSkel="$CWD/../org.eclipse.bpel.site"
	
	if [ -d $siteSkel ]
	then
	     cp -prfv $siteSkel/web .
	     cp -pfv $siteSkel/* .
	     for d in `find $siteDir -name CVS -a -type d -print`
	     do
	         echo "Removing $d  ..." && /bin/rm -fr $d
	     done
	     
	     # edit the site.xml
	     echo "+-------------------------------------------------+"
	     echo "The site is ready in $siteDir"
	     echo "Note: if you want this to be a local site, not hosted on eclipse.org, then "
	     echo "      you need to edit site.xml and remove the references to all the urls in it"
	     echo "      which includes the mirrorsURL as well."
	     echo "+-------------------------------------------------+"	     
	else
	     echo "Cannot find $siteSkel, so I cannot copy the site.xml and other items needed by the update site"
	     echo "These reside in the org.eclipse.bpel.site feature in CVS... you'd have to copy them manually"
	fi
	
else 
    echo "Skipping build site ..."
fi	
#    
#
exit 0