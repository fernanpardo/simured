---------------------------
(SEE ENGLISH VERSION BELOW)
---------------------------

La versi�n para ser ejecutada por el int�rprete de comandos (Unix shell o 
DOS) se llama simured_cmd. Los par�metros de la simulaci�n se toman de la 
entrada est�ndar y existen valores por defecto para todos ellos. Lo normal es 
redireccionar la entrada est�ndar para que tome los valores de un fichero de 
configuraci�n. Si no se redirecciona la entrada est�ndar habr� que introducir 
los valores a mano y pulsar fin de fichero al terminar (Ctrl-D).

Algunos valores estad�sticos y los mensajes de simulaci�n se muestran por la 
salida est�ndar de error. Los resultados siguiendo el formato CSV (campos 
separados por comas) se sacan por la salida est�ndar, por lo que resulta 
interesante redireccionar la salida est�ndar a alg�n fichero para luego 
importalo a cualquier herramienta gr�fica. Un ejemplo de llamada al programa 
desde el int�rprete de comandos ser�a:

simured_cmd < simured.conf > resultados.csv

FUENTES
=======

Las fuentes de esta versi�n est�n la p�gina del simulador 
(http://simured.uv.es). Estas fuentes consisten en un fichero tar 
comprimido que se puede compilar con el gcc tanto en Unix, en DOS 
(djgpp) o en Windows (minWG). Es posible que funcione con otros compiladores pues el c�digo es 
bastante est�ndar aunque seguramente no al 100%.

Los ficheros que se tienen son los siguientes:

- simured_cmd.cpp Son las fuentes del front-end basado en l�nea de comandos para el simulador que se encuentra en red.cpp. 

- red.cpp Es el c�digo fuente del simulador. Aqu� se definen todas las clases y m�todos para el movimiento de los paquetes por la red. Tanto la versi�n visual como la de texto comparten estas mismas fuentes.

- red.h Es el fichero de cabecera para red.cpp.
- simured_cmd Es el ejecutable para Linux (seguramente necesita varias 
bibliotecas).

- simured_cmd.exe Es el ejecutable para DOS (seguramente no necesita ninguna 
biblioteca din�mica).

- simured.conf Es un fichero de configuraci�n de ejemplo con todos los 
par�metros que necesita el simulador.

- Makefile Con este Makefile s�lo se necesita hacer "make" y compila todo, 
tanto en Linux como en DOS.

- README.txt This file.

- simured.pdf El manual en castellano.

- trace.trc Un fichero de trazas de ejemplo.

La instalaci�n consiste en copiar el ejecutable en cualquier directorio y 
nada m�s. Si se hacen cambios en las fuentes del programa basta con hacer
"make" para generar el nuevo ejecutable. Si se usa cualquier otro compilador
distingo al gcc basta con modificar el Makefile para apartarlo al nuevo.


------------------------------
ENGLISH VERSION (simplified :-)
------------------------------

Configuration parameters can be stored in any file (say simured.conf).
The simulator can be invoked as follows:

simured_cmd < simured.conf > resultados.csv

SOURCES
=======

Sources can be downloaded from simered web page (http://tapec.uv.es/simured). The sources are included in a single tar file.

To untar: tar xvfz simured_cmd.tgz

These are the files included in the tar:

- simured_cmd.cpp It is the source code of the simulator front-end. It tooks all methods included in red.cpp to perform simulations. 

- red.cpp It is the source code for the internals of the simulator itself. All classes and methods for the simulator are defined in this file. This source is the shared between this version and the visual.

- red.h It is the header file of red.cpp.

- simured_cmd It is the binary executable for Linux (486).

- simured_cmd.exe It is the binary executablefor DOS.

- simured.conf It is a sample configuration file will all parameter the simulator may ask for.

- Makefile Just doing "make" under Linux or DOS (djgpp) compile the source and generate executable.

- README.txt This text file.

- simured.pdf User Manual (Spanish).

- trace.trc A trace sample file.

INSTALATION: copy executable file where you want.
RECOMPILATION: "make" or "make all".

