
package car_rental_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import static car_rental_system.Car.calculateTotalPrice;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
public class CarRentalSystem {
    
    
	
	public void addCar(Car car) {
		try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "INSERT INTO Car (CarId, brand, model, PricePerDay, IsAvailabel) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, car.getCarId());
                    statement.setString(2, car.getBrand());
                    statement.setString(3, car.getModel());
                    statement.setDouble(4, car.getBasePricePerDay());
                    statement.setBoolean(5, true); // New cars are available by default
                    statement.executeUpdate();
                    System.out.println("Car added to the system.");
                } catch (Exception e) {
                    System.out.println(e);
                }
	}
	
	public void addCustomer(Customer customer) {
            
		try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO Customer (CustName,CustId) VALUES (?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, customer.getCus_Name());
                statement.setInt(1, customer.getCusId());
                statement.executeUpdate();
                System.out.println("Customer added to the system.");
            } catch (Exception e) {
                System.out.println(e);
            }
	}
	
    public static void returnCarInDatabase(int carId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // 1. Update the car's availability in the Car table
            String updateCarQuery = "UPDATE Car SET IsAvailabel = TRUE WHERE CarId = ?";
            try (PreparedStatement updateCarStmt = connection.prepareStatement(updateCarQuery)) {
                updateCarStmt.setInt(1, carId);
                updateCarStmt.executeUpdate();
            }

            // 2. Delete the rental record from the Rental table
            String deleteRentalQuery = "DELETE FROM Rental WHERE CarId = ?";
            try (PreparedStatement deleteRentalStmt = connection.prepareStatement(deleteRentalQuery)) {
                deleteRentalStmt.setInt(1, carId);
                deleteRentalStmt.executeUpdate();
            }

            System.out.println("Car returned successfully and rental information updated in the database.");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed to return the car. Please try again.");
        }
    }
	
	public static void rentCarInDatabase(int carId, int customerId, int rentalDays) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                // 1. Update the car's availability in the Car table
                String updateCarQuery = "UPDATE Car SET IsAvailabel = FALSE WHERE CarId = ?";
                try (PreparedStatement updateCarStmt = connection.prepareStatement(updateCarQuery)) {
                    updateCarStmt.setInt(1, carId);
                    updateCarStmt.executeUpdate();
                }

                // 2. Insert the rental information into the Rental table
                String insertRentalQuery = "INSERT INTO Rental (CarId, CustId, RentalDays, RentedDate) VALUES (?, ?, ?, CURDATE())";
                try (PreparedStatement insertRentalStmt = connection.prepareStatement(insertRentalQuery)) {
                    insertRentalStmt.setInt(1, carId);
                    insertRentalStmt.setInt(2, customerId);
                    insertRentalStmt.setInt(3, rentalDays);
                    
                    insertRentalStmt.executeUpdate();
                }

                System.out.println("Car rented successfully and rental information saved in the database.");
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Failed to rent the car. Please try again.");
            }
        }
        
        public void menu() {
            boolean flag = true;
            Scanner sc = new Scanner(System.in);

            while (flag) {
                System.out.println("===== Car Rental System =====");
                System.out.println("1. Rent a Car");
                System.out.println("2. Return a Car");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        rentCarMenu(sc);
                        break;
                    case 2:
                        returnCarMenu(sc);
                        break;
                    case 3:
                        flag = false;
                        System.out.println("Exiting the system.");
                        break;
                    default:
                        System.out.println("Please enter a correct choice.");
                        break;
                }
            }

            sc.close();
}
	
	
	private void rentCarMenu(Scanner sc) {
            System.out.println("===== Rent a Car =====");
            System.out.print("Enter your name: ");
            String customerName = sc.nextLine();

            // Display available cars from the database
            System.out.println("\nAvailable Car List:\n");
            try (Connection connection = DatabaseConnection.getConnection()) {
                String fetchCarsQuery = "SELECT * FROM Car WHERE IsAvailabel = TRUE";
                PreparedStatement statement = connection.prepareStatement(fetchCarsQuery);
                ResultSet resultSet = statement.executeQuery();

                boolean carsAvailable = false;
                while (resultSet.next()) {
                    carsAvailable = true;
                    int carId = resultSet.getInt("CarId");
                    String brand = resultSet.getString("brand");
                    String model = resultSet.getString("model");
                    double PricePerDay = resultSet.getDouble("PricePerDay");
                    System.out.printf("Car ID: %d - %s %s (Rate: $%.2f/day)\n", carId, brand, model, PricePerDay);
                }

                if (!carsAvailable) {
                    System.out.println("No cars are available for rent.");
                    return;
                }
            } catch (Exception e) {
                System.out.println(e);
                return;
            }

            System.out.print("\nEnter the Car ID you want to rent: ");
            int carId = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            System.out.print("Enter the number of days for rental: ");
            int rentalDays = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            // Add customer to the database
            int customerId = addCustomerToDatabase(customerName);

            if (customerId == -1) {
                System.out.println("Failed to add customer. Please try again.");
                return;
            }

            // Check if the selected car is available
            if (isCarAvailable(carId)) {
                double totalPrice = calculateTotalPrice(carId, rentalDays);
                System.out.printf("\n== Rental Information ==\n");
                System.out.printf("Customer ID: %d\nCustomer Name: %s\nCar ID: %d\nRental Days: %d\nTotal Price: $%.2f\n",
                        customerId, customerName, carId, rentalDays, totalPrice);

                System.out.print("\nConfirm rental (Y/N): ");
                String confirm = sc.nextLine();

                if (confirm.equalsIgnoreCase("Y")) {
                    rentCarInDatabase(carId, customerId, rentalDays);
                    System.out.println("\nCar rented successfully.");
                } else {
                    System.out.println("\nRental canceled.");
                }
            } else {
                System.out.println("\nInvalid car selection or car not available for rent.");
            }
        }
        
	public static int addCustomerToDatabase(String customerName) {
	    if (customerName == null || customerName.trim().isEmpty()) {
	        throw new IllegalArgumentException("Customer name must not be null or empty.");
	    }

	    try (Connection connection = DatabaseConnection.getConnection()) {
	        String query = "INSERT INTO Customer (CustName) VALUES (?)";
	        PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
	        statement.setString(1, customerName.trim());
	        statement.executeUpdate();

	        ResultSet generatedKeys = statement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            return generatedKeys.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Database error while adding customer.", e);
	    }

	    return -1;
	}

        
        private void returnCarMenu(Scanner sc) {
            System.out.println("\n== Return a Car ==\n");
            System.out.print("Enter the car ID you want to return: ");
            int carId = sc.nextInt();
            sc.nextLine(); // Consume the newline character

            if (isCarRented(carId)) {
                returnCarInDatabase(carId);
                System.out.println("Car returned successfully.");
            } else {
                System.out.println("Invalid car ID or car is not currently rented.");
            }
        }
        
        
        public static boolean isCarAvailable(int carId) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                return isCarAvailable(connection, carId);
            } catch (Exception e) {
                System.out.println(e);
            }
            return false;
        }

        // Overloaded helper for testing (package-private so tests can use it)
        public static boolean isCarAvailable(Connection connection, int carId) throws SQLException {
            String query = "SELECT IsAvailabel FROM Car WHERE CarId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, carId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBoolean("IsAvailabel");
            }
            return false;
        }

        
        
        public static boolean isCarRented(int carId) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM Rental WHERE CarId = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, carId);
                ResultSet resultSet = statement.executeQuery();

                return resultSet.next(); // Returns true if the car is found in the Rental table
            } catch (Exception e) {
                System.out.println(e);
            }
            return false;
        }


	public static void main(String[] args) {
		
		
            CarRentalSystem rentalSystem = new CarRentalSystem();
            
            rentalSystem.menu();
       
        
	}

}


