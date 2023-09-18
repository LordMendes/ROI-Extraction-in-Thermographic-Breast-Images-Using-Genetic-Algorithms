import math
import os
import time
import individual as ind
import cardioid as c
import cv2
import random as r
from math import floor
import sys

TRIAL = 4


class GA:
    # CONSTANTS
    decimalArraySize = 10
    POPULATION_SIZE = 60
    GENERATIONS = 50
    ELITE_PERCENTAGE = 0.1
    mR = 0.02

    def __init__(self):
        self.population = []  # LISTA DE INDIVIDUOS

    def getIndividual(self, pos):
        return self.population[pos]

    def getTop5(self, tops):
        tops.extend(self.population[-5:])

    def sort_population(self, individual):
        return individual.get_score()

    def initPop(self, img):
        self.population.clear()
        for i in range(self.POPULATION_SIZE):
            self.population.append(ind.Individual(img))
        self.fitness_all()
        self.population.sort(key=self.sort_population, reverse=True)

    def slice_binary(self, binary, point):
        first_half = binary[:point]
        second_half = binary[point:]
        return first_half, second_half

    def fitness_all(self):
        for i in self.population:
            i.fitness()

    # def fitness_all(self):
    #     for i in self.population:
    #         t = threading.Thread(target=i.fitness)
    #         t.start()
    #     for i in self.population:
    #         t.join()

    def gray_to_decimal(self, gray_code):
        n = len(gray_code)
        decimal = 0
        for i in range(n):
            decimal += 2 ** (n - i - 1) * int(gray_code[i])
        return decimal

    def crossover(self, a1, a2, img):
        decimalArraySize = self.decimalArraySize

        # Calculate random points
        x_point, y_point, r_point = (
            r.randint(0, decimalArraySize - 1),
            r.randint(0, decimalArraySize - 1),
            r.randint(0, decimalArraySize - 1),
        )

        # Slice and concatenate the binary strings
        child1_x = self.gray_to_decimal(
            a1.cardioid.x_cordinate.gray_code[x_point:] + a2.cardioid.x_cordinate.gray_code[:x_point])
        child2_x = self.gray_to_decimal(
            a2.cardioid.x_cordinate.gray_code[x_point:] + a1.cardioid.x_cordinate.gray_code[:x_point])

        child1_y = self.gray_to_decimal(
            a1.cardioid.y_cordinate.gray_code[y_point:] + a2.cardioid.y_cordinate.gray_code[:y_point])
        child2_y = self.gray_to_decimal(
            a2.cardioid.y_cordinate.gray_code[y_point:] + a1.cardioid.y_cordinate.gray_code[:y_point])

        child1_r = self.gray_to_decimal(
            a1.cardioid.size.gray_code[r_point:] + a2.cardioid.size.gray_code[:r_point])
        child2_r = self.gray_to_decimal(
            a2.cardioid.size.gray_code[r_point:] + a1.cardioid.size.gray_code[:r_point])

        # Create the new individuals directly
        child1 = ind.Individual(img, c.Cardioid(child1_x, child1_y, child1_r))
        child2 = ind.Individual(img, c.Cardioid(child2_x, child2_y, child2_r))

        return child1, child2

    def tournament(self):
        a1 = self.population[r.randint(0, self.POPULATION_SIZE - 1)]
        a2 = self.population[r.randint(0, self.POPULATION_SIZE - 1)]
        if a1.get_score() > a2.get_score():
            return a1
        else:
            return a2

    def print_best(self):
        print(self.population[0].get_score())

    def get_best_score(self):
        return self.population[0].get_score()

    def print_best_info(self):
        return self.population[0].print_info()

    def get_best_info(self):
        return self.population[0].get_info()

    def print_all(self):
        for i in self.population:
            print("Score: ", i.get_score())

    def mutate(self):
        for i in self.population:
            if r.random() < self.mR:
                i.mutate()


def run(img, trial):
    ga = GA()
    generation_info = ""
    TRIAL = trial
    folder_name = f'tests/timed/{TRIAL}'
    file_name = f'{folder_name}/generation_info.txt'

    # Check if the folder exists, and create it if it doesn't
    if not os.path.exists(folder_name):
        os.makedirs(folder_name)
    if not os.path.exists(file_name):
        with open(file_name, 'w'):  # Create the file in write mode
            pass  # Do nothing

    ga.initPop(img)

    with open(file_name, 'a') as file:
        generation_info = f"============= GENERATION 0 =============\n"
        generation_info += f"Population size : {len(ga.population)}\n"
        generation_info += "================== BEST ==================\n"
        generation_info += ga.get_best_info()
        generation_info += "==========================================\n"
        file.write(generation_info)

    print(generation_info)
    ga.population[0].save_to_file(folder_name, 'gen_0')
    for i in range(ga.GENERATIONS):
        start_time = time.time()
        new_population = []
        new_population = ga.population[:floor(
            ga.POPULATION_SIZE*ga.ELITE_PERCENTAGE)]
        while(len(new_population) < ga.POPULATION_SIZE):
            a1 = ga.tournament()
            a2 = ga.tournament()
            child1, child2 = ga.crossover(a1, a2, img)

            new_population.append(child1)
            new_population.append(child2)
        ga.population = new_population
        ga.mutate()
        ga.fitness_all()
        ga.population.sort(key=ga.sort_population, reverse=True)

        with open(file_name, 'a') as file:
            generation_info = f"============= GENERATION {i+1} =============\n"
            generation_info += f"Population size : {len(ga.population)}\n"
            generation_info += "================== BEST ==================\n"
            generation_info += ga.get_best_info()
            generation_info += "================= TIMING =================\n"
            generation_info += f"Time to run: {math.floor(time.time() - start_time)}s\n"
            generation_info += "==========================================\n"
            file.write(generation_info)

        print(generation_info)
        ga.population[0].save_to_file(folder_name, f'gen_{i+1}')
    return ga.population[0]


if __name__ == '__main__':
    # Check if there are command-line arguments

    start_time = time.time()
    if len(sys.argv) > 1:
        # The first command-line argument (sys.argv[0]) is the script name, so the actual arguments start from sys.argv[1]
        argument = sys.argv[1]
        print("Trial number:", argument)
    else:
        print("No trial number provided.")

    img = cv2.imread("img6.jpg")
    best_fit_individual = run(img, argument)
    end_time = time.time()
    print("Trial number:", argument)
    print("Time to run:", end_time - start_time)
    # best_fit_individual.draw()

    cv2.waitKey(0)
