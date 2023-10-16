package cardioid

import (
	"coordinate"
	"math"
	"math/rand"
)

var MUTATION_RATE = 0.25

type Cardioid struct {
	XCoordinate coordinate.Coordinate
	YCoordinate coordinate.Coordinate
	Size        coordinate.Coordinate
}

func NewCardioid(x int, y int, size int) Cardioid {
	return Cardioid{coordinate.NewCoordinate(x), coordinate.NewCoordinate(y), coordinate.NewCoordinate(size)}
}

func (c *Cardioid) InitCardioid(maxX int, maxY int, maxSize int) {
	c.XCoordinate.SetCoordinate(rand.Intn(maxX))
	c.YCoordinate.SetCoordinate(rand.Intn(maxY))
	c.Size.SetCoordinate(rand.Intn(maxSize))
}

func (c *Cardioid) GetXCoordinate() *coordinate.Coordinate {
	return &c.XCoordinate
}

func (c *Cardioid) GetYCoordinate() *coordinate.Coordinate {
	return &c.YCoordinate
}

func (c *Cardioid) GetSize() *coordinate.Coordinate {
	return &c.Size
}

func (c *Cardioid) SetXCoordinate(x int) {
	c.XCoordinate.SetCoordinate(x)
}

func (c *Cardioid) SetYCoordinate(y int) {
	c.YCoordinate.SetCoordinate(y)
}

func (c *Cardioid) SetSize(size int) {
	c.Size.SetCoordinate(size)
}

func (c *Cardioid) IsInsideCardioid(x int, y int) bool {
	size := c.Size.GetDecimal()
	x_c := c.XCoordinate.GetDecimal()
	y_c := c.YCoordinate.GetDecimal()
	r := float64(size / 2)

	distance := math.Sqrt(math.Pow(float64(x-x_c), 2) + math.Pow(float64(y-y_c), 2))
	theta := math.Atan2(float64(y-y_c), float64(x-x_c))
	cardioidEquation := math.Pow(distance, 2) <= math.Pow(r, 2)*(1-math.Sin(theta))
	return cardioidEquation
}

func (c *Cardioid) WillMutate() bool {
	return rand.Float64() <= MUTATION_RATE
}

func (c *Cardioid) Mutate() {
	willXMutate := c.WillMutate()
	willYMutate := c.WillMutate()
	willSizeMutate := c.WillMutate()
	if willXMutate {
		c.XCoordinate.Mutate()
	}
	if willYMutate {
		c.YCoordinate.Mutate()
	}
	if willSizeMutate {
		c.Size.Mutate()
	}
}
