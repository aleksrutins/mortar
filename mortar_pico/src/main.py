import PicoRobotics
import sys
from mortar.math import aim

board = PicoRobotics.KitronikPicoRobotics()

while True:
    cmd = sys.stdin.readline().strip()
    parts = cmd.split(' ')
    match parts[0]:
        case 'aim':
            aim(board, float(parts[1]), float(parts[2]))
        case _:
            print("Error: unknown command " + parts[0])