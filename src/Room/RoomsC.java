package Room;

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
public class RoomsC implements Initializable {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/timetable?autoReconnect=true&useSSL=false";

    //  Database credentials
    ValueScreen v= new ValueScreen();
    final String USER = v.user;
    final String PASS = v.pass;

    @FXML
    TableView<Rooms> RoomTable;
    @FXML
    TableColumn<Rooms,Integer> ColumnID;
    @FXML
    TableColumn<Rooms,String> ColumnRoom;
    @FXML
    TableColumn<Rooms,Integer> ColumnCapacity;
    @FXML
    TableColumn<Rooms,String> ColumnType;
    @FXML
    TextField textRoom;
    @FXML
    TextField textType;
    @FXML
    TextField textCapacity;
    @FXML
    Button roomBack;


    Boolean editbtn=false;
    int pid;

    public void roomEditClick()
    {
        Rooms d=RoomTable.getSelectionModel().getSelectedItem();
        //System.out.println(d.getRoomName());
        textRoom.setText(d.getRoomName());
        textType.setText(d.getTypeName());
        textCapacity.setText(""+d.getCapacity());
        pid=d.getID();
        editbtn=true;
    }

    public void roomSaveClick()
    {
        if(editbtn==false)
        {
            if(textRoom.getText().length()>0  && textType.getText().length()>0 && textCapacity.getText().length()>0)
            {
                addData(textRoom.getText(),textType.getText(),Integer.parseInt(textCapacity.getText()));
                refreshTable();
                textRoom.setText("");
                textCapacity.setText("");
                textType.setText("");
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
            if(textRoom.getText().length()>0  && textType.getText().length()>0 && textCapacity.getText().length()>0)
            {
                updateData(pid, textRoom.getText(), textType.getText(), Integer.parseInt(textCapacity.getText()));
                refreshTable();
                textRoom.setText("");
                textCapacity.setText("");
                textType.setText("");
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

    public void roomCancelClick()
    {
        textRoom.setText("");
        textCapacity.setText("");
        textType.setText("");
        editbtn=false;
    }

    public void roomDeleteClick()
    {
        if(editbtn==true)
        {
            deleteData(pid);
            refreshTable();
            textRoom.setText("");
            textCapacity.setText("");
            textType.setText("");
            editbtn=false;
        }
    }

    public void roomRefreshClick()
    {
        refreshTable();
    }

    public void roomBackClick()throws IOException
    {
        Stage stage;
        Parent root;
        stage=(Stage) roomBack.getScene().getWindow();
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

        ColumnID.setCellValueFactory(new PropertyValueFactory<Rooms,Integer>("ID"));
        ColumnRoom.setCellValueFactory(new PropertyValueFactory<Rooms,String>("RoomName"));
        ColumnType.setCellValueFactory(new PropertyValueFactory<Rooms,String>("TypeName"));
        ColumnCapacity.setCellValueFactory(new PropertyValueFactory<Rooms,Integer>("Capacity"));

        refreshTable();

    }


    public void refreshTable()
    {
        ObservableList<Rooms> data = FXCollections.observableArrayList();

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

            String sql = "SELECT * FROM rooms ORDER BY id";
            rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                int id1=rs.getInt("id");
                String room1=rs.getString("name");
                int cap1=rs.getInt("capacity");
                String type1=rs.getString("type");
                //System.out.println("id:"+id1+" day: "+day1);

                Rooms d=new Rooms();
                d.setID(id1);
                d.setRoomName(room1);
                d.setCapacity(cap1);
                d.setTypeName(type1);

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

        RoomTable.setItems(data);

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

            String sql = "INSERT INTO rooms (name,type,capacity) " +
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
            String sql = "UPDATE rooms " +
                    "SET name ='"+sname+"', type ='"+stype+"', capacity="+scap+" WHERE id="+pid+";";
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
            String sql = "DELETE FROM rooms " +
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

