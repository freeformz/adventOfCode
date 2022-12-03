package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"

	"github.com/freeformz/adventOfCode2022/go/internal/input"
)

type elf struct {
	calories int
}

func main() {
	in, err := input.Get(os.Args)
	if err != nil {
		fmt.Println(err)
		return
	}
	defer in.Close()

	var ce, me elf
	is := bufio.NewScanner(in)
	for is.Scan() {
		row := is.Text()
		if len(row) == 0 {
			if ce.calories > me.calories {
				me = ce
			}
			ce.calories = 0
			continue
		}
		c, err := strconv.Atoi(row)
		if err != nil {
			fmt.Println(err)
			return
		}
		ce.calories += c
	}
	if ce.calories > me.calories {
		me = ce
	}

	fmt.Println(me.calories)
}
