package sample;

import com.sun.prism.Graphics;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.*;

public class Neighbourhood {
    int[][] neumannCells;
    int[][] mooreCells;
    int[][] pentLeftCells;
    int[][] pentRightCells;
    int[][] pentBottomCells;
    int[][] pentTopCells;
    int[][] hexLeftCells;
    int[][] hexRightCells;
    int energy = 0;

    List<Point> cellsBoundary = new ArrayList<>();
    List<Point> cellsNeigbour = new ArrayList<>();
    HashMap<Point, Integer> tmp = new HashMap<>();



    public Neighbourhood(){

        this.neumannCells = new int[][] {{-1, 0},
                                        {0, -1},
                                        {1, 0},
                                        {0, 1}};


        this.mooreCells = new int[][] {{-1, 1},
                                        {-1, 0},
                                        {-1, -1},
                                        {0, -1},
                                        {1, -1},
                                        {1, 0},
                                        {1, 1},
                                        {0, 1}};

        this.pentLeftCells = new int[][] {{0, 1},
                                        {-1, 1},
                                        {-1, 0},
                                        {-1, -1},
                                        {0, -1}};

        this.pentBottomCells = new int[][] {{1, 0},
                                        {1, 1},
                                        {0, 1},
                                        {-1, 1},
                                        {-1, 0}};

        this.pentTopCells = new int[][] {{-1, 0},
                                        {-1, -1},
                                        {0, -1},
                                        {1, -1},
                                        {1, 0}};

        this.hexLeftCells = new int[][] {{0, 1},
                                        {-1, 1},
                                        {-1, 0},
                                        {0, -1},
                                        {1, -1},
                                        {1, 0}};

        this.hexRightCells = new int[][] {{-1, 0},
                                        {-1, -1},
                                        {0, -1},
                                        {1, 0},
                                        {1, 1},
                                        {0, 1}};
    }

    public void growth(int[][] neighbourCells,
                       Nucleation nucleation,
                       Nucleation newNucleation,
                       int checkBoxSelected, WritableImage snap, int size, GraphicsContext graphicsContext) {


        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                checkAndFillCellsGrowth(x, y, neighbourCells,
                                nucleation,newNucleation, checkBoxSelected, snap, size, graphicsContext);

            }
        }
    }

    public void checkAndFillCellsGrowth(int x, int y, int[][] neighbourCells,
                                        Nucleation nucleation,
                                        Nucleation newNucleation,
                                        int checkBoxSelected, WritableImage snap, int size, GraphicsContext graphicsContext) {
        int j = 0;
        int state = -1;

        HashMap<Point, Integer> checkingMaxNeigbours = new HashMap<>();
        if(nucleation.getStateAbsorbingBC(x,y) == 0
                || nucleation.getStatePeriodicBC(x, y, size) == 0) {
            checkNeigboursLoop(x, y, neighbourCells, nucleation, checkBoxSelected, 1, size,
                    checkingMaxNeigbours);

            Point maxPoint = findTheMaxNeighbour(checkingMaxNeigbours);
            if (checkingMaxNeigbours.get(maxPoint) != 0) {
                newNucleation.makeCellAlive(x, y, checkingMaxNeigbours.get(maxPoint));
                int[] arr;
                int xpos, ypos;
                arr = nucleation.getCordinatesBCPeriodic(maxPoint.getX(),
                        maxPoint.getY(), size);
                xpos = arr[0];
                ypos = arr[1];

                xpos = (int) ((xpos * 900 / size) + (0.5 * (900 / size)));
                ypos = (int) ((ypos * 900 / size) + (0.5 * (900 / size)));

                int color = snap.getPixelReader().getArgb(xpos, ypos);
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;

                graphicsContext.setFill(Color.rgb(red, green, blue));
                graphicsContext.fillRect(x, y, 1, 1);
            }
        }
    }



    public int checkNeigboursLoop(int x, int y, int[][] neighbourCells, Nucleation nucleation,
                                          int checkBoxSelected, int functionChoose, int size,
                                  HashMap<Point, Integer> checkingMaxNeigbours) {
        int j = 0;
        int state = -1;
        int deltaKroneckera = -1;
        int stateOfMyCell = nucleation.getStateAbsorbingBC(x, y);
        int res = 0;
        energy = 0;


        for (int i = 0; i < neighbourCells.length; i++) {
            int iTemp = x + neighbourCells[i][j];
            int jTemp = y + neighbourCells[i][j + 1];

            state = checkStateBC(iTemp, jTemp, nucleation, checkBoxSelected, size);

            switch(functionChoose){
                case 1:
                    addPointToMaxList(iTemp, jTemp, state, checkingMaxNeigbours);
                    break;

                case 2:
                    int tmp = monteCarloCheckTheEdge(state, stateOfMyCell, x, y);
                    if(tmp == 1){
                        return 1;
                    }
                    break;

                case 3:
                    energy += monteCarloCalculateEnergy(energy, state, stateOfMyCell, deltaKroneckera);
                    res += energy;
                    break;

                case 4:
                    monteCarloChangeState(iTemp, jTemp, state, stateOfMyCell, cellsNeigbour);
                    break;

                default:
                        break;
            }
        }
        return res;
    }

    public int checkStateBC(int x, int y, Nucleation nucleation, int checkBoxSelected, int size) {
        int state = -1;

        if(checkBoxSelected == 1){
            state = nucleation.getStatePeriodicBC(x, y, size);

        } else if(checkBoxSelected == 0){
            state = nucleation.getStateAbsorbingBC(x, y);
        }
        return state;
    }

    public void addPointToMaxList(int x, int y, int state, HashMap<Point, Integer> checkingMaxNeigbours) {
        Point point = new Point(x, y);
        checkingMaxNeigbours.put(point, state);
    }

    public Point findTheMaxNeighbour(HashMap<Point, Integer> checkingMaxNeigbours) {
        Map.Entry<Point, Integer> maxEntry = null;

        for (Map.Entry<Point, Integer> entry : checkingMaxNeigbours.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

    public int monteCarloCheckTheEdge(int state, int stateOfMyCell, int x, int y){
        if(state != stateOfMyCell){
            Point point = new Point(x,y,stateOfMyCell);
            cellsBoundary.add(point);
            return 1;
        }
        return 0;
    }

    public int monteCarloCalculateEnergy(int energy, int state, int stateOfMyCell, int deltaKroneckera) {
        if(stateOfMyCell == state){
            deltaKroneckera = 1;
        }
        else {
            deltaKroneckera = 0;
        }
        energy += deltaKroneckera;
        return energy;
    }

    public void monteCarloChangeState(int x, int y, int state, int stateOfMyCell, List<Point> cellsNeigbour) {
        if(state != stateOfMyCell){
            Point pointNeighbour = new Point(x, y, state);
            cellsNeigbour.add(pointNeighbour);
        }

    }


    // -------------------------------------------------------------------------------------------------------------


    public void crossingTheGridMonteCarlo(Nucleation nucleation, Nucleation newNucleation,
                                          int checkBoxSelected,
                                          int size, GraphicsContext graphicsContext,
                                          WritableImage snap) {

        Random random = new Random();

        cellsBoundary.clear();
        cellsNeigbour.clear();


        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                checkNeigboursLoop(x, y, mooreCells, nucleation, checkBoxSelected, 2,
                        size, tmp);
            }
        }

        int energyCalc = 0;
        int newEnergy = 0;
        Point randomPointToChangeState;
        double probability = 0;

        while(cellsBoundary.size() > 0) {
            energyCalc = 0;
            newEnergy = 0;

            System.out.println(cellsBoundary.size());

            randomPointToChangeState = cellsBoundary.get(random.nextInt(cellsBoundary.size()));

            int tempState = randomPointToChangeState.getState();

            energyCalc = checkNeigboursLoop(randomPointToChangeState.getX(), randomPointToChangeState.getY(),
                    mooreCells, nucleation, checkBoxSelected, 3, size, tmp);

             checkNeigboursLoop(randomPointToChangeState.getX(), randomPointToChangeState.getY(),
                    mooreCells, nucleation, checkBoxSelected, 4, size, tmp);

            Point pointNeighbour;
            pointNeighbour = cellsNeigbour.get(random.nextInt(cellsNeigbour.size()));

            randomPointToChangeState.setState(pointNeighbour.getState());


            newEnergy = checkNeigboursLoop(randomPointToChangeState.getX(), randomPointToChangeState.getY(),
                    mooreCells, nucleation, checkBoxSelected, 3, size, tmp);


            int deltaEnergy = newEnergy - energyCalc;

            if(deltaEnergy <= 0){
                probability = 1;
            }
            else {
                probability = Math.exp(-deltaEnergy/(-0.6));
            }



            if(probability == 1) {
                newNucleation.makeCellAlive(randomPointToChangeState.getX(), randomPointToChangeState.getY(),
                        randomPointToChangeState.getState());

                int[] arr;
                int xpos, ypos;
                arr = nucleation.getCordinatesBCPeriodic(pointNeighbour.getX(),
                        pointNeighbour.getY(), size);
                xpos = arr[0];
                ypos = arr[1];

                xpos = (int) ((xpos * 900 / size) + (0.5 * (900 / size)));
                ypos = (int) ((ypos * 900 / size) + (0.5 * (900 / size)));

                int color = snap.getPixelReader().getArgb(xpos, ypos);
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;

                graphicsContext.setFill(Color.rgb(red, green, blue));
                graphicsContext.fillRect(randomPointToChangeState.getX(),
                        randomPointToChangeState.getY(), 1, 1);
                randomPointToChangeState.setState(tempState);

                cellsBoundary.remove(randomPointToChangeState);
            }
            else {
                System.out.println("4");
                randomPointToChangeState.setState(tempState);
            }

        }

    }


}
