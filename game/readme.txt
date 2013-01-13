#Allgemein
-Auf Tablets ist die Steuerung mit Accelerometer verdreht, dafur einfach in der Klasse GameScreen.java
 in Zeile 347 accelY = game.getInput().getAccelY()/1.5f; in accelY = game.getInput().getAccelX()/1.5f; ändern
 und neu compilieren.
-Auf Tegra 3 Geräten, wie dem Acer funktioniert das Pitchen des Motorsounds on the fly.. auf leistungsschwächeren
 Geräten leider nicht.. da bleibt der Ton konstant :( zur Not komplett die SFX abschalten, wenn es
 nervt... wir arbeiten an einer Lösung/Kompromiss


#Multiplayer
-Sollte sich das Spiel aufhängen, oder keine Daten versenden, dann in der Klasse world.java in
 Zeile 191 die Ganzzahl hinter dem ModuloOperator erhohen. (bei Tobis/Simons app geht es mit Modulo 1
 also mit voller Geschwindigkeit. bei Hans/Atabak bis jetzt in der THM mit modulo 8 und Zuhause über VPN/DSL6000 modulo 20 )
-Die Spieler wählen das Auto und warten dann, denn der Host wählt die Map aus
-die Ausrutschanimation und Explosion wird noch nicht übertragen
-Im Multiplayer können die Collider noch stranger sein, als sie eh schon im Singleplayer sind...

 
 
 erfolgreich getestet wurde der Multiplayer mit folgenden apps:

 -Tobis/Simons(60 pkts/s a 16 bytes)
 -Hans/Atabaks(5 pkts/s a 16 bytes)



	viel Spaß :)  

 Source: https://subversion.assembla.com/svn/ponyhofgang/trunk/game/
 Apk:    https://subversion.assembla.com/svn/ponyhofgang/trunk/game/bin/Ponyhofgame.apk



