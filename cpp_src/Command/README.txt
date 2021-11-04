---------------------------
(SEE ENGLISH VERSION BELOW)
---------------------------

La versión para ser ejecutada por el intérprete de comandos (Unix shell o 
DOS) se llama simured_cmd. Los parámetros de la simulación se toman de la 
entrada estándar y existen valores por defecto para todos ellos. Lo normal es 
redireccionar la entrada estándar para que tome los valores de un fichero de 
configuración. Si no se redirecciona la entrada estándar habrá que introducir 
los valores a mano y pulsar fin de fichero al terminar (Ctrl-D).

Algunos valores estadísticos y los mensajes de simulación se muestran por la 
salida estándar de error. Los resultados siguiendo el formato CSV (campos 
separados por comas) se sacan por la salida estándar, por lo que resulta 
interesante redireccionar la salida estándar a algún fichero para luego 
importalo a cualquier herramienta gráfica. Un ejemplo de llamada al programa 
desde el intérprete de comandos sería:

simured_cmd < simured.conf > resultados.csv

FUENTES
=======

Las fuentes de esta versión están la página del simulador 
(http://simured.uv.es). Estas fuentes consisten en un fichero tar 
comprimido que se puede compilar con el gcc tanto en Unix, en DOS 
(djgpp) o en Windows (minWG). Es posible que funcione con otros compiladores pues el código es 
bastante estándar aunque seguramente no al 100%.

Los ficheros que se tienen son los siguientes:

- simured_cmd.cpp Son las fuentes del front-end basado en línea de comandos para el simulador que se encuentra en red.cpp. 

- red.cpp Es el código fuente del simulador. Aquí se definen todas las clases y métodos para el movimiento de los paquetes por la red. Tanto la versión visual como la de texto comparten estas mismas fuentes.

- red.h Es el fichero de cabecera para red.cpp.
- simured_cmd Es el ejecutable para Linux (seguramente necesita varias 
bibliotecas).

- simured_cmd.exe Es el ejecutable para DOS (seguramente no necesita ninguna 
biblioteca dinámica).

- simured.conf Es un fichero de configuración de ejemplo con todos los 
parámetros que necesita el simulador.

- Makefile Con este Makefile sólo se necesita hacer "make" y compila todo, 
tanto en Linux como en DOS.

- README.txt This file.

- simured.pdf El manual en castellano.

- trace.trc Un fichero de trazas de ejemplo.

La instalación consiste en copiar el ejecutable en cualquier directorio y 
nada más. Si se hacen cambios en las fuentes del programa basta con hacer
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

