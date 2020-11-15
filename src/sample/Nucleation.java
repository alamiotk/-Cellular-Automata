package sample;

public class Nucleation {
    int width;
    int height;
    int[][] grid;

    public Nucleation(int width, int height, int[][] grid) {
        this.width = width;
        this.height = height;
        this.grid = new int[width][height];
    }
    public void makeCellAlive(int x, int y) {
        this.grid[x][y] = 1;
    }

    public void makeCellAliveHomogeneous(int x, int y, int stage){
        this.grid[x][y] = stage;
    }

    public int absorbingBC(int x, int y){
        if (x < 0 || x >= width) {
            return 0;
        }
        if (y < 0 || y >= height) {
            return 0;
        }
        return this.grid[x][y];
    }
    public int periodicBC(){
//        if (x < 0 || x >= width) {
//            return 0;
//        }
//        if (y < 0 || y >= height) {
//            return 0;
//        }
//        return this.grid[x][y];
    return 0;
    }

}
