package StudentFaculty;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.ValueScreen;

import java.io.IOException;

/**
 * Created by VISHAL-PC on 4/29/2017.
 */
public class SFDashBoardC {

    @FXML
    Button stBtn;
    @FXML
    Button faBtn;
    @FXML
    Button roBtn;
    @FXML
    Button reBtn;
    @FXML
    Button logoutBtn;

    public void stBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) logoutBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/ScheduleMem/StudentSchedule.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Section Wise TimeTable");
        stage.setScene(scene);
        stage.show();
    }

    public void faBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) logoutBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/ScheduleMem/FacultySchedule.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Faculty Wise TimeTable");
        stage.setScene(scene);
        stage.show();
    }

    public void roBtnClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) logoutBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/ScheduleMem/RoomSchedule.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Room Wise TimeTable");
        stage.setScene(scene);
        stage.show();
    }

    public void reBtnClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) logoutBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/ScheduleMem/Reschedule.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Reschedule Period");
        stage.setScene(scene);
        stage.show();
    }

    public void logoutBtnClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) logoutBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/sample/sample.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

}
