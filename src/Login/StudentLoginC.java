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
import java.sql.*;

/**
 * Created by VISHAL-PC on 5/6/2017.
 */
public class StudentLoginC {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/timetable?autoReconnect=true&useSSL=false";

    //  Database credentials
    ValueScreen v= new ValueScreen();
    final String USER = v.user;
    final String PASS = v.pass;


    @FXML
    Button memLogin;
    @FXML
    Button backBt;
    @FXML
    TextField textUser;
    @FXML
    PasswordField textPass;

    boolean login=false;

    public void memLoginClick()throws IOException
    {
        login=loginCheck();
        if(login==true)
        {
            Stage stage;
            Parent root;
            stage = (Stage) memLogin.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/StudentFaculty/SFDashBoard.fxml"));
            ValueScreen v = new ValueScreen();
            Scene scene = new Scene(root, v.width, v.height);
            stage.setFullScreen(v.full);
            stage.setTitle("Member DashBoard");
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

    public boolean loginCheck()
    {
        String user=textUser.getText();
        String pass=textPass.getText();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT * FROM students WHERE registration='"+user+"' AND password='"+pass+"';";
            rs = stmt.executeQuery(sql);
            rs.last();
            if(rs.getRow()!=0)
            {
                login=true;
                return login;
            }
            //STEP 5: Extract data from result set
            rs.close();

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

        return login;
    }
}
