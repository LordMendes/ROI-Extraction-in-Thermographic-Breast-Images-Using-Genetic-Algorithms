# from cmath import sqrt
from math import atan2, sin, pi, sqrt
import random as r

mutation_rate = 0.5
ARRAY_SIZE = 10

class Cordinate:
    def __init__(self, n) -> None:
        self.decimal = int(n)
        self.binary = self.int_to_binary(int(self.decimal))
        self.gray_code = self.binary_to_gray(self.binary)

    def flip_bit(self, binary, index):
        binary_list = list(binary)
        if binary_list[index] == "0":
            binary_list[index] = "1"
        else:
            "0"
        return ''.join(binary_list)

    def mutate(self):
        mutation_index = r.randint(0, len(self.gray_code) - 1)

        mutated_gray_code = self.flip_bit(str(self.gray_code), mutation_index)
        self.gray_code = mutated_gray_code
        self.binary = self.gray_to_binary(self.gray_code)
        self.decimal = int(self.binary, 2)

    def gray_to_binary(self, n):
        n = int(n, 2)
        mask = n
        while mask != 0:
            mask >>= 1
            n ^= mask
        return bin(n)[2:]

    def int_to_binary(self, n):
        binary_string = ""
        while n > 0:
            binary_string += str(n % 2)
            n //= 2

        while len(binary_string) < ARRAY_SIZE:
            binary_string = "0" + binary_string

        return binary_string

    def binary_to_gray(self, n):
        n = int(n, 2)
        n ^= (n >> 1)
        gray_code = bin(n)[2:]
        while len(gray_code) < ARRAY_SIZE:
            gray_code = "0" + gray_code

        return gray_code


class Cardioid:
    def __init__(self, x, y, size) -> None:
        self.x_cordinate = Cordinate(x)
        self.y_cordinate = Cordinate(y)
        self.size = Cordinate(size)

    def is_inside_cardioid(self, x, y):
        size = self.size.decimal
        x_c = self.x_cordinate.decimal
        y_c = self.y_cordinate.decimal
        r = size / 2
        # distance = math.sqrt((x - x_c) ** 2 + (y - y_c) ** 2)
        theta = atan2(y - y_c, x - x_c)
        cardioid_equation = ((x - x_c) ** 2 + (y - y_c) ** 2) <= r ** 2 * (1 - sin(theta))
        return cardioid_equation
    

    
    def will_mutate(self, mutation_rate):
        return r.uniform(0, 1) < mutation_rate

    def mutate(self):
        has_x_mutated = self.will_mutate(mutation_rate)
        has_y_mutated = self.will_mutate(mutation_rate)
        has_radius_mutated = self.will_mutate(mutation_rate)
        if has_x_mutated:
            self.x_cordinate.mutate()
        if has_y_mutated:
            self.y_cordinate.mutate()
        if has_radius_mutated:
            self.size.mutate()
    
