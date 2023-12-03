package main

import (
	"fmt"
	"os"

	"github.com/samber/lo"

	"github.com/freeformz/adventOfCode2022/go/internal/input"
)

func main() {
	in, err := input.GetAsSlice(os.Args)
	if err != nil {
		fmt.Println(err)
		return
	}

	badges := lo.Map(lo.Chunk(in, 3), func(bags []string, _ int) string {
		if len(bags) != 3 {
			fmt.Printf("%#v\n", bags)
			panic("bad chunk")
		}

		bags = lo.Map(bags, func(s string, _ int) string { // find uniq characters in each part
			return string(lo.Uniq([]byte(s)))
		})

		return string( // find the common character
			lo.Intersect(
				lo.Intersect([]byte(bags[0]), []byte(bags[1])),
				[]byte(bags[2]),
			),
		)
	})
	val := lo.SumBy(badges, func(s string) int { // sum based on ascii value
		switch s[0] {
		case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z':
			return int(s[0]) - 96
		case 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z':
			return int(s[0]) - 38
		default:
			panic("bad char")
		}
	})

	fmt.Println(val)
}
