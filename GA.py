import individual as ind
import cardioid as c
import cv2
import random as r
from math import ceil, floor


class GA:
    # CONSTANTS
    decimalArraySize = 10
    POPULATION_SIZE = 30
    GENERATIONS = 30
    ELITE_PERCENTAGE = 0.1
    mR = 0.02
    cR = 0.7

    imagem = "img6"  # NOME DA IMAGEM QUE O GA RODAR�

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

    def gray_to_decimal(self, gray_code):
        n = len(gray_code)
        decimal = 0
        for i in range(n):
            decimal += 2 ** (n - i - 1) * int(gray_code[i])
        return decimal

    def crossover(self, a1, a2, img):
        # Choose a random point in the binary string.
        x_point = r.randint(0, self.decimalArraySize - 1)
        y_point = r.randint(0, self.decimalArraySize - 1)
        r_point = r.randint(0, self.decimalArraySize - 1)
        # Slice the binary string in the random point.
        a_x1 = a1.cardioid.x_cordinate.gray_code[x_point:]
        a_x2 = a1.cardioid.x_cordinate.gray_code[:x_point]

        a_y1 = a1.cardioid.y_cordinate.gray_code[y_point:]
        a_y2 = a1.cardioid.y_cordinate.gray_code[:y_point]

        a_r1 = a1.cardioid.size.gray_code[r_point:]
        a_r2 = a1.cardioid.size.gray_code[:r_point]

        b_x1 = a2.cardioid.x_cordinate.gray_code[x_point:]
        b_x2 = a2.cardioid.x_cordinate.gray_code[:x_point]

        b_y1 = a2.cardioid.y_cordinate.gray_code[y_point:]
        b_y2 = a2.cardioid.y_cordinate.gray_code[:y_point]

        b_r1 = a2.cardioid.size.gray_code[r_point:]
        b_r2 = a2.cardioid.size.gray_code[:r_point]
        # Create the new binary strings.
        child1_x = self.gray_to_decimal(a_x1 + b_x2)
        child2_x = self.gray_to_decimal(b_x1 + a_x2)

        child1_y = self.gray_to_decimal(a_y1 + b_y2)
        child2_y = self.gray_to_decimal(b_y1 + a_y2)

        child1_r = self.gray_to_decimal(a_r1 + b_r2)
        child2_r = self.gray_to_decimal(b_r1 + a_r2)

        cardioid1 = c.Cardioid(child1_x, child1_y, child1_r)
        cardioid2 = c.Cardioid(child2_x, child2_y, child2_r)

        # Create the new individuals.
        child1 = ind.Individual(img, cardioid1)
        child2 = ind.Individual(img, cardioid2)

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

    def print_all(self):
        for i in self.population:
            print("Score: ", i.get_score())

    def mutate(self):
        for i in self.population:
            if r.random() < self.mR:
                i.mutate()


def run(img):
    ga = GA()
    ga.initPop(img)
    print("==========================================")
    print("Generation ", 0)
    print("Population size : ", len(ga.population))
    # print("All :")
    # ga.print_all()
    print("==========================================")
    print("best :")
    ga.print_best()
    print("==========================================")
    ga.population[0].save_to_file('gen_0')
    for i in range(ga.GENERATIONS):
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

        print("=========== GENERATION ",i+1," ===========")
        print("Generation ", i+1)
        print("Population size : ", len(ga.population))
        print("================== BEST ==================")
        ga.print_best_info()
        print("==========================================")
        ga.population[0].save_to_file('test/2/gen_{i}'.format(i=i+1))
    return ga.population[0]


if __name__ == '__main__':
    img = cv2.imread("img6.jpg")
    best_fit_individual = run(img)
    best_fit_individual.draw()

    cv2.waitKey(0)
