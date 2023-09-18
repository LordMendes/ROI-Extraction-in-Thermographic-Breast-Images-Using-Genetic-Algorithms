#ifndef CARDIOID_HPP
#define CARDIOID_HPP

#include <cmath>
#include <random>
#include "Coordinate.hpp"

extern const double mutation_rate;
extern const int ARRAY_SIZE;

class Cardioid {
public:
    Coordinate x_coordinate;
    Coordinate y_coordinate;
    Coordinate size;

    Cardioid(int x, int y, int s);

    bool isInsideCardioid(int x, int y);

    bool willMutate(double mutation_rate);

    void mutate();
};

#endif // CARDIOID_HPP
