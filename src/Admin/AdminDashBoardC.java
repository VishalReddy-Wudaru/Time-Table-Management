package Admin;

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
public class AdminDashBoardC {

    @FXML
    Button logoutBtn;
    @FXML
    Button tdayBtn;
    @FXML
    Button tpeBtn;
    @FXML
    Button troBtn;
    @FXML
    Button tseBtn;
    @FXML
    Button tsuBtn;
    @FXML
    Button tfaBtn;
    @FXML
    Button tstBtn;
    @FXML
    Button pttBtn;
    @FXML
    Button dttBtn;
    @FXML
    Button stBtn;
    @FXML
    Button faBtn;
    @FXML
    Button roBtn;
    @FXML
    Button reBtn;

    public void logoutBtnClick()throws  IOException
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

    public void tdayBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) tdayBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Day/DaysF.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Days Table");
        stage.setScene(scene);
        stage.show();
    }

    public void tpeBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) tpeBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Period/PeriodsF.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Periods Table");
        stage.setScene(scene);
        stage.show();
    }

    public void troBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) troBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Room/RoomsF.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Rooms Table");
        stage.setScene(scene);
        stage.show();
    }

    public void tseBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) tseBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Section/SectionsF.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Sections Table");
        stage.setScene(scene);
        stage.show();
    }

    public void tsuBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) tsuBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Subject/SubjectsF.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Subjects Table");
        stage.setScene(scene);
        stage.show();
    }

    public void tfaBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) tfaBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Faculty/FacultysF.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Facultys Table");
        stage.setScene(scene);
        stage.show();
    }

    public void tstBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) tstBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Student/StudentsF.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Students Table");
        stage.setScene(scene);
        stage.show();
    }

    public void pttBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) pttBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/TimeTable/PttF.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Permanent TimeTable");
        stage.setScene(scene);
        stage.show();
    }

    public void dttBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) dttBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/TimeTable/DttF.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Temporary TimeTable");
        stage.setScene(scene);
        stage.show();
    }

    public void stBtnClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) logoutBtn.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Schedule/StudentSchedule.fxml"));
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
        root= FXMLLoader.load(getClass().getResource("/Schedule/FacultySchedule.fxml"));
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
        root= FXMLLoader.load(getClass().getResource("/Schedule/RoomSchedule.fxml"));
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
        root= FXMLLoader.load(getClass().getResource("/Schedule/Reschedule.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Reschedule Period");
        stage.setScene(scene);
        stage.show();
    }

}
