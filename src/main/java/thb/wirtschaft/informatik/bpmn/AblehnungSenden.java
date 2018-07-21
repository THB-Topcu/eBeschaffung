package thb.wirtschaft.informatik.bpmn;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AblehnungSenden implements JavaDelegate {

  // TODO: Set Mail Server Properties
	  private static final String HOST = "smtp.gmail.com"; //funktioniert nur für gmail-Konten, ansonsten anpassen
	  private static final String USER = "e-MAIL ADRESSE HINZUFÜGEN"; //email muss hinzugefügt werden
	  private static final String PWD = "PASSWORD";  //entsprechendes PW zur e-Mail hier einfügen

  private final static Logger LOGGER = Logger.getLogger(AblehnungSenden.class.getName());

  public void execute(DelegateExecution execution) throws Exception {

      String lieferanten = (String) execution.getVariable("lieferant");

      String emailaddress = null;
      if (lieferanten.equals("Lieferant D")) { //E-Mail des Lieferanten D
  		emailaddress = "lieferantD@service.de";
  	}
        else if (lieferanten.equals("Lieferant E")) { //E-Mail des Lieferanten E
      		emailaddress = "lieferantE@service.de";
    	}
    	
      String recipient = emailaddress ;
      String etext = "Sehr geehrter " + lieferanten + ", \n leider müssen wir Ihnen mitteilen, dass der Vergabeprozess gescheitert ist, sodass wir keine Bestellung bei Ihnen auslösen können. \n Mit freundlichen Grüßen, \n das e-Beschaffungsteam";
      
      if (emailaddress != null){
      Email email = new SimpleEmail();
      email.setCharset("utf-8");
      email.setHostName(HOST);
      email.setSmtpPort(465);
      email.setAuthentication(USER, PWD);
      email.setSSL(true);
      
      try {
          email.setFrom("noreply@eBeschaffung.com"); //e-Mail Adresse verschleiern
          email.setSubject("Artikel bestellen");
          email.setMsg(etext);

          email.addTo(recipient);

          email.send();
          LOGGER.info("Aufgabe AblehnungSenden wurde erfolgreich an folgende Adresse geschickt: " + recipient); 

        } catch (Exception e) {
          LOGGER.log(Level.WARNING, "Konnte e-Mail nicht an Beauftragten senden", e);
        }
      

      } 
    }

}
