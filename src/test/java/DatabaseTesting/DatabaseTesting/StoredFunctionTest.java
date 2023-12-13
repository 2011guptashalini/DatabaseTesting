package DatabaseTesting.DatabaseTesting;

import java.sql.*;

import org.testng.Assert;
import org.testng.annotations.Test;


public class StoredFunctionTest extends BaseTest {
	
	// Store function exist or not. Just need to change the name
	@Test(priority=1)
	void test_StoreFunctionExist_CustomerLevel() throws SQLException {
	String sfName = sf_exists("CustomerLevel");
	Assert.assertEquals(sfName, "CustomerLevel");//Change the name here
	}
	
	
	
	// Calling Stored function from select statement and comparing the results with query.
	@Test(priority=2)
	void test_callSF_CustomerLevelFromSelect() throws SQLException {
		
		String callSf = "select customerName, CustomerLevel(creditLimit) from customers";
		stmt1 = con.createStatement();
		rs1 = stmt1.executeQuery(callSf);		
		
		//stmt=con.createStatement();
		String query= "select customerName,\n"
				+ "CASE \n"
				+ "	\n"
				+ "	WHEN creditLimit > 50000 THEN\n"
				+ "		'PLATINUM'\n"
				+ "	WHEN creditLimit >=10000 AND creditLimit <=50000 THEN \n"
				+ "		'GOLD'\n"
				+ "	WHEN creditLimit < 10000 THEN\n"
				+ "		'SILVER'\n"
				+ "	\n"
				+ "END as customerLevel from customers ;";
		stmt2 = con.createStatement();
		stmt2 = con.createStatement();
	    rs2=stmt2.executeQuery(query); //Change the name 
	    
	    boolean match = compareResultsSets(rs1,rs2);
		//boolean match = test_callSP("SelectAllCustomers", "Customers");
		Assert.assertEquals(match, true);
	}
	
	@Test(priority=2)
	void test_callSF_CustomerLevelFromSP() throws SQLException {
		
		//String callSf = "CALL GetCustomerLevel(?,?)";
		cstmt = con.prepareCall("{CALL GetCustomerLevel(?,?)}");
		cstmt.setInt(1, 131);
		
		//Output variable
		cstmt.registerOutParameter(2, Types.VARCHAR);
		
		cstmt.executeQuery();
		
		String customerLevel = cstmt.getString(2);
		
		stmt = con.createStatement();		
		
		//stmt=con.createStatement();
		String query= "select customerName,\n"
				+ "CASE \n"
				+ "	\n"
				+ "	WHEN creditLimit > 50000 THEN\n"
				+ "		'PLATINUM'\n"
				+ "	WHEN creditLimit >=10000 AND creditLimit <=50000 THEN \n"
				+ "		'GOLD'\n"
				+ "	WHEN creditLimit < 10000 THEN\n"
				+ "		'SILVER'\n"
				+ "	\n"
				+ "END as customerLevel from customers WHERE customerNumber = 131 ;";

	    rs=stmt.executeQuery(query); //Change the name 
	    rs.next();
	    String exp_customerLevel =  rs.getString("customerLevel");
	    
	   Assert.assertEquals(customerLevel, exp_customerLevel);
	}
	
	
	
}
