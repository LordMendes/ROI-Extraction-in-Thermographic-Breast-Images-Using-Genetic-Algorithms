package cardioid

import (
	"math/rand"
	"reflect"
	"testing"
)

func TestCardioid(t *testing.T) {
	card := Cardioid{}

	// Test SetXCoordinate, SetYCoordinate, SetSize, GetXCoordinate, GetYCoordinate, GetSize
	card.SetXCoordinate(3)
	if card.GetXCoordinate().GetDecimal() != 3 {
		t.Errorf("Expected 3, got %v", card.GetXCoordinate().GetDecimal())
	}

	card.SetYCoordinate(4)
	if card.GetYCoordinate().GetDecimal() != 4 {
		t.Errorf("Expected 4, got %v", card.GetYCoordinate().GetDecimal())
	}

	card.SetSize(5)
	if card.GetSize().GetDecimal() != 5 {
		t.Errorf("Expected 5, got %v", card.GetSize().GetDecimal())
	}

	// Test IsInsideCardioid
	if !card.IsInsideCardioid(3, 4) {
		t.Errorf("Expected true, got false")
	}

	if card.IsInsideCardioid(8, 8) {
		t.Errorf("Expected false, got true")
	}

	// Test WillMutate
	rand.Seed(1) // Setting seed for consistent results in tests
	if card.WillMutate() {
		t.Errorf("Expected false, got true")
	}

	rand.Seed(2)
	if !card.WillMutate() {
		t.Errorf("Expected true, got false")
	}

	// Test Mutate
	originalX := card.GetXCoordinate().GetDecimal()
	originalY := card.GetYCoordinate().GetDecimal()
	originalSize := card.GetSize().GetDecimal()

	card.Mutate()
	if reflect.DeepEqual(originalX, card.GetXCoordinate().GetDecimal()) && reflect.DeepEqual(originalY, card.GetYCoordinate().GetDecimal()) && reflect.DeepEqual(originalSize, card.GetSize().GetDecimal()) {
		t.Errorf("Expected the coordinates to mutate, but they remained unchanged")
	}
}
