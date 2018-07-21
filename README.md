# eBeschaffung - Prozess

![e-Beschaffung Prozess](https://raw.githubusercontent.com/THB-Topcu/eBeschaffung/master/Bilder/e-Beschaffung.png)


### Lokale Installation / Konfiguration

Für eine lokale Installation und Konfiguration verweisen wir auf die Dokumentation von Herrn Kay Spannberg, die über folgenden Link aufgerufen werden kann. 
> [Dokumentation von Herrn Spannberg](https://github.com/KaySpannberg/imma-prozess).

### Tools
- Eclipse Java Oxygen
- Camunda Modeler

## Einordnung des Themas in die Prozess-Landschaft (2 Punkte)

Der Prozess **elektronische Beschaffung** ist stark mit dem Prozess **eRechnung** verwoben, da eine elektronische Beschaffung nur verwendet werden kann, wenn alle Teilprozesse ebenfalls automatisiert sind. Diese Dokumentation befasst sich allerdings ausschließlich mit der elektronischen Beschaffung, die seinen Teil zur Schaffung einer Digitalen Hochschule beiträgt. Eine andere Gruppe wird sich ganz dem Prozess eRechnung widmen, sodass dieser Prozess ebenfalls betrachtet wird. Beide Prozesse fallen in dem Bereich der Digitalen Verwaltung unter Führung des Hochschulkanzlers. Die elektronische Beschaffung beschränkt sich nicht nur auf den Einkauf von Materialen, sondern inkludiert auch das Bestellen von Dienstleistungen, die jeweils in unterschiedlichen Bereichen der Hochschule durchgeführt werden. Durch eine Digitalisierung dieser Felder kann ein hochschulseitiger Standard implementiert werden. 

Die elektronische Beschaffung kann mit ihren einzelnen Aktivitäten als Prozesshierarchie wie folgt dargestellt werden:
![Prozesshierarchie](https://raw.githubusercontent.com/THB-Topcu/eBeschaffung/master/Bilder/Prozesshierarchie.png)


## Abgrenzung und Beschreibung der Prozesse und Entscheidungen (6 Punkte)




## Erläuterung fachlicher und technischer Modellierungsentscheidungen (6 Punkte)




## Reflexion von Schwachstellen und Optionen für Verbesserungen (3 Punkte)
Der Prozess ist mangels Zeit und Know-How nicht vollständig fachlich modelliert. Der Simple-Path funktioniert einwandfrei, aber sollte eine Lieferung nicht über den Rahmenvertrag laufen, fehlen noch die technischen Implementierungsschritte für das manuelle Einleiten des Vergabeprozesses. Das ist zwar über eine technisch nicht sehr anspruchsvolle Weise zu lösen, nämlich durch einen einfachen Button, der die Vergabe auslöst und anschließend eine E-Mail oder dergleichen an Extern rausschickt. Hier müsste dann eventuell sogar noch eine zeitliche Komponente (Timer) eingebaut werden, die den ganzen Vergabeprozess auf zwei Wochen terminiert. Allerdings kann das ganze Prozedere auch weitaus schöner dargestellt werden, was dann jedoch auch in eine anspruchsvollere technische Modellierung mündet. Anschließend müsste eine Bestätigung oder Ablehnung empfangen werden, die besagt, ob die Lieferung mit dem ausgewählten Lieferanten einwandfrei ist. Ist diese nicht einwandfrei, wird eine Ablehnung per Mail verschickt und der Prozess beendet. Diesen Fall haben wir sogar technisch modelliert, aber da die vorangegangen Aktivitäten fehlen, haben wir das nicht weiter betrachtet. 

Als weitere Eigenkritik ist anzumerken, dass die Aktivität *Rechnung begleichen* nur via Button ausgeführt wird, der die Variable dann auf True setzt, damit der Prozess weiterläuft. Hier ist es sicher auch möglich noch eine elegantere Weise zu wählen, die den Überweisungsprozess etwas anschaulicher darstellt.


## potenzielle Verknüpfungen zu anderen Prozessen (3 Punkte)
Wie eingangs beschrieben, ist der gesamte Prozess der e-Beschaffung stark mit allen anderen digitalisierten Prozessen innerhalb der  Verwaltung verknüpft, sodass jede noch manuell ausgeführte Aktivität weiter digitalisiert werden könnte. Auch Themen wie e-Akte oder die bereits erwähnte eRechnung kann ideal mit dem Prozess verknüpft werden. 

Bei Kleinbestellungen (bis 100€?) können automatisierte Überweisungsprozesse greifen, die selbstverständlich in einem regemäßigen Zyklus von Intern oder Extern geprüft werden müssten, damit Betrug und Untreue ausgeschlossen werden kann, bzw. schnell geahndet wird. Eine weitere Verknüpfungsmöglichkeit bietet die *digitale Archivierung*. In unserem Prozess findet zwar eine „Archivierung“ via Datenbank und das Abspeichern der Bestellung in eine extra Datei statt, aber es kann durch einen komplett aufgezogenen Prozess eine *digitale Archivierung* geschaffen werden, die nicht nur die e-Beschaffung, sondern auch weitere Prozesse digital festhält.

Darausfolgend kann durch die geschaffene Datenbank oder digitale Archivierung der IST-Stand festgehalten werden, der z.B. Konsumprodukte in Echtzeit bei Benutzung minimiert, und beim Fall unter den vordefinierten eisernen Bestand eine automatische Bestellung auslöst, sodass nie Mangel an dem Produkt herrscht und der Verwaltungsakt in dem Fall auf ein Minimum dezimiert werden könnte. Als Anwendungsbeispiel kann man sich hier das Druckerpapier vorstellen. 
Die Drucker könnten 1x am Tag digital an einen Service senden, wie viele Blätter in einem bestimmten Zyklus gedruckt worden sind. Die gedruckte Zahl an Blättern wird dann digital über eine weitere Plattform von dem insgesamt vorhandenen Druckpapier abgezogen, sodass am Ende des Tages immer ein genauer IST-Bestand über das vorhandene Druckerpapier geliefert wird. Sollte dieser dann, wie oben beschrieben, unter den eisernen Bestand fallen, wird automatisch eine Bestellung via Rahmenvertrag ausgelöst und an die Hochschule verschickt.
