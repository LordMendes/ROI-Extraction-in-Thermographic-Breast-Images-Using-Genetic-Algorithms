package cardioid

import (
	"coordinate"
	"math"
	"math/rand"
)

var MUTATION_RATE = 0.5

type Cardioid struct {
	XCoordinate coordinate.Coordinate
	YCoordinate coordinate.Coordinate
	Size        coordinate.Coordinate
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
	return (x-c.XCoordinate.GetDecimal())*(x-c.XCoordinate.GetDecimal())+(y-c.YCoordinate.GetDecimal())*(y-c.YCoordinate.GetDecimal()) <= int(math.Pow(2, float64(c.Size.GetDecimal())))
}

func (c *Cardioid) WillMutate() bool {
	return rand.Float64() < MUTATION_RATE
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
