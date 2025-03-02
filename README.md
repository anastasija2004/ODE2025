# ODE2025
This is my repo for ODE FH Technikum

## General Requirements:

Die Implementierung muss alle der folgenden Anforderungen erfüllen, um positiv bewertet
zu werden:

● Der Code ist mit JavaDoc-Kommentaren versehen, um eine einfache Wartung und
Erweiterung des Codes zu ermöglichen.

● Eine Readme.md-Datei wird bereitgestellt, die einen guten Überblick über das Projekt
gibt, einschließlich Anweisungen zum Ausführen des Spiels.

● Die Klassen verwenden Vererbung, Overriding von Methoden und haben mehrere
Konstruktoren.

● Die Zugriffsrechte für Klassen, Methoden und Properties (Variablen) wurden sinnvoll
gewählt.

● Es wurde ein durchdachtes Exception-Handling implementiert, um unerwartete Fehler
zu vermeiden.

● Multithreading wird verwendet, um z.B. zeitintensive Berechnungen NICHT im GUIThread durchzuführen.

● Die GUI wurde durchdacht und aufgeräumt gestaltet, um eine intuitive Bedienung zu
ermöglichen.

● Die GUI wurde mit JavaFX umgesetzt.
Zusätzlich erfüllt die Implementierung die spezifischen Anforderungen für ein Tic-Tac-ToeSpiel:

● Das Spiel verfügt über eine einfache Benutzeroberfläche, auf der das Spielfeld und
die Symbole angezeigt werden.

● Das Spiel erkennt, wenn ein/e Spieler/In gewonnen hat, und zeigt eine
entsprechende Meldung an.

● Das Spiel erkennt, wenn es zu einem Unentschieden gekommen ist.

● Das Spiel ermöglicht es dem/der Benutzer/in, das Spiel neu zu starten oder zu
beenden.

## Project specific requirements:

Must haves (Genügend)
- Benutzeroberfläche für ein Tic-Tac-Toe Spiel
- GewinnerInermittlung
- Neustart
- 2 SpielerInnen möglich über das Netzwerk
- Konfiguration der Verbindung (Mitspieler IP-Adresse) über ein File
  
Should haves (Befriedigend)
- Berechnen und Anzeige von Spielstatistiken
- Spiel merkt sich Namen
- Gewinne, Verluste, Unentschieden
- SpielerInnen können ein eigenes Zeichen auswählen statt X und O
- Diese Einstellung wird in einer Datei abgespeichert
  
Nice to haves (Gut)
- Animationen für das Setzen von X und O, z.B. FadeIn
- Auswahl eines benutzerdefinierten Hintergrundbilds für das Tic-Tac-Toe Spielfeld
  über eine Dateiauswahl
  
Overkill (Sehr gut)
- Implementierung eines Lobby Modus
- SpielerInnen müssen nicht mehr die genaue IP-Adresse der jeweils anderen
  Person wissen, sondern können sich zu einer Lobby-IP verbinden, um so
  Gegenspieler zu finden
- Spielen gegen KI