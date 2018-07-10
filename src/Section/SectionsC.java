package Section;

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
public class SectionsC implements Initializable {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/timetable?autoReconnect=true&useSSL=false";

    //  Database credentials
    ValueScreen v= new ValueScreen();
    final String USER = v.user;
    final String PASS = v.pass;

    @FXML
    TableView<Sections> SectionTable;
    @FXML
    TableColumn<Sections,Integer> ColumnID;
    @FXML
    TableColumn<Sections,String> ColumnSection;
    @FXML
    TableColumn<Sections,Integer> ColumnYear;
    @FXML
    TableColumn<Sections,String> ColumnDepartment;
    @FXML
    TextField textSection;
    @FXML
    TextField textDepartment;
    @FXML
    TextField textYear;
    @FXML
    Button sectionBack;


    Boolean editbtn=false;
    int pid;

    public void sectionEditClick()
    {
        Sections d=SectionTable.getSelectionModel().getSelectedItem();
        //System.out.println(d.getSectionName());
        textSection.setText(d.getSectionName());
        textDepartment.setText(d.getDeptName());
        textYear.setText(""+d.getYear());
        pid=d.getID();
        editbtn=true;
    }

    public void sectionSaveClick()
    {
        if(editbtn==false)
        {
            if(textSection.getText().length()>0 && textDepartment.getText().length()>0 && textYear.getText().length()>0)
            {
                addData(textSection.getText(),textDepartment.getText(),Integer.parseInt(textYear.getText()));
                refreshTable();
                textSection.setText("");
                textDepartment.setText("");
                textYear.setText("");
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
            if(textSection.getText().length()>0 && textDepartment.getText().length()>0 && textYear.getText().length()>0)
            {
                updateData(pid, textSection.getText(), textDepartment.getText(), Integer.parseInt(textYear.getText()));
                refreshTable();
                textSection.setText("");
                textDepartment.setText("");
                textYear.setText("");
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

    public void sectionCancelClick()
    {
        textSection.setText("");
        textDepartment.setText("");
        textYear.setText("");
        editbtn=false;
    }

    public void sectionDeleteClick()
    {
        if(editbtn==true)
        {
            deleteData(pid);
            refreshTable();
            textSection.setText("");
            textDepartment.setText("");
            textYear.setText("");
            editbtn=false;
        }
    }

    public void sectionRefreshClick()
    {
        refreshTable();
    }

    public void sectionBackClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) sectionBack.getScene().getWindow();
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

        ColumnID.setCellValueFactory(new PropertyValueFactory<Sections,Integer>("ID"));
        ColumnSection.setCellValueFactory(new PropertyValueFactory<Sections,String>("SectionName"));
        ColumnDepartment.setCellValueFactory(new PropertyValueFactory<Sections,String>("DeptName"));
        ColumnYear.setCellValueFactory(new PropertyValueFactory<Sections,Integer>("Year"));

        refreshTable();

    }


    public void refreshTable()
    {
        ObservableList<Sections> data = FXCollections.observableArrayList();

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

            String sql = "SELECT * FROM sections ORDER BY id";
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                int id1=rs.getInt("id");
                String room1=rs.getString("section");
                int year1=rs.getInt("year");
                String dept1=rs.getString("department");
                //System.out.println("id:"+id1+" day: "+day1);

                Sections d=new Sections();
                d.setID(id1);
                d.setSectionName(room1);
                d.setYear(year1);
                d.setDeptName(dept1);

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

        SectionTable.setItems(data);

    }



    public void addData(String sname,String stype,int scap)
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

            String sql = "INSERT INTO sections (section,department,year) " +
                    "VALUES ('"+sname+"', '"+stype+"',"+scap+");";
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


    public void updateData(int pid,String sname,String stype,int scap)
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
            String sql = "UPDATE sections " +
                    "SET section ='"+sname+"', department ='"+stype+"', year="+scap+" WHERE id="+pid+";";
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
            String sql = "DELETE FROM sections " +
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
