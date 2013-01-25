#Allgemein
-Auf Tablets ist die Steuerung mit Accelerometer verdreht, dafur einfach in der Klasse GameScreen.java
 in Zeile 409 accelY = game.getInput().getAccelY()/1.5f; in accelY = -game.getInput().getAccelX()/1.5f; ändern
 und neu compilieren.
-Auf einigen Geräten funktioniert das Pitchen des Motorsounds nicht


#Multiplayer
-Die Spieler wählen das Auto und warten dann, denn der Host wählt die Map aus
-die Ausrutschanimation und Explosion wird noch nicht übertragen
-Mit mehr als 3 Spielern gibt es Probleme bei der Übertragung
-Im Multiplayer können die Collider noch stranger sein, als sie eh schon im Singleplayer sind...

 
 
 erfolgreich getestet wurde der Multiplayer mit allen apps.


	viel Spaß :)  

 Source: https://subversion.assembla.com/svn/ponyhofgang/trunk/game/
 Apk:    https://subversion.assembla.com/svn/ponyhofgang/trunk/game/bin/Ponyhofgame.apk



