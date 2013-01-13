package de.ponyhofgang.ponyhofgame.game.screens;


import java.util.List;
import javax.microedition.khronos.opengles.GL10;
import de.ponyhofgang.ponyhofgame.framework.Game;
import de.ponyhofgang.ponyhofgame.framework.Input.TouchEvent;
import de.ponyhofgang.ponyhofgame.framework.gl.Camera2D;
import de.ponyhofgang.ponyhofgame.framework.gl.SpriteBatcher;
import de.ponyhofgang.ponyhofgame.framework.impl.GLScreen;
import de.ponyhofgang.ponyhofgame.framework.math.OverlapTester;
import de.ponyhofgang.ponyhofgame.framework.math.PonyMath;
import de.ponyhofgang.ponyhofgame.framework.math.Rectangle;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;
import de.ponyhofgang.ponyhofgame.game.Assets;

public class AboutScreen extends GLScreen {
	Camera2D guiCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	Rectangle musicCheckboxBounds;
	Rectangle sfxCheckboxBounds;
	Rectangle arrowLeftBounds;
	Rectangle arrowRightBounds;
	Rectangle backButtonBounds;

	int height;
	int width;
	

	
	public boolean pressedBackKey = false;
	
	private static AboutScreen instance = null;
	

	private AboutScreen(Game game)  {
		super(game);

	MainMenuScreen mainMenuScreen = MainMenuScreen.getInstance();
		
		height = mainMenuScreen.height;
		width = mainMenuScreen.width;
		
		;

		guiCam = new Camera2D(glGraphics, width, height);
		batcher = new SpriteBatcher(glGraphics, 10);
		touchPoint = new Vector2();
		backButtonBounds = new Rectangle(PonyMath.getRatio(width, 100)-PonyMath.getRatio(width, 132/2), PonyMath.getRatio(width, 100)-PonyMath.getRatio(width, 132/2), PonyMath.getRatio(width, 132), PonyMath.getRatio(width, 132));

	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> events = game.getInput().getTouchEvents();

		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
			
			if (OverlapTester.pointInRectangle(backButtonBounds, touchPoint)) {
				pressedBackKey = false;
				Assets.playSound(Assets.clickSound);
				game.setScreen(MainMenuScreen.getInstance());
				
			}
		}
		
		
		if (pressedBackKey){
			
			pressedBackKey = false;
			Assets.playSound(Assets.clickSound);
			game.setScreen(MainMenuScreen.getInstance());
			
		}
			
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_TEXTURE_2D);
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(width / 2, height / 2, width, height,
				Assets.backgroundRegion);
		batcher.endBatch();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(PonyMath.getRatio(width, 100), PonyMath.getRatio(width, 100), PonyMath.getRatio(width, 132), PonyMath.getRatio(width, 132), Assets.backButtonRegion);
		
		batcher.endBatch();
		
		
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
	
	
	 public static AboutScreen getInstance(Game game) {
	        if (instance == null) {
	            instance = new AboutScreen(game);
	        }
	        return instance;
	    }
	 
	 public static AboutScreen getInstance() {
	        if (instance == null) {
	            instance = new AboutScreen(null);
	        }
	        return instance;
	    }
}
