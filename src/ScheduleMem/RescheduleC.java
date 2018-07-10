package ScheduleMem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.ValueScreen;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by VISHAL-PC on 4/30/2017.
 */
public class RescheduleC implements Initializable{

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/timetable?autoReconnect=true&useSSL=false";

    //  Database credentials
    ValueScreen v= new ValueScreen();
    final String USER = v.user;
    final String PASS = v.pass;

    @FXML
    TableView<Data2> Table;
    @FXML
    TableColumn<Data2,String> ColumnDay;
    @FXML
    TableColumn<Data2,String> ColumnPeriod;
    @FXML
    TableView<Data3> Table1;
    @FXML
    TableColumn<Data3,String> ColumnRoom;
    @FXML
    ChoiceBox<String> choiceFaculty;
    @FXML
    ChoiceBox<String> choiceSection;
    @FXML
    Button back;
    @FXML
    Button room;

    public void backClick()throws IOException
    {

        Stage stage;
        Parent root;
        stage=(Stage) back.getScene().getWindow();
        root= FXMLLoader.load(getClass().getResource("/StudentFaculty/SFDashBoard.fxml"));
        ValueScreen v= new ValueScreen();
        Scene scene = new Scene(root,v.width,v.height);
        stage.setFullScreen(v.full);
        stage.setTitle("Member DashBoard");
        stage.setScene(scene);
        stage.show();

    }


    public void roomClick()
    {
        roomtable(Table.getSelectionModel().getSelectedItem());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ColumnDay.setCellValueFactory(new PropertyValueFactory<Data2,String>("day"));
        ColumnPeriod.setCellValueFactory(new PropertyValueFactory<Data2,String>("period"));
        ColumnRoom.setCellValueFactory(new PropertyValueFactory<Data3,String>("room"));
        getFacultyChoice();
        getSectionChoice();

        choiceFaculty.getSelectionModel().selectedItemProperty().addListener( (v,oldvalue,newvalue) -> refreshTable1(newvalue,choiceSection.getValue()));
        choiceSection.getSelectionModel().selectedItemProperty().addListener( (v,oldvalue,newvalue) -> refreshTable2(newvalue,choiceFaculty.getValue()));

        //Table.getSelectionModel().selectedItemProperty().addListener( (v,olvalue,newvalue) -> roomtable(newvalue));

    }


    public void getFacultyChoice()
    {
        //choice.getItems().clear();
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

            String sql = "SELECT * FROM facultys ORDER BY id";
            rs = stmt.executeQuery(sql);
            int i=0;
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                String room1=rs.getString("name");
                choiceFaculty.getItems().add(room1);
                if(i==0)
                {
                    choiceFaculty.setValue(room1);
                }
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

    public void getSectionChoice()
    {
        //choice.getItems().clear();
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
            int i=0;
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                String room1=rs.getString("section");
                choiceSection.getItems().add(room1);
                if(i==0)
                {
                    choiceSection.setValue(room1);
                }
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

    public void refreshTable1(String faculty,String section)
    {

        //System.out.println("faculty:"+faculty+" section:"+section);

        ObservableList<Data2> data = FXCollections.observableArrayList();
        Connection conn = null;
        Statement stmt = null,st1=null,st2=null,st3=null;
        int i,j,c,n,m;
        int a[][];
        String s1="",s2="";
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            //System.out.println("Inserting records into the table...");
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            st1 = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            st2 = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            String sql1="SELECT day FROM days ORDER BY id";
            String sql2="SELECT period FROM periods ORDER BY id";
            ResultSet rs1 = st1.executeQuery(sql1);
            ResultSet rs2 = st2.executeQuery(sql2);

            rs1.last();
            n=rs1.getRow();


            rs2.last();
            m=rs2.getRow();

            rs1.beforeFirst();
            a= new int[n][m];
            i=0;
            while(rs1.next()) {

                s1=rs1.getString("day");
                rs2.beforeFirst();
                j=0;
                while(rs2.next()) {
                    s2=rs2.getString("period");
                    //System.out.println(s1+"   "+s2);
                    int count=0;

                    String sql = "SELECT * FROM dtt WHERE day='"+s1+"' AND period='"+s2+"' AND faculty='"+faculty+"' ORDER BY id;";
                    ResultSet rs = stmt.executeQuery(sql);
                    rs.last();
                    c=rs.getRow();
                    a[i][j]=a[i][j]+c;
                    count=count+c;

                    sql = "SELECT * FROM dtt WHERE day='"+s1+"' AND period='"+s2+"' AND section='"+section+"' ORDER BY id;";
                    rs = stmt.executeQuery(sql);
                    rs.last();
                    c=rs.getRow();
                    a[i][j]=a[i][j]+c;
                    count=count+c;
                    if(count==0)
                    {
                        Data2 d= new Data2();
                        d.setDay(s1);
                        d.setPeriod(s2);
                        data.add(d);
                    }
                    j++;
                }
                i++;
            }

            for(i=0;i<n;i++)
            {
                for(j=0;j<m;j++)
                {
                    System.out.print(a[i][j]+"  ");
                }
                System.out.println();
            }


            //System.out.println("Updated records of the table...");

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
        Table.setItems(data);
    }

    public void refreshTable2(String section,String faculty)
    {
        ObservableList<Data2> data = FXCollections.observableArrayList();
        Connection conn = null;
        Statement stmt = null,st1=null,st2=null,st3=null;
        int i,j,c,n,m;
        int a[][];
        String s1="",s2="";
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            //System.out.println("Inserting records into the table...");
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            st1 = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            st2 = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            String sql1="SELECT day FROM days ORDER BY id";
            String sql2="SELECT period FROM periods ORDER BY id";
            ResultSet rs1 = st1.executeQuery(sql1);
            ResultSet rs2 = st2.executeQuery(sql2);

            rs1.last();
            n=rs1.getRow();


            rs2.last();
            m=rs2.getRow();

            rs1.beforeFirst();
            a= new int[n][m];
            i=0;
            while(rs1.next()) {

                s1=rs1.getString("day");
                rs2.beforeFirst();
                j=0;
                while(rs2.next()) {
                    s2=rs2.getString("period");
                    //System.out.println(s1+"   "+s2);
                    int count=0;

                    String sql = "SELECT * FROM dtt WHERE day='"+s1+"' AND period='"+s2+"' AND faculty='"+faculty+"'  ORDER BY id;";
                    ResultSet rs = stmt.executeQuery(sql);
                    rs.last();
                    c=rs.getRow();
                    a[i][j]=a[i][j]+c;
                    count=count+c;

                    sql = "SELECT * FROM dtt WHERE day='"+s1+"' AND period='"+s2+"' AND section='"+section+"'  ORDER BY id;";
                    rs = stmt.executeQuery(sql);
                    rs.last();
                    c=rs.getRow();
                    a[i][j]=a[i][j]+c;
                    count=count+c;
                    if(count==0)
                    {
                        Data2 d= new Data2();
                        d.setDay(s1);
                        d.setPeriod(s2);
                        data.add(d);
                    }
                    j++;
                }
                i++;
            }

            for(i=0;i<n;i++)
            {
                for(j=0;j<m;j++)
                {
                    System.out.print(a[i][j]+"  ");
                }
                System.out.println();
            }


            //System.out.println("Updated records of the table...");

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
        Table.setItems(data);
    }


    public void roomtable(Data2 d)
    {
        ObservableList<Data3> data = FXCollections.observableArrayList();

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

            String sql = "SELECT * FROM dtt WHERE day='"+d.getDay()+"' AND period='"+d.getPeriod()+"'  ORDER BY id;";
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            //int i=0;
            while(rs.next())
            {
                String room1 = rs.getString("room");
                //System.out.println(room1);
                try
                {

                    if (rs.getString("faculty").length() > 5) {
                        //room1 = rs.getString("room");
                        //System.out.println(room1);

                    } else {
                        Data3 d1 = new Data3();
                        d1.setRoom(room1);

                        data.add(d1);
                    }
                }
                catch(Exception e)
                {
                    Data3 d1 = new Data3();
                    d1.setRoom(room1);

                    data.add(d1);
                }
                //i++;
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
        Table1.getItems().clear();
        Table1.setItems(data);
    }

}
