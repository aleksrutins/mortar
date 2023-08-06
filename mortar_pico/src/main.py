import PicoRobotics
import sys
from mortar.math import aim
from mortar.trigger import fire
from mortar.ports import *

board = PicoRobotics.KitronikPicoRobotics()

board.servoWrite(BASE_SERVO, 90)
board.servoWrite(TRIGGER_SERVO, 0)

while True:
    cmd = sys.stdin.readline().strip()
    parts = cmd.split(' ')
    match parts[0]:
        case 'ready':
            print("board ready")
        case 'aim':
            aim(board, float(parts[1]), float(parts[2]))
        case 'fire':
            fire(board)    
        case _:
            print("Error: unknown command " + parts[0])