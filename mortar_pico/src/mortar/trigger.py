import PicoRobotics
import utime
from .ports import *

def fire(board: PicoRobotics.KitronikPicoRobotics):
    board.servoWrite(TRIGGER_SERVO, 90)
    utime.sleep_ms(500)
    board.servoWrite(TRIGGER_SERVO, 0)