package Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.ValueScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by VISHAL-PC on 4/29/2017.
 */
public class StudentsC implements Initializable {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/timetable?autoReconnect=true&useSSL=false";

    //  Database credentials
    ValueScreen v= new ValueScreen();
    final String USER = v.user;
    final String PASS = v.pass;

    @FXML
    TableView<Students> StudentTable;
    @FXML
    TableColumn<Students,Integer> ColumnID;
    @FXML
    TableColumn<Students,String> ColumnName;
    @FXML
    TableColumn<Students,String> ColumnRegistration;
    @FXML
    TableColumn<Students,String> ColumnDepartment;
    @FXML
    TableColumn<Students,String> ColumnPassword;
    @FXML
    TextField textName;
    @FXML
    TextField textDepartment;
    @FXML
    TextField textRegistration;
    @FXML
    TextField textPassword;
    @FXML
    Button studentBack;

    Boolean editbtn=false;
    int pid;

    public void studentEditClick()
    {
        Students d=StudentTable.getSelectionModel().getSelectedItem();
        //System.out.println(d.getStudentName());
        textName.setText(d.getStudent());
        textDepartment.setText(d.getDepartment());
        textRegistration.setText(d.getRegistration());
        textPassword.setText(d.getPassword());
        pid=d.getID();
        editbtn=true;
    }

    public void studentSaveClick()
    {
        if(editbtn==false)
        {
            if(textName.getText().length()>0 && textRegistration.getText().length()>0 &&
                    textPassword.getText().length()>0 && textDepartment.getText().length()>0)
            {
                addData(textName.getText(),textRegistration.getText(),textPassword.getText(),textDepartment.getText());
                refreshTable();
                textName.setText("");
                textDepartment.setText("");
                textRegistration.setText("");
                textPassword.setText("");
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("All details not entered");
                alert.setContentText("Enter all details");
                alert.show();
            }
        }
        else
        {
            if (textName.getText().length() > 0 && textRegistration.getText().length() > 0 &&
                    textPassword.getText().length() > 0 && textDepartment.getText().length() > 0)
            {
                updateData(pid, textName.getText(), textRegistration.getText(), textPassword.getText(), textDepartment.getText());
                refreshTable();
                textName.setText("");
                textDepartment.setText("");
                textRegistration.setText("");
                textPassword.setText("");
                editbtn = false;
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert");
                alert.setHeaderText("All details not entered");
                alert.setContentText("Enter all details");
                alert.show();
            }
        }
    }

    public void studentCancelClick()
    {
        textName.setText("");
        textDepartment.setText("");
        textRegistration.setText("");
        textPassword.setText("");
        editbtn=false;
    }

    public void studentDeleteClick()
    {
        if(editbtn==true)
        {
            deleteData(pid);
            refreshTable();
            textName.setText("");
            textDepartment.setText("");
            textRegistration.setText("");
            textPassword.setText("");
            editbtn=false;
        }
    }

    public void studentRefreshClick()
    {
        refreshTable();
    }

    public void studentBackClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) studentBack.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Admin/adimDashBoard.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Admin DashBoard");
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ColumnID.setCellValueFactory(new PropertyValueFactory<Students,Integer>("ID"));
        ColumnName.setCellValueFactory(new PropertyValueFactory<Students,String>("Student"));
        ColumnDepartment.setCellValueFactory(new PropertyValueFactory<Students,String>("Department"));
        ColumnRegistration.setCellValueFactory(new PropertyValueFactory<Students,String>("Registration"));
        ColumnPassword.setCellValueFactory(new PropertyValueFactory<Students,String>("Password"));

        refreshTable();

    }


    public void refreshTable()
    {
        ObservableList<Students> data = FXCollections.observableArrayList();

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

            String sql = "SELECT * FROM students ORDER BY id";
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                int id1=rs.getInt("id");
                String name1=rs.getString("name");
                String reg1=rs.getString("registration");
                String pass1=rs.getString("password");
                String dept1=rs.getString("department");
                //System.out.println("id:"+id1+" day: "+day1);

                Students d=new Students();
                d.setID(id1);
                d.setStudent(name1);
                d.setRegistration(reg1);
                d.setPassword(pass1);
                d.setDepartment(dept1);

                data.add(d);

            }
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

        StudentTable.setItems(data);

    }



    public void addData(String sname,String sreg,String spass,String sdep)
    {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            //System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();

            String sql = "INSERT INTO students (name,registration,password,department) " +
                    "VALUES ('"+sname+"', '"+sreg+"', '"+spass+"', '"+sdep+"');";
            stmt.executeUpdate(sql);

            //System.out.println("Inserted records into the table...");

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
    }


    public void updateData(int pid,String sname,String sreg,String spass,String sdep)
    {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "UPDATE students " +
                    "SET name ='"+sname+"', registration ='"+sreg+"', password='"+spass+"', department ='"+sdep+"' WHERE id="+pid+";";
            stmt.executeUpdate(sql);

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
    }

    public void deleteData(int pid)
    {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "DELETE FROM students " +
                    "WHERE id = "+pid+";";
            stmt.executeUpdate(sql);

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
    }

}

