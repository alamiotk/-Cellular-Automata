package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

import java.util.Random;

public class View extends VBox {
    private Button buttonNextGeneration;
    private Button buttonHomogeneous;
    private Button buttonWithRadius;
    private Button buttonRandom;
    private Button buttonClickOut;
    private CheckBox checkBoxPeriodic;

    private TextField textField;
    private Canvas canvas;
    int valueOfCellsInRow = 100;

    private Affine affine;
    private Generations generations;
    private Nucleation nucleation;
    Random random = new Random();

    public View() {
        this.generations = new Generations(valueOfCellsInRow, valueOfCellsInRow);
        this.textField = new TextField("");
        textField.setMaxHeight(10);
        textField.setMaxWidth(100);

        this.textField.setOnAction(actionEvent -> {
            String text = textField.getText();
            valueOfCellsInRow = Integer.parseInt(text);
            for(int i = 0; i < valueOfCellsInRow*valueOfCellsInRow*0.4; i++){
                generations.makeCellAlive(random.nextInt(valueOfCellsInRow),
                        random.nextInt(valueOfCellsInRow));
            }
            show();
        });
        this.buttonHomogeneous = new Button("Homogeous");

        this.buttonHomogeneous.setOnAction(actionEvent -> {
            int stage = 0;
            for(int i = 0; i < valueOfCellsInRow; i += 3){
                for(int j = 0; j < valueOfCellsInRow; j += 3) {
                    nucleation.makeCellAliveHomogeneous(i, j, stage);
                    stage++;
                    show();
                }
            }
        });

        this.buttonWithRadius = new Button("With radius");
        this.buttonRandom = new Button("Random");
        this.buttonClickOut = new Button("Click-out");
        this.checkBoxPeriodic = new CheckBox("periodic");
        this.checkBoxPeriodic.setOnAction(actionEvent -> {
            nucleation.periodicBC();
        });

        this.buttonNextGeneration = new Button("Next generation");
        this.buttonNextGeneration.setOnAction(actionEvent -> {
            generations.step();
            show();
        });

        this.canvas = new Canvas(600, 600);
        this.getChildren().addAll(this.textField,
                this.buttonHomogeneous,
                this.buttonWithRadius,
                this.buttonRandom,
                this.buttonClickOut,
                this.checkBoxPeriodic,
                this.buttonNextGeneration,
                this.canvas);
    }

    public void show() {
        this.affine = new Affine();
        this.affine.appendScale(600/ valueOfCellsInRow, 600/ valueOfCellsInRow);
        getProperties().clear();
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        graphicsContext.setTransform(this.affine);
        graphicsContext.setFill(Color.BEIGE);
        graphicsContext.fillRect(0, 0, 600, 600);

        graphicsContext.setFill(Color.DARKCYAN);
        for (int x = 0; x < this.generations.width; x++) {
            for (int y = 0; y < this.generations.height; y++) {
                if (this.generations.getState(x, y) == 1) {
                    graphicsContext.fillRect(x, y, 1, 1);
                }
            }
        }

        graphicsContext.setStroke(Color.GRAY);
        graphicsContext.setLineWidth(0.05);
        for (int x = 0; x <= this.generations.width; x++) {
            graphicsContext.strokeLine(x, 0, x, valueOfCellsInRow);
        }
        for (int y = 0; y <= this.generations.height; y++) {
            graphicsContext.strokeLine(0, y, valueOfCellsInRow, y);
        }
    }
}
