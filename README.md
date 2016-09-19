Stunner Modelling Tool
=======================

A multi-purpose modelling tool based on [JBoss Uberfire](http://www.uberfireframework.org/).                         

Documentation
-------------

All Stunner documents are shared in a public Google Docs folder [here](https://drive.google.com/open?id=0B5LZ7oQ3Bza2Qk1GY1ZPeEN6Q0E).

Showcase Docker Image
---------------------

For those not interested on building the application from sources, there exists a Docker image for the Stunner showcase available [here](https://hub.docker.com/r/romartin/stunner-showcase/).             

1.- Pull the docker image to your local            

    docker pull romartin/stunner-showcase
    
2.- Run the image                 

    docker run -p 8080:8080 -p 8001:8001 -t -i romartin/stunner-showcase

3.- Navigate into the showcase               

    http://localhost:8080/wirez ( admin/ admin )

NOTE: Please consider it's not being built for each daily snapshot, so do not expect it using always latest showcase version, will try to keep it updated.            

Building
--------

Follow these instructions for building the application from sources.            

	git clone https://github.com/romartin/wirez.git
	cd wirez/
	mvn clean install -DskipTests

Running the application
-----------------------

**Wildfly 10.x**

There exist a concrete distribution for Wilfly 10.x you can use, instructions [here](./wirez-distros/src/main/wildfly/README.md).

Another option is to run the application using GWT SuperDevMode. If you're planning more than just try the tool, 
the most comfortable and easy way is to use the GWT plugin for IntelliJ IDEA, if you just want to run it quicly you can use the command line as the following sections describe.                                  

**SuperDevMove - Running from command line**                                          

    cd wirez-showcase
    mvn clean gwt:run

**SuperDevMove - Running from IntelliJ IDEA**                                          

1.- Build the project from command line ( see above *Building  - section )             
  
2.- Open the project using IntelliJ IDEA - Use option "import project from Maven"                 
  
3.- Create a new Run/Debug configuration as:                
  - *Type*: GWT configuration                  
  - *Name*: Wirez Showcase                     
  - *Use SDM*: true                  
  - *Module*: wirez-showcase             
  - *GWT Modules to load*: org.wirez.FastCompiledWirezShowcase             
  - *VM options*: 
        
        -Xmx4g
        -Xms1g
        -Xss1M
        -XX:CompileThreshold=7000
        -Derrai.jboss.home=$PATH_OF_YOUR_CLONED_WIREZ_REPO/wirez-showcase/target/wildfly-10.0.0.Final
        -Derrai.server.classOutput=$PATH_OF_YOUR_CLONED_WIREZ_REPO/wirez-showcase/target
        -Djava.util.prefs.syncInterval=2000000
        -Dorg.uberfire.async.executor.safemode=true
        -Dgwt.watchFileChanges=false
        -Dorg.uberfire.nio.git.dir=/tmp/dir

                      
  - *Dev mode parameters*: 
        
        -server org.jboss.errai.cdi.server.gwt.EmbeddedWildFlyLauncher
                      
  - *Start page*: wirez.html                  
  
  - On before launch section - Add a new "Run Maven Goal" BEFORE the existing "Make" item as:                
    - *Working directory*: 
        
            $PATH_OF_YOUR_CLONED_WIREZ_REPO/wirez-showcase
                          
    - *Goal*: 
        
            clean process-resources                 
  
Once done, you can run or debug the application using this recently created configuration.                   
  
*TIP*: While coding it's a good practice to remove application's old artifacts the GWT idea plugin working's directory. It is usually present on your home directory as `$HOME/.IntelliJIdea15/system/gwt/`. On Macs you can find this under ~//Library/Caches/IntelliJIdea15/gwt.

Requirements
------------
* Java8+          
* Maven 3.2.5+       
* Git 1.8+        
