cmake_minimum_required(VERSION 3.16)
project(ciff_clean)

set(CMAKE_CXX_STANDARD 14)

add_executable(ciff_clean main.cpp ciff.cpp ciff.h)
