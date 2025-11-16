package car_rental_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class ShowCars extends JFrame implements ActionListener{
    public JTable carsTable;
    private JButton backButton;
    ShowCars s2;
    public ShowCars() {
        setTitle("Available Cars");
        setSize(600, 400);
        setLocation(400, 200);
        setLayout(new BorderLayout());

        // Create a table to display cars
        carsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(carsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load cars from the database
        loadCars();

        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Raleway", Font.BOLD, 18));
        backButton.setBackground(Color.blue);
        backButton.setForeground(Color.white);
        backButton.addActionListener(this); 
            
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadCars() {
        // Set up column names
        String[] columnNames = {"Car ID", "Brand", "Model", "Price Per Day", "Available"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String fetchCarsQuery = "SELECT * FROM Car";
            PreparedStatement statement = connection.prepareStatement(fetchCarsQuery);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Retrieve car details from result set
                int carId = resultSet.getInt("CarId");
                String brand = resultSet.getString("brand");
                String modelStr = resultSet.getString("model");
                double pricePerDay = resultSet.getDouble("PricePerDay");
                boolean isAvailable = resultSet.getBoolean("IsAvailabel");

                // Add a row to the table model
                Vector<Object> row = new Vector<>();
                row.add(carId);
                row.add(brand);
                row.add(modelStr);
                row.add(pricePerDay);
                row.add(isAvailable ? "Yes" : "No");

                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading cars: " + e.getMessage());
        }

        // Set the model to the table
        carsTable.setModel(model);
    }
            @Override
            public void actionPerformed(ActionEvent e) {
                this.setVisible(false);// Close the frame
                new Testing().setVisible(true);
            }

    public static void main(String[] args) {
        ShowCars s1 = new ShowCars();
    }
}
