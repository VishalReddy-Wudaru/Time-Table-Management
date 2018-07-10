package Period;

import TimeTable.TimeTables;
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
public class PeriodsC implements Initializable {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/timetable?autoReconnect=true&useSSL=false";

    //  Database credentials
    ValueScreen v= new ValueScreen();
    final String USER = v.user;
    final String PASS = v.pass;

    @FXML
    TableView<Periods> PeriodTable;
    @FXML
    TableColumn<Periods,Integer> ColumnID;
    @FXML
    TableColumn<Periods,String> ColumnPeriod;
    @FXML
    TextField textPeriod;
    @FXML
    Button periodBack;


    Boolean editbtn=false;
    int pid;

    public void periodEditClick()
    {
        Periods d=PeriodTable.getSelectionModel().getSelectedItem();
        //System.out.println(d.getPeriodName());
        textPeriod.setText(d.getPeriodName());
        pid=d.getID();
        editbtn=true;
    }

    public void periodSaveClick()
    {
        if(editbtn==false)
        {
            if(textPeriod.getText().length()>0)
            {
                addData(textPeriod.getText());
                refreshTable();
                textPeriod.setText("");
                TimeTables tt= new TimeTables();
                tt.createTimeTable();
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
            if(textPeriod.getText().length()>0)
            {
                updateData(pid, textPeriod.getText());
                refreshTable();
                textPeriod.setText("");
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

    public void periodCancelClick()
    {
        textPeriod.setText("");
        editbtn=false;
    }

    public void periodDeleteClick()
    {
        if(editbtn==true)
        {
            deleteData(pid);
            refreshTable();
            textPeriod.setText("");
            editbtn=false;
        }
    }

    public void periodRefreshClick()
    {
        refreshTable();
    }

    public void periodBackClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) periodBack.getScene().getWindow();
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

        ColumnID.setCellValueFactory(new PropertyValueFactory<Periods,Integer>("ID"));
        ColumnPeriod.setCellValueFactory(new PropertyValueFactory<Periods,String>("PeriodName"));

        refreshTable();

    }


    public void refreshTable()
    {
        ObservableList<Periods> data = FXCollections.observableArrayList();

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

            String sql = "SELECT * FROM periods ORDER BY id";
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                int id1=rs.getInt("id");
                String period1=rs.getString("period");
                //System.out.println("id:"+id1+" Period: "+Period1);

                Periods d=new Periods();
                d.setID(id1);
                d.setPeriodName(period1);

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

        PeriodTable.setItems(data);

    }



    public void addData(String sPeriod)
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

            String sql = "INSERT INTO periods (period) " +
                    "VALUES ('"+sPeriod+"');";
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


    public void updateData(int pid,String sPeriod)
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
            String sql = "UPDATE periods " +
                    "SET period ='"+sPeriod+"' WHERE id="+pid+";";
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
            String sql = "DELETE FROM periods " +
                    "WHERE id = "+pid+";";
            stmt.executeUpdate(sql);

            TimeTables tt= new TimeTables();
            tt.createTimeTable();

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
