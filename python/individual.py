import os
import cv2
import cardioid as c
import random as r

binarySize = 10
color_interval = [0, 45, 140, 200, 256]
color_weights = [-25, 5, 25, -27.5]


class Individual:
    score = 0

    def __init__(self, img, cardioid='') -> None:
        self.img = img
        self.cardioid = c.Cardioid(0, 0, 0)
        if cardioid != '':
            self.cardioid = cardioid
        else:
            x = int(img.shape[1]*r.uniform(0, 1))
            y = int(img.shape[0]*r.uniform(0, 1))
            size = int(img.shape[1]*r.uniform(0, 1)/2)
            self.cardioid = c.Cardioid(x, y, size)

    def set_cardioid(self, cardioid):
        self.cardioid = cardioid

    def get_score(self):
        return self.score

    def get_cardioid(self):
        return self.cardioid

    def mutate(self):
        # Mutate the cardioid.
        self.cardioid.mutate()

    def fitness(self):

        # Check if cordinates are inside the image
        if self.cardioid.x_cordinate.decimal < 0 or self.cardioid.x_cordinate.decimal > self.img.shape[1]:
            return 0
        if self.cardioid.y_cordinate.decimal < 0 or self.cardioid.y_cordinate.decimal > self.img.shape[0]:
            return 0

        # The total weight.
        total_weight = sum(color_weights)

        # Get the pixel volume of the image.
        pixel_volume = self.get_pixel_volume()


        if (pixel_volume[0]+pixel_volume[1]+pixel_volume[2]+pixel_volume[3]) == 0:
            return 0

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
                is_inside_cardioid = self.is_inside_cardioid(j, i)
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

    def draw(self):
        for i in range(self.img.shape[0]):
            for j in range(self.img.shape[1]):
                is_inside_cardioid = self.is_inside_cardioid(j, i)
                if(is_inside_cardioid):
                    self.img[i, j] = (255, 0, 0)
        return self.img

    def save_to_file(self, folder_name, file_name):
        clone = Individual(self.img.copy(), self.cardioid)
        clone.draw()

        # Check if the folder exists, and create it if it doesn't
        if not os.path.exists(folder_name):
            os.makedirs(folder_name)

        # Save the image inside the folder
        cv2.imwrite(os.path.join(folder_name, f'{file_name}.png'), clone.img)

    def slice(self, point):
        # Slice the cardioid.
        self.cardioid.slice(point)

    def is_inside_cardioid(self, x, y):
        return self.cardioid.is_inside_cardioid(x, y)

    def print_binary(self):
        print("X: ", self.cardioid.x_cordinate.gray_code,
              "Y: ", self.cardioid.y_cordinate.gray_code,
              "R: ", self.cardioid.size.gray_code)

    def print_info(self):
        print("Score: ", self.score, "\n",
              "    X: ", self.cardioid.x_cordinate.decimal, "\n",
              "    Y: ", self.cardioid.y_cordinate.decimal, "\n",
              "    R: ", self.cardioid.size.decimal)

    def get_info(self):
        return "Score: " + str(self.score) + "\n" + \
               "    X: " + str(self.cardioid.x_cordinate.decimal) + "\n" + \
               "    Y: " + str(self.cardioid.y_cordinate.decimal) + "\n" + \
               "    R: " + str(self.cardioid.size.decimal) + "\n"
