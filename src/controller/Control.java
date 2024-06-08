package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.cj.xdevapi.PreparableStatement;

import model.Employee;

public class Control {
	Employee e[];

	public Control() {
		e = new Employee[50];
	}

	Scanner sc = new Scanner(System.in);
	int n = 0, k = 0;

	public static Connection connect() throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3306/e1", uname = "root", pass = "abc123";

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, uname, pass);

		return con;
	}

	public void Insert() throws SQLException, ClassNotFoundException {
		try {
			Loding();
			System.out.print("\nEnter the number of Employees:");
			n = Integer.parseInt(sc.nextLine());
			for (int i = 0; i < n; i++) {
				System.out.print("Enter Your Id : ");
				int eid = Integer.parseInt(sc.nextLine());
				System.out.print("Enter your name : ");
				String name = sc.nextLine();
				System.out.print("Enter your Address : ");
				String address = sc.nextLine();
				System.out.print("Enter Your Salary : ");
				int salary = Integer.parseInt(sc.nextLine());

				e[i] = new Employee();
				e[i].setEid(eid);
				e[i].setName(name);
				e[i].setAddress(address);
				e[i].setSalary(salary);

				Connection con = Control.connect();// step 2

				PreparedStatement ps = con.prepareStatement("insert into t2 values(?,?,?,?)");
				ps.setInt(1, eid);
				ps.setString(2, name);
				ps.setString(3, address);
				ps.setInt(4, salary);

				int a = ps.executeUpdate();

				if (a > 0) {
					System.out.println("Data Insert ~ ");
				} else {
					System.out.println("Data Not Insert ~");
				}
			}
		}

		catch (Exception e) {
			System.out.println("Exception " + e);
		}
		System.out.println("...................................|");
	}

	public void Show() throws ClassNotFoundException, SQLException {

		Connection con = Control.connect();// step 2

		PreparedStatement ps = con.prepareStatement("select * from  t2 ");

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {

			System.out.println("Eid : " + rs.getInt(1) + " , Name : " + rs.getString(2) + "\nAddress : "
					+ rs.getString(3) + ", Salary : " + rs.getInt(4) + "");
			System.out.println("...........................|");
		}
	}

	 public void Update() throws ClassNotFoundException, SQLException {
	        Connection con1 = null;
	        PreparedStatement ps1 = null;
	        ResultSet rs = null;

	        try {
	            System.out.println("Enter Eid : ");
	            int i = Integer.parseInt(sc.nextLine());

	            // Establishing the connection
	            con1 = Control.connect();

	            // Query to retrieve the current data
	            ps1 = con1.prepareStatement("SELECT * FROM t2 WHERE eid = ?");
	            ps1.setInt(1, i);
	            rs = ps1.executeQuery();

	            // Check if the eid exists and display current data
	            if (rs.next()) {
	                System.out.println("Current data:");
	                System.out.println("Eid: " + rs.getInt("eid"));
	                System.out.println("Name: " + rs.getString("name"));
	                System.out.println("Salary: " + rs.getDouble("salary"));
	                System.out.println("Address: " + rs.getString("address"));

	                boolean updateDone = false;
	                
	                while (!updateDone) {
	                    showUpdateMenu();
	                    int choice = Integer.parseInt(sc.nextLine());

	                    switch (choice) {
	                        case 1:
	                            System.out.println("\nEnter Update name : ");
	                            String name = sc.nextLine();
	                            ps1 = con1.prepareStatement("UPDATE t2 SET name = ? WHERE eid = ?");
	                            ps1.setString(1, name);
	                            ps1.setInt(2, i);
	                            break;
	                        case 2:
	                            System.out.println("\nEnter Update salary : ");
	                            double salary = Double.parseDouble(sc.nextLine());
	                            ps1 = con1.prepareStatement("UPDATE t2 SET salary = ? WHERE eid = ?");
	                            ps1.setDouble(1, salary);
	                            ps1.setInt(2, i);
	                            break;
	                        case 3:
	                            System.out.println("\nEnter Update address : ");
	                            String address = sc.nextLine();
	                            ps1 = con1.prepareStatement("UPDATE t2 SET address = ? WHERE eid = ?");
	                            ps1.setString(1, address);
	                            ps1.setInt(2, i);
	                            break;
	                        case 4:
	                            updateDone = true;
	                            continue;
	                        default:
	                            System.out.println("Invalid option. Please try again.");
	                            continue;
	                    }

	                    int a = ps1.executeUpdate();
	                    if (a > 0) {
	                        System.out.println("Update successful!");
	                    } else {
	                        System.out.println("Please try again..~");
	                    }
	                }
	            } else {
	                System.out.println("Eid not found in the database.");
	            }
	        } catch (Exception e) {
	            System.out.println("Exception " + e);
	        } finally {
	            // Close resources
	            if (rs != null) rs.close();
	            if (ps1 != null) ps1.close();
	            if (con1 != null) con1.close();
	        }
	    }

	    private void showUpdateMenu() {
	        System.out.println("\nUpdate Menu:");
	        System.out.println("1. Update name");
	        System.out.println("2. Update salary");
	        System.out.println("3. Update address");
	        System.out.println("4. Exit");
	        System.out.print("Enter your choice: ");
	    }

	    public void Delete() throws ClassNotFoundException, SQLException {
	    	
	        Connection con2 = null;
	        PreparedStatement ps2 = null;
	        ResultSet rs = null;

	        try {
	            boolean l = true;
	            
	            while (l) {
	                System.out.println("Enter Eid : ");
	                int i = Integer.parseInt(sc.nextLine());
	                Loding();

	                // Establish connection
	                con2 = Control.connect();

	                // Check if the eid exists
	                ps2 = con2.prepareStatement("SELECT COUNT(*) FROM t2 WHERE eid = ?");
	                ps2.setInt(1, i);
	                rs = ps2.executeQuery();

	                if (rs.next() && rs.getInt(1) > 0) {
	                    // If eid exists, proceed with deletion
	                    ps2 = con2.prepareStatement("DELETE FROM t2 WHERE eid = ?");
	                    ps2.setInt(1, i);
	                    int a = ps2.executeUpdate();

	                    if (a > 0) {
	                        System.out.println("\nDelete Data ~");
	                        l = false;
	                    } else {
	                        System.out.println("\nPlease try again..~");
	                    }
	                } else {
	                    System.out.println("\nEid not found in the database.");
	                    showMenu();
	                    int choice = Integer.parseInt(sc.nextLine());
	                    switch (choice) {
	                        case 1:
	                            continue; // Retry
	                        case 2:
	                            l = false; // Exit delete operation
	                            break;
	                        default:
	                            System.out.println("Invalid option. Exiting.");
	                            l = false;
	                            break;
	                    }
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Exception " + e);
	            System.out.println(".........................|");
	        } finally {
	            // Close resources
	            if (rs != null) rs.close();
	            if (ps2 != null) ps2.close();
	            if (con2 != null) con2.close();
	        }
	    }

	    private void showMenu() {
	        System.out.println("\nMenu:");
	        System.out.println("1. Retry");
	        System.out.println("2. Exit");
	        System.out.print("Enter your choice: ");
	    }


	    public void Search() throws ClassNotFoundException, SQLException {
	        Connection con4 = null;
	        PreparedStatement ps4 = null;
	        ResultSet rs = null;

	        try {
	            boolean found = false;
	            System.out.print("Enter eid : ");
	            int i = Integer.parseInt(sc.nextLine());
	            Loding();

	            // Establish connection
	            con4 = Control.connect();

	            // Prepare and execute the query
	            String query = "SELECT * FROM t2 WHERE eid = ?";
	            ps4 = con4.prepareStatement(query);
	            ps4.setInt(1, i);
	            rs = ps4.executeQuery();

	            // Process the results
	            if (rs.next()) {
	                System.out.println("\nEid : " + rs.getInt("eid") + 
	                                   " , Name : " + rs.getString("name") + 
	                                   "\nAddress : " + rs.getString("address") + 
	                                   ", Salary : " + rs.getDouble("salary"));
	                System.out.println("...........................|");
	                found = true;
	            }

	            if (!found) {
	                System.out.println("\nEmployee Not Found  ~ ");
	                System.out.println(".........................|");
	            }

	        } catch (Exception e) {
	            System.out.println("Exception " + e);
	            System.out.println(".........................|");
	        } finally {
	            // Close resources
	            if (rs != null) rs.close();
	            if (ps4 != null) ps4.close();
	            if (con4 != null) con4.close();
	        }
	    }

	  
	public void Loding() {
	    System.out.print("Loading");

	    for (int i = 0; i < 5; i++) {
	        try {
	            System.out.print(".");
	            Thread.sleep(150);
	        } catch (InterruptedException e) {
	            System.out.println("Loading interrupted");
	            Thread.currentThread().interrupt(); // Preserve the interrupt status
	            break;
	        }
	    }

	    System.out.println();
	}


}
