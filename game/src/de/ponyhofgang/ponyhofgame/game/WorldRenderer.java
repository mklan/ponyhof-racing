package de.ponyhofgang.ponyhofgame.game;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import de.ponyhofgang.ponyhofgame.framework.gl.AmbientLight;
import de.ponyhofgang.ponyhofgame.framework.gl.DirectionalLight;
import de.ponyhofgang.ponyhofgame.framework.gl.LookAtCamera;
import de.ponyhofgang.ponyhofgame.framework.gl.Texture;
import de.ponyhofgang.ponyhofgame.framework.gl.Vertices3;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGraphics;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Car;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Gadget;
import de.ponyhofgang.ponyhofgame.game.gameObjects.OilSpill;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Rocket;

public class WorldRenderer {
	
	GLGraphics glGraphics;
	LookAtCamera camera;
	
	
	AmbientLight ambientLight;
	DirectionalLight directionalLight;
	private Texture texture;
	private Vertices3 model;
	private float boxRotation;
	private float boxTranslation;
	private boolean back;
	
	
	

	
	

	public WorldRenderer(GLGraphics glGraphics) {
		
		
		
		
		
		this.glGraphics = glGraphics;
		camera = new LookAtCamera(67, glGraphics.getWidth()
		/ (float) glGraphics.getHeight(), 0.1f, 100);
		
		
		ambientLight = new AmbientLight();
		ambientLight.setColor(0.2f, 0.2f, 0.2f, 1.0f);
		directionalLight = new DirectionalLight();
		directionalLight.setDirection(-1, -0.5f, 0);
		
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 67,
				glGraphics.getWidth() / (float) glGraphics.getHeight(), 0.1f,
				10.0f);
		
		}
	
	public void render(World world, float deltaTime) {
		GL10 gl = glGraphics.getGL();

		//3rd Person FollowCam
		//camera.getPosition().set(world.car.position.x, 2, world.car.position.y).sub(world.car.direction.x * 3 , 0, world.car.direction.y * 3 );  //followCam
		
		//3rd Person ViewerCam
		camera.getPosition().set(world.myCar.position.x, 5, world.myCar.position.y-5);   
		camera.getLookAt().set(world.myCar.position.x, 0, world.myCar.position.y) ;
		
		//1st Person
		//camera.getPosition().set(world.car.position.x, 0.5f, world.car.position.y).add(world.car.direction.x * 0.5f , 0, world.car.direction.y * 0.5f );   //FirstPersonCam
		//camera.getLookAt().set(world.car.position.x, 0.5f, world.car.position.y).add(world.car.direction.x * 3 , 0, world.car.direction.y * 3 );           //FirstPersonCam
		
		
		camera.setMatrices(gl);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		gl.glEnable(GL10.GL_BLEND); 
				
		ambientLight.enable(gl);
		directionalLight.enable(gl, GL10.GL_LIGHT0);
		
		
	    //--->um Collider zu visualisieren...
		
//		int len = world.rockets.size();
//		for (int i = 0; i < len; i++) {
//			Rocket rocket = world.rockets.get(i);
//			renderCarBounds(gl, rocket);
//			}
		
		//renderCarBounds(gl, world.myCar);

		//<---
		
	

		if (world.car0 != null)renderCar(gl, world.car0, world.car0.carType);
		if (world.car1 != null)renderCar(gl, world.car1, world.car1.carType);
		if (world.car2 != null)renderCar(gl, world.car2, world.car2.carType);
		if (world.car3 != null)renderCar(gl, world.car3, world.car3.carType);
		
		
		//--> GadgetBox Anmiation( abschwingen und drehen
		//Box rotation
		boxRotation = boxRotation += deltaTime * 50;
		if (boxRotation > 360) {
			boxRotation = boxRotation - 360;
		}
		//
		
		//Box swing
		if(!back) boxTranslation = boxTranslation += deltaTime * 0.3f;
		if(back) boxTranslation = boxTranslation -= deltaTime * 0.3f;
		if (boxTranslation > 0.1f) back = true;
		if (boxTranslation < -0.1f) back = false;
		//<---
		
		renderLevelDocks(gl);
		
		renderGadgetBoxes(gl, deltaTime,  world);  
		renderOilSpills(gl, world);
		renderRockets(gl, world);
		
		
	
		
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glDisable(GL10.GL_BLEND);  
		}
	
	
	private void renderOilSpills(GL10 gl, World world) {
		
	 	Assets.gadgetsTexture.bind();
    	Assets.oilSpillModel.bind();
		
		int len = world.oilSpills.size();
		
		
		for (int i = 0; i < len; i++) {
			OilSpill oilspill = world.oilSpills.get(i);
			
			gl.glPushMatrix();
			gl.glTranslatef(oilspill.position.x, 0, oilspill.position.y );
			gl.glRotatef(-oilspill.bounds.angle, 0, 1, 0);
			
			Assets.oilSpillModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.oilSpillModel.getNumVertices());
			
			
			gl.glPopMatrix();
			
			

			}
		
		

	}
	
private void renderRockets(GL10 gl, World world) {
		
	 	Assets.gadgetsTexture.bind();
    	Assets.rocketModel.bind();
		
		int len = world.rockets.size();
		
		
		for (int i = 0; i < len; i++) {
			Rocket rocket = world.rockets.get(i);
			
			gl.glPushMatrix();
			gl.glTranslatef(rocket.position.x, 0, rocket.position.y );
			gl.glRotatef(-rocket.bounds.angle, 0, 1, 0);
			
			Assets.rocketModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.rocketModel.getNumVertices());
			
			
			gl.glPopMatrix();
			
			

			}
		
		

	}
	
	private void renderGadgetBoxes(GL10 gl, float deltaTime, World world) {
		
	 	Assets.gadgetBoxTexture.bind();
    	Assets.gadgetBoxModel.bind();
		
		int len = world.gadgets.size();
		for (int i = 0; i < len; i++) {
			Gadget gadgetBox = world.gadgets.get(i);
			
			if(!gadgetBox.active) continue;
	   
			
	    	gl.glPushMatrix();
			gl.glTranslatef(gadgetBox.position.x, boxTranslation, gadgetBox.position.y );
			gl.glRotatef(-boxRotation, 0, 1, 0);
			
			Assets.gadgetBoxModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.gadgetBoxModel.getNumVertices());
			
			
			gl.glPopMatrix();
			
			

			}
		
		
		Assets.gadgetBoxModel.unbind();
		
		
		Assets.gadgetBoxShadowTexture.bind();
    	Assets.gadgetBoxShadowModel.bind();
		
		len = world.gadgets.size();
		for (int i = 0; i < len; i++) {
			Gadget gadgetBox = world.gadgets.get(i);
			
			if(!gadgetBox.active) continue;
	   
			
	    	gl.glPushMatrix();
			gl.glTranslatef(gadgetBox.position.x, 0.01f, gadgetBox.position.y );
			gl.glRotatef(-boxRotation, 0, 1, 0);
			
			Assets.gadgetBoxShadowModel.draw(GL10.GL_TRIANGLES, 0,
					Assets.gadgetBoxShadowModel.getNumVertices());
			
			
			gl.glPopMatrix();
			
			

			}
		
		
		Assets.gadgetBoxShadowModel.unbind();
	}

	private void renderCarBounds(GL10 gl, Rocket car) {
		
		gl.glColor4f(1, 0, 0, 1f);
		Assets.boundsBallModel.bind();
		
		gl.glPushMatrix();
		gl.glTranslatef(car.bounds.p1.x, 0, car.bounds.p1.y);
		Assets.boundsBallModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.boundsBallModel.getNumVertices());
		
		gl.glPopMatrix();
		
		
		gl.glPushMatrix();
		gl.glTranslatef(car.bounds.p2.x, 0, car.bounds.p2.y);
		Assets.boundsBallModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.boundsBallModel.getNumVertices());
		
        gl.glPopMatrix();
		
		
		gl.glPushMatrix();
		
		
		gl.glTranslatef(car.bounds.p3.x, 0, car.bounds.p3.y);
		Assets.boundsBallModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.boundsBallModel.getNumVertices());
		
        gl.glPopMatrix();
		
		
		gl.glPushMatrix();
		
		gl.glTranslatef(car.bounds.p4.x, 0, car.bounds.p4.y);
		Assets.boundsBallModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.boundsBallModel.getNumVertices());
		
		gl.glPopMatrix();
		Assets.boundsBallModel.unbind();
		gl.glColor4f(1, 1, 1, 1f);
	}
	
	
	
	
	
private void renderCar(GL10 gl, Car car, int carType) {
	
	
	  switch(carType){
	  
	  case CarSpecs.ECTOMOBILE:
		  
			
	    	texture = Assets.ectoMobileTexture;
	    	model = Assets.ectoMobileModel;
	    	break;
	    	
	  case CarSpecs.BATMOBILE:
		  
			
	    	texture = Assets.batMobileTexture;
	    	model = Assets.batMobileModel;
	    	break;
	    	
	  case CarSpecs.MYSTERYMACHINE:
		  
			
	    	texture = Assets.mysteryMachineTexture;
	    	model = Assets.mysteryMachineModel;
	    	break;
	    	
	  case CarSpecs.PODRACER:
		  
			
	    	texture = Assets.podRacerTexture;
	    	model = Assets.podRacerModel;
	    	break;
	  
	  
	  }
	    

	  
	
		texture.bind();
		model.bind();
		
		gl.glPushMatrix();
		
		
	    gl.glTranslatef(car.position.x, 0, car.position.y);
	    
		
		gl.glRotatef(-car.pitch, 0, 1, 0);
		
		//gl.glTranslatef(0, 0, car.bounds.height/3.0f);   //pivot nach vorne Schieben
		
		
		model.draw(GL10.GL_TRIANGLES, 0,
				model.getNumVertices());
		gl.glPopMatrix();
		
		
		
		model.unbind();
		
		
	}


private void renderLevelDocks(GL10 gl){
	

	
	Assets.groundLevelDocksTexture.bind();
	Assets.groundLevelDocksModel.bind();
	gl.glPushMatrix();
	Assets.groundLevelDocksModel.draw(GL10.GL_TRIANGLES, 0,
			Assets.groundLevelDocksModel.getNumVertices());
	gl.glPopMatrix();
	Assets.groundLevelDocksModel.unbind();
	
	gl.glDisable(GL10.GL_DEPTH_TEST);
	Assets.shadowsLevelDocksTexture.bind();
	Assets.groundLevelDocksModel.bind();
    gl.glPushMatrix();
    gl.glTranslatef(-0.04f, 0, 0);
     Assets.groundLevelDocksModel.draw(GL10.GL_TRIANGLES, 0,
			Assets.groundLevelDocksModel.getNumVertices());
	gl.glPopMatrix();
    Assets.groundLevelDocksModel.unbind();
    gl.glEnable(GL10.GL_DEPTH_TEST);
	Assets.levelDocksTexture.bind();
	Assets.levelDocksModel.bind();
	gl.glPushMatrix();
	Assets.levelDocksModel.draw(GL10.GL_TRIANGLES, 0,
			Assets.levelDocksModel.getNumVertices());
	gl.glPopMatrix();
	Assets.levelDocksModel.unbind();
	
    Assets.craneTexture.bind();
	Assets.craneModel.bind();
	gl.glPushMatrix();
	
	gl.glTranslatef(12.877f, 0.91f, -1.487f);
	
	Assets.craneModel.draw(GL10.GL_TRIANGLES, 0,
			Assets.craneModel.getNumVertices());
	gl.glPopMatrix();
	
	Assets.craneModel.unbind();
	
	
}
	


	
	
	
	
	
}

