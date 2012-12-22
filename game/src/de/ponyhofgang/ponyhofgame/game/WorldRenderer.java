package de.ponyhofgang.ponyhofgame.game;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import de.ponyhofgang.ponyhofgame.framework.gl.AmbientLight;
import de.ponyhofgang.ponyhofgame.framework.gl.DirectionalLight;
import de.ponyhofgang.ponyhofgame.framework.gl.LookAtCamera;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGraphics;
import de.ponyhofgang.ponyhofgame.framework.math.LineRectangle;
import de.ponyhofgang.ponyhofgame.game.gameObjects.Car;

public class WorldRenderer {
	
	GLGraphics glGraphics;
	LookAtCamera camera;
	
	
	AmbientLight ambientLight;
	DirectionalLight directionalLight;
	
	
	

	
	

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

		
		//camera.getPosition().set(world.car.position.x, 2, world.car.position.y).sub(world.car.direction.x * 3 , 0, world.car.direction.y * 3 );  //followCam
		camera.getPosition().set(world.myCar.position.x, 5, world.myCar.position.y-5);   
		camera.getLookAt().set(world.myCar.position.x, 0, world.myCar.position.y) ;
		
		
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
		
		
	//um Collider zu visualisieren...
		
//		int len = world.colliders.size();
//		for (int i = 0; i < len; i++) {
//			LineRectangle collider = world.colliders.get(i);
//			renderGengarBounds(gl, collider);
//			}

		
		//renderCarBounds(gl, world.myCar);
	

		if (world.car0 != null)renderCar(gl, world.car0);
		if (world.car1 != null)renderCar(gl, world.car1);
		if (world.car2 != null)renderCar(gl, world.car2);
		if (world.car3 != null)renderCar(gl, world.car3);
		
		        
		
		renderLevelDocks(gl);
	
		
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glDisable(GL10.GL_BLEND);  
		}
	
	
	private void renderCarBounds(GL10 gl, Car gengar) {
		
		gl.glColor4f(1, 0, 0, 1f);
		Assets.boundsBallModel.bind();
		
		gl.glPushMatrix();
		gl.glTranslatef(gengar.bounds.p1.x, 0, gengar.bounds.p1.y);
		Assets.boundsBallModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.boundsBallModel.getNumVertices());
		
		gl.glPopMatrix();
		
		
		gl.glPushMatrix();
		gl.glTranslatef(gengar.bounds.p2.x, 0, gengar.bounds.p2.y);
		Assets.boundsBallModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.boundsBallModel.getNumVertices());
		
        gl.glPopMatrix();
		
		
		gl.glPushMatrix();
		
		
		gl.glTranslatef(gengar.bounds.p3.x, 0, gengar.bounds.p3.y);
		Assets.boundsBallModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.boundsBallModel.getNumVertices());
		
        gl.glPopMatrix();
		
		
		gl.glPushMatrix();
		
		gl.glTranslatef(gengar.bounds.p4.x, 0, gengar.bounds.p4.y);
		Assets.boundsBallModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.boundsBallModel.getNumVertices());
		
		gl.glPopMatrix();
		Assets.boundsBallModel.unbind();
		gl.glColor4f(1, 1, 1, 1f);
	}
	
	
	
	
	
private void renderCar(GL10 gl, Car car) {
	
		
		Assets.carTexture.bind();
		
		Assets.carModel.bind();
		
		gl.glPushMatrix();
		
		
	    gl.glTranslatef(car.position.x, 0, car.position.y);
	    
		
		gl.glRotatef(-car.pitch, 0, 1, 0);
		
		//gl.glTranslatef(0, 0, car.bounds.height/3.0f);   //pivot nach vorne Schieben
		
		
		Assets.carModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.carModel.getNumVertices());
		gl.glPopMatrix();
		
		
		
		Assets.carModel.unbind();
		
		
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

