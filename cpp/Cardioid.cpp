#include <iostream>
#include <cmath>
#include <random>
#include <string>
#include <bitset>
#include "Coordinate.hpp"

const double mutation_rate = 0.5;
const int ARRAY_SIZE = 10;

class Cardioid
{
public:
  Coordinate x_coordinate;
  Coordinate y_coordinate;
  Coordinate size;

  Cardioid(int x, int y, int s) : x_coordinate(x), y_coordinate(y), size(s) {}

  bool isInsideCardioid(int x, int y)
  {
    int size_val = size.decimal;
    int x_c = x_coordinate.decimal;
    int y_c = y_coordinate.decimal;
    double r = static_cast<double>(size_val) / 2.0;

    double theta = atan2(y - y_c, x - x_c);
    double cardioid_equation = pow(x - x_c, 2) + pow(y - y_c, 2) <= r * r * (1 - sin(theta));

    return cardioid_equation;
  }

  bool willMutate(double mutation_rate)
  {
    return (static_cast<double>(rand()) / RAND_MAX) < mutation_rate;
  }

  void mutate()
  {
    bool has_x_mutated = willMutate(mutation_rate);
    bool has_y_mutated = willMutate(mutation_rate);
    bool has_radius_mutated = willMutate(mutation_rate);

    if (has_x_mutated)
    {
      x_coordinate.mutate();
    }
    if (has_y_mutated)
    {
      y_coordinate.mutate();
    }
    if (has_radius_mutated)
    {
      size.mutate();
    }
  }

  
};
