package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by VISHAL-PC on 5/5/2017.
 */
public class FirstRun {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/timetable?autoReconnect=true&useSSL=false";

    //  Database credentials
    ValueScreen v= new ValueScreen();
    final String USER = v.user;
    final String PASS = v.pass;

    public void createDB()
    {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/?autoReconnect=true&useSSL=false", USER, PASS);

            //STEP 4: Execute a query
            //System.out.println("Creating database...");
            stmt = conn.createStatement();

            String sql = "CREATE DATABASE timetable";
            stmt.executeUpdate(sql);
            //System.out.println("Database created successfully...");
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
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public void createTables()
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
            //System.out.println("Creating table in given database...");
            stmt = conn.createStatement();

            //Creating Table days;
            String sql = "CREATE TABLE days " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " day VARCHAR(255) NOT NULL , " +
                    "UNIQUE (day), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);

            //Creating Table facultys;
            sql = "CREATE TABLE facultys " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255) NOT NULL , " +
                    " registration VARCHAR(255) NOT NULL , " +
                    " password VARCHAR(255), " +
                    " department VARCHAR(255), " +
                    "UNIQUE (registration), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);

            //Creating Table periods;
            sql = "CREATE TABLE periods " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " period VARCHAR(255) NOT NULL , " +
                    "UNIQUE (period), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);

            //Creating Table rooms;
            sql = "CREATE TABLE rooms " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255) NOT NULL, " +
                    " type VARCHAR(255), " +
                    " capacity INTEGER , " +
                    "UNIQUE (name), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);

            //Creating Table sections;
            sql = "CREATE TABLE sections " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " section VARCHAR(255) NOT NULL , " +
                    " department VARCHAR(255) NOT NULL , " +
                    " year VARCHAR(255) NOT NULL , " +
                    "UNIQUE (section,department,year), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);

            //Creating Table students;
            sql = "CREATE TABLE students " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " name VARCHAR(255) NOT NULL , " +
                    " registration VARCHAR(255) NOT NULL , " +
                    " password VARCHAR(255), " +
                    " department VARCHAR(255), " +
                    "UNIQUE (registration), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);

            //Creating Table subjects;
            sql = "CREATE TABLE subjects " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " subject VARCHAR(255) NOT NULL, " +
                    "UNIQUE (subject), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);

            //Creating Table ptt;
            sql = "CREATE TABLE ptt " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " day VARCHAR(255) NOT NULL , " +
                    " period VARCHAR(255) NOT NULL , " +
                    " room VARCHAR(255) NOT NULL , " +
                    " section VARCHAR(255), " +
                    " faculty VARCHAR(255), " +
                    " subject VARCHAR(255), " +
                    "UNIQUE (day,period,room), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);

            //Creating Table dtt;
            sql = "CREATE TABLE dtt " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " day VARCHAR(255) NOT NULL , " +
                    " period VARCHAR(255) NOT NULL , " +
                    " room VARCHAR(255) NOT NULL , " +
                    " section VARCHAR(255), " +
                    " faculty VARCHAR(255), " +
                    " subject VARCHAR(255), " +
                    "UNIQUE (day,period,room), " +
                    " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);



            //System.out.println("Created table in given database...");
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
