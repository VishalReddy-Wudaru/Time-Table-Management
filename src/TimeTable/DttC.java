package TimeTable;

import Section.Sections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.ValueScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by VISHAL-PC on 4/29/2017.
 */
public class DttC implements Initializable {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/timetable?autoReconnect=true&useSSL=false";

    //  Database credentials
    ValueScreen v= new ValueScreen();
    final String USER = v.user;
    final String PASS = v.pass;

    @FXML
    TableView<TimeTables> dttTable;
    @FXML
    TableColumn<TimeTables,Integer> ColumnID;
    @FXML
    TableColumn<TimeTables,String> ColumnDay;
    @FXML
    TableColumn<TimeTables,String> ColumnPeriod;
    @FXML
    TableColumn<TimeTables,String> ColumnRoom;
    @FXML
    TableColumn<TimeTables,String> ColumnSection;
    @FXML
    TableColumn<TimeTables,String> ColumnFaculty;
    @FXML
    TableColumn<TimeTables,String> ColumnSubject;
    @FXML
    ChoiceBox<String> choiceSection;
    @FXML
    ChoiceBox<String> choiceFaculty;
    @FXML
    ChoiceBox<String> choiceSubject;
    @FXML
    Text textDay;
    @FXML
    Text textPeriod;
    @FXML
    Text textRoom;
    @FXML
    Button dttGoTT;
    @FXML
    Button dttBack;

    Boolean editbtn=false;
    int pid;

    public void dttEditClick()
    {
        TimeTables d=dttTable.getSelectionModel().getSelectedItem();
        //System.out.println(d.getStudentName());
        textDay.setText(d.getDay());
        textPeriod.setText(d.getPeriod());
        textRoom.setText(d.getRoom());
        choiceSection.setValue(d.getSection());
        choiceFaculty.setValue(d.getFaculty());
        choiceSubject.setValue(d.getSubject());
        pid=d.getId();
        getSectionChoice();

        getFacultyChoice();

        getSubjectChoice();

        removeExtraChoices(d.getDay(), d.getPeriod());


        try{
        if(d.getFaculty().length()>5)
        {
            choiceSection.getItems().add(d.getSection());
            choiceFaculty.getItems().add(d.getFaculty());
            choiceSection.setValue(d.getSection());
            choiceFaculty.setValue(d.getFaculty());
            choiceSubject.setValue(d.getSubject());
        }
        }
        catch(Exception e)
        {
            choiceSection.setValue("null");
            choiceFaculty.setValue("null");
            choiceSubject.setValue("null");
        }
        editbtn=true;
    }

    public void dttSaveClick()
    {
        if(editbtn==false)
        {
            /*if(textSection.getText().length()>0)
            {
                addData(textSection.getText(),textFaculty.getText(),textSubject.getText());
                refreshTable();
                textDay.setText("");
                textPeriod.setText("");
                textRoom.setText("");
                textSection.setText("");
                textFaculty.setText("");
                textSubject.setText("");
            }*/
        }
        else
        {
            updateData(pid,choiceSection.getValue(),choiceFaculty.getValue(),choiceSubject.getValue());
            refreshTable();
            textDay.setText("");
            textPeriod.setText("");
            textRoom.setText("");
            choiceSection.setValue("null");
            choiceFaculty.setValue("null");
            choiceSubject.setValue("null");
            editbtn=false;
        }
    }

    public void dttCancelClick()
    {
        textDay.setText("");
        textPeriod.setText("");
        textRoom.setText("");
        choiceSection.setValue("null");
        choiceFaculty.setValue("null");
        choiceSubject.setValue("null");
        editbtn=false;
    }


    public void dttRefreshClick()
    {
        refreshTable();
    }

    public void dttBackClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) dttBack.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/Admin/adimDashBoard.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Admin DashBoard");
        stage.setScene(scene);
        stage.show();
    }

    public void dttGoTTClick() throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) dttGoTT.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("PttF.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Permanent TimeTable");
        stage.setScene(scene);
        stage.show();
    }

    public void dttGetTTClick()
    {
        System.out.println("Copying PTT Start");
        TimeTables tt= new TimeTables();
        tt.droptt("dtt");
        tt.creatett("dtt");
        tt.copytt2();
        refreshTable();
        System.out.println("Copying PTT Complete");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ColumnID.setCellValueFactory(new PropertyValueFactory<TimeTables,Integer>("id"));
        ColumnDay.setCellValueFactory(new PropertyValueFactory<TimeTables,String>("day"));
        ColumnPeriod.setCellValueFactory(new PropertyValueFactory<TimeTables,String>("period"));
        ColumnRoom.setCellValueFactory(new PropertyValueFactory<TimeTables,String>("room"));
        ColumnSection.setCellValueFactory(new PropertyValueFactory<TimeTables,String>("section"));
        ColumnFaculty.setCellValueFactory(new PropertyValueFactory<TimeTables,String>("faculty"));
        ColumnSubject.setCellValueFactory(new PropertyValueFactory<TimeTables,String>("subject"));

        choiceSection.getItems().add("null");
        choiceSection.setValue("null");

        choiceSubject.getItems().add("null");
        choiceSubject.setValue("null");

        choiceFaculty.getItems().add("null");
        choiceFaculty.setValue("null");

        refreshTable();

    }


    public void refreshTable()
    {
        ObservableList<TimeTables> data = FXCollections.observableArrayList();

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

            String sql = "SELECT * FROM dtt ORDER BY id;";
            rs = stmt.executeQuery(sql);
            if(rs.next()==false)
            {
                TimeTables tt= new TimeTables();
                tt.createTimeTable();
                rs= stmt.executeQuery(sql);
            }
            else
            {
                rs.previous();
            }
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                int id  = rs.getInt("id");
                String day = rs.getString("day");
                String period = rs.getString("period");
                String room = rs.getString("room");
                String section = rs.getString("section");
                String faculty = rs.getString("faculty");
                String subject = rs.getString("subject");
                //System.out.println("id:"+id1+" day: "+day1);

                TimeTables d=new TimeTables();
                d.setId(id);
                d.setDay(day);
                d.setPeriod(period);
                d.setRoom(room);
                try
                {
                    if (faculty.length() > 4) {
                        d.setSection(section);
                        d.setFaculty(faculty);
                        d.setSubject(subject);
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }

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

        dttTable.setItems(data);

    }






    public void updateData(int pid,String sSec,String sFac,String sSub)
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
            String sql = "UPDATE dtt " +
                    "SET section ='"+sSec+"', faculty ='"+sFac+"', subject='"+sSub+"' WHERE id="+pid+";";
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




    public void getSectionChoice()
    {
        choiceSection.getItems().clear();
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

            String sql = "SELECT * FROM sections  ORDER BY id";
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                String room1=rs.getString("section");
                choiceSection.getItems().add(room1);
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


    }


    public void getFacultyChoice()
    {

        choiceFaculty.getItems().clear();
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

            String sql = "SELECT * FROM facultys  ORDER BY id";
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                String room1=rs.getString("name");
                choiceFaculty.getItems().add(room1);
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


    }


    public void getSubjectChoice()
    {

        choiceSubject.getItems().clear();
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

            String sql = "SELECT * FROM subjects ORDER BY id";
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                String room1=rs.getString("subject");
                choiceSubject.getItems().add(room1);
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

    }

    public void removeExtraChoices(String day,String period)
    {

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

            String sql = "SELECT section,faculty,subject FROM dtt WHERE day = '"+day+"' AND period = '"+period+"' ORDER BY id;";
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                String section=rs.getString("section");
                String faculty=rs.getString("faculty");
                //String subject=rs.getString("subject");


                choiceSection.getItems().remove(section);
                choiceFaculty.getItems().remove(faculty);
                //choiceSubject.getItems().remove(subject);

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

        choiceSection.getItems().add("null");
        choiceSection.setValue("null");

        choiceSubject.getItems().add("null");
        choiceSubject.setValue("null");

        choiceFaculty.getItems().add("null");
        choiceFaculty.setValue("null");


    }


}
