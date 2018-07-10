package Login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ValueScreen;

import java.io.IOException;

/**
 * Created by VISHAL-PC on 4/29/2017.
 */
public class AdminLoginC {

    @FXML
    Button adminLogin;
    @FXML
    Button backBt;
    @FXML
    TextField textUser;
    @FXML
    PasswordField textPass;

    public void adminLoginClick()throws IOException
    {
        if(textUser.getText().equals("admin") && textPass.getText().equals("admin"))
        {
            Stage stage;
            Parent root;
            stage = (Stage) adminLogin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/Admin/AdimDashBoard.fxml"));
            ValueScreen v = new ValueScreen();
            Scene scene = new Scene(root, v.width, v.height);
            stage.setFullScreen(v.full);
            stage.setTitle("Admin DashBoard");
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            textUser.setText("");
            textPass.setText("");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("Incorrect Details");
            alert.setContentText("Enter correct details");
            alert.show();
        }
    }

    public void backBtClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) backBt.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

}
