package main

import (
	"bufio"
	"fmt"
	"os"

	"github.com/freeformz/adventOfCode2022/go/internal/input"
)

type outcome int

const (
	lose outcome = iota
	draw
	win
)

var (
	rock     = item{val: 1}
	paper    = item{val: 2}
	scissors = item{val: 3}

	opItems  map[string]item
	outcomes = map[string]outcome{
		"X": lose,
		"Y": draw,
		"Z": win,
	}
)

type item struct {
	beats func() item
	lose  func() item
	val   int
}

func init() {
	rock.lose = func() item {
		return paper
	}
	rock.beats = func() item {
		return scissors
	}
	paper.lose = func() item {
		return scissors
	}
	paper.beats = func() item {
		return rock
	}
	scissors.lose = func() item {
		return rock
	}
	scissors.beats = func() item {
		return paper
	}
	opItems = map[string]item{
		"A": rock,
		"B": paper,
		"C": scissors,
	}
}

type throw struct {
	op      item
	outcome outcome
}

func NewThrow(o, m string) (throw, error) {
	op, ok := opItems[o]
	if !ok {
		return throw{}, fmt.Errorf("invalid opponent item: %s", o)
	}
	me, ok := outcomes[m]
	if !ok {
		return throw{}, fmt.Errorf("invalid my item: %s", m)
	}
	return throw{op: op, outcome: me}, nil
}

// Score for a given throw
func (t throw) Score() int {
	switch t.outcome {
	case lose:
		return t.op.beats().val
	case draw:
		return t.op.val + (int(draw) * 3)
	case win:
		return t.op.lose().val + (int(win) * 3)
	default:
		panic("invalid outcome")
	}
}

func main() {
	in, err := input.Get(os.Args)
	if err != nil {
		fmt.Println(err)
		return
	}
	defer in.Close()

	is := bufio.NewScanner(in)
	var score int
	for is.Scan() {
		val := is.Text()
		if len(val) != 3 {
			panic("invalid input")
		}
		throw, err := NewThrow(string(val[0]), string(val[2]))
		if err != nil {
			panic(err)
		}
		score += throw.Score()
	}
	fmt.Println(score)
}
