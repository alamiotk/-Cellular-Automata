package sample;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class View extends VBox {
    Button buttonNextGeneration;
    Button buttonHomogeneous;
    Button buttonWithRadius;
    Button buttonRandom;
    Button buttonClickOut;
    Label label1;
    Label label2;
    Label label3;
    Label label4;
    Label label5;
    Label label6;

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
    Random random = new Random();

    public View() {
        this.canvas = new Canvas(1000, 1000);
        graphicsContext = this.canvas.getGraphicsContext2D();
//-----------------------------------------------------------------------------
        this.label1 = new Label("Number of cells to create the grid:");
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

//----------------------------------------------------------------------------

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
                    nucleation.makeCellAlive(i, j, stage);
                    stage++;
                    show();
                }
            }
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
            List<Point> listOfPoints = new ArrayList<>();
            int xRandom = random.nextInt(size);
            int yRandom = random.nextInt(size);
            listOfPoints.clear();

            Point p1 = new Point(xRandom, yRandom);
            listOfPoints.add(p1);

            int stage = 1;
            nucleation.makeCellAlive(xRandom, yRandom, stage);
            stage++;

            int aliveCellsInGrid = 1;
            int tmp;

            while(aliveCellsInGrid < aliveRadiusCells){
                tmp = 0;
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
                        nucleation.makeCellAlive(xRandom, yRandom, stage);
                        Point nextPoint = new Point(xRandom, yRandom);
                        listOfPoints.add(nextPoint);
                        stage++;
                        aliveCellsInGrid++;
                    }
                }
            }
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
            int stage = 1;
            for(int i = 0; i < aliveRandomCells; i++){
                nucleation.makeCellAlive(random.nextInt(size),
                        random.nextInt(size), stage);
                stage++;
            }
            show();
        });

//--------------------------------------------------------------------------
        this.buttonClickOut = new Button("Click-out");
        this.buttonClickOut.setOnAction(actionEvent -> {
            resetView();
            show();
            int stage = 1;
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    graphicsContext.setFill(Color.rgb(a,b,c));
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    nucleation.makeCellAlive(x* size /1000,y* size /1000, stage);
                    graphicsContext.fillRect(x* size /1000,y* size /1000,1,1);
                    a = random.nextInt(255);
                    b = random.nextInt(255);
                    c = random.nextInt(255);
                }
            });
            show();
        });
        this.checkBoxPeriodic = new CheckBox("periodic");
        this.buttonNextGeneration = new Button("Next generation");
        this.buttonNextGeneration.setOnAction(actionEvent -> {
        //    generations.step();
            show();
        });
//------------------------------------------------------------------------------

        this.getChildren().addAll(
                this.label1,
                this.textFieldNumberCells,
                this.label2,
                this.textFieldNumberInRow,
                this.label3,
                this.textFieldNumberInColumn,
                this.buttonHomogeneous,
                this.label5,
                this.textFieldRadius,
                this.label6,
                this.textFieldRadNumber,
                this.buttonWithRadius,
                this.label4,
                this.textFieldRandomNumber,
                this.buttonRandom,
                this.buttonClickOut,
                this.checkBoxPeriodic,
            //    this.buttonNextGeneration,
                this.canvas);
    }

    public void show() {
        this.affine = new Affine();
        this.affine.appendScale(1000/ size, 600/ size);
        getProperties().clear();
        graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setTransform(this.affine);
        graphicsContext.setFill(Color.BEIGE);
        graphicsContext.fillRect(0, 0, 1000, 1000);

        for (int x = 0; x < this.nucleation.width; x++) {
            for (int y = 0; y < this.nucleation.height; y++) {

                if(checkBoxPeriodic.isSelected()){
                    if (this.nucleation.periodicBC(x, y, size) > 0) {
                        graphicsContext.setFill(Color.rgb(a, b, c));
                        graphicsContext.fillRect(x, y, 1, 1);
                        a = random.nextInt(255);
                        b = random.nextInt(255);
                        c = random.nextInt(255);
                    }
                }
                else {
                    if (this.nucleation.absorbingBC(x, y) > 0) {
                        graphicsContext.setFill(Color.rgb(a, b, c));
                        graphicsContext.fillRect(x, y, 1, 1);
                        a = random.nextInt(255);
                        b = random.nextInt(255);
                        c = random.nextInt(255);
                    }
                }
            }
        }

        graphicsContext.setStroke(Color.GRAY);
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
        this.affine.appendScale(600/ size, 600/ size);
        getProperties().clear();
        graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setTransform(this.affine);
        graphicsContext.setFill(Color.BEIGE);
        graphicsContext.fillRect(0, 0, 1000, 1000);

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                nucleation.setStage(i,j);
            }
        }
    }
}
