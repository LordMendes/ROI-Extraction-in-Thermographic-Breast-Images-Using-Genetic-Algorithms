// code gocv to show an image on screen
// gocv.io/x/gocv
package main

import (
	"fmt"

	"gocv.io/x/gocv"
)

func main() {
	// read image
	img := gocv.IMRead("img6.jpg", gocv.IMReadColor)

	// check if image was read
	if img.Empty() {
		fmt.Println("Error reading image from file")
		return
	}

	// display image in window
	window := gocv.NewWindow("Lena")
	window.IMShow(img)
	window.WaitKey(0)

	// save image
	gocv.IMWrite("data/out.jpg", img)
}
