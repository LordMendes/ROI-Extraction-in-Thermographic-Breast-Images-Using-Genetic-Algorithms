#include "Cardioid.hpp"

Cardioid::Cardioid(int x, int y, int size)
{
	this->x = x;
	this->y = y;
	this->size = size;
}

bool Cardioid::isInside(int x, int y)
{
	float r = size/2;

	float distance = sqrt(pow(x - this->x, 2) + pow(y - this->y, 2));
	float theta = atan2(y - this->y, x - this->x);

	bool cardioidEquation = pow(distance, 2) <= pow(r,2)*(1 - sin(theta));

	return cardioidEquation;
}

bool Cardioid::operator==(const Cardioid& other) const
{
	return this->x == other.x && this->y == other.y && this->size == other.size;
}

//Getters and Setters
void Cardioid::setX(int x)
{
	this->x = x;
}

void Cardioid::setY(int y)
{
	this->y = y;
}

void Cardioid::setSize(int size)
{
	this->size = size;
}

int Cardioid::getX()
{
	return x;
}

int Cardioid::getY()
{
	return y;
}

int Cardioid::getSize()
{
	return size;
}


