package coordinate

import (
	"math"
	"math/rand"
	"strconv"
)

type Coordinate struct {
	//decimal value of the coordinate
	decimal int
	//binary array with ARRAY_MAX_SIZE size that represents the decimal value
	binary string
	//gray code array with ARRAY_MAX_SIZE size that represents the decimal value
	gray string
}

const ARRAY_MAX_SIZE = 10

func NewCoordinate(decimal int) Coordinate {
	newCoordinate := Coordinate{decimal, "", ""}
	newCoordinate.SetCoordinate(decimal)
	return newCoordinate
}

func (c *Coordinate) SetDecimal(decimal int) {
	c.decimal = decimal
}

func (c *Coordinate) SetBinary(binary string) {
	c.binary = binary
}

func (c *Coordinate) SetGray(gray string) {
	c.gray = gray
}

func (c *Coordinate) SetCoordinate(decimal int) {
	c.SetDecimal(decimal)
	c.SetBinary(c.DecimalToBinary(decimal))
	c.SetGray(c.DecimalToGray(decimal))
}

func (c *Coordinate) GetDecimal() int {
	return c.decimal
}

func (c *Coordinate) GetBinary() string {
	return c.binary
}

func (c *Coordinate) GetGray() string {
	return c.gray
}

func (c *Coordinate) GetCoordinate() (int, string, string) {
	return c.decimal, c.binary, c.gray
}

func (c *Coordinate) DecimalToBinary(decimal int) string {
	binary := strconv.FormatInt(int64(decimal), 2)
	// Pad the binary string with leading zeros if necessary
	for len(binary) < ARRAY_MAX_SIZE {
		binary = "0" + binary
	}
	return binary
}

func (c *Coordinate) BinaryToGray(binary string) string {
	var gray string
	gray = string(binary[0])
	for i := 1; i < len(binary); i++ {
		if binary[i] == binary[i-1] {
			gray += "0"
		} else {
			gray += "1"
		}
	}
	return gray
}

func (c *Coordinate) GrayToBinary(gray string) string {
	var binary string
	binary = string(gray[0])
	for i := 1; i < len(gray); i++ {
		if gray[i] == binary[i-1] {
			binary = binary + "0"
		} else {
			binary = binary + "1"
		}
	}
	return binary
}

func (c *Coordinate) DecimalToGray(decimal int) string {
	binary := c.DecimalToBinary(decimal)
	gray := c.BinaryToGray(binary)
	return gray
}

func (c *Coordinate) BinaryToDecimal(binary string) int {
	var decimal int
	for i := 0; i < len(binary); i++ {
		if binary[i] == '1' {
			decimal = decimal + int(math.Pow(2, float64(len(binary)-i-1)))
		}
	}
	return decimal
}

func (c *Coordinate) GrayToDecimal(gray string) int {
	binary := c.GrayToBinary(gray)
	decimal := c.BinaryToDecimal(binary)
	return decimal
}

func (c *Coordinate) FlipBit(binary string, index int) string {
	var newBinary string
	for i := 0; i < len(binary); i++ {
		if i == index {
			if binary[i] == '0' {
				newBinary = newBinary + "1"
			} else {
				newBinary = newBinary + "0"
			}
		} else {
			newBinary = newBinary + string(binary[i])
		}
	}
	return newBinary
}

func (c *Coordinate) Mutate() string {
	randIndex := rand.Intn(len(c.gray))
	newGray := c.FlipBit(c.gray, randIndex)
	newbinary := c.GrayToBinary(newGray)
	newDecimal := c.BinaryToDecimal(newbinary)
	c.SetDecimal(newDecimal)
	c.SetBinary(newbinary)
	c.SetGray(newGray)
	return newGray
}
