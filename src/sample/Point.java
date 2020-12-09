package sample;

public class Point {
    int x;
    int y;
    int state;
    double dislocation;
    int recrystallization;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public Point(int x, int y, double dislocation) {
        this.x = x;
        this.y = y;
        this.dislocation = dislocation;
    }

    public Point(int x, int y, int state, double dislocation, int recrystallization) {
        this.x = x;
        this.y = y;
        this.state = state;
        this.dislocation = dislocation;
        this.recrystallization = recrystallization;
    }

    public int getRecrystallization() {
        return recrystallization;
    }

    public void setRecrystallization(int recrystallization) {
        this.recrystallization = recrystallization;
    }

    public double getDislocation() {
        return dislocation;
    }

    public void setDislocation(double dislocation) {
        this.dislocation = dislocation;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
