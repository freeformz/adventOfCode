package main

import (
	"bufio"
	"fmt"
	"os"

	"github.com/freeformz/adventOfCode2022/go/internal/input"
)

const (
	win = 6
	tie = 3
)

var (
	rock     = item{val: 1}
	paper    = item{val: 2}
	scissors = item{val: 3}

	opItems, myItems map[string]item
)

type item struct {
	wins func(item) bool
	val  int
}

func init() {
	rock.wins = func(other item) bool {
		return other.val == scissors.val
	}
	paper.wins = func(other item) bool {
		return other.val == rock.val
	}
	scissors.wins = func(other item) bool {
		return other.val == paper.val
	}

	opItems = map[string]item{
		"A": rock,
		"B": paper,
		"C": scissors,
	}

	myItems = map[string]item{
		"X": rock,
		"Y": paper,
		"Z": scissors,
	}
}

type throw struct {
	op, me item
}

func NewThrow(o, m string) (throw, error) {
	op, ok := opItems[o]
	if !ok {
		return throw{}, fmt.Errorf("invalid opponent item: %s", o)
	}
	me, ok := myItems[m]
	if !ok {
		return throw{}, fmt.Errorf("invalid my item: %s", m)
	}
	return throw{op: op, me: me}, nil
}

// Score for a given throw
func (t throw) Score() int {
	if t.me.wins(t.op) {
		return t.me.val + win
	}
	if t.op.val == t.me.val {
		return t.me.val + tie
	}
	return t.me.val
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
