import cardioid as c
import cv2
import random as r

binarySize = 10
color_interval = [0, 45, 140, 200, 256]


class Individual:
    score = 0

    def __init__(self, img, cardioid='') -> None:
        self.img = img
        if cardioid != '':
            self.cardioid = cardioid
        else:
            x = int(img.shape[1]*r.uniform(0, 1))
            y = int(img.shape[0]*r.uniform(0, 1))
            radius = int(img.shape[1]*r.uniform(0, 1)/2)
            self.cardioid = c.Cardioid(x, y, radius)

    def set_cardioid(self, cardioid):
        self.cardioid = cardioid

    def get_score(self):
        self.fitness()
        return self.score

    def get_cardioid(self):
        return self.cardioid

    def mutate(self):
        # Mutate the cardioid.
        self.cardioid.mutate()

    def fitness(self):
        color_weights = [-250, 50, 250, -275]

        # The total weight.
        total_weight = sum(color_weights)

        # Get the pixel volume of the image.
        pixel_volume = self.get_pixel_volume()

        # Calculate the total color score.
        total_color_score = sum(
            color_weights[i] * pixel_volume[i] for i in range(4))

        # Calculate the fitness score.
        fitness_score = total_color_score / abs(total_weight)

        self.score = fitness_score
        return fitness_score

    def get_pixel_volume(self):
        # Get the pixel volume of the image.
        pixel_volume = [0, 0, 0, 0]

        # Get the pixel volume of the image.
        for i in range(self.img.shape[0]):
            for j in range(self.img.shape[1]):
                is_inside_cardioid= self.is_inside_cardioid(j, i)
                if(is_inside_cardioid):
                    pixel_color = self.img[i, j][0]
                    if pixel_color < color_interval[1]:
                        pixel_volume[0] += 1
                    elif pixel_color < color_interval[2]:
                        pixel_volume[1] += 1
                    elif pixel_color < color_interval[3]:
                        pixel_volume[2] += 1
                    else:
                        pixel_volume[3] += 1

        return pixel_volume

    def slice(self, point):
        # Slice the cardioid.
        self.cardioid.slice(point)

    def is_inside_cardioid(self, x, y):
        return self.cardioid.is_inside_cardioid(x, y)
