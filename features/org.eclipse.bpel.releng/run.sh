#!/bin/sh
#
# The eclipse directory from which eclipse will be started for building and testing. 
# This eclipse instance will be the build host
# the AntRunner application is started within this eclipse installation
#
# Reset path
#
PATH=/bin:/usr/bin
export PATH
# This is evil
export CLASSPATH=""
#
export ECLIPSE_HOME="c:/builds/eclipse-3.2.1/eclipse"
#
# Where the build will happen
export BUILD_ID="`date +'%Y%m%d'`"
export BUILD_DIRECTORY="c:/builds/org.eclipse.bpel"
#
echo "Build-ID: ${BUILD_ID}"
#
# this location, adjust for cygdrive pathing
cd `dirname $0`
CWD=`pwd | sed -e 's/^\/cygdrive\/\(.\)/\1:/g'`

# Java things
export JAVA_HOME="c:/usr/jdk15_06"
export JAVA_CMD="${JAVA_HOME}/bin/java"

# ant args like verbose for example ... (-v)
export ANT_ARGS=""

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
# basic idiot checks
#
if [ ! -f "${BUILD_XML}" ] 
then
	echo "error: Somehow I cannot find build.xml in ${ECLIPSE_HOME}/plugins/org.eclipse.pde.build_* ..."
	echo "error: please check your eclispse installation and make sure that this plugin exists and is"
	echo "error: in fact a directory. Also, check your eclipse installation ..."	
	sleep 4
	exit 2
fi
if [ ! -f "${JAVA_CMD}" ]
then
	echo "error: Somehow JAVA_CMD=${JAVA_CMD} is not a file that I can run"
	sleep 4
	exit 2
fi
#
#
echo "  Using-Java: ${JAVA_CMD}"
echo "Eclipse-Home: ${ECLIPSE_HOME}"
echo "  Build-File: ${BUILD_XML}"
#
#
echo "Running build ..."
#
${JAVA_CMD} -cp \
	"${ECLIPSE_HOME}/startup.jar" \
	org.eclipse.core.launcher.Main \
	-application org.eclipse.ant.core.antRunner \
	-buildfile "${BUILD_XML}" \
	-data "${BUILD_DIRECTORY}/workspace-build" \
	-Dconfigs="*,*,*" \
	-Dbuilder="${CWD}" \
	-DbuildId="${BUILD_ID}" \
	-DbuildDirectory="${BUILD_DIRECTORY}" \
	-DbaseLocation="${ECLIPSE_HOME}" ${ANT_ARGS}
#
#
sleep 4
#
exit 0