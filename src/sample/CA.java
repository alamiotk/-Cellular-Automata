package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CA extends Application {

    @Override
    public void start(Stage stage){

      //  AnimationTimer animationTimer;


        View view = new View();

//        animationTimer = new AnimationTimer() {
//            @Override
//            public void handle(long l) {
//                view.simulationOneStep();
//            }
//        };

        Scene scene = new Scene(view, 1500, 980);
        stage.setScene(scene);
        stage.show();
        //view.resetView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
