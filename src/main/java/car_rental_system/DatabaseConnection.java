
package car_rental_system;

import java.sql.*;
class DatabaseConnection {

    
    public Connection c;
    public Statement s;
    public DatabaseConnection(){
        try{
           
            
            c=DriverManager.getConnection("jdbc:mysql:///stqa","root","ishan");//syntax for establish connection
            s = c.createStatement(); //using this statement we can fire the sql commands
            
        }catch(Exception e){
            System.out.println(e);//if error present prints(Exception error printer )
        }
    }
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is loaded
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql:///stqa","root","ishan");
    }

}
