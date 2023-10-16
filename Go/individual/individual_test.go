package individual

import (
	"cardioid"
	"testing"

	"gocv.io/x/gocv"
)

func TestIndividual(t *testing.T) {
	// Create a sample cardioid and image for testing
	c := cardioid.NewCardioid(50, 30, 10)
	img := gocv.NewMatWithSize(100, 100, gocv.MatTypeCV8UC3)

	individual := NewIndividual(c, img)

	// Test the functions
	expectedScore := 0.0
	if individual.GetScore() != expectedScore {
		t.Errorf("Expected score: %v, got: %v", expectedScore, individual.GetScore())
	}

	expectedFitness := 0.0
	if individual.Fitness() != expectedFitness {
		t.Errorf("Expected fitness: %v, got: %v", expectedFitness, individual.Fitness())
	}

	expectedPixelVolume := [4]float64{3193, 0, 0, 0}
	pixelVolume := individual.GetPixelVolume()
	if pixelVolume != expectedPixelVolume {
		t.Errorf("Expected pixel volume: %v, got: %v", expectedPixelVolume, pixelVolume)
	}

	// Test the mutation function
	individual.Mutate()
	// No direct way to test mutation as it's random, so we're skipping it for testing purposes.

	// Test the drawing function
	drawnImg := individual.Draw()
	if drawnImg.Cols() != img.Cols() || drawnImg.Rows() != img.Rows() {
		t.Errorf("Expected drawn image size to be %dx%d, but got %dx%d",
			img.Cols(), img.Rows(), drawnImg.Cols(), drawnImg.Rows())
	}
}
