package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import model.Student;

public class Control {
    
    Student[] s;
    Scanner sc = new Scanner(System.in);
    
    private static int rollNumberCounter = 1; // Static variable to track the last roll number assigned

    public static Connection Connect() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/e1"; // Corrected URL
        String uname = "root";
        String pass =  "abc123";
        Class.forName("com.mysql.cj.jdbc.Driver"); // Corrected driver class name
        Connection con = DriverManager.getConnection(url, uname, pass);
        
        return con;
    }

    public void Insert() throws ClassNotFoundException, SQLException {    
        
        System.out.println("Enter the number of Students: ");
        int number = Integer.parseInt(sc.nextLine());
        
        s = new Student[number]; // Initialize the array

        for (int i = 0; i < number; i++) {
            int rollno = rollNumberCounter++; // Auto-generate roll number

            System.out.println("Enter the Name: ");
            String name = sc.nextLine();
            System.out.println("Enter the Address: ");
            String address = sc.nextLine();
            System.out.println("Enter the Marks: ");
            long mark = Long.parseLong(sc.nextLine());
            
            s[i] = new Student();
            s[i].setRollno(rollno);
            s[i].setName(name);
            s[i].setAddress(address);
            s[i].setMark(mark);
            
            Connection con = null;
            PreparedStatement ps = null;

            try {
                con = Control.Connect();
                ps = con.prepareStatement("INSERT INTO t1 (rollno, name, address, mark) VALUES (?, ?, ?, ?)");
                ps.setInt(1, rollno);
                ps.setString(2, name);
                ps.setString(3, address);
                ps.setLong(4, mark);
                
                int status = ps.executeUpdate();
                
                if (status > 0) 
                {    
                    System.out.println("Data Inserted ~");
                } else {
                    System.out.println("Data Not Inserted ~");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            } 
            finally {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        }
    }
}
