C++ sources of Simured
======================

This directory containts all the C++ sources of Simured. It has the following directories:
* Builder: The builder version (visual interface) is find here. It is good for Windows and partially Linux (Builder does not support Linux anymore).
* Command: In this directory the command line version (no visual interface) is found.
* Common: The common files of the simulator are here. Basically it has the cpp and h files of the cimulator core.

There are two versions (same core, different user interface and GUI):

BUILDER version
---------------

The visual interface has been programmed with Borland Builder C++ and Kylix for the Linux version (not supported anymore). In Windows and Linux subdirectories you can find the projects depending on the platform. Windows version is stable while the Linux can have strange visual behaviour.
This version is not updated anymore as there is no current compatible version of Borland C++ Builder, and I do not know how to easily port it, ideas?

COMMAND version
---------------

This is a simple front-end version (no visual interface) of the simulator with the same functionality.
It is specially interesting since it can be compiled in almost any platform with a C++ compiler.
