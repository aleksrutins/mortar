#include <stdio.h>
#include <pico/stdlib.h>
#include <hardware/pwm.h>

int main() {
    stdio_init_all();

    gpio_set_function(15, GPIO_FUNC_PWM);

    uint slice_num = pwm_gpio_to_slice_num(0);

    char input[255];
    while(true) {
        gets(input);
        puts(input);
    }
}