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
import de.ponyhofgang.ponyhofgame.game.ShowCaseRenderer;

public class SelectACarScreen extends GLScreen {
	
	
	
	
	private Camera2D guiCam;
	private SpriteBatcher batcher;
	private Vector2 touchPoint;
	private Rectangle backButtonBounds, nextButtonBounds;
	
	private ShowCaseRenderer renderer;

	private int height;
	private int width;
	
	
	
	public boolean pressedBackKey = false;
	public static int selectedCar = 0;
	
	private int down_x = -1;
	private int swipe;
	
	
	private static SelectACarScreen instance = null;
	

	private SelectACarScreen(Game game)  {
		super(game);

	MainMenuScreen mainMenuScreen = MainMenuScreen.getInstance();
		
		height = mainMenuScreen.height;
		width = mainMenuScreen.width;
		
		
		
		renderer = new ShowCaseRenderer(glGraphics);

		guiCam = new Camera2D(glGraphics, width, height);
		batcher = new SpriteBatcher(glGraphics, 10);
		touchPoint = new Vector2();
		backButtonBounds = new Rectangle(PonyMath.getRatio(width, 100)-PonyMath.getRatio(width, 132/2), PonyMath.getRatio(width, 100)-PonyMath.getRatio(width, 132/2), PonyMath.getRatio(width, 132), PonyMath.getRatio(width, 132));
		nextButtonBounds = new Rectangle(width-PonyMath.getRatio(width, 100)-PonyMath.getRatio(width, 132/2), PonyMath.getRatio(width, 100)-PonyMath.getRatio(width, 132/2), PonyMath.getRatio(width, 132), PonyMath.getRatio(width, 132));
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> events = game.getInput().getTouchEvents();

		
		
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			
			if (event.type == TouchEvent.TOUCH_DOWN){
			
				down_x = event.x;
				
			    
			}
			
			
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
				
			
			if(down_x >= 0){
				
				if ((down_x - event.x) > 50){ swipe  = ShowCaseRenderer.SWIPE_LEFT; ShowCaseRenderer.rotation2 -= 1.5;}
				if ((down_x - event.x) < -50){ swipe = ShowCaseRenderer.SWIPE_RIGHT; ShowCaseRenderer.rotation2 += 1.5;}
				
				down_x =-1;
				
			}
			
				
			
			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
			
			if (OverlapTester.pointInRectangle(backButtonBounds, touchPoint)) {
				pressedBackKey = false;
				Assets.playSound(Assets.clickSound);
				game.setScreen(MainMenuScreen.getInstance());
				clear();
				
			}
			if (OverlapTester.pointInRectangle(nextButtonBounds, touchPoint)) {
				
				selectedCar = Math.round(ShowCaseRenderer.rotation2);
				Assets.playSound(Assets.clickSound);
				game.setScreen(SelectAMapScreen.getInstance(game));
				
				
				
			}
		}
			
		
		
		if (pressedBackKey){
			
			pressedBackKey = false;
			Assets.playSound(Assets.clickSound);
			game.setScreen(MainMenuScreen.getInstance());
			clear();
			
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
		batcher.drawSprite(width-PonyMath.getRatio(width, 100), PonyMath.getRatio(width, 100), PonyMath.getRatio(width, 132), PonyMath.getRatio(width, 132), Assets.backButtonRegion);
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		renderer.render(swipe, deltaTime);
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
	
	
	 public static SelectACarScreen getInstance(Game game) {
	        if (instance == null) {
	            instance = new SelectACarScreen(game);
	        }
	        return instance;
	    }
	 
	 public static SelectACarScreen getInstance() {
	        if (instance == null) {
	            instance = new SelectACarScreen(null);
	        }
	        return instance;
	    }
	 public void clear()  
	  {  
	    instance = null;  
	  }  

}
