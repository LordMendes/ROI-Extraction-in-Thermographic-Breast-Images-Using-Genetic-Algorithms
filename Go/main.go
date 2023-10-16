// code gocv to show an image on screen
// gocv.io/x/gocv
package main

import (
	"GA"
	"fmt"
	"time"

	"gocv.io/x/gocv"
)

func timer(name string) func() {
	start := time.Now()
	return func() {
		fmt.Printf("%s took %v\n", name, time.Since(start))
	}
}

func main() {
	// read image
	img := gocv.IMRead("img6.jpg", gocv.IMReadColor)

	// check if image was read
	if img.Empty() {
		fmt.Println("Error reading image from file")
		return
	}

	ga := GA.GA{}
	ga.Run(img)
	best := ga.GetBest()
	fmt.Println("X: ", best.Cardioid.GetXCoordinate().GetDecimal())
	fmt.Println("Y: ", best.Cardioid.GetYCoordinate().GetDecimal())
	fmt.Println("Size: ", best.Cardioid.GetSize().GetDecimal())
	bestImg := best.Draw()
	window := gocv.NewWindow("GATIS")
	window.IMShow(bestImg)
	window.WaitKey(0)
	defer timer("main")()
}
