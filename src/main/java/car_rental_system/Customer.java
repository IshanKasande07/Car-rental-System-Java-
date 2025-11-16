
package car_rental_system;


public class Customer {
    private int CusId;
	private String Cus_Name;
	
	public Customer(int CusId,String Cus_Name) {
		
		this.Cus_Name=Cus_Name;
		this.CusId=CusId;
	}

	public int getCusId() {
		return CusId;
	}

	public String getCus_Name() {
		return Cus_Name;
	}
	
}
