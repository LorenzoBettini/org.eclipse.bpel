You can run the build from your checked out location of the org.eclipse.bpel.releng
plugin. Or you can just checkout that plugin and run the run.sh script from there.

Since the run.sh is a shell script (bourne shell script), you might need to install cygwin in order
to run it on Windows. Linux, OSX, and others Unix flavors should "just run". 
   
   % ./run.sh --help
usage: run.sh  [--java-home=dir] [--eclipse-home=dir] [--skip-build]
        [--skip-site] [--build-directory=dir]

      JAVA_HOME=C:\usr\jdk16_1
   ECLIPSE_HOME=/usr/eclipse
BUILD_DIRECTORY=/cygdrive/c/Documents and Settings/..../builds/org.eclipse.bpel

There are 3 things that the build script needs:

1) Your java home directory. This will be used to start Eclipse in headless mode and also 
construct the appropriate minimum java environment (J2SE-1.5, J2SE-1.6, etc). If this option is not
passed then the JAVA_HOME environment variable is used as default. If that is not passed, some
other default will probably be taken though who knows to where this will lead. 

2) Eclipse home is the eclipse instance that will be used in the build and run headless. It's the 
directory where the "plugins" and "features" directory is.

3) The build directory is where the build will take place. All the scratch work will be done there,
the workspace log will be there as well. 

4) The build script attempts to build a site suitable for update manager deployment. You can 
skip the build or skip the site creation by passing the obvious arguments to the script.

Problems with build
-------------------
0) Look at build.properties. There are properties in there that will become obvious.

1) The file build.properties contains some values that a careless user may override. If you
are having classpath issues someone may have overridden the location
of a JDK for a particular java compliance level (J2SE-1.5 for example). These are called
JAVA COMPILER OPTIONS in the build.properties. The JDKs used for compilation are specified
in there, unless they are not specified, and then they default to the one being used to execute 
Eclipse in the headless mode. It's a little screwy, but it makes sense in the end.

2) The build script will pull the right version (tag) of the plugins from CVS. Please note
that this information is currently specified in the build.properties file. There is no way 
to change it from the command line. Strangly, you will need the command line version of CVS
and the connectivity the Eclipse.org repository. If you are behind a proxy you will have to use 
some magic to make this work. On Windows I suggest ProxyCap.

  