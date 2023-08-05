from math import *
import PicoRobotics
from .ports import *

# Muzzle velocity, ft/s
VELOCITY = 10

def aim(board: PicoRobotics.KitronikPicoRobotics, base_angle: float, distance: float):
    g = 32.18503937 # 9.81 m/s in ft/s
    pitch = atan((VELOCITY**2 + sqrt(VELOCITY**4 - (g**2)*(distance**2))) / g*distance)
    board.servoWriteRadians(BASE_SERVO, base_angle)
    board.servoWriteRadians(PITCH_SERVO, pitch)