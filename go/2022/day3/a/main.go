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

	p := lo.Map(
		lo.Map(in, func(s string, _ int) []string { // split each line in 1/2
			return []string{s[0 : len(s)/2], s[len(s)/2:]}
		}),
		func(b []string, _ int) string {
			if len(b) != 2 {
				panic("bad split")
			}
			b = lo.Map(b, func(s string, _ int) string { // find uniq characters in each part
				return string(lo.Uniq([]byte(s)))
			})
			return string(lo.Intersect([]byte(b[0]), []byte(b[1]))) // find intersection of uniq characters
		})

	val := lo.SumBy(p, func(s string) int { // map characters to priority values and sum
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
