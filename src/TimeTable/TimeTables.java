package TimeTable;

import sample.ValueScreen;

import java.sql.*;

/**
 * Created by VISHAL-PC on 4/29/2017.
 */
public class TimeTables {

    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost/timetable?autoReconnect=true&useSSL=false";

    //  Database credentials
    ValueScreen v= new ValueScreen();
    final String USER = v.user;
    final String PASS = v.pass;

    private int id;
    private String day,period,room,section,faculty,subject;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public void createTimeTable()
    {
        System.out.println("TimeTable Creation Start");
        droptt("ptt");
        creatett("ptt");
        inserttt();
        copytt1();
        droptt("dtt");
        creatett("dtt");
        copytt2();
        System.out.println("TimeTable Creation End");
    }

    public void droptt(String s)
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
            String sql = "DROP TABLE "+s;

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


    public void creatett(String s)
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
            String sql = "CREATE  TABLE "+s+
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


    public void copytt2()
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
            String sql = "INSERT INTO dtt SELECT * FROM ptt;";

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

    public void inserttt()
    {

        Connection conn = null;
        Statement stmt = null,st1=null,st2=null,st3=null;
        int i=0;
        String s1="",s2="",s3="";
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
            st1 = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            st2 = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            st3 = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            String sql1="SELECT day FROM days ORDER BY id";
            String sql2="SELECT period FROM periods ORDER BY id";
            String sql3="SELECT name FROM rooms ORDER BY id";
            ResultSet rs1 = st1.executeQuery(sql1);
            ResultSet rs2 = st2.executeQuery(sql2);
            ResultSet rs3 = st3.executeQuery(sql3);
            while(rs1.next()) {

                s1=rs1.getString("day");
                rs2.beforeFirst();

                while(rs2.next()) {

                    s2=rs2.getString("period");
                    rs3.beforeFirst();
                    while(rs3.next()) {
                        s3=rs3.getString("name");
                        i++;
                        //System.out.println("id:"+i);
                        String sql = "INSERT INTO ptt " +
                                "VALUES (" + i + ",'" + s1 + "','" + s2 + "','" + s3 + "',NULL,NULL,NULL)";
                        stmt.executeUpdate(sql);

                    }
                }
            }
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

    public void copytt1()
    {

        Connection conn = null;
        Statement stmt = null,stmt1 = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            //System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            //System.out.println("Creating statement...");
            stmt1 = conn.createStatement();
            stmt = conn.createStatement( ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

            String sql = "SELECT * FROM dtt ORDER BY id";
            ResultSet rs = stmt.executeQuery(sql);
            //STEP 5: Extract data from result set
            //rs.last();
            //rs.beforeFirst();
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                String day = rs.getString("day");
                String period = rs.getString("period");
                String room = rs.getString("room");
                String section = rs.getString("section");
                String faculty = rs.getString("faculty");
                String subject = rs.getString("subject");

                String sql1= "UPDATE ptt SET section ='"+section+"', faculty='"+faculty+"', subject='"+subject+"' WHERE day='"+day+"' AND period='"
                        +period+"' AND room='"+room+"';";

                stmt1.executeUpdate(sql1);

                //Display values
                /*System.out.print("ID: " + id);
                System.out.print("  , day: " + day);
                System.out.print("  , period: " + period);
                System.out.print("  , room: " + room);
                System.out.print("  , class: " + section);
                System.out.print("  , faculty: " + faculty);
                System.out.println("  , subject: " + subject);*/
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
