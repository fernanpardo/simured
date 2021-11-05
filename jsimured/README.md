Jsimured
========

This is the Java implementation of the SimuRed Computer Network Simulator.

It is a Maven project developed using Netbeans IDE.

A clean build generates jar files in target directory; you just need jsimured-x.x.jar to run the app.

In the [release](release) directory you can find last released [jsimured.jar](release/jsimured.jar) and windows binaries generated with launch4j, with and without console ([jsimured_con.exe](release/jsimured_con.exe) and [jsimured.exe](release/jsimured.exe)). Just copy the jar file or any of the binaries to any place and double click to launch.

Binaries are not really necessary since any platform (Windows, Linux, MacOS) is able to execute the jar just by clicking on it. Otherwise you can run the app from command line:

    java -jar jsimured.jar

You need a JRE or JDK java installed on your computer. You can install it preferably from [Adoptium](https://adoptium.net)

Acknowledgements
----------------

- [JFreeChart](https://www.jfree.org/jfreechart/) for drawing the charts.
- launch4j for generating windows binaries.
- Apache netbeans IDE, maven, Java Adoptium JDK 11,...
- Oscar Bayo, for translating an old version from C++ to Java.

2021 Fernando Pardo
