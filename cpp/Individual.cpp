#include <iostream>
#include <opencv2/opencv.hpp>
#include <random>
#include "Cardioid.hpp"
#include <filesystem>
#include <fstream>

namespace fs = std::filesystem;

const int binarySize = 10;
const int color_interval[] = {0, 45, 140, 200, 256};
const double color_weights[] = {-25, 5, 25, -27.5};

class Individual
{
public:
  double score = 0;
  cv::Mat img;
  Cardioid cardioid;

  Individual(cv::Mat image, Cardioid c) : img(image), cardioid(c) {}

  Individual(cv::Mat image) : img(image), cardioid(Cardioid(0, 0, 0))
  {
    cardioid = Cardioid(0, 0, 0);
    int x = static_cast<int>(img.cols * (std::rand() / static_cast<double>(RAND_MAX)));
    int y = static_cast<int>(img.rows * (std::rand() / static_cast<double>(RAND_MAX)));
    int size = static_cast<int>(img.cols * (std::rand() / static_cast<double>(RAND_MAX)) / 2);
    cardioid = Cardioid(x, y, size);
  }

  void setCardioid(Cardioid c)
  {
    cardioid = c;
  }

  double getScore()
  {
    return score;
  }

  Cardioid getCardioid()
  {
    return cardioid;
  }

  void mutate()
  {
    cardioid.mutate();
  }

  double fitness()
  {
    if (cardioid.x_coordinate.decimal < 0 || cardioid.x_coordinate.decimal > img.cols ||
        cardioid.y_coordinate.decimal < 0 || cardioid.y_coordinate.decimal > img.rows)
    {
      return 0;
    }

    double totalWeight = 0;
    for (double weight : color_weights)
    {
      totalWeight += weight;
    }

    std::vector<double> pixelVolume = getPixelVolume();

    if (pixelVolume[0] + pixelVolume[1] + pixelVolume[2] + pixelVolume[3] == 0)
    {
      return 0;
    }

    double totalColorScore = 0;
    for (int i = 0; i < 4; i++)
    {
      totalColorScore += color_weights[i] * pixelVolume[i];
    }

    double fitnessScore = totalColorScore / std::abs(totalWeight);
    score = fitnessScore;
    return fitnessScore;
  }

  std::vector<double> getPixelVolume()
  {
    std::vector<double> pixelVolume(4, 0);

    for (int i = 0; i < img.rows; i++)
    {
      for (int j = 0; j < img.cols; j++)
      {
        bool isInside = isInsideCardioid(j, i);
        if (isInside)
        {
          int pixelColor = img.at<cv::Vec3b>(i, j)[0];
          if (pixelColor < color_interval[1])
          {
            pixelVolume[0] += 1;
          }
          else if (pixelColor < color_interval[2])
          {
            pixelVolume[1] += 1;
          }
          else if (pixelColor < color_interval[3])
          {
            pixelVolume[2] += 1;
          }
          else
          {
            pixelVolume[3] += 1;
          }
        }
      }
    }

    return pixelVolume;
  }

  cv::Mat draw()
  {
    cv::Mat result = img.clone();
    for (int i = 0; i < img.rows; i++)
    {
      for (int j = 0; j < img.cols; j++)
      {
        bool isInside = isInsideCardioid(j, i);
        if (isInside)
        {
          result.at<cv::Vec3b>(i, j) = cv::Vec3b(255, 0, 0);
        }
      }
    }
    return result;
  }

  void saveToFile(const std::string &folderName, const std::string &fileName)
  {
    Individual clone(img.clone(), cardioid);
    cv::Mat drawn = clone.draw();

    if (!fs::exists(folderName))
    {
      fs::create_directory(folderName);
    }

    cv::imwrite(fs::path(folderName) / (fileName + ".png"), drawn);
  }

  bool isInsideCardioid(int x, int y)
  {
    return cardioid.isInsideCardioid(x, y);
  }

  void printBinary()
  {
    std::cout << "X: " << cardioid.x_coordinate.gray_code << " Y: " << cardioid.y_coordinate.gray_code
              << " R: " << cardioid.size.gray_code << std::endl;
  }

  void printInfo()
  {
    std::cout << "Score: " << score << "\n"
              << "    X: " << cardioid.x_coordinate.decimal << "\n"
              << "    Y: " << cardioid.y_coordinate.decimal << "\n"
              << "    R: " << cardioid.size.decimal << std::endl;
  }

  std::string getInfo()
  {
    return "Score: " + std::to_string(score) + "\n" +
           "    X: " + std::to_string(cardioid.x_coordinate.decimal) + "\n" +
           "    Y: " + std::to_string(cardioid.y_coordinate.decimal) + "\n" +
           "    R: " + std::to_string(cardioid.size.decimal) + "\n";
  }
};
