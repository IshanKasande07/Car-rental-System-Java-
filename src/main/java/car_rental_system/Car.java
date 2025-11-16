
package car_rental_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Car {
    
    private int CarId;
	private String brand;
	private String model;
	private boolean isAvailable;
	private double basePricePerDay;
	
	//parameterized constructor of Car class 
	public Car(int CarId,String brand,String model,double basePricePerDay) {
		
		this.basePricePerDay=basePricePerDay;
		this.brand=brand;
		this.CarId=CarId;
		this.isAvailable=true;
		this.model=model;
	}
	
	//only get() methods because we are setting the values using constructor
	public int getCarId() {
		return CarId;
	}
	public String getBrand() {
		return brand;
	}
	public String getModel() {
		return model;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	
	//calculating total price
	public static double calculateTotalPrice(int carId, int rentalDays) throws IllegalArgumentException {
	    if (rentalDays < 0) {
	        throw new IllegalArgumentException("Rental days must be positive.");
	    }

	    double dailyRate = 0.0;

	    try (Connection connection = DatabaseConnection.getConnection()) {
	        String query = "SELECT PricePerDay FROM Car WHERE CarId = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setInt(1, carId);
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            dailyRate = resultSet.getDouble("PricePerDay");
	        } else {
	            throw new IllegalArgumentException("Car with ID " + carId + " not found.");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Database error while calculating total price.", e);
	    }

	    return dailyRate * rentalDays;
	}

        //getting basePrice Per day
        
        public double getBasePricePerDay(){
            return basePricePerDay;
        }
	
//	// if car is gone to rent then set Availability of that car is to false
//	public void rent() {
//		isAvailable=false;
//	}
//	
//	
//	// if car is returned then set Availability of that car is to true
//	public void returnCar() {
//		isAvailable=true;
//	}
	
}
