#include <iostream>
#include <fstream>
#include <vector>
#include <cmath>
#include <ctime>
#include <opencv2/opencv.hpp>
#include <random>
#include "Individual.hpp"
#include "Cardioid.hpp"
#include <filesystem>
#include <fstream>

const int decimalArraySize = 10;
const int POPULATION_SIZE = 60;
const int GENERATIONS = 50;
const double ELITE_PERCENTAGE = 0.1;
const double mR = 0.02;

class GA
{
public:
  std::vector<Individual> population;

  Individual getIndividual(int pos)
  {
    return population[pos];
  }

  void getTop5(std::vector<Individual> &tops)
  {
    tops.insert(tops.end(), population.end() - 5, population.end());
  }

  bool sortPopulation(Individual &individual1, Individual &individual2)
  {
    return individual1.getScore() > individual2.getScore();
  }

  void initPop(cv::Mat img)
  {
    population.clear();
    for (int i = 0; i < POPULATION_SIZE; i++)
    {
      population.push_back(Individual(img));
    }
    fitnessAll();
    std::sort(population.begin(), population.end(), [this](Individual &a, Individual &b)
              { return sortPopulation(a, b); });
  }

  std::pair<std::string, std::string> sliceBinary(const std::string &binary, int point)
  {
    return std::make_pair(binary.substr(0, point), binary.substr(point));
  }

  void fitnessAll()
  {
    for (Individual &individual : population)
    {
      individual.fitness();
    }
  }

  int grayToDecimal(const std::string &grayCode)
  {
    int n = grayCode.length();
    int decimal = 0;
    for (int i = 0; i < n; i++)
    {
      decimal += std::pow(2, n - i - 1) * (grayCode[i] - '0');
    }
    return decimal;
  }

  std::pair<Individual, Individual> crossover(Individual &a1, Individual &a2, cv::Mat img)
  {
    int x_point = std::rand() % decimalArraySize;
    int y_point = std::rand() % decimalArraySize;
    int r_point = std::rand() % decimalArraySize;

    auto [child1_x, child2_x] = sliceBinary(a1.getCardioid().x_coordinate.gray_code, x_point);
    auto [child1_y, child2_y] = sliceBinary(a1.getCardioid().y_coordinate.gray_code, y_point);
    auto [child1_r, child2_r] = sliceBinary(a1.getCardioid().size.gray_code, r_point);

    int child1_x_decimal = grayToDecimal(child1_x + a2.getCardioid().x_coordinate.gray_code.substr(x_point));
    int child2_x_decimal = grayToDecimal(child2_x + a1.getCardioid().x_coordinate.gray_code.substr(x_point));
    int child1_y_decimal = grayToDecimal(child1_y + a2.getCardioid().y_coordinate.gray_code.substr(y_point));
    int child2_y_decimal = grayToDecimal(child2_y + a1.getCardioid().y_coordinate.gray_code.substr(y_point));
    int child1_r_decimal = grayToDecimal(child1_r + a2.getCardioid().size.gray_code.substr(r_point));
    int child2_r_decimal = grayToDecimal(child2_r + a1.getCardioid().size.gray_code.substr(r_point));

    Individual child1(img, Cardioid(child1_x_decimal, child1_y_decimal, child1_r_decimal));
    Individual child2(img, Cardioid(child2_x_decimal, child2_y_decimal, child2_r_decimal));

    return std::make_pair(child1, child2);
  }

  Individual tournament()
  {
    Individual a1 = population[std::rand() % POPULATION_SIZE];
    Individual a2 = population[std::rand() % POPULATION_SIZE];
    return (a1.getScore() > a2.getScore()) ? a1 : a2;
  }

  void printBest()
  {
    std::cout << population[0].getScore() << std::endl;
  }

  double getBestScore()
  {
    return population[0].getScore();
  }

  void printBestInfo()
  {
    population[0].printInfo();
  }

  std::string getBestInfo()
  {
    return population[0].getInfo();
  }

  void printAll()
  {
    for (Individual &individual : population)
    {
      std::cout << "Score: " << individual.getScore() << std::endl;
    }
  }

  void mutate()
  {
    for (Individual &individual : population)
    {
      if (std::rand() / static_cast<double>(RAND_MAX) < mR)
      {
        individual.mutate();
      }
    }
  }
};

void run(cv::Mat img, int trial)
{
  GA ga;
  int TRIAL = trial;
  std::string folderName = "tests/timed/" + std::to_string(TRIAL);
  std::string fileName = folderName + "/generation_info.txt";

  if (!std::filesystem::exists(folderName))
  {
    std::filesystem::create_directory(folderName);
  }

  std::ofstream file(fileName, std::ios::app);

  ga.initPop(img);

  std::string generationInfo;
  generationInfo += "============= GENERATION 0 =============\n";
  generationInfo += "Population size : " + std::to_string(ga.population.size()) + "\n";
  generationInfo += "================== BEST ==================\n";
  generationInfo += ga.getBestInfo();
  generationInfo += "==========================================\n";
  file << generationInfo;

  std::cout << generationInfo;
  ga.population[0].saveToFile(folderName, "gen_0");

  for (int i = 0; i < GENERATIONS; i++)
  {
    double start_time = static_cast<double>(std::clock()) / CLOCKS_PER_SEC;
    std::vector<Individual> newPopulation;
    newPopulation.insert(newPopulation.end(), ga.population.end() - static_cast<int>(POPULATION_SIZE * ELITE_PERCENTAGE), ga.population.end());

    while (newPopulation.size() < POPULATION_SIZE)
    {
      Individual a1 = ga.tournament();
      Individual a2 = ga.tournament();
      auto [child1, child2] = ga.crossover(a1, a2, img);

      newPopulation.push_back(child1);
      newPopulation.push_back(child2);
    }

    ga.population = newPopulation;
    ga.mutate();
    ga.fitnessAll();
    std::sort(ga.population.begin(), ga.population.end(), [&ga](Individual &a, Individual &b)
              { return ga.sortPopulation(a, b); });

    double end_time = static_cast<double>(std::clock()) / CLOCKS_PER_SEC;

    generationInfo = "============= GENERATION " + std::to_string(i + 1) + " =============\n";
    generationInfo += "Population size : " + std::to_string(ga.population.size()) + "\n";
    generationInfo += "================== BEST ==================\n";
    generationInfo += ga.getBestInfo();
    generationInfo += "================= TIMING =================\n";
    generationInfo += "Time to run: " + std::to_string(static_cast<int>(end_time - start_time)) + "s\n";
    generationInfo += "==========================================\n";

    file << generationInfo;
    std::cout << generationInfo;

    ga.population[0].saveToFile(folderName, "gen_" + std::to_string(i + 1));
  }
}

int main(int argc, char *argv[])
{
  if (argc > 1)
  {
    int trial = std::atoi(argv[1]);
    std::cout << "Trial number: " << trial << std::endl;

    cv::Mat img = cv::imread("img6.jpg");
    run(img, trial);
  }
  else
  {
    std::cout << "No trial number provided." << std::endl;
  }

  return 0;
}
