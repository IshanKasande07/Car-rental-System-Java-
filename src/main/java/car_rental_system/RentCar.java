
package car_rental_system;
import static car_rental_system.CarRentalSystem.addCustomerToDatabase;
import static car_rental_system.CarRentalSystem.isCarAvailable;
import static car_rental_system.CarRentalSystem.rentCarInDatabase;
import static car_rental_system.Car.calculateTotalPrice;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class RentCar extends JFrame implements ActionListener {
    private JTextField customerNameField, carIdField, rentalDaysField;
    private JButton rentButton, cancelButton;
    private JTable carTable;
    private DefaultTableModel carTableModel;

    public RentCar() {
        setTitle("Rent a Car");
        setSize(900, 600);
        setLocation(300, 100);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        // Title Label
        JLabel titleLabel = new JLabel("Rent a Car");
        titleLabel.setBounds(350, 20, 200, 40);
        titleLabel.setFont(new Font("Raleway", Font.BOLD, 28));
        add(titleLabel);

        // Customer Name Label and Text Field
        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setBounds(50, 80, 150, 30);
        nameLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        add(nameLabel);

        customerNameField = new JTextField();
        customerNameField.setBounds(200, 80, 200, 30);
        add(customerNameField);

        // Car ID Label and Text Field
        JLabel carIdLabel = new JLabel("Car ID:");
        carIdLabel.setBounds(50, 120, 150, 30);
        carIdLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        add(carIdLabel);

        carIdField = new JTextField();
        carIdField.setBounds(200, 120, 200, 30);
        add(carIdField);

        // Rental Days Label and Text Field
        JLabel rentalDaysLabel = new JLabel("Rental Days:");
        rentalDaysLabel.setBounds(50, 160, 150, 30);
        rentalDaysLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        add(rentalDaysLabel);

        rentalDaysField = new JTextField();
        rentalDaysField.setBounds(200, 160, 200, 30);
        add(rentalDaysField);

        // Rent Button
        rentButton = new JButton("Rent");
        rentButton.setBounds(50, 220, 150, 40);
        rentButton.setBackground(Color.blue);
        rentButton.setForeground(Color.white);
        rentButton.setFont(new Font("Raleway", Font.BOLD, 18));
        rentButton.addActionListener(this);
        add(rentButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(250, 220, 150, 40);
        cancelButton.setBackground(Color.red);
        cancelButton.setForeground(Color.white);
        cancelButton.setFont(new Font("Raleway", Font.BOLD, 18));
        cancelButton.addActionListener(this);
        add(cancelButton);

        // Table to display available cars
        carTableModel = new DefaultTableModel(new String[]{"Car ID", "Brand", "Model", "Price Per Day"}, 0);
        carTable = new JTable(carTableModel);
        JScrollPane carScrollPane = new JScrollPane(carTable);
        carScrollPane.setBounds(450, 80, 400, 400);
        add(carScrollPane);

        // Load available cars into the table
        loadAvailableCars();

        setVisible(true);
    }

    private void loadAvailableCars() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String fetchCarsQuery = "SELECT * FROM Car WHERE IsAvailabel = TRUE";
            PreparedStatement statement = connection.prepareStatement(fetchCarsQuery);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int carId = resultSet.getInt("CarId");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                double pricePerDay = resultSet.getDouble("PricePerDay");
                carTableModel.addRow(new Object[]{carId, brand, model, pricePerDay});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading available cars: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rentButton) {
            rentCar();
        } else if (e.getSource() == cancelButton) {
            this.setVisible(false);
            new Testing();
        }
    }

    private void rentCar() {
        String customerName = customerNameField.getText().trim();
        String carIdText = carIdField.getText().trim();
        String rentalDaysText = rentalDaysField.getText().trim();

        if (customerName.isEmpty() || carIdText.isEmpty() || rentalDaysText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        int carId, rentalDays;
        try {
            carId = Integer.parseInt(carIdText);
            rentalDays = Integer.parseInt(rentalDaysText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Car ID and Rental Days.");
            return;
        }

        // Add customer to the database
        int customerId = addCustomerToDatabase(customerName);

        if (customerId == -1) {
            JOptionPane.showMessageDialog(this, "Failed to add customer. Please try again.");
            return;
        }
        // Check if the selected car is available
        if (isCarAvailable(carId)) {
            double totalPrice = calculateTotalPrice(carId, rentalDays);
            int confirm = JOptionPane.showConfirmDialog(this,
                    String.format("Rental Information:\nCustomer ID: %d\nCustomer Name: %s\nCar ID: %d\nRental Days: %d\nTotal Price: $%.2f\n\nConfirm rental?",
                            customerId, customerName, carId, rentalDays, totalPrice),
                    "Confirm Rental", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                rentCarInDatabase(carId, customerId, rentalDays);
                JOptionPane.showMessageDialog(this, "Car rented successfully.");
                this.setVisible(false);
                new Testing().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Rental canceled.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid car selection or car not available for rent.");
        }
    }
    public static void main(String[] args) {
        new RentCar();
    }
}