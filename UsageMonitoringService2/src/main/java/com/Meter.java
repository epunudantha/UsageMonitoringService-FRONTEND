package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

public class Meter 
{ //A common method to connect to the DB
private Connection connect() 
 { 
 Connection con = null; 
 try
 { 
 Class.forName("com.mysql.jdbc.Driver"); 
 
 //Provide the correct details: DBServer/DBName, username, password 
 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/meterreadings", "root", ""); 
 } 
 catch (Exception e) 
 {e.printStackTrace();} 
 return con; 
 } 


//INSERT
public String insertMeter(String code, String name, String suprice, String unitsamount) 
{ 
Connection conn = connect();
Double totalprice = Double.parseDouble(suprice)*Double.parseDouble(unitsamount);	
String output = "";

try
{ 
if (conn == null) 
 {
  return "Error while connecting to the database for inserting."; 
 }

//create a prepared statement
String query = " insert into meterreadings (`meterID`,`meterCode`,`houseownerName`,`singleUnitPrice`, `unitsAmount`,`totalPrice`)"
+ " values (?, ?, ?, ?, ?, ?)"; 
PreparedStatement preparedStmt = conn.prepareStatement(query); 
//binding values
preparedStmt.setInt(1, 0); 
preparedStmt.setString(2, code); 
preparedStmt.setString(3, name); 
preparedStmt.setDouble(4, Double.parseDouble(suprice));
preparedStmt.setInt(5, Integer.parseInt(unitsamount));
preparedStmt.setDouble(6, totalprice);

//execute the statement
preparedStmt.execute(); 
conn.close(); 

String newMeters = readMeters(); 
output = "{\"status\":\"success\", \"data\": \"" + newMeters + "\"}";
      	
} catch(Exception e) {
  output = "{\"status\":\"error\", \"data\": \"Failed to insert the meter readings\"}";
  System.err.println(e.getMessage());

} 
return output; 
} 




//READ
public String readMeters() 
{ 
Connection conn = connect();
String output = "";

try
{ 
if (conn == null) 
  {
   return "Error while connecting to the database for reading."; 
  }

// Prepare the html table to be displayed
output = "<table border='1'><tr><th>Meter Code</th><th>House Owner's Name</th>" +
"<th>Price of a unit(Rs.)</th>" + 
"<th>Amount of Units</th>" +
"<th>Total Price(Rs.)</th>" +
"<th>Update</th><th>Remove</th></tr>"; 

String query = "select * from meterreadings"; 
Statement stmt = conn.createStatement(); 
ResultSet rs = stmt.executeQuery(query);

// iterate through the rows in the result set
while (rs.next()) 
{ 
String meterID = Integer.toString(rs.getInt("meterID")); 
String meterCode = rs.getString("meterCode"); 
String houseownerName= rs.getString("houseownerName"); 
String singleUnitPrice= Double.toString(rs.getDouble("singleUnitPrice"));
String unitsAmount= Integer.toString(rs.getInt("unitsAmount"));
String totalPrice= Double.toString(rs.getDouble("totalPrice"));

// Add into the html table
output += "<tr><td><center>" + meterCode + "</center></td>"; 
output += "<td><center>" + houseownerName+ "</center></td>"; 
output += "<td><center>" + singleUnitPrice+ "</center></td>";
output += "<td><center>" + unitsAmount+ "</center></td>"; 
output += "<td><center>" + totalPrice+ "</center></td>";

//buttons
output += "<td>"
	  + "<input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-sm btn-secondary' data-meterid='" + meterID + "'>"
	  + "</td>" 
      + "<td>"
	  + "<input name='btnRemove' type='button' value='Remove' class='btn btn-sm btn-danger btnRemove' data-meterid='" + meterID + "'>"
	  + "</td></tr>";
}

conn.close(); 
// Complete the html table
output += "</table>";

} catch (Exception e) { 
   output = "Error while reading the meter details."; 
   System.err.println(e.getMessage());

} 
return output; 
} 



//UPDATE
public String updateMeter(String ID, String code, String houseownername, String suprice, String unitsamount) 
{ 

Connection conn = connect();
Double totalprice1 = Double.parseDouble(suprice)*Double.parseDouble(unitsamount);
String output = "";

try
{  
if (conn == null) 
 {
   return "Error while connecting to the database for updating."; 
 } 

// create a prepared statement
String query = "UPDATE meterreadings SET meterCode=?,houseownerName=?,singleUnitPrice=?,unitsAmount=?,totalPrice=? WHERE meterID=?"; 
PreparedStatement preparedStmt = conn.prepareStatement(query);

// binding values
preparedStmt.setString(1, code); 
preparedStmt.setString(2, houseownername); 
preparedStmt.setDouble(3, Double.parseDouble(suprice));
preparedStmt.setInt(4, Integer.parseInt(unitsamount)); 
preparedStmt.setDouble(5, totalprice1); 
preparedStmt.setInt(6, Integer.parseInt(ID));

// execute the statement
preparedStmt.executeUpdate();
conn.close();

String newMeters = readMeters(); 
output = "{\"status\":\"success\", \"data\": \"" + newMeters + "\"}";
      	
} catch(Exception e) {
   output = "{\"status\":\"error\", \"data\":\"Failed to update the meter readings.\"}"; 
   System.err.println(e.getMessage());

} 
return output;

} 



//DELETE
public String deleteMeter(String meterID) 
{ 
String output = "";
Connection conn = connect();

try
{ 
if (conn == null) 
 {
   return "Error while connecting to the database for deleting."; 
 }

//SQL query
String query = "delete from meterreadings where meterID=?";

//binding data to the SQL query
PreparedStatement preparedStmt = conn.prepareStatement(query);
preparedStmt.setInt(1, Integer.parseInt(meterID));

// execute the statement
preparedStmt.execute(); 
conn.close();

String newMeters = readMeters(); 
output = "{\"status\":\"success\", \"data\": \"" + newMeters + "\"}"; 
      	
} catch(Exception e) {
    output = "{\"status\":\"error\", \"data\":\"Failed to delete the meter readings.\"}";
    System.err.println(e.getMessage());

} 
return output; 

}


} 



