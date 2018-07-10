package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Run only first time

        //FirstRun fr=new FirstRun();
        //fr.createDB();
        //fr.createTables();


        Parent root = FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
        primaryStage.setTitle("TimeTable Management");
        ValueScreen v= new ValueScreen();
        primaryStage.setScene(new Scene(root,v.width, v.height));
        primaryStage.setFullScreen(v.full);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
