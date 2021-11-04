Original jar file has to be copied from ../store after being clean compiled and store-packaged. See Readme.txt file in the root of jsimured.

MULTIPLATFORM JAR

jsimured.jar (Multiplatform jar file)

USAGE:
Interactive mode:
	double click in jar file (if supported by your OS) or type in a console:
	java -jar jsimured.jar
Command mode:
	java -jar jsimured.jar -c < simured.conf > result.csv


WINDOWS EXE

EXE Windows files have been generated with launch4j directly from the jar file (http://launch4j.sourceforge.net/)
Both exe versions have been generated with with launch4j, selecting either GUI or Console in the Header section.

- jsimured.exe (Windows executable file, with no console)

USAGE:
Interactive mode (without console):
	double click in exe file or type in a console:
	jsimured.exe
Command mode (It works fine in this mode though it is not recommended due to the lack of messages during execution):
	jsimured.exe -c < simured.conf > result.csv

	
- jsimured_con.exe (Windows executable file with console, better for command line execution)

The only difference between jsimured.exe and jsimured_con.exe is that the console is always shown in the _con version, while  it is never shown in the no _con version, even when launched from a console.

