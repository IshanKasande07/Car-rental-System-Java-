

package car_rental_system;
import static car_rental_system.CarRentalSystem.isCarRented;
import static car_rental_system.CarRentalSystem.returnCarInDatabase;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ReturnCar extends JFrame implements ActionListener {
    public JTextField carIdField;
    public JButton returnButton, cancelButton;

    public ReturnCar() {
        setTitle("Return a Car");
        setSize(400, 300);
        setLocation(500, 250);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        // Title Label
        JLabel titleLabel = new JLabel("Return a Car");
        titleLabel.setBounds(120, 20, 200, 40);
        titleLabel.setFont(new Font("Raleway", Font.BOLD, 24));
        add(titleLabel);

        // Car ID Label and Text Field
        JLabel carIdLabel = new JLabel("Car ID:");
        carIdLabel.setBounds(50, 80, 100, 30);
        carIdLabel.setFont(new Font("Raleway", Font.PLAIN, 18));
        add(carIdLabel);

        carIdField = new JTextField();
        carIdField.setBounds(150, 80, 180, 30);
        add(carIdField);

        // Return Button
        returnButton = new JButton("Return");
        returnButton.setBounds(50, 150, 120, 40);
        returnButton.setBackground(Color.blue);
        returnButton.setForeground(Color.white);
        returnButton.setFont(new Font("Raleway", Font.BOLD, 18));
        returnButton.addActionListener(this);
        add(returnButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(210, 150, 120, 40);
        cancelButton.setBackground(Color.red);
        cancelButton.setForeground(Color.white);
        cancelButton.setFont(new Font("Raleway", Font.BOLD, 18));
        cancelButton.addActionListener(this);
        add(cancelButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnButton) {
            returnCar();
        } else if (e.getSource() == cancelButton) {
            this.setVisible(false);
            new Testing().setVisible(true);
        }
    }

    private void returnCar() {
        String carIdText = carIdField.getText().trim();

        if (carIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Car ID is required.");
            return;
        }

        int carId;
        try {
            carId = Integer.parseInt(carIdText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for Car ID.");
            return;
        }

        // Check if the car is rented
        if (isCarRented(carId)) {
            // Perform the return operation
            returnCarInDatabase(carId);
            JOptionPane.showMessageDialog(this, "Car returned successfully.");
            this.setVisible(false);// Close the frame
            new Testing().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid car ID or car is not currently rented.");
        }
    }
    public static void main(String[] args) {
        new ReturnCar();
    }
}
