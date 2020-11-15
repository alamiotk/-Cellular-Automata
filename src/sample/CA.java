package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CA extends Application {

    @Override
    public void start(Stage stage){
        View view = new View();
        Scene scene = new Scene(view, 600, 700);
        stage.setScene(scene);
        stage.show();
        view.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
