package de.ponyhofgang.ponyhofgame.game;


import de.ponyhofgang.ponyhofgame.framework.Music;
import de.ponyhofgang.ponyhofgame.framework.Sound;
import de.ponyhofgang.ponyhofgame.framework.gl.Animation;
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

	
	//Sounds
	public static Sound clickSound;
	public static Sound explosionSound;
	public static Sound squashSound;
	public static Sound slippingSound;
	public static Sound launchingSound;
	public static Sound collectSound;
	public static Sound engineSound;
	public static Sound idleSound;
	
	//
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
	public static Texture explosionTexture;
	public static Animation explosionAnim;
	public static TextureRegion tabToStartRegion;
	public static TextureRegion pleaseWaitForOtherRegion;
	public static TextureRegion levelDocksRegion;
	public static TextureRegion chooseAMapTextRegion;

	
	
	
	public static void loadLoadingScreen(GLGame game) {
		loading = new Texture(game, "gui/loading.jpg", false);
		loadingBackgroundRegion = new TextureRegion(loading, 0, 493, 2, 2);
		iconAndLoadingRegion = new TextureRegion(loading, 0, 0, 512, 271);
		
	}
	
	
	public static void load(GLGame game) {
		background = new Texture(game, "gui/background.png", false);
		backgroundRegion = new TextureRegion(background, 0, 0, 1280, 720);
		
		//Choose a Map
		levelDocksRegion = new TextureRegion(background, 0, 721, 715, 417);
		chooseAMapTextRegion = new TextureRegion(background, 1281, 0, 516, 109);
		
		
		//
		
		items = new Texture(game, "gui/items.png", true);
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
     	
     	
     	ectoMobileTexture = new Texture(game, "texture/ghostbusters.png", true);
     	ectoMobileModel = ObjLoader.load(game, "mesh/ghostbusters.obj");
     	
     	batMobileTexture = new Texture(game, "texture/gengar.png", true);
     	batMobileModel = ObjLoader.load(game, "mesh/batmobile.obj");
     	
     	mysteryMachineTexture = new Texture(game, "texture/scooby_bus_textur.png", true);
     	mysteryMachineModel = ObjLoader.load(game, "mesh/scooby_bus.obj");
     	
     	podRacerTexture = new Texture(game, "texture/rocket.png", true);
     	podRacerModel = ObjLoader.load(game, "mesh/rocket.obj");
     	
     	
     	clickSound = game.getAudio().newSound("sound/click.wav");
     	
     	
     
	}
	
	
	
	public static void loadLevel(GLGame game) {
	items2 = new Texture(game, "gui/items2.png", true);
 	
	pauseMenuRegion = new TextureRegion(items2, 0, 0, 512, 419);
 	pauseBackgroundRegion = new TextureRegion(items2, 0, 443, 2, 2);
 	tabToStartRegion = new TextureRegion(items2, 0, 726, 512, 138);
 	pleaseWaitForOtherRegion = new TextureRegion(items2, 0, 863, 512, 138);
 
 	oilSpillButtonRegion = new TextureRegion(items2, 256, 443, 129, 129);
 	rocketButtonRegion = new TextureRegion(items2, 384, 443, 129, 129);
 	
 	levelDocksTexture = new Texture(game, "texture/container.png", true);
 	craneTexture = new Texture(game, "texture/crane.png", true);
 	groundLevelDocksTexture = new Texture(game, "texture/ground_level_docks.png", true);
 	shadowsLevelDocksTexture = new Texture(game, "texture/shadows_level_docks.png", true);
 	levelDocksModel = ObjLoader.load(game, "mesh/level_docks.obj");
 	groundLevelDocksModel = ObjLoader.load(game, "mesh/ground_level_docks.obj");
 	craneModel = ObjLoader.load(game, "mesh/crane.obj");
 	
 	gadgetBoxTexture = new Texture(game, "texture/gadgetBox.png", true);
 	gadgetBoxModel = ObjLoader.load(game, "mesh/gadgetBox.obj");
 	gadgetBoxShadowTexture = new Texture(game, "texture/gadgetBoxShadow.png", true);
 	gadgetBoxShadowModel = ObjLoader.load(game, "mesh/gadgetBoxShadow.obj");
 	
 	boundsBallModel = ObjLoader.load(game, "mesh/boundsBall.obj");
 	
 	gadgetsTexture = new Texture(game, "texture/rocket.png", true);
 	oilSpillModel = ObjLoader.load(game, "mesh/oilspill.obj");
 	

 	rocketModel = ObjLoader.load(game, "mesh/rocket.obj");
 	
 	explosionTexture = new Texture(game, "texture/explode.png", true);
 	TextureRegion[] keyFrames = new TextureRegion[16];
 	int frame = 0;
 	for (int y = 0; y < 256; y += 64) {
 	for (int x = 0; x < 256; x += 64) {
 	keyFrames[frame++] = new TextureRegion(explosionTexture, x, y, 64, 64);
 	}
 	}
 	explosionAnim = new Animation(0.1f, keyFrames);
 	
 	launchingSound = game.getAudio().newSound("sound/launching.wav");
 	explosionSound = game.getAudio().newSound("sound/explosion.wav");
 	squashSound = game.getAudio().newSound("sound/squash.wav");
 	slippingSound = game.getAudio().newSound("sound/slipping.wav");
 	collectSound = game.getAudio().newSound("sound/collect.wav");
 	engineSound = game.getAudio().newSound("sound/engine_loop.ogg");
	idleSound = game.getAudio().newSound("sound/engine_idle.mp3");
 	
 	
	}
	
	
	
	public static void reload() {
		background.reload();
		items.reload();
		ectoMobileTexture.reload();
		batMobileTexture.reload();
		mysteryMachineTexture.reload();
		podRacerTexture.reload();
		loading.reload();
		//gadgetsTexture.reload(); //wenn keine Rakete mehr im Showcase gezeigt wird dann hier einfügen und bei 186 enfernen
		
//		if(Settings.soundEnabled)
//			music.play();
//			}

		
	}
	
	
	
	public static void gameScreenReload() {
	
		items2.reload();
		levelDocksTexture.reload();
		groundLevelDocksTexture.reload();
		craneTexture.reload();
		shadowsLevelDocksTexture.reload();
		gadgetBoxTexture.reload();
		gadgetBoxShadowTexture.reload();
		gadgetsTexture.reload(); // wenn keine Rakete mehr im Showcase gezeigt wird dann hier entfernen
		explosionTexture.reload();
	}
	
	
	
	
	
	public static void playSound(Sound sound) {
		if (Settings.sfxEnabled)
		sound.play(1);
		}
	
	public static void playEngineSound(Music music) {
		if (Settings.sfxEnabled)
		music.setLooping(true);
		music.play();
		}
	
	public static void playloopedSound(Sound sound, float pitch) {
		if (Settings.sfxEnabled)
		sound.play(1, -1, pitch);
		}
	

	
	
	
}
