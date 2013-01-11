#Allgemein
-Auf Tablets ist die Steuerung mit Accelerometer verdreht, dafur einfach in der Klasse GameScreen.java
 in Zeile 347 accelY = game.getInput().getAccelY()/1.5f; in accelY = game.getInput().getAccelX()/1.5f; andern
 und neu compilieren.
-Auf Tegra 3 Geraten, wie dem Acer funktioniert das Pitchen des Motorsounds on the fly.. auf leistungsschwacheren
 Geraten leider nicht.. da bleibt der Ton konstant :( zur Not komplett die SFX abschalten, wenn es
 nervt... wir arbeiten an einer Losung/Kompromiss


#Singleplayer
-Beim Autoauswahlmenu wird man immer den Ghostbuster bekommen
-nach der Autoauswahl erscheint die spatere Mapauswahl ( hier noch alle grau)... 
 einfach mit dem rechten unteren Button fortfahren
-warend dem Ladescreen nicht auf das Touchscreen touchen, sonst landet man schell
 in der Pampa, da noch keine Collider geladen wurden (wird so schnell es geht gefixt)

#Multiplayer
-Sollte sich das Spiel aufhangen, oder keine Daten versenden, dann in der Klasse world.java in
 Zeile 191 die Ganzzahl hinter dem ModuloOperator erhohen.
-nachdem man sich in der Gruppe A Activity verbunden hat und ins Spiel zuruckkehrt,
 nichts mehr drucken, das Spiel bleibt hier im hauptmenu... es lad aber die Assets
 und das Spiel sollte in wenigen Sekunden starten. Auch hier nicht auf dem Bildschirm
 touchen, bis das Spiel geladen wurde.
-bis jetzt kann man also noch keine Fahrzeuge wahlen, sondern landet direkt in Level Docks
 mit Spieler 1 als Mystery Machine und Spieler 2 mit Ghostbuster
-das Abschie?en von Gegnern, einsammeln von Gadgetboxen und visualisieren von Olflecken bzw.
 Raketen wird noch nicht auf andere Gerate ubertragen.
-Im Multiplayer konnen die Collider noch stranger sein, als sie eh schon im Singleplayer sind...
 keine Ahnung wieso das so ist =/
 


	viel Spa? :)  

 Source: https://subversion.assembla.com/svn/ponyhofgang/trunk/game/
 Apk:    https://subversion.assembla.com/svn/ponyhofgang/trunk/game/bin/Ponyhofgame.apk



