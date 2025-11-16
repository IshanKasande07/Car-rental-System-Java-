package car_rental_system;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class Testing extends JFrame implements ActionListener{
    
    JButton cars ,Addcars,Rentcars,Returncars,historyButton,Exit;
    
    public Testing() {
        setSize(1500, 700);
        setLayout(null);
        getContentPane().setBackground(Color.white);
        setTitle("Car Rental System");
        setLocation(300, 100);
        
        ImageIcon i1 = new ImageIcon(getClass().getResource("/CarRentalSystemImage1.png")); // Import the image
        Image i2 = i1.getImage().getScaledInstance(1500, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3); // Create a JLabel to add the image
        label.setBounds(00, 00, 1500, 700);
        
        add(label); // Add the label to the frame before setting it visible
        
        JLabel Title = new JLabel("WELCOME TO CAR RENTAL SYSTEM");
        Title.setBounds(400, 20, 800, 50);
        Title.setFont(new Font("Raleway",Font.BOLD,34));
        //Title.setBackground(Color.red);
        add(Title);
        
        cars = new JButton("Cars");
        cars.setBounds(100, 150, 150, 40);
        cars.setBackground(Color.blue);
        cars.setForeground(Color.white);
        cars.setFont(new Font("Raleway",Font.BOLD,18));
        cars.addActionListener(this);
        add(cars);
        
         Addcars = new JButton("Add Car");
        Addcars.setBounds(325, 150, 150, 40);
        Addcars.setBackground(Color.blue);
        Addcars.setForeground(Color.white);
        Addcars.setFont(new Font("Raleway",Font.BOLD,18));
        Addcars.addActionListener(this);
        add(Addcars);
        
         Rentcars = new JButton("Rent Car");
        Rentcars.setBounds(550, 150, 150, 40);
        Rentcars.setBackground(Color.blue);
        Rentcars.setForeground(Color.white);
        Rentcars.setFont(new Font("Raleway",Font.BOLD,18));
        Rentcars.addActionListener(this);
        add(Rentcars);
        
         Returncars = new JButton("Return Car");
        Returncars.setBounds(775, 150, 150, 40);
        Returncars.setBackground(Color.blue);
        Returncars.setForeground(Color.white);
        Returncars.setFont(new Font("Raleway",Font.BOLD,18));
        Returncars.addActionListener(this);
        add(Returncars);
        
        historyButton = new JButton("History");
        historyButton.setBounds(1000, 150, 150, 40);
        historyButton.setBackground(Color.blue);
        historyButton.setForeground(Color.white);
        historyButton.setFont(new Font("Raleway",Font.BOLD,18));
        historyButton.addActionListener(this);
        add(historyButton);
        
         Exit = new JButton("Exit");
        Exit.setBounds(1250, 150, 150, 40);
        Exit.setBackground(Color.red);
        Exit.setForeground(Color.white);
        Exit.setFont(new Font("Raleway",Font.BOLD,18));
        Exit.addActionListener(this);
        add(Exit);
        
        setVisible(true); // Now set the frame to visible
    }
    
    public static void main(String args[]) {
        new Testing();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==cars){
            this.setVisible(false);
            new ShowCars().setVisible(true);
        }
        else if(e.getSource()==Addcars){
            this.setVisible(false);
            new AddCar().setVisible(true);
        }
        else if(e.getSource()==Rentcars){
            this.setVisible(false);
            new RentCar().setVisible(true);
        }
        else if(e.getSource()==Returncars){
            this.setVisible(false);
            new ReturnCar().setVisible(true);
        }
        else if(e.getSource()==historyButton){
            this.setVisible(false);
            new ShowHistory().setVisible(true);
        }
        else if(e.getSource()==Exit){
            this.setVisible(false);
            this.dispose();
        }
    }
}
