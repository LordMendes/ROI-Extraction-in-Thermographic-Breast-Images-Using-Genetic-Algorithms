import random as r

mutation_rate = 0.5


class Cordinate:
    def __init__(self, n) -> None:
        self.int = int(n)
        self.binary = self.int_to_binary(int(self.int))
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
        self.int = int(self.binary, 2)

    def gray_to_binary(self, n):
        n = int(n, 2)
        mask = n
        while mask != 0:
            mask >>= 1
            n ^= mask
        return bin(n)[2:]

    def int_to_binary(self, n, size=10):
        binary_string = ""
        while n > 0:
            binary_string += str(n % 2)
            n //= 2

        while len(binary_string) < size:
            binary_string = "0" + binary_string

        return binary_string

    def binary_to_gray(self, n):
        n = int(n, 2)
        n ^= (n >> 1)

        return bin(n)[2:]


class Cardioid:
    def __init__(self, x, y, size) -> None:
        self.x_cordinate = Cordinate(x)
        self.y_cordinate = Cordinate(y)
        self.radius = Cordinate(size)

    def is_inside_cardioid(self, x, y):
        return ((x - self.x_cordinate.int) ** 2 * (1 + ((y - self.y_cordinate.int) / self.radius.int) ** 2) + (y - self.y_cordinate.int) ** 2 - self.radius.int ** 2 )<= 0

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
            self.radius.mutate()
