package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strconv"

	"github.com/freeformz/adventOfCode2022/go/internal/input"
)

func main() {
	in, err := input.Get(os.Args)
	if err != nil {
		fmt.Println(err)
		return
	}
	defer in.Close()

	elves := []int{0}
	var i int
	is := bufio.NewScanner(in)
	for is.Scan() {
		row := is.Text()
		if len(row) == 0 {
			i++
			elves = append(elves, 0)
			continue
		}
		c, err := strconv.Atoi(row)
		if err != nil {
			fmt.Println(err)
			return
		}
		elves[i] += c
	}

	sort.Sort(sort.Reverse(sort.IntSlice(elves)))

	fmt.Println(elves[0] + elves[1] + elves[2])
}
