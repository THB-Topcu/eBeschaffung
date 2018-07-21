package thb.wirtschaft.informatik.bpmn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Date;
//import java.util.Date;
import java.util.logging.Logger;
//import java.sql.Timestamp;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class Database implements JavaDelegate {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/eBeschaffung";

	private final static Logger LOGGER = Logger.getLogger(Database.class.getName());
	// Database Login-Informationen
	static final String USER = "sa";
	static final String PASS = "";

	public void execute(DelegateExecution execution) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		String bezeichnung=(String) execution.getVariable("bezeichnung");
		Long menge=(Long) execution.getVariable("menge");
	    Long gesamtpreis = (Long) execution.getVariable("gesamtpreis");
	    String lieferanten = (String) execution.getVariable("lieferant");
		//Date vardat=(Date) execution.getVariable("dat");
		
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			LOGGER.info("Verbunden mit Datenbank");
			//Timestamp timestamp = new Timestamp(vardat.getTime());
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 3: Execute a query
			stmt = conn.createStatement();
			String sql = "INSERT INTO WARENBESTAND (BEZEICHNUNG, ANZAHL, GESAMTPREIS, LIEFERANT) VALUES ('"+ bezeichnung +"','" + menge + "','" + gesamtpreis + "','" + lieferanten + "')";
			LOGGER.info("Try to Insert in DB: "+sql);
			stmt.executeUpdate(sql);
			LOGGER.info("complete");

			LOGGER.info("Store Data in Database"); 
			
			

			// STEP 4: Clean-up environment
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		LOGGER.info("Done");
	}	
}

