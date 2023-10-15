package individual

import (
	"cardioid"
)

type Individual struct {
	Cardioid cardioid.Cardioid
	Img      Img
	Score    float64
}
