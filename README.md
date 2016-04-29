Stunner Modelling Tool
=======================

A multi-purpose modelling tool based on [JBoss Uberfire](http://www.uberfireframework.org/).                         

Building
--------

    mvn clean install -DskipTests

Running the application
-----------------------

**Wildfly 8.x**                         

There exist a concrete distribution for Wilfly 8.x you can use, instructions [here](./wirez-distros/src/main/wildfly8/README.md).         

Another option is to run the application using GWT SuperDevMode. If you're planning more than just try the tool, 
the most comfortable and easy way is to use the GWT plugin for IntelliJ IDEA, if you just want to run it quicly you can use the command line as the following sections describe.                                  

**SuperDevMove - Running from command line**                                          

    mvn gwt:run

**SuperDevMove - Running from IntelliJ IDEA**                                          

1.- Build the project from command line ( see above *Building  - section )             
  
2.- Open the project using IntelliJ IDEA - Use option "import project from Maven"                 
  
3.- Create a new Run/Debug configuration as:                
  - *Type*: GWT configuration                  
  - *Name*: Wirez Showcase                     
  - *Use SDM*: true                  
  - *Module*: wirez-webapp             
  - *GWT Modules to load*: org.wirez.FastCompiledWirezShowcase             
  - *VM options*: 
        
        -Xmx2048m -Xms1024m -Xss1M -XX:CompileThreshold=7000 -Derrai.jboss.home=$PATH_OF_YOUR_CLONED_WIREZ_REPO/wirez-webapp/target/wildfly-8.1.0.Final
                      
  - *Dev mode parameters*: 
        
        -server org.jboss.errai.cdi.server.gwt.EmbeddedWildFlyLauncher
                      
  - *Start page*: wirez.html                  
  
  - On before launch section - Add a new "Run Maven Goal" BEFORE the existing "Make" item as:                
    - *Working directory*: 
        
            $PATH_OF_YOUR_CLONED_WIREZ_REPO/wirez-webapp
                          
    - *Goal*: 
        
            clean process-resources                 
  
Once done, you can run or debug the application using this recently created configuration.                   
  
*TIP*: While coding it's a good practice to remove application's old artifacts the GWT idea plugin working's directory. It is usually present on your home directory as `$HOME/.IntelliJIdea15/system/gwt/`.                        


Forms integration
-----------------

The branch `forms` contains the dynamic forms integration. To use it, please previously build Livespark locally from [here](https://github.com/droolsjbpm/livespark).                   
 
NOTE: You can compile it faster by using `mvn clean install -DskipTests -Dgwt.compiler.skip=true`.                     
 
Requirements
------------
* Java8+          
* Maven 3.2.5+       
* Git 1.8+        

