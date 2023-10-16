package individual

import (
	"cardioid"
	"math"

	"gocv.io/x/gocv"
)

var COLOR_WEIGHTS = [5]float64{-70, 15, 25, -20.5}
var COLOR_INTERVAL = [5]uint8{0, 45, 140, 200, 255}

type Individual struct {
	Cardioid cardioid.Cardioid
	Img      gocv.Mat
	Score    float64
}

func NewIndividual(c cardioid.Cardioid, img gocv.Mat) Individual {
	return Individual{c, img, 0}
}

func (i *Individual) InitIndividual(img gocv.Mat) {
	i.Cardioid.InitCardioid(img.Rows(), img.Cols(), img.Rows()/2)
	i.Img = img
	i.Score = 0
}

func (i *Individual) SetCardioid(c cardioid.Cardioid) {
	i.Cardioid = c
}

func (i *Individual) GetScore() float64 {
	return i.Score
}

func (i *Individual) SetScore(s float64) {
	i.Score = s
}

func (i *Individual) GetCardioid() cardioid.Cardioid {
	return i.Cardioid
}

func (i *Individual) GetImg() gocv.Mat {
	return i.Img
}

func (i *Individual) Mutate() {
	i.Cardioid.Mutate()
}

func (i *Individual) GetPixelVolume() [4]float64 {
	cols := i.Img.Cols()
	rows := i.Img.Rows()
	pixelVolume := [4]float64{0, 0, 0, 0}

	for x := 0; x < cols; x++ {
		for y := 0; y < rows; y++ {
			isInsideCardioid := i.Cardioid.IsInsideCardioid(x, y)
			if isInsideCardioid {
				pixelColorValue := i.Img.GetVecbAt(y, x)[0]
				if pixelColorValue < COLOR_INTERVAL[1] {
					pixelVolume[0] += 1
				} else if pixelColorValue < COLOR_INTERVAL[2] {
					pixelVolume[1] += 1
				} else if pixelColorValue < COLOR_INTERVAL[3] {
					pixelVolume[2] += 1
				} else {
					pixelVolume[3] += 1
				}
			}
		}
	}
	return pixelVolume
}

func (i *Individual) Fitness() float64 {
	if i.Cardioid.XCoordinate.GetDecimal() < 0 || i.Cardioid.YCoordinate.GetDecimal() < 0 || i.Cardioid.Size.GetDecimal() < 0 {
		return 0
	}
	if i.Cardioid.XCoordinate.GetDecimal() > i.Img.Cols() || i.Cardioid.YCoordinate.GetDecimal() > i.Img.Rows() {
		return 0
	}

	pixelVolume := i.GetPixelVolume()
	totalWeight := COLOR_WEIGHTS[0] + COLOR_WEIGHTS[1] + COLOR_WEIGHTS[2] + COLOR_WEIGHTS[3]
	colorScore := 0.0
	for j := 0; j < 4; j++ {
		colorScore += pixelVolume[j] * COLOR_WEIGHTS[j]
	}
	if colorScore < 0 {
		return 0
	}
	i.SetScore(colorScore / math.Abs(totalWeight))
	return colorScore / math.Abs(totalWeight)
}

func (i *Individual) Draw() gocv.Mat {
	rows, cols := i.Img.Rows(), i.Img.Cols()
	result := i.Img.Clone()
	for y := 0; y < rows; y++ {
		for x := 0; x < cols; x++ {
			isInsideCardioid := i.Cardioid.IsInsideCardioid(x, y)
			if isInsideCardioid {
				result.SetDoubleAt3(y, x, 0, 13)
			}
		}
	}
	return result
}

func (i *Individual) SaveToFile(name string) {
	gocv.IMWrite(name, i.Draw())
}
