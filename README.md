Stunner Modelling Tool
=======================

A multi-purpose modelling tool base on [JBoss Uberfire](http://www.uberfireframework.org/).                         

Usage
-----

While the development is in progress, you have to build a custom lienzo-core artifact by running:           

    git clone https://github.com/romartin/lienzo-core.git lienzo-core-romartin
    cd lienzo-core-romartin
    git checkout -b stunner remotes/origin/stunner
    mvn clean install -DskipTests
    
This build process generates a custom lienzo-core artifact with suffix `-stunner`, which is only used by this tool, so you will not hit with Maven conflicts if using lienzo in other areas. 
Once done, you can build Stunner as:

    mvn clean install -DskipTests
    
Requirements
------------
* Java8+          
* Maven 3.2.5+       
* Git 1.8+        

