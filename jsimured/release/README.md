MULTIPLATFORM JAR (Recommended)
---

jsimured.jar (Multiplatform jar file)

## Interactive mode with Graphical User Interface (GUI)
* Double click jar file in explorer (Windows, Linux, MacOS) or
* From console:

		java -jar jsimured.jar

## Batch mode
*	From command line using a configuration file (simured.conf) and storing the results in result.csv:

		java -jar jsimured.jar -c < simured.conf > result.csv


WINDOWS EXE (just in case jar file is not an option)
---
EXE Windows files have been generated with launch4j directly from the jar file (http://launch4j.sourceforge.net/)

Both exe versions have been generated with with launch4j, selecting either GUI or Console in the Header section.

- jsimured.exe (Windows executable file, with no console)
- jsimured_con.exe (Windows executable file with console, better for command line execution, or to see log/error messages)

## Interactive mode (GUI)
* Double click in exe file or type in a console:
## Batch mode
*	From command line using a configuration file (simured.conf) and storing the results in result.csv:

		jsimured_con.exe -c < simured.conf > result.csv
		jsimured.exe -c < simured.conf > result.csv
* Both options are fine though the second one will not show any message in case of errors.


The only difference between jsimured.exe and jsimured_con.exe is that the console is always shown in the _con version, while it is never shown in the no _con version, even when launched from a console.
