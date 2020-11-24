package sample;

import com.sun.prism.Graphics;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Neighbourhood {
    int[][] neumannCells;
    int[][] mooreCells;
    int[][] pentLeftCells;
    int[][] pentRightCells;
    int[][] pentBottomCells;
    int[][] pentTopCells;
    int[][] hexLeftCells;
    int[][] hexRightCells;




    public Neighbourhood() {
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

    public void generationDevelopment(int size, int[][] neighbourCells,
                                      Nucleation nucleation,
                                      Nucleation newNucleation,
                                      GraphicsContext graphicsContext, int checkBoxSelected) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                checkAndFillCells(x, y, size, neighbourCells,
                  nucleation,newNucleation, graphicsContext, checkBoxSelected);
            }
        }
    }

    public void checkAndFillCells(int x, int y, int size, int[][] neighbourCells,
                                  Nucleation nucleation,
                                  Nucleation newNucleation,
                                  GraphicsContext graphicsContext,
                                  int checkBoxSelected) {
        HashMap<Point, Integer> checkingMaxNeigbours = new HashMap<>();
        int j = 0;
        int state = -1;

        WritableImage snap = graphicsContext.getCanvas()
                .snapshot(null, null);
        if(nucleation.getStateAbsorbingBC(x,y) == 0 || nucleation.getStatePeriodicBC(x, y, size) == 0) {

            for (int i = 0; i < neighbourCells.length; i++) {
                int iTemp = x + neighbourCells[i][j];
                int jTemp = y + neighbourCells[i][j + 1];

                if(checkBoxSelected == 1){
                    state = nucleation.getStatePeriodicBC(iTemp, jTemp, size);

                } else if(checkBoxSelected == 0){
                    state = nucleation.getStateAbsorbingBC(iTemp, jTemp);
                }
                Point point = new Point(iTemp, jTemp);
                checkingMaxNeigbours.put(point, state);
            }

            Point maxPoint = findTheMaxNeighbour(checkingMaxNeigbours);
            if (checkingMaxNeigbours.get(maxPoint) != 0) {
                newNucleation.makeCellAlive(x, y, checkingMaxNeigbours.get(maxPoint));

                int xpos = (int) ((maxPoint.getX() * 900 / size) + (0.5 * (900 / size)));
                int ypos = (int) ((maxPoint.getY() * 900 / size) + (0.5 * (900 / size)));

                int color = snap.getPixelReader().getArgb(xpos, ypos);

                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;

                graphicsContext.setFill(Color.rgb(red, green, blue));
                graphicsContext.fillRect(x, y, 1, 1);
            }
        }
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
}
