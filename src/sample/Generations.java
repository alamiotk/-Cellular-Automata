package sample;

public class Generations {
    int width;
    int height;
    int[][] grid;

    public Generations(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[width][height];
    }
    public void makeCellAlive(int x, int y) {
        this.grid[x][y] = 1;
    }

    public int getState(int x, int y) {
        if (x < 0 || x >= width) {
            return 0;
        }
        if (y < 0 || y >= height) {
            return 0;
        }
        return this.grid[x][y];
    }

    public int calculateNeighbours(int x, int y) {
        int sumAliveNeighbours = 0;

        sumAliveNeighbours += getState(x - 1, y - 1);
        sumAliveNeighbours += getState(x, y - 1);
        sumAliveNeighbours += getState(x + 1, y - 1);
        sumAliveNeighbours += getState(x - 1, y);
        sumAliveNeighbours += getState(x + 1, y);
        sumAliveNeighbours += getState(x - 1, y + 1);
        sumAliveNeighbours += getState(x, y + 1);
        sumAliveNeighbours += getState(x + 1, y + 1);

        return sumAliveNeighbours;
    }

    public void step() {
        int[][] newBoard = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int aliveNeighbours = calculateNeighbours(x, y);

                if (getState(x, y) == 1) {
                    if (aliveNeighbours < 2) {
                        newBoard[x][y] = 0;
                    } else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
                        newBoard[x][y] = 1;
                    } else if (aliveNeighbours > 3) {
                        newBoard[x][y] = 0;
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        newBoard[x][y] = 1;
                    }
                }
            }
        }
        this.grid = newBoard;
    }

}
