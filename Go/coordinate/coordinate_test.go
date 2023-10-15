package coordinate

import (
	"reflect"
	"testing"
)

func TestCoordinate(t *testing.T) {
	coordinate := Coordinate{}
	coordinate.SetDecimal(7)
	coordinate.SetBinary("111")
	coordinate.SetGray("101")

	if coordinate.GetDecimal() != 7 {
		t.Errorf("Expected 7, got %v", coordinate.GetDecimal())
	}

	if coordinate.GetBinary() != "111" {
		t.Errorf("Expected 111, got %v", coordinate.GetBinary())
	}

	if coordinate.GetGray() != "101" {
		t.Errorf("Expected 101, got %v", coordinate.GetGray())
	}

	decimalToBinaryResult := coordinate.DecimalToBinary(5)
	if decimalToBinaryResult != "101" {
		t.Errorf("Expected 101, got %v", decimalToBinaryResult)
	}

	binaryToDecimalResult := coordinate.BinaryToDecimal("101")
	if binaryToDecimalResult != 5 {
		t.Errorf("Expected 5, got %v", binaryToDecimalResult)
	}

	binaryToGrayResult := coordinate.BinaryToGray("101")
	if binaryToGrayResult != "111" {
		t.Errorf("Expected 111, got %v", binaryToGrayResult)
	}

	grayToBinaryResult := coordinate.GrayToBinary("100")
	if grayToBinaryResult != "111" {
		t.Errorf("Expected 111, got %v", grayToBinaryResult)
	}

	decimalToGrayResult := coordinate.DecimalToGray(5)
	if decimalToGrayResult != "111" {
		t.Errorf("Expected 111, got %v", decimalToGrayResult)
	}

	flipBitResult := coordinate.FlipBit("101", 1)
	if flipBitResult != "111" {
		t.Errorf("Expected 111, got %v", flipBitResult)
	}

	mutateResult := coordinate.Mutate()
	if !reflect.DeepEqual(mutateResult, coordinate.GetGray()) {
		t.Errorf("Expected %v, got %v", mutateResult, coordinate.GetGray())
	}

	coordinate.SetCoordinate(5)
	if coordinate.GetDecimal() != 5 {
		t.Errorf("Expected 5, got %v", coordinate.GetDecimal())
	}
	if coordinate.GetBinary() != "101" {
		t.Errorf("Expected 101, got %v", coordinate.GetBinary())
	}
	if coordinate.GetGray() != "111" {
		t.Errorf("Expected 111, got %v", coordinate.GetGray())
	}
}
