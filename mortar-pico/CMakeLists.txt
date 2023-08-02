cmake_minimum_required(VERSION 3.12)

include(pico_sdk_import.cmake)

project(mortar-pico VERSION 0.1 LANGUAGES C CXX ASM)

if (PICO_SDK_VERSION_STRING VERSION_LESS "1.3.0")
    message(FATAL_ERROR "Raspberry Pi Pico SDK version 1.3.0 (or later) required. Your version is ${PICO_SDK_VERSION_STRING}")
endif()

pico_sdk_init()

add_executable(mortar-pico mortar.c)
target_link_libraries(mortar-pico pico_stdlib hardware_pwm)

pico_enable_stdio_usb(mortar-pico 1)
pico_enable_stdio_uart(mortar-pico 0)

pico_add_extra_outputs(mortar-pico)

pico_set_program_url(mortar-pico "https://github.com/aleksrutins/mortar/tree/main/mortar-pico")