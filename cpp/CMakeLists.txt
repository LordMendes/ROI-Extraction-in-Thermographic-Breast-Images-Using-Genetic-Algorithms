cmake_minimum_required(VERSION 3.12)
project(GA)

# Find OpenCV package
find_package(OpenCV REQUIRED)

# Set C++ standard to C++17
set(CMAKE_CXX_STANDARD 17)

# Add executable and specify source files
add_executable(GA GA.cpp Individual.cpp Cardioid.cpp Coordinate.cpp)

# Include OpenCV libraries
target_include_directories(GA PRIVATE ${OpenCV_INCLUDE_DIRS})

# Link OpenCV libraries
target_link_libraries(GA ${OpenCV_LIBS})

# Enable C++ filesystem library
target_compile_options(GA PRIVATE -lstdc++fs)

# Set custom compiler flags (if needed)
# target_compile_options(GA PRIVATE -Wall -Wextra)

# Optionally, set the output directory for the binary
# set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/bin)
