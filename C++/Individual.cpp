#include "Individual.hpp"

Individual::Individual(Cardioid cardioid)
{
	this->cardioid = cardioid;
	genotype = "";
	fitness = 0;
}

Individual::Individual(std::string genotype)
{
	setGenotypeAndUpdateCardioid(genotype);
	fitness = 0;
}


void Individual::setGenotypeAndUpdateCardioid(std::string genotype)
{
	this->genotype = genotype;

	//Update cardioid
	std::string x = genotype.substr(0, 10);
	std::string y = genotype.substr(10, 10);
	std::string size = genotype.substr(20, 10);

	cardioid.setX(Helper::gray2decimal(x));
	cardioid.setY(Helper::gray2decimal(y));
	cardioid.setSize(Helper::gray2decimal(size));
}

std::string Individual::getGenotype()
{
	if(genotype == "")
	{
		genotype = Helper::decimal2gray(cardioid.getX(), 10) + 
					Helper::decimal2gray(cardioid.getY(), 10) + 
					Helper::decimal2gray(cardioid.getSize(), 10);
	}
	return genotype;
}

cv::Mat Individual::getCardioidImage(cv::Mat img)
{
	cv::Mat cardioidImage = img.clone();
	int x = cardioid.getX();
	int y = cardioid.getY();
	int size = cardioid.getSize();

	for(int i = 0; i < cardioidImage.rows; i++)
	{
		for(int j = 0; j < cardioidImage.cols; j++)
		{
			if(cardioid.isInside(j, i))
			{
				cardioidImage.at<cv::Vec3b>(i, j)[0] = 0;
				cardioidImage.at<cv::Vec3b>(i, j)[1] = 0;
				cardioidImage.at<cv::Vec3b>(i, j)[2] = 0;
			}
		}
	}

	return cardioidImage;
}

bool Individual::operator<(const Individual& other) const
{
	return fitness > other.fitness;
}

bool Individual::operator==(const Individual& other) const
{
	return this->cardioid == other.cardioid;
}

void Individual::saveCardioidImage(cv::Mat img, std::string path)
{
	cv::Mat cardioidImage = getCardioidImage(img);
	cv::imwrite(path, cardioidImage);
}

//Getters and Setters
void Individual::setFitness(float fitness)
{
	this->fitness = fitness;
}

float Individual::getFitness()
{
	return fitness;
}

Cardioid Individual::getCardioid()
{
	return cardioid;
}

void Individual::setCardioid(Cardioid cardioid)
{
	this->cardioid = cardioid;
}