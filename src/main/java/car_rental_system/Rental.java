
package car_rental_system;


public class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
    	if (car == null || customer == null) {
            throw new IllegalArgumentException("Car and Customer must not be null.");
        }

        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be greater than zero.");
        }
	this.car = car;
	this.customer = customer;
	this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
	return customer;
    }

    public int getDays() {
	return days;
    }
}
