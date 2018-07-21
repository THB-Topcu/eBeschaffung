package thb.wirtschaft.informatik.bpmn;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.Execution;


public class EmailEmpfangen implements JavaDelegate {

  // TODO: Set Mail Server Properties
	
	  private static final String HOST = "smtp.gmail.com"; //funktioniert nur f�r gmail-Konten, ansonsten anpassen
	  private static final String USER = "e-MAIL ADRESSE HINZUF�GEN"; //email muss hinzugef�gt werden
	  private static final String PWD = "PASSWORD";  //entsprechendes PW zur e-Mail hier einf�gen

	
  private final static Logger LOGGER = Logger.getLogger(EmailEmpfangen.class.getName());

  public void execute(DelegateExecution execution) throws Exception {
      String bezeichnung = (String) execution.getVariable("bezeichnung");  
      String lieferanten = (String) execution.getVariable("lieferant");
      Long menge = (Long) execution.getVariable("menge");
      Long gesamtpreis = (Long) execution.getVariable("gesamtpreis");
	  
      String recipient = "empfaenger@emailadresse.de"; //hier empf�ngeremail eingeben

      String inhalt = "Liebes e-Beschaffungsteam, \nfolgende Artikel werden in K�rze verschickt und an die in Ihrem Konto verkn�pfte Adresse geliefert. /nIn K�rze wird Ihnen der Liefertermin mit einem Zeitfenster per e-Mail zugesandt. /nAm Liefertag steht Ihnen unter ihrem Konto unsere geographische Sendungsverfolgung zur Verf�gung. Damit erfahren Sie bis zu 30 Minuten genau, wann Ihre Bestellung bei Ihnen sein wird und k�nnen die aktuelle Position des Spediteurs sehen. /n /nArtikel�bersicht: " + bezeichnung + ": " + menge + " /nGesamtpreis: " + gesamtpreis + "� /nFreundlich gr��t Sie /nIhr " + lieferanten + "";
      
      Email email = new SimpleEmail();
      email.setCharset("utf-8");
      email.setHostName(HOST);
      email.setSmtpPort(465);
      email.setAuthentication(USER, PWD);
      email.setSSL(true);
      
      
      
      
      try {
          email.setFrom("noreply@eBeschaffung.com");
          email.setSubject("Ihre Bestell�bersicht");
		email.setMsg(inhalt);
        //  email.setMsg ("Liebes e-Beschaffungsteam, "
          //		+ "\nfolgende Artikel werden in K�rze verschickt und an die in Ihrem Konto verkn�pfte Adresse geliefert. "
          	//	+ "In K�rze wird Ihnen der Liefertermin mit einem Zeitfenster per e-Mail zugesandt. "
          		//+ "\r\n"
          	//	+ "\r\nAm Liefertag steht Ihnen unter ihrem Konto unsere geographische Sendungsverfolgung zur Verf�gung. Damit erfahren Sie bis zu 30 Minuten genau, wann Ihre Bestellung bei Ihnen sein wird und k�nnen die aktuelle Position des Spediteurs sehen. "
          	//	+ "\n"
          	//	+ "\nArtikel�bersicht: \n BEZEICHNUNG: MENGE \nPreis: GESAMTPREIS � "
          	//	+ "\n"
          	//	+ "\nFreundlich gr��t Sie"
          	//	+ "\n"
          	//	+ "Ihr LIEFERANTEN");
          
          email.addTo(recipient);

          email.send();
          LOGGER.info("Task Assignment Email successfully sent to address: " + recipient); 

        } catch (Exception e) {
          LOGGER.log(Level.WARNING, "Could not send email to assignee", e);
        }
      

      	
      RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
      
      	runtimeService.createMessageCorrelation("receiveMessage");
      	Execution message = runtimeService.createExecutionQuery().messageEventSubscriptionName("receiveMessage").singleResult();
        runtimeService.messageEventReceived("receiveMessage", message.getId());
      	

    }

	

}
