package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;

import java.util.Random;

public class Generations {


    public void grainDevelopment(Nucleation nucleation, GraphicsContext graphicsContext,
                                 Random random, int a, int b, int c) {
        for (int x = 0; x < nucleation.width; x++) {
            for (int y = 0; y < nucleation.height; y++) {

                if (
                        nucleation.getStateAbsorbingBC(x, y) > 0) {
                    graphicsContext.setFill(Color.rgb(a, b, c));
                    graphicsContext.fillRect(x, y, 1, 1);
                    a = random.nextInt(255);
                    b = random.nextInt(255);
                    c = random.nextInt(255);
                }

            }
        }
    }
}
