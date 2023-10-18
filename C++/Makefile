# Define the compiler to use
CXX = g++

# Define any compile-time flags
CXXFLAGS = -std=c++11 -I/usr/include/opencv4 -O2 -pthread 

# Define any directories containing header files other than /usr/include
INCLUDES = 

# Define any libraries to link into executable
LIBS = -L/usr/lib -lopencv_core -lopencv_highgui -lopencv_imgcodecs -lQt6Widgets -lQt6Test -lQt6OpenGLWidgets -lQt6Gui -lQt6Core -lprofiler

# Define the C++ source files
SRCS = main.cpp Cardioid.cpp GA.cpp Individual.cpp

# Define the C++ header files
HDRS = Cardioid.hpp GA.hpp Helper.hpp Individual.hpp

# Define the C++ object files 
OBJS = $(SRCS:.cpp=.o)

# Define the output file name
TARGET = output

.PHONY: depend clean

all: $(TARGET)
	@echo  Simple compiler named my_compiler has been compiled

$(TARGET): $(OBJS) 
	$(CXX) $(CXXFLAGS) $(INCLUDES) -o $(TARGET) $(OBJS) $(LIBS)

.cpp.o:
	$(CXX) $(CXXFLAGS) $(INCLUDES) -c $<  -o $@

clean:
	$(RM) *.o *~ $(TARGET)

depend: $(SRCS)
	makedepend $(INCLUDES) $^

# DO NOT DELETE THIS LINE -- make depend needs it
