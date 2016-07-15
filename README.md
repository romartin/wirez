Stunner Modelling Tool
=======================

A multi-purpose modelling tool based on [JBoss Uberfire](http://www.uberfireframework.org/).                         

Building
--------

The project currently depends on Livespark's Forms modules, so you must previously clone and build locally [Livespark](https://github.com/droolsjbpm/livespark) as:

	git clone https://github.com/droolsjbpm/livespark.git
	cd livespark/
	mvn clean install -DskipTests -Dgwt.compiler.skip=true

Once you have Livespark artifacts in you local Maven repository, you can clone and build Stunner as:

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
        
        -Xmx2048m
        -Xms1024m
        -Xss1M
        -XX:CompileThreshold=7000
        -Derrai.jboss.home=$PATH_OF_YOUR_CLONED_WIREZ_REPO/wirez-showcase/target/wildfly-10.0.0.Final
        -Derrai.server.classOutput=$PATH_OF_YOUR_CLONED_WIREZ_REPO/wirez-showcase/target
        -Djava.util.prefs.syncInterval=2000000
        -Dorg.uberfire.async.executor.safemode=true
        -Dgwt.watchFileChanges=false
                      
  - *Dev mode parameters*: 
        
        -server org.jboss.errai.cdi.server.gwt.EmbeddedWildFlyLauncher
                      
  - *Start page*: wirez.html                  
  
  - On before launch section - Add a new "Run Maven Goal" BEFORE the existing "Make" item as:                
    - *Working directory*: 
        
            $PATH_OF_YOUR_CLONED_WIREZ_REPO/wirez-showcase
                          
    - *Goal*: 
        
            clean process-resources                 
  
Once done, you can run or debug the application using this recently created configuration.                   
  
*TIP*: While coding it's a good practice to remove application's old artifacts the GWT idea plugin working's directory. It is usually present on your home directory as `$HOME/.IntelliJIdea15/system/gwt/`.                        

Requirements
------------
* Java8+          
* Maven 3.2.5+       
* Git 1.8+        

Documentation
-------------

All Stunner documents are shared in a public Google Docs folder [here](https://drive.google.com/open?id=0B5LZ7oQ3Bza2Qk1GY1ZPeEN6Q0E).
