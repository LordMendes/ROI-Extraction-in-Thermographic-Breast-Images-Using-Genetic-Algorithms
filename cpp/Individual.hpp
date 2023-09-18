#ifndef INDIVIDUAL_HPP
#define INDIVIDUAL_HPP

#include <iostream>
#include <opencv2/opencv.hpp>
#include <random>
#include <vector>
#include "Cardioid.hpp"

extern const int binarySize;
extern const int color_interval[];
extern const double color_weights[];

class Individual {
public:
    double score = 0;
    cv::Mat img;
    Cardioid cardioid;

    Individual(cv::Mat image, Cardioid c);

    Individual(cv::Mat image);

    void setCardioid(Cardioid c);

    double getScore();

    Cardioid getCardioid();

    void mutate();

    double fitness();

    std::vector<double> getPixelVolume();

    cv::Mat draw();

    void saveToFile(const std::string &folderName, const std::string &fileName);

    void slice(double point);

    bool isInsideCardioid(int x, int y);

    void printBinary();

    void printInfo();

    std::string getInfo();
};

#endif // INDIVIDUAL_HPP
