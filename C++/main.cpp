#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include <chrono>
#include <map>
#include "Helper.hpp"
#include "GA.hpp"

#include <thread>

using namespace std;
void testGrayConversion(){
	int a = 500;
	string gray = Helper::decimal2gray(a, 10);

	cout << "decimal: " << a << endl;
	cout << "gray: " << gray << endl;

	int b = Helper::gray2decimal(gray);

	cout << "decimal: " << b << endl;
}

void testGAConvergence(){
	auto start = chrono::high_resolution_clock::now();
	cv::Mat img = cv::imread("../Go/img6.jpg", cv::IMREAD_COLOR);
	GA ga(img);
	ga.showPopulation();
	Individual best = ga.getBestIndividual();
	cv::Mat cardioid = best.getCardioidImage(img);
	cv::imshow("Cardioid gen 0", cardioid);
	ga.savePopulation("./gen_0");

	ga.run(1);
	best = ga.getBestIndividual();
	ga.showPopulation();
	cardioid = best.getCardioidImage(img);
	cv::imshow("Cardioid gen 1", cardioid);
	ga.savePopulation("./gen_1");


	ga.run(9);
	best = ga.getBestIndividual();
	ga.showPopulation();
	cardioid = best.getCardioidImage(img);
	cv::imshow("Cardioid gen 10", cardioid);
	ga.savePopulation("./gen_10");

	ga.run(40);
	best = ga.getBestIndividual();
	ga.showPopulation();
	cardioid = best.getCardioidImage(img);
	cv::imshow("Cardioid gen 50", cardioid);
	ga.savePopulation("./gen_50");

	auto end = chrono::high_resolution_clock::now();
	auto duration = chrono::duration_cast<chrono::milliseconds>(end - start);
	cout<<"Total Time: "<<duration.count()<<"ms"<<endl;
	cv::waitKey(0);
}

void testGAExecTime(){
	auto start = chrono::high_resolution_clock::now();
	cv::Mat img = cv::imread("../dataset/sick/p138.jpg");
	GA ga(img);
	auto end = chrono::high_resolution_clock::now();
	auto duration = chrono::duration_cast<chrono::milliseconds>(end - start);
	cout<<"Setup time: "<<duration.count()<<"ms"<<endl;
	ga.runWithTimings(50);
}

void testAllImages(){
	vector<string> images = {"p138", "p179", "p180", "p181", "p192"};
	map<string,cv::Mat> bestIndividuals;
	mutex mut;
	
	vector<thread> threads;
	for(string image : images){
		threads.push_back(thread([](string image, map<string,cv::Mat>& bestIndividuals, mutex& mut){
			string path = "../dataset/sick/" + image + ".jpg";
			cv::Mat img = cv::imread(path);
			GA ga(img);
			ga.run(50);
			Individual best = ga.getBestIndividual();
			cout<<"Best fitness for image "<<image<<": "<<best.getFitness()<<endl;
			//mut.lock();
			//bestIndividuals[image] = best.getCardioidImage(img);
			//mut.unlock();
		}, image, std::ref(bestIndividuals), std::ref(mut)));
	}

	for (auto& thread : threads) {
		thread.join();
	}

	/*
	for(auto iten:bestIndividuals){
		cv::imshow("Image "+iten.first, iten.second);
	}
	cv::waitKey(0); */

	
}

int main()
{	
	testGAExecTime();
	return 0;
}