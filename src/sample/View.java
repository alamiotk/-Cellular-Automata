package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

import java.util.Random;

public class View extends GridPane {
    Button buttonNextGeneration;
    Button buttonHomogeneous;
    Button buttonWithRadius;
    Button buttonRandom;
    Button buttonClickOut;
    Button buttonNeumann;
    Button buttonMoore;
    Button buttonPer;
    Button buttonHex;
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

    int size = 1000;
    int aliveCellsInRow, aliveCellsInColumn, aliveRandomCells, radius, aliveRadiusCells;
    int a = 100, b = 60, c = 150;

    private Affine affine;
    Nucleation nucleation;
    Nucleation newNucleationGrid;
    Random random = new Random();


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
//            for(int i = 0; i < valueOfCellsInRow*valueOfCellsInRow*0.4; i++){
//                generations.makeCellAlive(random.nextInt(valueOfCellsInRow),
//                        random.nextInt(valueOfCellsInRow));
//            }
            show();
        });

        this.nucleation = new Nucleation(size, size);
        this.newNucleationGrid = new Nucleation(size, size);
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                newNucleationGrid.setStage(i,j);
            }
        }
//---------------------------------------------------------------------

        this.label2 = new Label("Number of alive cells in row:");

        this.textFieldNumberInRow = new TextField("");
            textFieldNumberInRow.setMaxHeight(10);
            textFieldNumberInRow.setMaxWidth(100);

        this.textFieldNumberInRow.setOnAction(actionEvent -> {
            String text1 = textFieldNumberInRow.getText();
            aliveCellsInRow = Integer.parseInt(text1);
        });


        this.label3 = new Label("Number of alive cells in column:");

        this.textFieldNumberInColumn = new TextField("");
            textFieldNumberInColumn.setMaxHeight(10);
            textFieldNumberInColumn.setMaxWidth(100);

        this.textFieldNumberInColumn.setOnAction(actionEvent -> {
            String text2 = textFieldNumberInColumn.getText();
            aliveCellsInColumn = Integer.parseInt(text2);
        });

        this.buttonHomogeneous = new Button("Homogeous");
        this.buttonHomogeneous.setOnAction(actionEvent -> {
            resetView();
            nucleation.homogeousNucleation(size, aliveCellsInColumn, aliveCellsInRow);
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
            nucleation.radiusNucleation(size, aliveRadiusCells, radius, random);
            show();
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
            nucleation.randomNucleation(size, aliveRandomCells,random);
            show();
        });

//--------------------------------------------------------------------------
        this.buttonClickOut = new Button("Click-out");
        this.buttonClickOut.setOnAction(actionEvent -> {
            resetView();
            show();
            int stage = 1;
            nucleation.clickNucleation(canvas, graphicsContext, size, stage, random);
            show();
        });

//--------------------------------------------------------------------------------

        this.checkBoxPeriodic = new CheckBox("periodic");
        this.buttonNextGeneration = new Button("Next generation");
        this.buttonNextGeneration.setOnAction(actionEvent -> {
            //    generations.step();
            show();
        });

//---------------------------------------------------------------------------------

        this.buttonNeumann = new Button("Von Neumann");
        this.buttonNeumann.setOnAction(actionEvent -> {
            resetView();
            show();
        });

        this.buttonMoore = new Button("Moore");
        this.buttonMoore.setOnAction(actionEvent -> {
            resetView();
            show();
        });

        this.buttonPer= new Button("Pentagonal");
        this.buttonPer.setOnAction(actionEvent -> {
            resetView();
            show();
        });

        this.buttonHex = new Button("Hexagonal");
        this.buttonHex.setOnAction(actionEvent -> {
            resetView();
            show();
        });

        this.label7 = new Label("");



//------------------------------------------------------------------------------
        this.add(canvas, 1, 2, 1, 15);
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

        //this.add(label7, 3, 12, 3, 1);

        this.add(buttonNeumann, 3, 14, 1, 1);
        this.add(buttonMoore, 5, 14, 1, 1);
        this.add(buttonPer, 3, 15, 1, 1);
        this.add(buttonHex, 5, 15, 1, 1);

    }


    public void show() {
        this.affine = new Affine();
        this.affine.appendScale(900/ size, 900/ size);
        getProperties().clear();
        graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setTransform(this.affine);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, 900, 900);

        for (int x = 0; x < this.nucleation.width; x++) {
            for (int y = 0; y < this.nucleation.height; y++) {

                if(checkBoxPeriodic.isSelected()){
                    if (this.nucleation.getStatePeriodicBC(x, y, size) > 0) {
                        graphicsContext.setFill(Color.rgb(a, b, c));
                        graphicsContext.fillRect(x, y, 1, 1);
                        a = random.nextInt(255);
                        b = random.nextInt(255);
                        c = random.nextInt(255);
                    }
                }
                else {
                    if (this.nucleation.getStateAbsorbingBC(x, y) > 0) {
                        graphicsContext.setFill(Color.rgb(a, b, c));
                        graphicsContext.fillRect(x, y, 1, 1);
                        a = random.nextInt(255);
                        b = random.nextInt(255);
                        c = random.nextInt(255);
                    }
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
