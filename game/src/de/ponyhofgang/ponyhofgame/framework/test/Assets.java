package de.ponyhofgang.ponyhofgame.framework.test;

import java.io.IOException;

import de.ponyhofgang.ponyhofgame.framework.Sound;
import de.ponyhofgang.ponyhofgame.framework.gl.ObjLoader;
import de.ponyhofgang.ponyhofgame.framework.gl.Texture;
import de.ponyhofgang.ponyhofgame.framework.gl.TextureRegion;
import de.ponyhofgang.ponyhofgame.framework.gl.Vertices3;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGame;

public class Assets {

	public static Texture background;
	public static TextureRegion backgroundRegion;
	public static Texture items;
	public static TextureRegion menuRegion;
	public static TextureRegion aboutRegion;
	public static TextureRegion settingsRegion;
	public static TextureRegion touchSettingRegion;
	public static TextureRegion accelSettingRegion;
	public static TextureRegion soundCrossRegion;
	public static TextureRegion backButtonRegion;
	public static TextureRegion pauseButtonRegion;
	
	
	public static Vertices3 gengarModel;
	public static Texture gengarTexture;

	public static Sound clickSound;
	
	
	
	public static void load(GLGame game) {
		background = new Texture(game, "background.jpg", true);
		backgroundRegion = new TextureRegion(background, 0, 0, 1280, 720);
		items = new Texture(game, "items.png", true);
		menuRegion = new TextureRegion(items, 0, 0, 512, 512);
     	aboutRegion = new TextureRegion(items, 132, 512, 132, 132); 
     	settingsRegion = new TextureRegion(items, 512, 0, 512, 512 );
     	touchSettingRegion = new TextureRegion(items, 512, 512, 512, 162);
     	accelSettingRegion = new TextureRegion(items, 512, 681, 512, 162);
     	soundCrossRegion = new TextureRegion(items, 457, 512, 55, 55);
     	backButtonRegion = new TextureRegion(items, 0, 512, 132, 132);
     	pauseButtonRegion = new TextureRegion(items, 0, 644, 132, 132);
     	
     	gengarTexture = new Texture(game, "gengar.png", true);
     	gengarModel = ObjLoader.load(game, "batmobile.obj");
     	
     	
     	
     	

     	clickSound = game.getAudio().newSound("click.wav");

	}
	
	public static void reload() {
		background.reload();
		items.reload();
		
		
	}
	
	
	public static void playSound(Sound sound) {
		if (Settings.sfxEnabled)
		sound.play(1);
		}
	
	
	
}
