cmake_minimum_required(VERSION 3.22.1)

project("baglan")

add_library(baglan SHARED gradient.cpp)

find_library(log-lib log)
find_library(jnigraphics-lib jnigraphics)

target_link_libraries(baglan ${log-lib} ${jnigraphics-lib})

