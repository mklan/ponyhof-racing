package de.ponyhofgang.ponyhofgame.game;

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
	
	public static Texture items2;
	public static TextureRegion pauseMenuRegion;
	public static TextureRegion pauseBackgroundRegion;
	
	public static Texture loading;
	public static TextureRegion iconAndLoadingRegion;
	public static TextureRegion loadingBackgroundRegion;
	

	public static Vertices3 levelDocksModel;
	public static Vertices3 boundsBallModel;
	
	public static Texture levelDocksTexture;

	public static Sound clickSound;
	public static TextureRegion steeringRegion;
	public static TextureRegion accelRegion;
	public static Texture groundLevelDocksTexture;
	public static Vertices3 groundLevelDocksModel;
	public static Vertices3 craneModel;
	public static Texture craneTexture;
	public static Texture shadowsLevelDocksTexture;
	
	
	//cars
	public static Texture ectoMobileTexture;
	public static Vertices3 ectoMobileModel;
	public static Texture batMobileTexture;
	public static Vertices3 batMobileModel;
	public static Texture mysteryMachineTexture;
	public static Vertices3 mysteryMachineModel;
	public static Texture podRacerTexture;
	public static Vertices3 podRacerModel;
    //
	public static Vertices3 gadgetBoxModel;
	public static Texture gadgetBoxTexture;
	public static Texture gadgetBoxShadowTexture;
	public static Vertices3 gadgetBoxShadowModel;
	public static TextureRegion oilSpillButtonRegion;
	public static TextureRegion rocketButtonRegion;
	public static Vertices3 rocketModel;
	public static Vertices3 oilSpillModel;
	public static Texture gadgetsTexture;
	
	public static void loadLoadingScreen(GLGame game) {
		loading = new Texture(game, "loading.jpg", true);
		loadingBackgroundRegion = new TextureRegion(loading, 0, 493, 2, 2);
		iconAndLoadingRegion = new TextureRegion(loading, 0, 0, 512, 271);
		
	}
	
	
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
     	accelRegion = new TextureRegion(items, 132, 770, 260, 126);
     	steeringRegion = new TextureRegion(items, 132, 644, 260, 126);
     	
     	ectoMobileTexture = new Texture(game, "ghostbusters.png", true);
     	ectoMobileModel = ObjLoader.load(game, "ghostbusters.obj");
     	
     	batMobileTexture = new Texture(game, "gengar.png", true);
     	batMobileModel = ObjLoader.load(game, "batmobile.obj");
     	
     	mysteryMachineTexture = new Texture(game, "rocket.png", true);
     	mysteryMachineModel = ObjLoader.load(game, "oilspill.obj");
     	
     	podRacerTexture = new Texture(game, "rocket.png", true);
     	podRacerModel = ObjLoader.load(game, "rocket.obj");
     	
     	
     	clickSound = game.getAudio().newSound("click.wav");
     	
     	
     
	}
	
	
	
	public static void loadLevel(GLGame game) {
	items2 = new Texture(game, "items2.png", true);
 	pauseMenuRegion = new TextureRegion(items2, 0, 0, 512, 419);
 	pauseBackgroundRegion = new TextureRegion(items2, 0, 443, 2, 2);
 
 	oilSpillButtonRegion = new TextureRegion(items2, 256, 443, 129, 129);
 	rocketButtonRegion = new TextureRegion(items2, 384, 443, 129, 129);
 	
 	levelDocksTexture = new Texture(game, "container.png", true);
 	craneTexture = new Texture(game, "crane.png", true);
 	groundLevelDocksTexture = new Texture(game, "ground_level_docks.png", true);
 	shadowsLevelDocksTexture = new Texture(game, "shadows_level_docks.png", true);
 	levelDocksModel = ObjLoader.load(game, "level_docks.obj");
 	groundLevelDocksModel = ObjLoader.load(game, "ground_level_docks.obj");
 	craneModel = ObjLoader.load(game, "crane.obj");
 	
 	gadgetBoxTexture = new Texture(game, "gadgetBox.png", true);
 	gadgetBoxModel = ObjLoader.load(game, "gadgetBox.obj");
 	gadgetBoxShadowTexture = new Texture(game, "gadgetBoxShadow.png", true);
 	gadgetBoxShadowModel = ObjLoader.load(game, "gadgetBoxShadow.obj");
 	
 	boundsBallModel = ObjLoader.load(game, "boundsBall.obj");
 	
 	gadgetsTexture = new Texture(game, "rocket.png", true);
 	oilSpillModel = ObjLoader.load(game, "oilspill.obj");
 	

 	rocketModel = ObjLoader.load(game, "rocket.obj");
	}
	
	
	
	public static void reload() {
		background.reload();
		items.reload();

		
	}
	
	
	
	public static void gameScreenReload() {
	
		items2.reload();
		//carTexture.reload();
		levelDocksTexture.reload();
		groundLevelDocksTexture.reload();
		craneTexture.reload();
		shadowsLevelDocksTexture.reload();
		
	}
	
	
	
	
	
	public static void playSound(Sound sound) {
		if (Settings.sfxEnabled)
		sound.play(1);
		}
	

	
	
	
}