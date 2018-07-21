package thb.wirtschaft.informatik.bpmn;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class EmailSenden implements JavaDelegate {

  private static final String HOST = "smtp.gmail.com"; //funktioniert nur für gmail-Konten, ansonsten anpassen
  private static final String USER = "e-MAIL ADRESSE HINZUFÜGEN"; //email muss hinzugefügt werden
  private static final String PWD = "PASSWORD";  //entsprechendes PW zur e-Mail hier einfügen

	
  private final static Logger LOGGER = Logger.getLogger(EmailSenden.class.getName());

  public void execute(DelegateExecution execution) throws Exception {
      String bezeichnung = (String) execution.getVariable("bezeichnung");  
      String lieferanten = (String) execution.getVariable("lieferant");
      Long menge = (Long) execution.getVariable("menge");
      Long gesamtpreis = (Long) execution.getVariable("gesamtpreis");
      
      
      String emailaddress = null;
      if (lieferanten.equals("Lieferant A")) {
    		emailaddress = "lieferantA@service.de"; //E-Mail des Lieferanten A
    	}
      else if (lieferanten.equals("Lieferant B")) { //E-Mail des Lieferanten B
  		emailaddress = "lieferantB@service.de";
  	    }
      else if (lieferanten.equals("Lieferant C")) { //E-Mail des Lieferanten C
  		emailaddress = "lieferantC@service.de";
     	}
      else if (lieferanten.equals("Lieferant D")) { //E-Mail des Lieferanten D
  		emailaddress = "lieferantD@service.de";
  	}
        else if (lieferanten.equals("Lieferant E")) { //E-Mail des Lieferanten E
      		emailaddress = "lieferantE@service.de";
    	}
      
      
      String recipient = emailaddress ;
      String inhalt = "Sehr geehrter "  + lieferanten +", \n\ndas e-Beschaffungsteam der Hochschule Musterburg würde gerne folgendes bestellen: \n\nArtikel: " + bezeichnung + "\nMenge: "+ menge + "\nListengesamtpreis: " + gesamtpreis + "€.\n\nMit freundlichen Gruessen, \n\ndas e-Beschaffungsteam";
      
      if (emailaddress != null){
      Email email = new SimpleEmail();
      email.setCharset("utf-8");
      email.setHostName(HOST);
      email.setSmtpPort(465);
      email.setAuthentication(USER, PWD);
      email.setSSL(true);
      
      try {
          email.setFrom("noreply@eBeschaffung.com"); //e-Mail Adresse verschleiern
          email.setSubject("Bestellauftrag");
          email.setMsg(inhalt);

          email.addTo(recipient);

          email.send();
          LOGGER.info("Aufgabe EmailSenden wurde erfolgreich an folgende Adresse geschickt: " + recipient); 

        } catch (Exception e) {
          LOGGER.log(Level.WARNING, "Konnte e-Mail nicht an Beauftragten senden", e);
        }
      
      RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
      runtimeService.correlateMessage("startMessage");
      runtimeService.startProcessInstanceByMessage("startMessage");
      } 
    }

}
