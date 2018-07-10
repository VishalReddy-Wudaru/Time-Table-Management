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
public class RoomScheduleC implements Initializable {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/timetable?autoReconnect=true&useSSL=false";

    //  Database credentials
    ValueScreen v= new ValueScreen();
    final String USER = v.user;
    final String PASS = v.pass;

    @FXML
    TableView<Data1> Table;
    @FXML
    TableColumn<Data1,String> Column0;
    @FXML
    TableColumn<Data1,String> Column1;
    @FXML
    TableColumn<Data1,String> Column2;
    @FXML
    TableColumn<Data1,String> Column3;
    @FXML
    TableColumn<Data1,String> Column4;
    @FXML
    TableColumn<Data1,String> Column5;
    @FXML
    TableColumn<Data1,String> Column6;
    @FXML
    TableColumn<Data1,String> Column7;
    @FXML
    TableColumn<Data1,String> Column8;
    @FXML
    TableColumn<Data1,String> Column9;
    @FXML
    ChoiceBox<String> choice;
    @FXML
    Button back;



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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Column0.setCellValueFactory(new PropertyValueFactory<Data1,String>("day"));
        Column1.setCellValueFactory(new PropertyValueFactory<Data1,String>("c1"));
        Column2.setCellValueFactory(new PropertyValueFactory<Data1,String>("c2"));
        Column3.setCellValueFactory(new PropertyValueFactory<Data1,String>("c3"));
        Column4.setCellValueFactory(new PropertyValueFactory<Data1,String>("c4"));
        Column5.setCellValueFactory(new PropertyValueFactory<Data1,String>("c5"));
        Column6.setCellValueFactory(new PropertyValueFactory<Data1,String>("c6"));
        Column7.setCellValueFactory(new PropertyValueFactory<Data1,String>("c7"));
        Column8.setCellValueFactory(new PropertyValueFactory<Data1,String>("c8"));
        Column9.setCellValueFactory(new PropertyValueFactory<Data1,String>("c9"));
        getRoomChoice();

        choice.getSelectionModel().selectedItemProperty().addListener( (v,oldvalue,newvalue) -> refreshTable(newvalue));

    }


    public void refreshTable(String s)
    {
        if(s.equals("null"))
        {

        }
        else {
            ObservableList<Data1> data = FXCollections.observableArrayList();

            Connection conn = null;
            Statement stmt = null, st1 = null, st2 = null, st3 = null;
            int i, j, c, n, m;
            //String a[][];
            String s1 = "", s2 = "";
            try {
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                //STEP 3: Open a connection
                //System.out.println("Connecting to a selected database...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                //System.out.println("Connected database successfully...");

                //STEP 4: Execute a query
                //System.out.println("Inserting records into the table...");
                stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                st1 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                st2 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                String sql1 = "SELECT day FROM days ORDER BY id;";
                String sql2 = "SELECT period FROM periods ORDER BY id;";
                ResultSet rs1 = st1.executeQuery(sql1);
                ResultSet rs2 = st2.executeQuery(sql2);

                rs1.last();
                n = rs1.getRow();


                rs2.last();
                m = rs2.getRow();

                rs1.beforeFirst();
                //a= new String[n][m+1];
                i = 0;
                while (rs1.next()) {

                    Data1 d = new Data1();
                    s1 = rs1.getString("day");
                    //a[i][0]=s1;
                    d.setDay(s1);
                    rs2.beforeFirst();
                    j = 1;
                    while (rs2.next()) {
                        s2 = rs2.getString("period");
                        //System.out.println(s1+"   "+s2);

                    /*String sql = "SELECT * FROM timetable WHERE day='"+s1+"' AND period='"+s2+"' AND faculty='MNO'";
                    ResultSet rs = stmt.executeQuery(sql);
                    rs.last();
                    c=rs.getRow();
                    a[i][j]=a[i][j]+c;*/

                        String sql = "SELECT * FROM dtt WHERE day='" + s1 + "' AND period='" + s2 + "' AND room='" + s + "' ORDER BY id;";
                        ResultSet rs = stmt.executeQuery(sql);
                        rs.last();
                        c = rs.getRow();
                        try {
                            if (c > 0 && rs.getString("faculty").length() > 4) {
                                //a[i][j]=rs.getString("subject")+"\n"+rs.getString("room");
                                switch (j) {
                                    case 1:
                                        d.setC1(rs.getString("section") + "\n" + rs.getString("faculty"));
                                        break;
                                    case 2:
                                        d.setC2(rs.getString("section") + "\n" + rs.getString("faculty"));
                                        break;
                                    case 3:
                                        d.setC3(rs.getString("section") + "\n" + rs.getString("faculty"));
                                        break;
                                    case 4:
                                        d.setC4(rs.getString("section") + "\n" + rs.getString("faculty"));
                                        break;
                                    case 5:
                                        d.setC5(rs.getString("section") + "\n" + rs.getString("faculty"));
                                        break;
                                    case 6:
                                        d.setC6(rs.getString("section") + "\n" + rs.getString("faculty"));
                                        break;
                                    case 7:
                                        d.setC7(rs.getString("section") + "\n" + rs.getString("faculty"));
                                        break;
                                    case 8:
                                        d.setC8(rs.getString("section") + "\n" + rs.getString("room"));
                                        break;
                                    case 9:
                                        d.setC9(rs.getString("section") + "\n" + rs.getString("room"));
                                        break;

                                }
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        j++;
                    }
                    i++;
                    data.add(d);
                }

            /*for(i=0;i<n;i++)
            {
                for(j=0;j<m+1;j++)
                {
                    System.out.print(a[i][j]+"  ");
                }
                System.out.println();
            }*/


                //System.out.println("Updated records of the table...");

            } catch (SQLException se) {
                //Handle errors for JDBC
                se.printStackTrace();
            } catch (Exception e) {
                //Handle errors for Class.forName
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                try {
                    if (stmt != null)
                        conn.close();
                } catch (SQLException se) {
                }// do nothing
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try
            Table.setItems(data);
        }
    }




    public void getRoomChoice()
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

            String sql = "SELECT * FROM rooms  ORDER BY id;";
            rs = stmt.executeQuery(sql);
            int i=0;
            //STEP 5: Extract data from result set
            while(rs.next())
            {
                String room1=rs.getString("name");
                choice.getItems().add(room1);
                if(i==0)
                {
                    choice.setValue(room1);
                    refreshTable(room1);
                    i++;
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

}
