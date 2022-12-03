package input

import (
	"errors"
	"io"
	"os"
)

func Get(args []string) (io.ReadCloser, error) {
	inf := args[1]
	if len(inf) == 0 {
		return nil, errors.New("no input file")
	}

	return os.Open(inf)
}
