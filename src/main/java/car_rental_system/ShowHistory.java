package car_rental_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Vector;

public class ShowHistory extends JFrame implements ActionListener{

    public JTable historyTable;

    private JButton back;
    public ShowHistory() {
        setTitle("Rental History");
        setSize(800, 500);
        setLocation(400, 200);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.white);
        
        back = new JButton("Back");
        back.setBounds(325, 400, 150, 30);
        back.setBackground(Color.blue);
        back.setForeground(Color.white);
        back.setFont(new Font("Raleway",Font.BOLD,16));
        back.addActionListener(this);
        add(back);

        // Table to display rental history
        historyTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load rental history data
        loadRentalHistory();

        setVisible(true);
    }

    private void loadRentalHistory() {
        String[] columnNames = { "Car ID", "Customer ID", "Rented Date","Rental days", "Days Since Rented" ,"Message"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String historyQuery = "SELECT CarId, CustId,RentalDays, RentedDate FROM Rental";
            PreparedStatement statement = connection.prepareStatement(historyQuery);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int carId = resultSet.getInt("CarId");
                int customerId = resultSet.getInt("CustId");
                int rentaldays = resultSet.getInt("RentalDays");
                Date rentedDate = resultSet.getDate("RentedDate");

                // Calculate days since rented
                LocalDate rentDate = rentedDate.toLocalDate();
                long daysSinceRented = ChronoUnit.DAYS.between(rentDate, LocalDate.now());

                // Add data to table row
                Vector<Object> row = new Vector<>();
                row.add(carId);
                row.add(customerId);
                row.add(rentDate);
                row.add(rentaldays);
                row.add(daysSinceRented);
                
                if(daysSinceRented>rentaldays){
                    row.add("penulty!!!");
                }
                else if(daysSinceRented==rentaldays){
                    row.add("last day to return a car otherwise you have to pay penulty!!!");
                }
                else if(daysSinceRented<rentaldays){
                    row.add("Enjoy your journey!!!");
                }
                

                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading rental history: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for detailed error information
        }

        // Set model for the table
        historyTable.setModel(model);
    }

    public static void main(String[] args) {
        new ShowHistory();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Testing().setVisible(true);
    }
}
