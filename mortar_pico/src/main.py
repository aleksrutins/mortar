import PicoRobotics
import sys
from mortar.math import aim
from mortar.ports import *

board = PicoRobotics.KitronikPicoRobotics()

board.servoWrite(TRIGGER_SERVO, 0)

while True:
    cmd = sys.stdin.readline().strip()
    parts = cmd.split(' ')
    match parts[0]:
        case 'aim':
            aim(board, float(parts[1]), float(parts[2]))
        case _:
            print("Error: unknown command " + parts[0])