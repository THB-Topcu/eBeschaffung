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
	
	  private static final String HOST = "smtp.gmail.com"; //funktioniert nur für gmail-Konten, ansonsten anpassen
	  private static final String USER = "e-MAIL ADRESSE HINZUFÜGEN"; //email muss hinzugefügt werden
	  private static final String PWD = "PASSWORD";  //entsprechendes PW zur e-Mail hier einfügen

	
  private final static Logger LOGGER = Logger.getLogger(EmailEmpfangen.class.getName());

  public void execute(DelegateExecution execution) throws Exception {
      String bezeichnung = (String) execution.getVariable("bezeichnung");  
      String lieferanten = (String) execution.getVariable("lieferant");
      Long menge = (Long) execution.getVariable("menge");
      Long gesamtpreis = (Long) execution.getVariable("gesamtpreis");
	  
      String recipient = "empfaenger@emailadresse.de"; //hier empfängeremail eingeben

      String inhalt = "Liebes e-Beschaffungsteam, \nfolgende Artikel werden in Kürze verschickt und an die in Ihrem Konto verknüpfte Adresse geliefert. /nIn Kürze wird Ihnen der Liefertermin mit einem Zeitfenster per e-Mail zugesandt. /nAm Liefertag steht Ihnen unter ihrem Konto unsere geographische Sendungsverfolgung zur Verfügung. Damit erfahren Sie bis zu 30 Minuten genau, wann Ihre Bestellung bei Ihnen sein wird und können die aktuelle Position des Spediteurs sehen. /n /nArtikelübersicht: " + bezeichnung + ": " + menge + " /nGesamtpreis: " + gesamtpreis + "€ /nFreundlich grüßt Sie /nIhr " + lieferanten + "";
      
      Email email = new SimpleEmail();
      email.setCharset("utf-8");
      email.setHostName(HOST);
      email.setSmtpPort(465);
      email.setAuthentication(USER, PWD);
      email.setSSL(true);
      
      
      
      
      try {
          email.setFrom("noreply@eBeschaffung.com");
          email.setSubject("Ihre Bestellübersicht");
		email.setMsg(inhalt);
        //  email.setMsg ("Liebes e-Beschaffungsteam, "
          //		+ "\nfolgende Artikel werden in Kürze verschickt und an die in Ihrem Konto verknüpfte Adresse geliefert. "
          	//	+ "In Kürze wird Ihnen der Liefertermin mit einem Zeitfenster per e-Mail zugesandt. "
          		//+ "\r\n"
          	//	+ "\r\nAm Liefertag steht Ihnen unter ihrem Konto unsere geographische Sendungsverfolgung zur Verfügung. Damit erfahren Sie bis zu 30 Minuten genau, wann Ihre Bestellung bei Ihnen sein wird und können die aktuelle Position des Spediteurs sehen. "
          	//	+ "\n"
          	//	+ "\nArtikelübersicht: \n BEZEICHNUNG: MENGE \nPreis: GESAMTPREIS € "
          	//	+ "\n"
          	//	+ "\nFreundlich grüßt Sie"
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
