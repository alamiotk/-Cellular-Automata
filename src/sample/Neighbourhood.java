package sample;

import com.sun.prism.Graphics;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Neighbourhood {
    public Neighbourhood() {
    }

    public void neumannNeighbour(Nucleation nucleation,
                                 Nucleation newNucleation,
                                 GraphicsContext graphicsContext, int size){

        int[][] neumannCells = {{-1, 0},
                                {0, -1},
                                {1, 0},
                                {0, 1}};

        generationDevelopment(size, neumannCells, nucleation, newNucleation,
                graphicsContext);
    }

    public void  mooreNeighbour(Nucleation nucleation,
                                Nucleation newNucleation,
                                GraphicsContext graphicsContext, int size) {
        int[][] mooreCells = {{-1, 1},
                            {-1, 0},
                            {-1, -1},
                            {0, -1},
                            {1, -1},
                            {1, 0},
                            {1, 1},
                            {0, 1}};
        generationDevelopment(size, mooreCells, nucleation, newNucleation,
                graphicsContext);
    }

    public void  pentLeftNeigbour(Nucleation nucleation,
                                Nucleation newNucleation,
                                GraphicsContext graphicsContext, int size) {
        int[][] pentLeftCells = {{0, 1},
                            {-1, 1},
                            {-1, 0},
                            {-1, -1},
                            {0, -1}};
        generationDevelopment(size, pentLeftCells, nucleation, newNucleation,
                graphicsContext);
    }

    public void  pentRightNeigbour(Nucleation nucleation,
                                  Nucleation newNucleation,
                                  GraphicsContext graphicsContext, int size) {
        int[][] pentRightCells = {{0, -1},
                                {1, -1},
                                {1, 0},
                                {1, 1},
                                {0, 1}};
        generationDevelopment(size, pentRightCells, nucleation, newNucleation,
                graphicsContext);
    }

    public void  pentBottomNeigbour(Nucleation nucleation,
                                   Nucleation newNucleation,
                                   GraphicsContext graphicsContext, int size) {
        int[][] pentBottomCells = {{1, 0},
                                {1, 1},
                                {0, 1},
                                {-1, 1},
                                {-1, 0}};
        generationDevelopment(size, pentBottomCells, nucleation, newNucleation,
                graphicsContext);
    }

    public void  pentTopNeigbour(Nucleation nucleation,
                                    Nucleation newNucleation,
                                    GraphicsContext graphicsContext, int size) {
        int[][] pentTopCells = {{-1, 0},
                            {-1, -1},
                            {0, -1},
                            {1, -1},
                            {1, 0}};
        generationDevelopment(size, pentTopCells, nucleation, newNucleation,
                graphicsContext);
    }

    public void  hexLeftNeigbour(Nucleation nucleation,
                                 Nucleation newNucleation,
                                 GraphicsContext graphicsContext, int size) {
        int[][] hexLeftCells = {{0, 1},
                            {-1, 1},
                            {-1, 0},
                            {0, -1},
                            {1, -1},
                            {1, 0}};
        generationDevelopment(size, hexLeftCells, nucleation, newNucleation,
                graphicsContext);
    }

    public void  hexRightNeigbour(Nucleation nucleation,
                                 Nucleation newNucleation,
                                 GraphicsContext graphicsContext, int size) {
        int[][] hexRightCells = {{-1, 0},
                                {-1, -1},
                                {0, -1},
                                {1, 0},
                                {1, 1},
                                {0, 1}};
        generationDevelopment(size, hexRightCells, nucleation, newNucleation,
                graphicsContext);
    }




    public void generationDevelopment(int size, int[][] neighbourCells,
                                      Nucleation nucleation,
                                      Nucleation newNucleation,
                                      GraphicsContext graphicsContext) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                checkAndFillCells(x, y, size, neighbourCells,
                  nucleation,newNucleation, graphicsContext);
            }
        }
    }



    public void checkAndFillCells(int x, int y, int size, int[][] neighbourCells,
                                  Nucleation nucleation,
                                  Nucleation newNucleation,
                                  GraphicsContext graphicsContext) {
        int j = 0;
        int state;

        WritableImage snap = graphicsContext.getCanvas()
                .snapshot(null, null);

        for(int i = 0; i < neighbourCells.length; i++){
            int iTemp = x + neighbourCells[i][j];
            int jTemp = y + neighbourCells[i][j + 1];

            state = nucleation.getStateAbsorbingBC(iTemp, jTemp);

            iTemp = (int) ((iTemp * 900/size) + (0.5 *(900/size)));
            jTemp = (int) ((jTemp * 900/size) + (0.5 *(900/size)));

            if(state != 0){
                newNucleation.makeCellAlive(x, y, state);
                int color = snap.getPixelReader().getArgb(iTemp, jTemp );
                System.out.println(color);
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;

                graphicsContext.setFill(Color.rgb(red, green, blue));
                graphicsContext.fillRect(x, y, 1, 1);
            }
        }
    }
}
