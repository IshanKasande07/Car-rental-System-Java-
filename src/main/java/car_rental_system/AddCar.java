
package car_rental_system;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class AddCar extends JFrame implements ActionListener{
    
     JButton Add  ;
     JTextField TCarId ,Tbrand,TModel,TPricePerday;
    
    public AddCar(){
        setSize(800, 800);
        setLayout(null);
        getContentPane().setBackground(Color.white);
        setTitle("Add Car on Car Rental System");
        setLocation(300, 100);
        
        
        JLabel add = new JLabel("*****Adding A Car On Car Rental System*****");
        add.setBounds(50, 20, 800, 50);
        add.setFont(new Font("Raleway",Font.BOLD,34));
        //Title.setBackground(Color.red);
        add(add);
        
        JLabel detail = new JLabel("*****Fill Car Details*****");
        detail.setBounds(200, 85, 800, 50);
        detail.setFont(new Font("Raleway",Font.BOLD,28));
        //Title.setBackground(Color.red);
        add(detail);
        
        JLabel Lcarid = new JLabel("Car Id :");
        Lcarid.setBounds(100, 150, 200, 30);
        Lcarid.setFont(new Font("Raleway",Font.BOLD,22));
        //Title.setBackground(Color.red);
        add(Lcarid);
        
         TCarId = new JTextField();
        TCarId.setBounds(300, 150, 400, 30);
        TCarId.setBackground(Color.white);
        TCarId.setFont(new Font("Raleway",Font.BOLD,18));
        add(TCarId);
        
        JLabel Lbrand = new JLabel("Car Brand :");
        Lbrand.setBounds(100, 200, 200, 30);
        Lbrand.setFont(new Font("Raleway",Font.BOLD,22));
        //Title.setBackground(Color.red);
        add(Lbrand);
        
         Tbrand = new JTextField();
        Tbrand.setBounds(300, 200, 400, 30);
        Tbrand.setBackground(Color.white);
        Tbrand.setFont(new Font("Raleway",Font.BOLD,18));
        add(Tbrand);
        
        JLabel LModel = new JLabel("Car Model :");
        LModel.setBounds(100, 250, 200, 30);
        LModel.setFont(new Font("Raleway",Font.BOLD,22));
        //Title.setBackground(Color.red);
        add(LModel);
        
         TModel = new JTextField();
        TModel.setBounds(300, 250, 400, 30);
        TModel.setBackground(Color.white);
        TModel.setFont(new Font("Raleway",Font.BOLD,18));
        add(TModel);
        
        JLabel LPricePerday = new JLabel("Price per Day :");
        LPricePerday.setBounds(100, 300, 200, 30);
        LPricePerday.setFont(new Font("Raleway",Font.BOLD,22));
        //Title.setBackground(Color.red);
        add(LPricePerday);
        
         TPricePerday = new JTextField();
        TPricePerday.setBounds(300, 300, 400, 30);
        TPricePerday.setBackground(Color.white);
        TPricePerday.setFont(new Font("Raleway",Font.BOLD,18));
        add(TPricePerday);
        
         Add  = new JButton("ADD");
        Add.setBounds(400, 350, 100, 40);
        Add.setBackground(Color.blue);
        Add.setForeground(Color.white);
        Add.setFont(new Font("Raleway",Font.BOLD,18));
        Add.addActionListener(this);
        add(Add);
        
        
        
        
        setVisible(true);
    }
    
    public static void main(String[] args){
        new AddCar();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        try {
        // Get input values
        String carIdText = TCarId.getText();
        String brandText = Tbrand.getText();
        String modelText = TModel.getText();
        String pricePerDayText = TPricePerday.getText();

        // Validation for empty fields
        if (carIdText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Car ID is required!");
            return;
        }
        if (brandText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Brand Name is required!");
            return;
        }
        if (modelText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Model Name is required!");
            return;
        }
        if (pricePerDayText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Price per Day is required!");
            return;
        }

        // Validate if Car ID is a valid integer
        int carId;
        try {
            carId = Integer.parseInt(carIdText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Car ID! Please enter a valid integer.");
            return;
        }

        // Validate if Price per Day is a valid double
        double pricePerDay;
        try {
            pricePerDay = Double.parseDouble(pricePerDayText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Price! Please enter a valid number.");
            return;
        }

        // All validations passed, proceed with database insertion
        boolean isAvailable = true;
        DatabaseConnection cc = new DatabaseConnection();
         String query = "INSERT INTO Car (CarId, Brand, Model, PricePerDay, IsAvailabel) VALUES (" 
                            + carId + ", '" + brandText + "', '" + modelText + "', " 
                            + pricePerDay + ", " + isAvailable + ")";
        cc.s.executeUpdate(query);

        // Success message and navigation
        JOptionPane.showMessageDialog(null, "Car added successfully!");
        this.setVisible(false);
        new Testing().setVisible(true);

    } catch (Exception ex) {
        System.out.println(ex);
        JOptionPane.showMessageDialog(null, "An error occurred while adding the car. Please try again.");
    }
    }
    
    
    }

