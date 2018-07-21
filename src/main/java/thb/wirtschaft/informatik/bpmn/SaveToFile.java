package thb.wirtschaft.informatik.bpmn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SaveToFile implements JavaDelegate {

	private static final String FILENAME = "C:\\Users\\USERNAME\\Desktop\\Inhalt.txt"; //Datei wird auf dem Desktop gespeichert, kann angepasst werden
	
	private final static Logger LOGGER = Logger.getLogger(SaveToFile.class.getName());

	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
	      String bezeichnung = (String) execution.getVariable("bezeichnung");  
	      String lieferanten = (String) execution.getVariable("lieferant");
	      Long menge = (Long) execution.getVariable("menge");
	      Long gesamtpreis = (Long) execution.getVariable("gesamtpreis");
	      Date vardat=(Date) execution.getVariable("dat");
	      
		Timestamp timestamp = new Timestamp(vardat.getTime());
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String data = System.lineSeparator() + "\"Artikel wurde beim " + lieferanten +" am " + timestamp + "bestellt: \n " + menge + "x "+ bezeichnung + " zu einem Gesamtpreis von " + gesamtpreis + "€.";

			File file = new File(FILENAME);

			// Wenn Datei nicht existiert, dann erstelle neue
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(data);

			LOGGER.info("Erfolgreich gespeichert in Inhalt.txt" );

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
		
	}

}