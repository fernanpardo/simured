# Linux and Windows (MinGW32)
GCC=g++

# DOS (JGPP):
#GCC=gpp


RED_DIR=../Common

INC=$(RED_DIR)

OPTS1= -x c++ -O2
OPTS2= -Wall

# Static linking for portable executable (specially insteresting for windows systems without MinGW installed)
OPTS3= -static

OPTS=$(OPTS1) $(OPTS2) $(OPTS3)

# uncomment the appropriate line (lpthread.dll is for windows only):
all:
	$(GCC) $(OPTS) -I $(INC) simured_cmd.cpp $(RED_DIR)/red.cpp -o simured_cmd -lm -lpthread.dll
#	$(GCC) $(OPTS) -I $(INC) simured_cmd.cpp $(RED_DIR)/red.cpp -o simured_cmd -lm -lpthread
