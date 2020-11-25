package sample;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nucleation {
    int width;
    int height;
    int[][] grid;
    int cellsCounter = 0;

    public Nucleation(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new int[width][height];
    }
    public void makeCellAlive(int x, int y, int stage){
        this.grid[x][y] = stage;
        cellsCounter++;
    }

    public void setStage(int x, int y){
        this.grid[x][y] = 0;
    }

    public int getStateAbsorbingBC(int x, int y) {
        if (x < 0 || x >= width) {
            return 0;
        }
        if (y < 0 || y >= height) {
            return 0;
        }
        return this.grid[x][y];
    }

    public int getStatePeriodicBC(int x, int y, int size){
        if (x < 0 && y < 0) {

            return this.grid[size - 1][size - 1];
        }
        if (x >= size && y < 0) {
            //System.out.println("drugie: ");
            return this.grid[0][size - 1];
        }
        if (x >= size && y >= size) {
           // System.out.println("trzecie: ");
            return this.grid[0][0];
        }
        if (x < 0 && y >= size) {
          ///  System.out.println("czwarte: ");
            return this.grid[size - 1][0];
        }
        if (x >= size && y <=size-1) {
          //  System.out.println("piate: ");
            return this.grid[0][y];
        }
        if (x < 0 && y <= size-1) {
          //  System.out.println("szósye: ");
            return this.grid[size - 1][y];
        }
        if (y >= size && x <= size-1) {
          //  System.out.println("siódme: ");
            return this.grid[x][0];
        }
        if (y < 0  && x <= size-1) {
          //  System.out.println("ósme: ");
            return this.grid[x][size - 1];
        }
     //   System.out.println("ostatnie: ");
        return this.grid[x][y];
    }

    public int[] getCordinatesBCPeriodic(int x, int y, int size) {
        if (x < 0 && y < 0) {

            return new int[]{size - 1, size - 1};
        }
        if (x >= size && y < 0) {
            return new int[]{0, size - 1};
        }
        if (x >= size && y >= size) {
            return new int[]{0, 0};
        }
        if (x < 0 && y >= size) {
            return new int[]{size - 1, 0};
        }
        if (x >= size && y <= size-1) {

            return new int[]{0, y};
        }
        if (x < 0 && y <= size-1) {
            return new int[]{size - 1, y};
        }
        if (y >= size && x <= size-1) {
            return new int[]{x, 0};
        }
        if (y < 0  &&  x <= size-1) {

            return new int[]{x, size - 1};
        }
       // System.out.println(x + " " + y);
        return new int[]{x,y};
    }

    public void homogeousNucleation(int size, int aliveCellsInColumn,
                                    int aliveCellsInRow) {
        int iColumn = (size/aliveCellsInColumn);
        int jRow = (size/aliveCellsInRow);

        if(size % aliveCellsInColumn != 0){
            iColumn += 1;
        }
        if(size % aliveCellsInRow != 0){
            jRow += 1;
        }

        int stage = 1;
        for(int i = 0; i < size; i += jRow){
            for(int j = 0; j < size; j += iColumn) {
                makeCellAlive(i, j, stage);
                stage++;
            }
        }
    }



    public int radiusNucleation(int size, int aliveRadiusCells,
                                 int radius, Random random){
        List<Point> listOfPoints = new ArrayList<>();
        int xRandom = random.nextInt(size);
        int yRandom = random.nextInt(size);
        listOfPoints.clear();

        Point p1 = new Point(xRandom, yRandom);
        listOfPoints.add(p1);

        int stage = 1;
        makeCellAlive(xRandom, yRandom, stage);
        stage++;

        int aliveCellsInGrid = 1;
        int tmp;
        int counter = 0;

        while(aliveCellsInGrid < aliveRadiusCells){
            tmp = 0;
            counter++;
            if(counter == size){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Too big radius or number of cells!");
                alert.showAndWait();
                return 0;
            }
            xRandom = random.nextInt(size);
            yRandom = random.nextInt(size);

            for(int j = 0; j < listOfPoints.size(); j++){
                int distance = (int)Math.sqrt(
                        Math.pow((listOfPoints.get(j).getX() - xRandom), 2) +
                                Math.pow((listOfPoints.get(j).getY() - yRandom), 2));
                if(distance <= radius){
                    tmp++;
                }
                else if(tmp == 0 && j == listOfPoints.size()-1){
                    makeCellAlive(xRandom, yRandom, stage);
                    Point nextPoint = new Point(xRandom, yRandom);
                    listOfPoints.add(nextPoint);
                    stage++;
                    aliveCellsInGrid++;
                }
            }
        }
        return 1;
    }

    public void randomNucleation(int size, int aliveRandomCells, Random random) {
        int stage = 1;
        for(int i = 0; i < aliveRandomCells; i++){
            makeCellAlive(random.nextInt(size),
                    random.nextInt(size), stage);
            stage++;
        }
    }

    public void clickNucleation(Canvas canvas, GraphicsContext graphicsContext,
                                int size, int stage, Random random) {
        final int[] a = {100};
        final int[] b = { 60 };
        final int[] c = { 150 };
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.setFill(Color.rgb(a[0], b[0], c[0]));
                int x = (int) event.getX();
                int y = (int) event.getY();
                makeCellAlive(x* size /900,y* size /900, stage);
                graphicsContext.fillRect(x* size /900,y* size /900,1,1);
                a[0] = random.nextInt(255);
                b[0] = random.nextInt(255);
                c[0] = random.nextInt(255);
            }
        });
    }
}
