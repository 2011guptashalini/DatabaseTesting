package DatabaseTesting.DatabaseTesting;

import java.sql.*;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class StoreProceduresTest extends BaseTest {
	
	// Store procedure exist or not. Just need to change the name
	@Test(priority=1)
	void test_StoreProcedureExist_SelectAllCustomers1() throws SQLException {
	stmt=con.createStatement();
    rs=stmt.executeQuery("SHOW PROCEDURE STATUS WHERE Name = 'SelectAllCustomers';");//Change the name
	rs.next();
	Assert.assertEquals(rs.getString("Name"), "SelectAllCustomers");//Change the name here
	}
	
	@Test(priority=1)
	void test_StoreProcedureExist_SelectAllCustomers() throws SQLException {
	String spName = sp_exists("SelectAllCustomers");
	Assert.assertEquals(spName, "SelectAllCustomers");//Change the name here
	}
	
	@Test(priority=1)
	void test_StoreProcedureExist_geCustomerShipping() throws SQLException {
		String spName = sp_exists("geCustomerShipping");
		Assert.assertEquals(spName, "geCustomerShipping");//Change the name here
	}
	
	@Test(priority=1)
	void test_StoreProcedureExist_get_order_by_cust() throws SQLException {
		String spName = sp_exists("get_order_by_cust");
		Assert.assertEquals(spName, "get_order_by_cust");//Change the name here
	}
	
	@Test(priority=1)
	void test_StoreProcedureExist_InsertSupplierProduct() throws SQLException {
		String spName = sp_exists("InsertSupplierProduct");
		Assert.assertEquals(spName, "InsertSupplierProduct");//Change the name here
		}
	
	@Test(priority=1)
	void test_StoreProcedureExist_SelectAllCustomerByCity() throws SQLException {
		String spName = sp_exists("SelectAllCustomerByCity");
		Assert.assertEquals(spName, "SelectAllCustomerByCity");//Change the name here
		
	}
	
	@Test(priority=1)
	void test_StoreProcedureExist_SelectAllCustomerByCityAndPin() throws SQLException {
		String spName = sp_exists("SelectAllCustomerByCityAndPin");
		Assert.assertEquals(spName, "SelectAllCustomerByCityAndPin");//Change the name here
		
		}
	
	// Calling Stored Procedure without any argument and return variables.
	@Test(priority=2)
	void test_callSP_SelectAllCustomers() throws SQLException {
		boolean match = test_callSP("SelectAllCustomers", "Customers");
		Assert.assertEquals(match, true);
	}
	
	
	@Test(priority=2)
	public void test_callSP_SelectAllCustomerByCity() throws SQLException {
		
		//String callSp = "{CALL SelectAllCustomerByCity(?)}";
		
		cstmt = con.prepareCall("CALL SelectAllCustomerByCity(?)");
		cstmt.setString(1, "Singapore");
		rs1 = cstmt.executeQuery();		
		
		stmt=con.createStatement();
		String query= "Select * from customers where city ='Singapore'";
	    rs2=stmt.executeQuery(query); //Change the name 
	    
	    boolean match = compareResultsSets(rs1,rs2);
	    Assert.assertEquals(match, true);
	    
	    
	}
	
	@Test(priority=2)
	public void test_callSP_SelectAllCustomerByCityAndPin() throws SQLException {
		
		//String callSp = "{CALL SelectAllCustomerByCity(?)}";
		
		cstmt = con.prepareCall("CALL SelectAllCustomerByCityAndPin(?,?)");
		cstmt.setString(1, "Singapore");
		cstmt.setString(2, "079903");
		rs1 = cstmt.executeQuery();		
		
		stmt=con.createStatement();
		String query= "Select * from customers where city ='Singapore' and postalCode = '079903'";
	    rs2=stmt.executeQuery(query); //Change the name 
	    
	    boolean match = compareResultsSets(rs1,rs2);
	    Assert.assertEquals(match, true);
	    
	    
	}
	
	@Test(priority=2)
	public void test_callSP_get_order_by_cust() throws SQLException {
		
		//String callSp = "{CALL SelectAllCustomerByCity(?)}";
		
		cstmt = con.prepareCall("CALL get_order_by_cust(?,?,?,?,?)");
		cstmt.setInt(1, 141);
		
		//Output variable
		cstmt.registerOutParameter(2, Types.INTEGER);
		cstmt.registerOutParameter(3, Types.INTEGER);
		cstmt.registerOutParameter(4, Types.INTEGER);
		cstmt.registerOutParameter(5, Types.INTEGER);
		
		cstmt.executeQuery();	
		
	    int shipped = cstmt.getInt(2);
	    int canceled = cstmt.getInt(3);
	    int resolved = cstmt.getInt(4);
	    int disputed = cstmt.getInt(5);
		
		stmt=con.createStatement();
		String query= "select \n"
				+ "(select count(*) as 'shipped' from orders where customerNumber = 141 and status = 'Shipped') as Shipped,\n"
				+ "(select count(*) as 'canceled' from orders where customerNumber = 141 and status = 'Canceled') as Canceled,\n"
				+ "(select count(*) as 'resolved' from orders where customerNumber = 141 and status = 'Resolved') as Resolved,\n"
				+ "(select count(*) as 'disputed' from orders where customerNumber = 141 and status = 'Disputed') as Disputed";
	    rs=stmt.executeQuery(query); //Change the name 
	    rs.next();
	    
	    int exp_shipped =  rs.getInt("shipped");
	    int exp_canceled = rs.getInt("canceled");
	    int exp_resolved = rs.getInt("resolved");
	    int exp_disputed = rs.getInt("disputed");
	 
	    
	    if(shipped==exp_shipped && canceled==exp_canceled && resolved == exp_resolved && disputed==exp_disputed) {
	    	Assert.assertTrue(true);
	    }
	    else
	    	Assert.assertTrue(false);
	    	

	    
	}
	
}
