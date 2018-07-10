package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Button btAdmin;
    @FXML
    Button btStudent;
    @FXML
    Button btFaculty;


    public void btAdminClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) btAdmin.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Login/AdminLogin.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Admin Login");
        stage.setScene(scene);
        stage.show();
    }

    public void btStudentClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) btAdmin.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Login/StudentLogin.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Member Login");
        stage.setScene(scene);
        stage.show();
    }

    public void btFacultyClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) btAdmin.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Login/FacultyLogin.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Member Login");
        stage.setScene(scene);
        stage.show();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

}
