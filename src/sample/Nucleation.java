package sample;

public class Nucleation {
    int width;
    int height;
    int[][] grid;

    public Nucleation(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[width][height];
    }
    public void makeCellAlive(int x, int y, int stage){
        this.grid[x][y] = stage;
    }

    public void setStage(int x, int y){
        this.grid[x][y] = 0;
    }

    public int absorbingBC(int x, int y) {
        if (x < 0 || x >= width) {
            return 0;
        }
        if (y < 0 || y >= height) {
            return 0;
        }
        return this.grid[x][y];
    }

    public int periodicBC(int x, int y, int valueOfCells){
        if (x < 0 && y < 0) {
            return this.grid[valueOfCells - 1][valueOfCells - 1];
        }
        if (x > width && y < 0) {
            return this.grid[0][valueOfCells - 1];
        }
        if (x > width && y > height) {
            return this.grid[0][0];
        }
        if (x < 0 && y > height) {
            return this.grid[valueOfCells - 1][0];
        }
        if (x == width && (y >= 0 && y < height)) {
            return this.grid[0][y];
        }
        if (x < 0 && (y >= 0 && y < height)) {
            return this.grid[valueOfCells - 1][y];
        }
        if (y == height && (x >= 0 && x < width)) {
            return this.grid[x][0];
        }
        if (y < 0  && (x >= 0 && x < width)) {
            return this.grid[x][valueOfCells - 1];
        }
        return this.grid[x][y];
    }

}
