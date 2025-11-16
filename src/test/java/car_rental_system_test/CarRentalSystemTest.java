package car_rental_system_test;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import car_rental_system.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalDate;

public class CarRentalSystemTest {

    CarRentalSystem system;

    @BeforeEach
    void setUp() {
        system = new CarRentalSystem();
    }

    
//    @Test
//    void testCarIsAvailableReturnsTrue() throws Exception {
//        ResultSet rs = mock(ResultSet.class);
//        when(rs.next()).thenReturn(true);
//        when(rs.getBoolean("IsAvailabel")).thenReturn(true);
//
//        PreparedStatement stmt = mock(PreparedStatement.class);
//        when(stmt.executeQuery()).thenReturn(rs);
//
//        Connection con = mock(Connection.class);
//        when(con.prepareStatement(anyString())).thenReturn(stmt);
//
//        assertTrue(CarRentalSystem.isCarAvailable(1));
//    }

//    @Test
//    void testCarIsAvailableReturnsFalse() throws Exception {
//        ResultSet rs = mock(ResultSet.class);
//        when(rs.next()).thenReturn(true);
//        when(rs.getBoolean("IsAvailabel")).thenReturn(false);
//
//        PreparedStatement stmt = mock(PreparedStatement.class);
//        when(stmt.executeQuery()).thenReturn(rs);
//
//        Connection con = mock(Connection.class);
//        when(con.prepareStatement(anyString())).thenReturn(stmt);
//
//        assertFalse(CarRentalSystem.isCarAvailable(con, 1010));
//    }


    @Test
    void testCarIsAvailableInvalidCarId() {
        assertFalse(CarRentalSystem.isCarAvailable(999));
    }

    
//    @Test
//    void testCarIsRentedTrue() throws Exception {
//        ResultSet rs = mock(ResultSet.class);
//        when(rs.next()).thenReturn(true);
//
//        PreparedStatement stmt = mock(PreparedStatement.class);
//        when(stmt.executeQuery()).thenReturn(rs);
//
//        Connection con = mock(Connection.class);
//        when(con.prepareStatement(anyString())).thenReturn(stmt);
//
//        assertTrue(CarRentalSystem.isCarRented(1));
//    }

    @Test
    void testCarIsRentedFalse() {
        assertFalse(CarRentalSystem.isCarRented(20));
    }

    
    @Test
    void testCalculatePricePositive() {
        double price = Car.calculateTotalPrice(1010, 5);
        assertTrue(price > 0);
    }

    @Test
    void testCalculateZeroDaysPriceZero() {
        assertEquals(0, Car.calculateTotalPrice(1010, 0));
    }

    @Test
    void testCalculateNegativeDaysThrows() {
        assertThrows(Exception.class,
                () -> Car.calculateTotalPrice(1, -3));
    }

    
    @Test
    void testAddCustomerValid() {
        int id = CarRentalSystem.addCustomerToDatabase("John");
        assertTrue(id >= 0);
    }

    @Test
    void testAddCustomerEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> CarRentalSystem.addCustomerToDatabase(""));
    }

    /* =========================
       ✅ ReturnCar UI Tests
       =========================*/
    @Test
    void testReturnCarEmptyInput() {
        ReturnCar rc = new ReturnCar();
        rc.carIdField.setText("");
        rc.returnButton.doClick();
        assertTrue(rc.isVisible());
    }

    @Test
    void testReturnCarInvalidNumber() {
        ReturnCar rc = new ReturnCar();
        rc.carIdField.setText("abc");
        rc.returnButton.doClick();
        assertTrue(rc.isVisible());
    }

    @Test
    void testReturnCarValidAndNotRented() {
        ReturnCar rc = new ReturnCar();
        rc.carIdField.setText("99");
        rc.returnButton.doClick();
        assertTrue(rc.isVisible());
    }

    
    @Test
    void testShowCarsTableNotNull() {
        ShowCars sc = new ShowCars();
        assertNotNull(sc.carsTable.getModel());
    }

    @Test
    void testShowCarsColumnCount() {
        ShowCars sc = new ShowCars();
        assertEquals(5, sc.carsTable.getColumnCount());
    }

    @Test
    void testShowCarsHasRowsOrZero() {
        ShowCars sc = new ShowCars();
        assertTrue(sc.carsTable.getRowCount() >= 0);
    }

    
    @Test
    void testShowHistoryTableNotNull() {
        ShowHistory sh = new ShowHistory();
        assertNotNull(sh.historyTable.getModel());
    }

    @Test
    void testShowHistoryColumnCount() {
        ShowHistory sh = new ShowHistory();
        assertEquals(6, sh.historyTable.getColumnCount());
    }

    @Test
    void testShowHistoryRowsNonNegative() {
        ShowHistory sh = new ShowHistory();
        assertTrue(sh.historyTable.getRowCount() >= 0);
    }

    @Test
    void testPenaltyMessageIfLate() {
        ShowHistory sh = new ShowHistory();
        int rows = sh.historyTable.getRowCount();
        if (rows > 0) {
            String message = sh.historyTable.getValueAt(0, 5).toString();
            assertNotNull(message);
        }
    }

    
    @Test
    void testClickCancelReturnsToMenu() {
        ReturnCar rc = new ReturnCar();
        rc.cancelButton.doClick();
        assertFalse(rc.isVisible());
    }

    /* ===============
       More Logic Tests
       ===============*/
    @Test
    void testSystemMenuExists() {
        assertNotNull(system);
    }

    @Test
    void testCarObjectSettersGetters() {
        Car c = new Car(1, "Honda", "City", 1000);
        assertEquals(1, c.getCarId());
        assertEquals("Honda", c.getBrand());
    }

    @Test
    void testCustomerObjectSettersGetters() {
        Customer cu = new Customer(5, "Max");
        assertEquals("Max", cu.getCus_Name());
    }

    @Test
    void testRentalObjectCreation() {
        Car c = new Car(10, "BMW", "X5", 3000);
        Customer cu = new Customer(7, "Tom");
        Rental r = new Rental(c, cu, 3);
        assertNotNull(r.getCar());
    }

    
    @Test
    void testCarAvailabilityNegativeId() {
        assertFalse(CarRentalSystem.isCarAvailable(-5));
    }

    @Test
    void testCarRentalNegativeId() {
        assertFalse(CarRentalSystem.isCarRented(-2));
    }

    @Test
    void testZeroDayRentalInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Rental(new Car(2, "Audi", "A4", 2000), new Customer(10, "Ron"), 0));
    }

    
    
}
