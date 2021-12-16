# Hello World for Rest Assured

## About

This project was based on the maven-archetype-quickstart to generate the basic project.

For more information contact Joe.Boon@valtech.com

## Setup

Install Oracle JDK
 - Recommended: JDK v17 x64 MSI Installer for Windows `jdk-17_windows-x64_bin.msi`
 - https://www.oracle.com/java/technologies/downloads/#jdk17-windows
 - Open a new command window and confirm installation by running:
     `java --version`

Install Maven
 - Recommended: v3.8.4 Binary Zip archive `apache-maven-3.8.4-bin.zip`
 - https://maven.apache.org/download.cgi
 - Install using instructions: https://maven.apache.org/install.html
 - Open a new command window and confirm installation by running:
     `mvn --version`

Install an IDE
 - Recommended: IntelliJ IDEA Community 2021.3 Windows EXE
 - https://www.jetbrains.com/idea/download/#section=windows

## Run the tests

```shell
cd {path}/HelloWorld_RestAssured
mvn verify
```

## View the results

Either
 - Use the Run window in IntelliJ to view the test results, and select the highest level node
 > View | Tool Windows | Run _(Alt+4)_ 

or
 - Goto `HelloWorld_RestAssured\target\surefire-reports` and view the text file
 
or
 - Inspect the console output