package input

import (
	"errors"
	"io"
	"os"
	"strings"
)

func Get(args []string) (io.ReadCloser, error) {
	inf := args[1]
	if len(inf) == 0 {
		return nil, errors.New("no input file")
	}

	return os.Open(inf)
}

func GetAsSlice(args []string) ([]string, error) {
	inf := args[1]
	if len(inf) == 0 {
		return nil, errors.New("no input file")
	}

	f, err := os.Open(inf)
	if err != nil {
		return nil, err
	}
	defer f.Close()
	data, err := io.ReadAll(f)
	if err != nil {
		return nil, err
	}
	return strings.Split(string(data), "\n"), nil
}
