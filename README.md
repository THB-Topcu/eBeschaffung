# eBeschaffung - Prozess

![e-Beschaffung Prozess](https://raw.githubusercontent.com/THB-Topcu/eBeschaffung/master/Bilder/e-Beschaffung.png)


### Lokale Installation / Konfiguration

Für eine lokale Installation und Konfiguration verweisen wir auf die Dokumentation von Herrn Kay Spannberg, die über folgenden Link aufgerufen werden kann. 
> [Dokumentation von Herrn Spannberg](https://github.com/KaySpannberg/imma-prozess)

### Tools
- Eclipse Java Oxygen
- Camunda Modeler

## Einordnung des Themas in die Prozess-Landschaft (2 Punkte)

Der Prozess **elektronische Beschaffung** ist stark mit dem Prozess **eRechnung** verwoben, da eine elektronische Beschaffung nur verwendet werden kann, wenn alle Teilprozesse ebenfalls automatisiert sind. Diese Dokumentation befasst sich allerdings ausschließlich mit der elektronischen Beschaffung, die seinen Teil zur Schaffung einer Digitalen Hochschule beiträgt. Eine andere Gruppe wird sich ganz dem Prozess eRechnung widmen, sodass dieser Prozess ebenfalls betrachtet wird. Beide Prozesse fallen in dem Bereich der Digitalen Verwaltung unter Führung des Hochschulkanzlers. Die elektronische Beschaffung beschränkt sich nicht nur auf den Einkauf von Materialen, sondern inkludiert auch das Bestellen von Dienstleistungen, die jeweils in unterschiedlichen Bereichen der Hochschule durchgeführt werden. Durch eine Digitalisierung dieser Felder kann ein hochschulseitiger Standard implementiert werden. 

Die elektronische Beschaffung kann mit ihren einzelnen Aktivitäten als Prozesshierarchie wie folgt dargestellt werden:
![Prozesshierarchie](https://raw.githubusercontent.com/THB-Topcu/eBeschaffung/master/Bilder/Prozesshierarchie.png)


## Abgrenzung und Beschreibung der Prozesse und Entscheidungen (6 Punkte)

### BPMN Modell

Das BPMN Modell kann ganz oben in der Dokumentation betrachtet werden.

Der Prozess e-Beschaffung startet in der Fachabteilung der TH Brandenburg mit einem Bedarf, der manuell über ein Formular gemeldet wird.
Der Bedarf wird über ein Formular mit folgenden Pflichtfeldern abgefragt:
- Bezeichnung
- Einzelpreis
- Menge
- Rahmenvertrag [ja, nein]
- Wenn Rahmenvertrag, welcher genau?

Anschließend wird über eine *ServiceTask* der Gesamtpreis aus Einzelpreis und Menge berechnet. Aus dem berechneten Gesamtpreis und der Abfrage, ob es sich um einen Rahmenvertrag handelt, wird über die *DMN-Tabelle* entschieden, welcher Lieferant für das zu bestellende Produkt und Gesamtpreis automatisch kontaktiert werden soll. Das *exklusive Gateway* prüft, ob die Position in einen Rahmenvertrag hinterlegt ist. Wenn dies der Fall ist, wird der obere *Sequenzfluss* aktiviert. Sollte es sich um keinen Rahmenvertrag handeln, liegt die Entscheidung dabei, ob die Position bis zu 500 € kostet, dann wird der mittlere *Sequenzfluss* aktiviert. Falls die Position über 500 bis maximal 20.000 € liegt, wird der untere *Sequenzfluss* angesprochen. Bei einer Position oberhalb der 20k kann der Prozess nicht fortgeführt werden.

Wird der oberste Pfad gefolgt wird eine "Send-Task" angesprochen, die den Bestellauftrag an den entsprechenden Dienstleister rausschicken. Bei dem *Message intermediate catch event* muss gewartet werden, bis eine Nachricht vom externen Dienstleister eingeht. In der Regel verschickt dieser die Bestellbestätigung. Die Rechnung muss anschließend (manuell) beglichen werden. Im weiteren Verlauf werden noch zwei *Service-Tasks* angesprochen, die die Produktinformationen zum einen in eine Datenbank abspeichern und zum anderen die Bestellung als Logfile in eine Textdatei schreiben. Der eingeleitete Bedarf ist dann damit gedeckt und der Prozess abgeschlossen.

Folgt man den mittleren oder unteren Pfad des Gateways dann wird in beiden Fällen ein Angebot eingeholt und aufgrund des Angebots dann ein Vergabeprozess eingeleitet. Dieser muss extern geprüft werden, und wird anschließend mit einer Bestätigung oder einer Ablehnung zurückgesandt. Das folgende Gateway prüft nun, ob die Auftragsvergabe erfolgreich war oder nicht. Ist diese erfolgreich, wird die Bestellung ausgelöst und der Prozess wird wie oben beschrieben forgeführt. Ist die Auftragsvergabe nicht erfolgreich, wird der Prozess abgebrochen und eine Ablehnung per e-Mail rausgeschickt.


### DMN Modell

![DMN Modell](https://raw.githubusercontent.com/THB-Topcu/eBeschaffung/master/Bilder/DMN%20Modell.PNG)

Kurzgefasst ist die Decision Model and Notation eine Entscheidungsregel im Geschäftsprozessmanagement, das heißt, dass vordefinierten Regeln gefolgt werden. Diese Regeln müssen in der Tabelle vollständig aufgefangen werden, und ein entsprechendes Output (ein entsprechender Ausgang) angegeben werden.

Unsere Hit Policy lautet: UNIQUE

Unser DMN Modell hat drei Inputvariablen, die überprüft werden müssen:
- Rahmenvertrag (Boolean)
- Gesamtpreis (Long)
- Rahmenverträge (String)

und einen Output, der sich aus den Inputvariablen ergibt:
- Lieferant (String)

Die DMN-Tabelle ist relativ einfach zu lesen. Sollte ein Rahmenvertrag für das Produkt vorliegen, wird der Wert mit true belegt und der entsprechende Rahmenvertrag ausgewählt (A, B oder C). Sollte kein Rahmenvertrag vorliegen, wird der Rahmenvertrag auf false gesetzt und die Entscheidung wird über den Gesamtpreis bestimmt. Zwischen 0 - 500 € wird Lieferant D ausgegeben, bei einem Preis oberhalb von 500 bis 20.000 € wird Lieferant E ausgegeben.


## Erläuterung fachlicher und technischer Modellierungsentscheidungen (6 Punkte)
Der Prozess wird über ein externes Formular eingeleitet, dabei haben wir uns bewusst dafür entschieden, den Einzelpreis eines Produkts abzufragen, und die Bestellmenge, sodass im nächsten Schritt eine automatische Berechnung des Gesamtpreises erfolgt.
Insgesamt haben wir mit zwei externen Formularen gearbeitet, die im Camunda Modeler im BPMN-Prozess in der jeweiligen Aktivität hinterlegt werden müssen.
Dazu klickt man die Aktivität an und wählt den Reiter Forms aus, und fügt den Link zum Formular in das Feld "Form Key" ein.
Bsp.: embedded:app:Formular/Rechnungbegleichen.html
Das Formular ist unter: //src/main/webapp/Formular/Rechnungbegleichen.html abgelegt.

![Formular](https://raw.githubusercontent.com/THB-Topcu/eBeschaffung/master/Bilder/Formular%20hinzuf%C3%BCgen.PNG)

Wir hatten anfangs ein internes Formular, aber haben uns letztlich für ein externes Formular entschieden, da das externe Formular mehr Freiheiten bot und Pflichtfelder einfacher bestimmt werden konnten. Außerdem konnten wir mit Hilfe des Befehls
`readonly` bestimmte Felder vor dem Bearbeiten schützen.

Die [SendTask](https://github.com/THB-Topcu/eBeschaffung/blob/master/src/main/java/thb/wirtschaft/informatik/bpmn/EmailSenden.java)
wird

Die [SendTask](https://github.com/THB-Topcu/eBeschaffung/blob/master/src/main/java/thb/wirtschaft/informatik/bpmn/EmailSenden.java)
wird

Die [Datenbank](https://github.com/THB-Topcu/eBeschaffung/blob/master/src/main/java/thb/wirtschaft/informatik/bpmn/Database.java)
wird

Die [SendTask](https://github.com/THB-Topcu/eBeschaffung/blob/master/src/main/java/thb/wirtschaft/informatik/bpmn/EmailSenden.java)
wird

## Reflexion von Schwachstellen und Optionen für Verbesserungen (3 Punkte)
Der Prozess ist mangels Zeit und Know-How nicht vollständig fachlich modelliert. Der Simple-Path funktioniert einwandfrei, aber sollte eine Lieferung nicht über den Rahmenvertrag laufen, fehlen noch die technischen Implementierungsschritte für das manuelle Einleiten des Vergabeprozesses. Das ist zwar über eine technisch nicht sehr anspruchsvolle Weise zu lösen, nämlich durch einen einfachen Button, der die Vergabe auslöst und anschließend eine E-Mail oder dergleichen an Extern rausschickt. Hier müsste dann eventuell sogar noch eine zeitliche Komponente (Timer) eingebaut werden, die den ganzen Vergabeprozess auf zwei Wochen terminiert. Allerdings kann das ganze Prozedere auch weitaus schöner dargestellt werden, was dann jedoch auch in eine anspruchsvollere technische Modellierung mündet. Anschließend müsste eine Bestätigung oder Ablehnung empfangen werden, die besagt, ob die Lieferung mit dem ausgewählten Lieferanten einwandfrei ist. Ist diese nicht einwandfrei, wird eine Ablehnung per Mail verschickt und der Prozess beendet. Diesen Fall haben wir sogar technisch modelliert, aber da die vorangegangen Aktivitäten fehlen, haben wir das nicht weiter betrachtet. 

Als weitere Eigenkritik ist anzumerken, dass die Aktivität *Rechnung begleichen* nur via Button ausgeführt wird, der die Variable dann auf True setzt, damit der Prozess weiterläuft. Hier ist es sicher auch möglich noch eine elegantere Weise zu wählen, die den Überweisungsprozess etwas anschaulicher darstellt.


## Potenzielle Verknüpfungen zu anderen Prozessen (3 Punkte)
Wie eingangs beschrieben, ist der gesamte Prozess der e-Beschaffung stark mit allen anderen digitalisierten Prozessen innerhalb der  Verwaltung verknüpft, sodass jede noch manuell ausgeführte Aktivität weiter digitalisiert werden könnte. Auch Themen wie e-Akte oder die bereits erwähnte eRechnung kann ideal mit dem Prozess verknüpft werden. 

Bei Kleinbestellungen (bis 100€?) können automatisierte Überweisungsprozesse greifen, die selbstverständlich in einem regemäßigen Zyklus von Intern oder Extern geprüft werden müssten, damit Betrug und Untreue ausgeschlossen werden kann, bzw. schnell geahndet wird. Eine weitere Verknüpfungsmöglichkeit bietet die *digitale Archivierung*. In unserem Prozess findet zwar eine „Archivierung“ via Datenbank und das Abspeichern der Bestellung in eine extra Datei statt, aber es kann durch einen komplett aufgezogenen Prozess eine *digitale Archivierung* geschaffen werden, die nicht nur die e-Beschaffung, sondern auch weitere Prozesse digital festhält.

Daraus folgend kann durch die geschaffene Datenbank oder digitale Archivierung der IST-Stand festgehalten werden, der z.B. Konsumprodukte in Echtzeit bei Benutzung minimiert, und beim Fall unter den vordefinierten eisernen Bestand eine automatische Bestellung auslöst, sodass nie Mangel an dem Produkt herrscht und der Verwaltungsakt in dem Fall auf ein Minimum dezimiert werden könnte. Als Anwendungsbeispiel kann man sich hier das Druckerpapier vorstellen. 
Die Drucker könnten 1x am Tag digital an einen Service senden, wie viele Blätter in einem bestimmten Zyklus gedruckt worden sind. Die gedruckte Zahl an Blättern wird dann digital über eine weitere Plattform von dem insgesamt vorhandenen Druckpapier abgezogen, sodass am Ende des Tages immer ein genauer IST-Bestand über das vorhandene Druckerpapier geliefert wird. Sollte dieser dann, wie oben beschrieben, unter den eisernen Bestand fallen, wird automatisch eine Bestellung via Rahmenvertrag ausgelöst und an die Hochschule verschickt.
