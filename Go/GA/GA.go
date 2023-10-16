package GA

import (
	"cardioid"
	"fmt"
	"gencsv"
	"individual"
	"math"
	"math/rand"
	"os"
	"sort"
	"sync"
	"time"

	"gocv.io/x/gocv"
)

type GA struct {
	Population []individual.Individual
}

const POPULATION_SIZE = 50
const GENERATIONS = 50
const ELITE_PERCENTAGE = 0.25
const ARRAY_SIZE = 10

func (ga *GA) GetIndividual(index int) individual.Individual {
	return ga.Population[index]
}

func (ga *GA) GetTop5() []individual.Individual {
	return ga.Population[:5]
}

func (ga *GA) GetBest() individual.Individual {
	return ga.Population[0]
}

func (ga *GA) SortPopulation() {
	sort.Slice(ga.Population, func(i, j int) bool {
		return ga.Population[i].Score > ga.Population[j].Score
	})
}

func (ga *GA) CreateRandomIndividual(img gocv.Mat) individual.Individual {
	var ind individual.Individual
	ind.InitIndividual(img)
	return ind
}

func (ga *GA) InitPopulation(img gocv.Mat) {
	for i := 0; i < POPULATION_SIZE; i++ {
		ga.Population = append(ga.Population, individual.Individual{})
		ga.Population[i].InitIndividual(img)
	}
}

func (ga *GA) GetPopulation() []individual.Individual {
	return ga.Population
}

func (ga *GA) GetPopulationSize() int {
	return len(ga.Population)
}

func (ga *GA) SliceBinaryString(binaryString string, sliceIndex int) (string, string) {
	return binaryString[:sliceIndex], binaryString[sliceIndex:]
}

func (ga *GA) FitnessAll() {
	var wg sync.WaitGroup
	wg.Add(len(ga.Population))

	for i := 0; i < len(ga.Population); i++ {
		go func(index int) {
			defer wg.Done()
			ga.Population[index].Fitness()
		}(i)
	}

	wg.Wait()
}

func (ga *GA) GrayToDecimal(gray string) int {
	var decimal int
	for i := 0; i < len(gray); i++ {
		if gray[i] == '1' {
			decimal = decimal + int(math.Pow(2, float64(len(gray)-i-1)))
		}
	}
	return decimal
}

func (ga *GA) Crossover(ind1 individual.Individual, ind2 individual.Individual, img gocv.Mat) (individual.Individual, individual.Individual) {
	xSliceIndex := rand.Intn(ARRAY_SIZE - 1)
	ySliceIndex := rand.Intn(ARRAY_SIZE - 1)
	sizeSliceIndex := rand.Intn(ARRAY_SIZE - 1)

	ind1x1, ind1x2 := ga.SliceBinaryString(ind1.Cardioid.GetXCoordinate().GetGray(), xSliceIndex)
	ind2x1, ind2x2 := ga.SliceBinaryString(ind2.Cardioid.GetXCoordinate().GetGray(), xSliceIndex)
	ind1y1, ind1y2 := ga.SliceBinaryString(ind1.Cardioid.GetYCoordinate().GetGray(), ySliceIndex)
	ind2y1, ind2y2 := ga.SliceBinaryString(ind2.Cardioid.GetYCoordinate().GetGray(), ySliceIndex)
	ind1size1, ind1size2 := ga.SliceBinaryString(ind1.Cardioid.GetSize().GetGray(), sizeSliceIndex)
	ind2size1, ind2size2 := ga.SliceBinaryString(ind2.Cardioid.GetSize().GetGray(), sizeSliceIndex)

	child1Cardioid := cardioid.NewCardioid(
		ga.GrayToDecimal(ind1x1+ind2x2),
		ga.GrayToDecimal(ind1y1+ind2y2),
		ga.GrayToDecimal(ind1size1+ind2size2),
	)

	child2Cardioid := cardioid.NewCardioid(
		ga.GrayToDecimal(ind2x1+ind1x2),
		ga.GrayToDecimal(ind2y1+ind1y2),
		ga.GrayToDecimal(ind2size1+ind1size2),
	)

	child1 := individual.NewIndividual(child1Cardioid, img)
	child2 := individual.NewIndividual(child2Cardioid, img)

	return child1, child2
}

func (ga *GA) TournamentSelection() (individual.Individual, individual.Individual) {
	var parent1, parent2 individual.Individual
	tournamentSize := 2
	for i := 0; i < tournamentSize; i++ {
		randomIndex := rand.Intn(len(ga.Population))
		if i == 0 {
			parent1 = ga.Population[randomIndex]
		} else {
			parent2 = ga.Population[randomIndex]
		}
	}
	return parent1, parent2
}

func (ga *GA) Mutate(ind individual.Individual) individual.Individual {
	ind.Mutate()
	return ind
}

func (ga *GA) Evolve(img gocv.Mat) {
	var newPopulation []individual.Individual
	eliteSize := int(ELITE_PERCENTAGE * float64(len(ga.Population)))
	for i := 0; i < eliteSize; i++ {
		newPopulation = append(newPopulation, ga.Population[i])
	}
	for len(newPopulation) < POPULATION_SIZE-1 {
		parent1, parent2 := ga.TournamentSelection()
		child1, child2 := ga.Crossover(parent1, parent2, img)
		if child1.Cardioid.WillMutate() {
			child1 = ga.Mutate(child1)
		}
		if child2.Cardioid.WillMutate() {
			child2 = ga.Mutate(child2)
		}
		newPopulation = append(newPopulation, child1)
		newPopulation = append(newPopulation, child2)
	}
	ga.Population = newPopulation
}

func (ga *GA) PrintPopulation() {
	for i := 0; i < len(ga.Population); i++ {
		fmt.Println(i, " : ", ga.Population[i].Score)
	}
}

func (ga *GA) Run(img gocv.Mat, folderName string) {
	os.Mkdir(folderName, os.ModePerm)

	ga.InitPopulation(img)
	ga.FitnessAll()
	data := []gencsv.Data{}
	for i := 0; i < GENERATIONS; i++ {
		start := time.Now()
		fmt.Println("Generation: ", i, " Score: ", ga.GetBest().Score, "Population Size: ", ga.GetPopulationSize())
		bestIndividual := ga.GetBest()
		// bestIndividual.SaveToFile(folderName + "/gen" + fmt.Sprint(i) + ".jpg")
		ga.FitnessAll()
		ga.SortPopulation()
		ga.Evolve(img)
		duration := time.Since(start)
		data = append(data, gencsv.Data{
			Gen:   i,
			X:     bestIndividual.Cardioid.GetXCoordinate().GetDecimal(),
			Y:     bestIndividual.Cardioid.GetYCoordinate().GetDecimal(),
			Size:  bestIndividual.Cardioid.GetSize().GetDecimal(),
			Score: int(bestIndividual.Score),
			Time:  duration.Milliseconds(),
		})
	}
	gencsv.GenerateCSV(folderName+"/data.csv", data)
	ga.FitnessAll()
	ga.SortPopulation()
}
