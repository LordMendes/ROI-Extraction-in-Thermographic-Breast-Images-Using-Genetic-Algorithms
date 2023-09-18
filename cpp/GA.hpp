#ifndef GA_HPP
#define GA_HPP

#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include <ctime>
#include <opencv2/opencv.hpp>
#include <random>
#include "individual.hpp"
#include "cardioid.hpp"
#include <filesystem>
#include <fstream>

const int decimalArraySize = 10;
const int POPULATION_SIZE = 60;
const int GENERATIONS = 50;
const double ELITE_PERCENTAGE = 0.1;
const double mR = 0.02;

class GA {
public:
    std::vector<Individual> population;

    Individual getIndividual(int pos);

    void getTop5(std::vector<Individual> &tops);

    bool sortPopulation(Individual &individual1, Individual &individual2);

    void initPop(cv::Mat img);

    std::pair<std::string, std::string> sliceBinary(const std::string &binary, int point);

    void fitnessAll();

    int grayToDecimal(const std::string &grayCode);

    std::pair<Individual, Individual> crossover(Individual &a1, Individual &a2, cv::Mat img);

    Individual tournament();

    void printBest();

    double getBestScore();

    void printBestInfo();

    std::string getBestInfo();

    void printAll();

    void mutate();
};

void run(cv::Mat img, int trial);

int main(int argc, char *argv[]);

#endif // GA_HPP
