package DatabaseTesting.DatabaseTesting;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class BaseTest {
	
	public Connection con = null;
	public  Statement stmt = null;
	public  ResultSet rs = null;
	public CallableStatement cstmt = null;
	
	 
		@BeforeClass
		Connection setup() throws SQLException
		{
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels","root","freelancer");
			return con;
		}
		
		@AfterClass
		void tearDown() throws SQLException
		{
			con.close();
		}
		
		
	// utility methods
		
		// For checking SP exists or not
		public String sp_exists(String spName) throws SQLException
		{
		stmt=con.createStatement();
		String query= "SHOW PROCEDURE STATUS WHERE Name ='" + spName + "'";
	    rs=stmt.executeQuery(query); //Change the name
		rs.next();
		String spNameFromQuery = rs.getString("Name");
		return spNameFromQuery;
		}
		
		// Comparing two results sets
		public boolean compareResultsSets(ResultSet rs1, ResultSet rs2) throws SQLException
		{
			while(rs1.next()){
				rs2.next();
				int count = rs1.getMetaData().getColumnCount();
				for (int i=1; i<=count; i++) {
					if(!StringUtils.equals(rs1.getString(i), rs2.getString(i)))
					{
						return false;
					}
				}
				
		 }
			return true;

			
			
		}
		
		
		
}