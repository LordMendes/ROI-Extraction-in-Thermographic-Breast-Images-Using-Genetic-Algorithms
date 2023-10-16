// code gocv to show an image on screen
// gocv.io/x/gocv
package main

import (
	"GA"
	"fmt"
	"os"
	"time"

	"gocv.io/x/gocv"
)

func timer(name string) func() {
	start := time.Now()
	return func() {
		fmt.Printf("%s took %v\n", name, time.Since(start))
	}
}

var imgs = []string{"p138", "p179", "p180", "p181", "p192"}

func main() {
	for _, imgName := range imgs {
		// read image
		img := gocv.IMRead("../dataset/sick/"+imgName+".jpg", gocv.IMReadColor)

		// check if image was read
		if img.Empty() {
			fmt.Println("Error reading image from file")
			return
		}
		trial := "4"
		trialFolder := "tests/trial-" + trial
		folderName := trialFolder + "/" + imgName
		os.Mkdir(trialFolder, os.ModePerm)

		ga := GA.GA{}
		ga.Run(img, folderName)
		best := ga.GetBest()
		best.SaveToFile(folderName + "/best.jpg")
		// bestImg := best.Draw()
		// window := gocv.NewWindow("GATIS")
		// window.IMShow(bestImg)
		// window.WaitKey(0)
		defer timer("main")()
	}
}
