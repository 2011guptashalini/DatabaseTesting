package DatabaseTesting.DatabaseTesting;

import java.sql.*;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit test for simple App.
 */
public class DataBaseTesting extends BaseTest {
	
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
	
	// Calling Stored Procedure
	@Test(priority=2)
	void test_callSP_SelectAllCustomers() {
		
		
		
	}
	
	
	
	
	
}
