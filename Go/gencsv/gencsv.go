package gencsv

import (
	"encoding/csv"
	"os"
	"strconv"
)

// Data structure
type Data struct {
	Gen   int
	X     int
	Y     int
	Size  int
	Score int
	Time  int64
}

// Method to generate CSV
func GenerateCSV(filename string, data []Data) error {
	file, err := os.Create(filename)
	if err != nil {
		return err
	}
	defer file.Close()

	writer := csv.NewWriter(file)
	defer writer.Flush()

	// Writing header
	header := []string{"Gen", "X", "Y", "Size", "Score", "Time"}
	err = writer.Write(header)
	if err != nil {
		return err
	}

	// Writing data
	for _, d := range data {
		record := []string{
			strconv.Itoa(d.Gen),
			strconv.Itoa(d.X),
			strconv.Itoa(d.Y),
			strconv.Itoa(d.Size),
			strconv.Itoa(d.Score),
			strconv.FormatInt(d.Time, 10)}

		if err := writer.Write(record); err != nil {
			return err
		}
	}
	return nil
}
