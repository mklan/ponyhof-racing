#Allgemein
-Auf Tablets ist die Steuerung mit Accelerometer verdreht, dafur einfach in der Klasse GameScreen.java
 in Zeile 347 accelY = game.getInput().getAccelY()/1.5f; in accelY = game.getInput().getAccelX()/1.5f; ändern
 und neu compilieren.
-Auf Tegra 3 Geräten, wie dem Acer funktioniert das Pitchen des Motorsounds on the fly.. auf leistungsschwächeren
 Geräten leider nicht.. da bleibt der Ton konstant :( zur Not komplett die SFX abschalten, wenn es
 nervt... wir arbeiten an einer Lösung/Kompromiss


#Singleplayer
-nach der Autoauswahl erscheint die spätere Mapauswahl ( hier noch alles in grau)... 
 einfach mit dem rechten unteren "backButton" fortfahren


#Multiplayer
-Sollte sich das Spiel aufhängen, oder keine Daten versenden, dann in der Klasse world.java in
 Zeile 191 die Ganzzahl hinter dem ModuloOperator erhohen.
-Die Spieler wählen das Auto und warten dann, denn der Host wählt die Map aus
-bis jetzt kann man nicht mit den ausgwählten Fahrzeugen fahren, sondern landet in Level Docks
 mit Spieler 1 als Mystery Machine und Spieler 2 mit Ghostbuster ( mehrere Spieler funktionieren noch nicht)
-das Abschießen von Gegnern, einsammeln von Gadgetboxen und visualisieren von Olflecken bzw.
 Raketen sollte übertragen werden, (bis auf die Ausrutsch animation), ist jedoch noch sehr buggy
-Im Multiplayer können die Collider noch stranger sein, als sie eh schon im Singleplayer sind...
 keine Ahnung wieso das so ist =/
 
 
 erfolgreich getestet wurde der Multiplayer mit Tobis/Simons(60 pkt/s) und Hans/Atabaks(5 okt/s) app

Changelog:

13.01: -Autoauswahl im Singleplayer klappt ( Rakete als Platzhalter für 4. Wagen + Batmobile pink texturiert
       -"Ladebildschirm touchen und dann in der Pampa landen" wurde hoffentlich behoben
       -man kann im Singleplayer mit dem ausgewähltem Auto fahren
       -die sämtlichen gadets werden übertragen und erzeugt ( nicht getestet ) (keine Ausrutschanimation auf anderen Geräten)


	viel Spaß :)  

 Source: https://subversion.assembla.com/svn/ponyhofgang/trunk/game/
 Apk:    https://subversion.assembla.com/svn/ponyhofgang/trunk/game/bin/Ponyhofgame.apk



