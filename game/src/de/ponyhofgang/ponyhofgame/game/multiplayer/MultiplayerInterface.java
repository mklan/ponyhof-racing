package de.ponyhofgang.ponyhofgame.game.multiplayer;

/*
* Bestimmt die Commands f�r die Kommunikation zwischen Games und Infrastruktur
*/


public interface MultiplayerInterface {
	
	
	   public final static String CONNECTION_SERVICE_PREFIX="de.thm.mps";

	    /**
	    * Nachricht I
	    */

	    //what:
	    public final static int MESSAGE_1_REGISTER = 1; // Beim Service anmelden
	    


	    /**
	    * Nachricht II
	    */

	    //what:
	    public final int MESSAGE_2_CONNECTION=2;
	    //Bundle der Nachricht II enth�lt folgende Keys:
	    public final static String CONNECTION_PLAYERCOUNT="message2.player.count"; // Gibt int zur�ck
	    public final static String CONNECTION_PLAYERNAMES="message2.player.names"; //Gibt String zur�ck, Player per ; getrennt
	    public final static String CONNECTION_OWN_ID="message2.self.id"; //Gibt Int der ID zur�ck
	    public final static String CONNECTION_ACTIVITY="message2.activity"; //Gibt String zur�ck in der Form activityname.class

	    /**
	    *Nachricht III
	    */

	    //what:
	    public final int MESSAGE_3_SEND=3;
	    //Bundle der Nachricht III enth�lt folgende Keys:
	    public final static String SEND_ADDRESS="message3.address.id"; // Gibt int zur�ck
	    public final static String SEND_TYPE="message3.datatype"; //Gibt String zur�ck
	    public final static String SEND_DATA="message3.data"; //Gibt Rohdaten zur�ck

	    /**
	    * Nachricht IV
	    */

	    //what:
	    public final int MESSAGE_4_GET=4;
	    //Bundle der Nachricht IV enth�lt folgende Keys:
	    public final static String GET_ADDRESS="message4.address.id"; // Gibt int zur�ck
	    public final static String GET_TYPE="message4.datatype"; //Gibt String zur�ck
	    public final static String GET_DATA="message4.data"; //Gibt Rohdaten zur�ck

	    /**
	    * Nachricht V
	    */

	    //what:
	    public final int MESSAGE_5_CLOSE=5;

	    /**
	    * Nachricht VI
	    */

	    //what:
	    public final int MESSAGE_6_CLOSED=6;
	    //Bundle der Nachricht V enth�lt folgende Keys:
	    public final static String CLOSED_BY = "closed.id"; // Liefert int zur�ck

	    }
