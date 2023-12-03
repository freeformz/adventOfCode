package main

import "testing"

func TestScore(t *testing.T) {
	tests := []struct {
		a, b   string
		expect int
	}{
		{"A", "Y", 4}, // draw
		{"B", "X", 1}, // lose
		{"C", "Z", 7}, // win
	}

	for _, test := range tests {
		throw, err := NewThrow(test.a, test.b)
		if err != nil {
			t.Fatal("unexpected error:", err)
		}
		if throw.Score() != test.expect {
			t.Errorf("Score(%s, %s) = %d, want %d", test.a, test.b, throw.Score(), test.expect)
		}
	}
}
