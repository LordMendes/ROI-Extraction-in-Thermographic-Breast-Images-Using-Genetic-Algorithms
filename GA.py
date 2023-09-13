import individual as ind
import cardioid as c
import cv2
import random as r


class GA:
    # CONSTANTS
    gat = 1                # QUANTITY OF GA'S TO BE RUN
    decimalArraySize = 10  # TAMANHO DO INDIVIDUO
    POP = 10               # TAMANHO DA POPULA��O
    GEN = 1                # TAMANHO DA GERA��O
    TOURNAMENT_SIZE = 2    # TAMANHO DO TORNEIO
    mR = 0.02              # CHANCE DE OCORRER MUTA��O
    cR = 0.7               # CHANCE DE OCORRER CRUZAMENTO

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
        for i in range(self.POP):
            self.population.append(ind.Individual(img))
        self.population.sort(key=self.sort_population)

    def slice_binary(self, binary, point):
        first_half = binary[:point]
        second_half = binary[point:]
        return first_half, second_half

    def crossover(self, a1, a2, img):
        # Choose a random point in the binary string.
        x_point = r.randint(0, self.decimalArraySize - 1)
        y_point = r.randint(0, self.decimalArraySize - 1)
        r_point = r.randint(0, self.decimalArraySize - 1)
        # Slice the binary string in the random point.
        a_x1, a_x2 = self.slice_binary(
            a1.cardioid.x_cordinate.gray_code, x_point)
        a_y1, a_y2 = self.slice_binary(
            a1.cardioid.y_cordinate.gray_code, y_point)
        a_r1, a_r2 = self.slice_binary(a1.cardioid.radius.gray_code, r_point)

        b_x1, b_x2 = self.slice_binary(
            a2.cardioid.x_cordinate.gray_code, x_point)
        b_y1, b_y2 = self.slice_binary(
            a2.cardioid.y_cordinate.gray_code, y_point)
        b_r1, b_r2 = self.slice_binary(a2.cardioid.radius.gray_code, r_point)

        # Create the new binary strings.
        child1_x = a_x1 + b_x2
        child2_x = b_x1 + a_x2

        child1_y = a_y1 + b_y2
        child2_y = b_y1 + a_y2

        child1_r = a_r1 + b_r2
        child2_r = b_r1 + a_r2

        # Create the new individuals.
        child1 = ind.Individual(img)
        child2 = ind.Individual(img)

        cardioid1 = c.Cardioid(child1_x, child1_y, child1_r)
        cardioid2 = c.Cardioid(child2_x, child2_y, child2_r)

        child1.set_cardioid(cardioid1)
        child2.set_cardioid(cardioid2)

        return child1, child2

    def tournament(self):
        # Tournament selection.
        # Choose 2 individuals randomly from the population.
        # The one with the highest fitness score is chosen.
        # Repeat until the number of individuals chosen is equal to the population size.
        # The chosen individuals are added to a new population.
        new_population = []
        while len(new_population) < self.POP:
            # Choose 2 individuals randomly from the population.
            a1 = self.population[r.randint(0, self.POP - 1)]
            a2 = self.population[r.randint(0, self.POP - 1)]
            # The one with the highest fitness score is chosen.
            if a1.get_score() > a2.get_score():
                new_population.append(a1)
            else:
                new_population.append(a2)
        return new_population

    def print_best(self):
        print(self.population[-1].get_score())

    def print_all(self):
        for i in self.population:
            print(i.get_score())


def run(img):
    ga = GA()
    ga.initPop(img)
    for i in range(ga.GEN):
        ga.population = ga.tournament()
        for j in range(ga.POP):
            if r.random() < ga.cR:
                child1, child2 = ga.crossover(
                    ga.population[j], ga.population[r.randint(0, ga.POP - 1)], img)
                ga.population.append(child1)
                ga.population.append(child2) 
        # for j in range(ga.POP):
        #     if r.random() < ga.mR:
        #         ga.population[j].mutate()
        ga.population.sort(key=ga.sort_population)
        ga.population = ga.population[:ga.POP]
        # ga.print_best()

    # ga.print_all()
    return ga.population[-1]


if __name__ == '__main__':
    img = cv2.imread("img6.jpg")
    run(img)
