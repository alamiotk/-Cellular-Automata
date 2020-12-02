package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import java.util.Random;

public class View extends GridPane {
    Button buttonHomogeneous;
    Button buttonWithRadius;
    Button buttonRandom;
    Button buttonClickOut;
    Button buttonNeumann;
    Button buttonMoore;
    Button buttonPer;
    Button buttonHex;
    Button animationStop;
    Button buttonMonteCarlo;
    Button stopMonteCarlo;
    Label label1;
    Label label2;
    Label label3;
    Label label4;
    Label label5;
    Label label6;
    Label label7;
    TextField textFieldNumberCells;
    TextField textFieldNumberInRow;
    TextField textFieldNumberInColumn;
    TextField textFieldRandomNumber;
    TextField textFieldRadius;
    TextField textFieldRadNumber;
    CheckBox checkBoxPeriodic;
    Canvas canvas;
    GraphicsContext graphicsContext;
    WritableImage snap;

    int size = 1000;
    int aliveCellsInRow, aliveCellsInColumn, aliveRandomCells, radius, aliveRadiusCells;
    final int[] a = {100};
    final int[] b = { 60 };
    final int[] c = { 150 };

    private Affine affine;
    Nucleation nucleation;
    Nucleation newNucleationGrid;
    Random random = new Random();
    Neighbourhood neighbourhood  = new Neighbourhood();

    public View() {
        this.setHgap(5);
        this.setVgap(5);
        this.setPadding(new Insets(10, 10, 10, 10));

        canvas = new Canvas(900, 900);
        graphicsContext = this.canvas.getGraphicsContext2D();

//-----------------------------------------------------------------------------
        this.label1 = new Label("Number of cells to create the grid:");
        label1.setAlignment(Pos.CENTER);
        this.textFieldNumberCells = new TextField("");
        textFieldNumberCells.setMaxHeight(10);
        textFieldNumberCells.setMaxWidth(100);
        this.textFieldNumberCells.setOnAction(actionEvent -> {
            resetView();
            String text = textFieldNumberCells.getText();
            size = Integer.parseInt(text);
            if(size > 1000){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("1000 is the biggest possible numbers of cells!");
                alert.showAndWait();
            }
            show();
        });

        this.nucleation = new Nucleation(size, size);
        this.newNucleationGrid = new Nucleation(size, size);

//---------------------------------------------------------------------
        this.label2 = new Label("Number of alive cells in row:");

        this.textFieldNumberInRow = new TextField("");
            textFieldNumberInRow.setMaxHeight(10);
            textFieldNumberInRow.setMaxWidth(100);

        this.textFieldNumberInRow.setOnAction(actionEvent -> {
            String text1 = textFieldNumberInRow.getText();
            aliveCellsInRow = Integer.parseInt(text1);
            if(aliveCellsInRow > size){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Too big number!");
                alert.showAndWait();
            }
        });

        this.label3 = new Label("Number of alive cells in column:");

        this.textFieldNumberInColumn = new TextField("");
            textFieldNumberInColumn.setMaxHeight(10);
            textFieldNumberInColumn.setMaxWidth(100);

        this.textFieldNumberInColumn.setOnAction(actionEvent -> {
            String text2 = textFieldNumberInColumn.getText();
            aliveCellsInColumn = Integer.parseInt(text2);
            if(aliveCellsInColumn > size){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Too big number!");
                alert.showAndWait();
            }
        });

        this.buttonHomogeneous = new Button("Homogeous");
        this.buttonHomogeneous.setOnAction(actionEvent -> {
            resetView();
            nucleation.homogeousNucleation(size, aliveCellsInColumn,
                    aliveCellsInRow);
            show();
        });

//-------------------------------------------------------------------------
        this.label5 = new Label("Radius:");

        this.textFieldRadius = new TextField("");
            textFieldRadius.setMaxHeight(10);
            textFieldRadius.setMaxWidth(100);

        this.textFieldRadius.setOnAction(actionEvent -> {
            String text5 = textFieldRadius.getText();
            radius = Integer.parseInt(text5);
        });

        this.label6 = new Label("Number of cells:");

        this.textFieldRadNumber = new TextField("");
            textFieldRadNumber.setMaxHeight(10);
            textFieldRadNumber.setMaxWidth(100);

        this.textFieldRadNumber.setOnAction(actionEvent -> {
            String text6 = textFieldRadNumber.getText();
            aliveRadiusCells = Integer.parseInt(text6);
        });

        this.buttonWithRadius = new Button("With radius");
        this.buttonWithRadius.setOnAction(actionEvent -> {
            resetView();
            int res = nucleation.radiusNucleation(size, aliveRadiusCells,
                    radius, random);
            if(res == 1) {
                show();
            }
        });

//-------------------------------------------------------------------------

        this.label4 = new Label("Number of alive random cells:");

        this.textFieldRandomNumber = new TextField("");
            textFieldRandomNumber.setMaxHeight(10);
            textFieldRandomNumber.setMaxWidth(100);

        this.textFieldRandomNumber.setOnAction(actionEvent -> {
            String text4 = textFieldRandomNumber.getText();
            aliveRandomCells = Integer.parseInt(text4);
        });


        this.buttonRandom = new Button("Random");
        this.buttonRandom.setOnAction(actionEvent -> {
            resetView();
            if(aliveRandomCells > size * size){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Too big number!");
                alert.showAndWait();
            }
            else {
                nucleation.randomNucleation(size, aliveRandomCells, random);
                show();
            }
        });

//--------------------------------------------------------------------------
        this.buttonClickOut = new Button("Click-out");
        this.buttonClickOut.setOnAction(actionEvent -> {
            resetView();
            show();
            int stage = 1;
            nucleation.clickNucleation(canvas, graphicsContext,
                    size, stage, random);
        });
//--------------------------------------------------------------------------------
        this.checkBoxPeriodic = new CheckBox("periodic");
//---------------------------------------------------------------------------------



        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                 simulationOneStep(neighbourhood.neumannCells);

                 try{
                     Thread.sleep(1);
                 }
                 catch(InterruptedException interruptedException){
                     interruptedException.printStackTrace();
                 }

                animationStop.setOnAction(actionEvent -> {
                    stop();
                });


            }
        };

        AnimationTimer animationTimer1 = new AnimationTimer() {
            @Override
            public void handle(long l) {
                simulationOneStep(neighbourhood.mooreCells);
                try{
                    Thread.sleep(5);
                }
                catch(InterruptedException interruptedException){
                    interruptedException.printStackTrace();
                }

                animationStop.setOnAction(actionEvent -> {
                    stop();
                });


            }
        };
        AnimationTimer animationTimer2 = new AnimationTimer() {
            @Override
            public void handle(long l) {
                simulationOneStep(neighbourhood.pentLeftCells);

                try{
                    Thread.sleep(5);
                }
                catch(InterruptedException interruptedException){
                    interruptedException.printStackTrace();
                }

                animationStop.setOnAction(actionEvent -> {
                    stop();
                });


            }
        };

        AnimationTimer animationTimer3 = new AnimationTimer() {
            @Override
            public void handle(long l) {
                simulationOneStep(neighbourhood.pentRightCells);

                try{
                    Thread.sleep(5);
                }
                catch(InterruptedException interruptedException){
                    interruptedException.printStackTrace();
                }

                animationStop.setOnAction(actionEvent -> {
                    stop();
                });
            }
        };

        AnimationTimer animationTimer4 = new AnimationTimer() {
            @Override
            public void handle(long l) {
                simulationOneStep(neighbourhood.pentBottomCells);

                try{
                    Thread.sleep(5);
                }
                catch(InterruptedException interruptedException){
                    interruptedException.printStackTrace();
                }

                animationStop.setOnAction(actionEvent -> {
                    stop();
                });
            }
        };
        AnimationTimer animationTimer5 = new AnimationTimer() {
            @Override
            public void handle(long l) {
                simulationOneStep(neighbourhood.pentTopCells);

                try{
                    Thread.sleep(5);
                }
                catch(InterruptedException interruptedException){
                    interruptedException.printStackTrace();
                }

                animationStop.setOnAction(actionEvent -> {
                    stop();
                });
            }
        };
        AnimationTimer animationTimer6 = new AnimationTimer() {
            @Override
            public void handle(long l) {
                simulationOneStep(neighbourhood.hexLeftCells);

                try{
                    Thread.sleep(5);
                }
                catch(InterruptedException interruptedException){
                    interruptedException.printStackTrace();
                }

                animationStop.setOnAction(actionEvent -> {
                    stop();
                });
            }
        };
        AnimationTimer animationTimer7 = new AnimationTimer() {
            @Override
            public void handle(long l) {
                simulationOneStep(neighbourhood.hexRightCells);

                try{
                    Thread.sleep(5);
                }
                catch(InterruptedException interruptedException){
                    interruptedException.printStackTrace();
                }

                animationStop.setOnAction(actionEvent -> {
                    stop();
                });
            }
        };

        AnimationTimer animationTimer9 = new AnimationTimer() {
            @Override
            public void handle(long l) {
                simulationMonteCarlo();

                try{
                    Thread.sleep(5);
                }
                catch(InterruptedException interruptedException){
                    interruptedException.printStackTrace();
                }

                stopMonteCarlo.setOnAction(actionEvent -> {
                    stop();
                });
            }
        };

        this.buttonNeumann = new Button("Von Neumann");
        this.buttonNeumann.setOnAction(actionEvent -> {
            animationTimer.start();
        });

        this.buttonMoore = new Button("Moore");
        this.buttonMoore.setOnAction(actionEvent -> {
            animationTimer1.start();
        });

        this.buttonPer = new Button("Pentagonal");
        this.buttonPer.setOnAction(actionEvent -> {
            int pent = random.nextInt(4);
            switch(pent){
                case 1:
                    animationTimer2.start();
                    break;
                case 2:
                    animationTimer3.start();
                    break;

                case 3:
                    animationTimer4.start();
                    break;

                case 4:
                    animationTimer5.start();
                    break;

                default:
                    break;
            }
        });

        this.buttonHex = new Button("Hexagonal");
        this.buttonHex.setOnAction(actionEvent -> {
            int pent = random.nextInt(2);
            switch(pent) {
                case 1:
                    animationTimer6.start();
                    break;
                case 2:
                    animationTimer7.start();
                    break;
                default:
                    break;

            }
        });

        this.label7 = new Label("");

        this.animationStop = new Button("Animation stop");

        this.buttonMonteCarlo = new Button("Monte Carlo");
        this.buttonMonteCarlo.setOnAction(actionEvent -> {
            //simulationMonteCarlo();
            animationTimer9.start();
        });

        this.stopMonteCarlo = new Button("Stop Monte Carlo");




//------------------------------------------------------------------------------
        this.add(canvas, 1, 2, 1, 18);
        this.add(label1, 3, 1,3,1);
        this.add(textFieldNumberCells, 4, 2, 1, 1);

        this.add(checkBoxPeriodic, 4, 3, 1,1);

        this.add(label2, 3, 4, 1, 1);
        this.add(textFieldNumberInRow, 3, 5, 1, 1);
        this.add(label3, 3, 6, 1, 1);
        this.add(textFieldNumberInColumn, 3, 7, 1, 1);
        this.add(buttonHomogeneous, 3, 8, 1, 1);


        this.add(label5, 5, 4, 1, 1);
        this.add(textFieldRadius, 5, 5, 1, 1);
        this.add(label6, 5, 6, 1, 1);
        this.add(textFieldRadNumber, 5, 7, 1, 1);
        this.add(buttonWithRadius, 5, 8, 1, 1);

        this.add(label7, 3, 9, 3, 3);

        this.add(label4, 3, 11, 1, 1);
        this.add(textFieldRandomNumber, 3, 12, 1, 1);
        this.add(buttonRandom, 3, 13, 1, 1);

        this.add(buttonClickOut, 5, 13, 1, 1);

        this.add(buttonNeumann, 3, 14, 1, 1);
        this.add(buttonMoore, 5, 14, 1, 1);
        this.add(buttonPer, 3, 15, 1, 1);
        this.add(buttonHex, 5, 15, 1, 1);

        this.add(animationStop, 4, 16, 1, 1);

        this.add(buttonMonteCarlo, 4, 17, 1, 1);
        this.add(stopMonteCarlo, 4, 18, 1, 1);
    }

    public int isCheckBoxPeriodicSelected(CheckBox checkBoxPeriodic) {
        if(checkBoxPeriodic.isSelected()){
            return 1;
        } else {
            return 0;
        }
    }


    public void simulationOneStep(int[][] cells){
        snap = graphicsContext.getCanvas()
                .snapshot(null, null);

        resetNewGrid(newNucleationGrid);

        neighbourhood.growth(cells,
             nucleation, newNucleationGrid,
             isCheckBoxPeriodicSelected(checkBoxPeriodic), snap, size, graphicsContext);

        newNucleationReset(nucleation, newNucleationGrid);
    }

    public void simulationMonteCarlo(){
        snap = graphicsContext.getCanvas()
                .snapshot(null, null);

        resetNewGridMonteCarlo(newNucleationGrid, nucleation);

        neighbourhood.crossingTheGridMonteCarlo(nucleation, newNucleationGrid,
                isCheckBoxPeriodicSelected(checkBoxPeriodic),
                size, graphicsContext, snap);

        newNucleationResMonteCarlo(nucleation, newNucleationGrid);
    }

    public void newNucleationReset(Nucleation nucleation,
                                   Nucleation newNucleationGrid) {
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++) {
                if(nucleation.grid[i][j] == 0) {
                    nucleation.grid[i][j] = newNucleationGrid.grid[i][j];
                }
            }
        }
    }

    public void newNucleationResMonteCarlo(Nucleation nucleation,
                                   Nucleation newNucleationGrid) {
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++) {
                if(newNucleationGrid.grid[i][j] != nucleation.grid[i][j]) {
                    nucleation.grid[i][j] = newNucleationGrid.grid[i][j];
                }
            }
        }
    }

    public void print(Nucleation nucleation,
                                   Nucleation newNucleationGrid) {
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++) { {
                    System.out.println(nucleation.grid[i][j]);
                }
            }
        }
    }


    public void resetNewGrid(Nucleation newNucleationGrid) {
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                newNucleationGrid.setStage(i,j);
            }
        }
    }
    public void resetNewGridMonteCarlo(Nucleation newNucleationGrid,
                                       Nucleation nucleation) {
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                newNucleationGrid.grid[i][j] = nucleation.grid[i][j];
            }
        }
    }

    public void show() {
        this.affine = new Affine();
        this.affine.appendScale(900/ size, 900/ size);
        getProperties().clear();
        graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setTransform(this.affine);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, 900, 900);

        int state = -1;

        for (int x = 0; x < this.nucleation.width; x++) {
            for (int y = 0; y < this.nucleation.height; y++) {

                state = nucleation.getStateAbsorbingBC(x, y);

                if(state > 0) {
                    graphicsContext.setFill(Color.rgb(a[0], b[0], c[0]));
                    graphicsContext.fillRect(x, y, 1, 1);
                    a[0] = random.nextInt(255);
                    b[0] = random.nextInt(255);
                    c[0] = random.nextInt(255);
                }
            }
        }

        graphicsContext.setStroke(Color.LIGHTGRAY);
        graphicsContext.setLineWidth(0.05);
        for (int x = 0; x <= this.nucleation.width; x++) {
            graphicsContext.strokeLine(x, 0, x, size);
        }
        for (int y = 0; y <= this.nucleation.height; y++) {
            graphicsContext.strokeLine(0, y, size, y);
        }
    }
    public void resetView() {
        this.affine = new Affine();
        this.affine.appendScale(900/ size, 900/ size);
        getProperties().clear();
        graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setTransform(this.affine);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, 900, 900);

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                nucleation.setStage(i,j);
            }
        }
    }
}
