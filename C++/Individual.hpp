#pragma once
#include <opencv2/opencv.hpp>
#include "Cardioid.hpp"
#include "Helper.hpp"
#include <string>
class Individual
{
	private:
		float fitness;
		Cardioid cardioid;
		std::string genotype;

	public:
		Individual(Cardioid cardioid);
		Individual(std::string genotype);
		Individual(){};
		void setFitness(float fitness);
		float getFitness();
		void setGenotypeAndUpdateCardioid(std::string genotype);
		std::string getGenotype();
		Cardioid getCardioid();
		void setCardioid(Cardioid cardioid);
		cv::Mat getCardioidImage(cv::Mat img);
		void saveCardioidImage(cv::Mat img, std::string path);
		bool operator<(const Individual& other) const;
		bool operator==(const Individual& other) const;
};