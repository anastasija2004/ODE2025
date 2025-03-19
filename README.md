# ODE2025
This is my repo for ODE FH Technikum

## Setup Instructions
Install Java Development Kit (JDK): Ensure you have JDK 8 or higher installed on your machine.

JavaFX: Download and set up JavaFX if not already bundled with your JDK.

Build: The project uses JavaFX for GUI. You can build and run the project using an IDE like IntelliJ IDEA or Eclipse that supports JavaFX, or you can build it manually via the command line with Maven or Gradle.

Run the Server: Before launching the clients, start the server. You can run the server by executing Server.java as a Java application. The server listens for incoming player connections on port 12345.

Run the Clients: After the server is running, launch two separate client instances:

Client1: This acts as Player 1.
Client2: This acts as Player 2.
Both clients will connect to the server, and they can start playing after the connection is established.

## General Requirements:

Die Implementierung muss alle der folgenden Anforderungen erfüllen, um positiv bewertet
zu werden:

● Der Code ist mit JavaDoc-Kommentaren versehen, um eine einfache Wartung und
Erweiterung des Codes zu ermöglichen. (Check)

● Eine Readme.md-Datei wird bereitgestellt, die einen guten Überblick über das Projekt
gibt, einschließlich Anweisungen zum Ausführen des Spiels. (Check)

● Die Klassen verwenden Vererbung, Overriding von Methoden und haben mehrere
Konstruktoren. (Check)

● Die Zugriffsrechte für Klassen, Methoden und Properties (Variablen) wurden sinnvoll
gewählt. (Check)

● Es wurde ein durchdachtes Exception-Handling implementiert, um unerwartete Fehler
zu vermeiden. (Check)

● Multithreading wird verwendet, um z.B. zeitintensive Berechnungen NICHT im GUIThread durchzuführen.
(Check)

● Die GUI wurde durchdacht und aufgeräumt gestaltet, um eine intuitive Bedienung zu
ermöglichen. (Check)

● Die GUI wurde mit JavaFX umgesetzt. (Check)
Zusätzlich erfüllt die Implementierung die spezifischen Anforderungen für ein Tic-Tac-ToeSpiel:


● Das Spiel verfügt über eine einfache Benutzeroberfläche, auf der das Spielfeld und
die Symbole angezeigt werden. (Check)

● Das Spiel erkennt, wenn ein/e Spieler/In gewonnen hat, und zeigt eine
entsprechende Meldung an. (Check)

● Das Spiel erkennt, wenn es zu einem Unentschieden gekommen ist. (Check)

● Das Spiel ermöglicht es dem/der Benutzer/in, das Spiel neu zu starten oder zu
beenden. (Check)

## Project specific requirements:

Must haves (Genügend)
- Benutzeroberfläche für ein Tic-Tac-Toe Spiel (Check)
- GewinnerInermittlung (Check)
- Neustart (Check)
- 2 SpielerInnen möglich über das Netzwerk (Check)
- Konfiguration der Verbindung (Mitspieler IP-Adresse) über ein File (Check)
  
Should haves (Befriedigend)
- Berechnen und Anzeige von Spielstatistiken
  - Spiel merkt sich Namen
  - Gewinne, Verluste, Unentschieden
- SpielerInnen können ein eigenes Zeichen auswählen statt X und O
  - Diese Einstellung wird in einer Datei abgespeichert
  
Nice to haves (Gut)
- Animationen für das Setzen von X und O, z.B. FadeIn (Check)
- Auswahl eines benutzerdefinierten Hintergrundbilds für das Tic-Tac-Toe Spielfeld
  über eine Dateiauswahl
  
Overkill (Sehr gut)
- Implementierung eines Lobby Modus
  - SpielerInnen müssen nicht mehr die genaue IP-Adresse der jeweils anderen
    Person wissen, sondern können sich zu einer Lobby-IP verbinden, um so
    Gegenspieler zu finden
- Spielen gegen KI


