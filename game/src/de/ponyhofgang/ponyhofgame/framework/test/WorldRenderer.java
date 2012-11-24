package de.ponyhofgang.ponyhofgame.framework.test;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import de.ponyhofgang.ponyhofgame.framework.gl.AmbientLight;
import de.ponyhofgang.ponyhofgame.framework.gl.DirectionalLight;
import de.ponyhofgang.ponyhofgame.framework.gl.LookAtCamera;
import de.ponyhofgang.ponyhofgame.framework.gl.Vertices3;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGraphics;

public class WorldRenderer {
	
	GLGraphics glGraphics;
	LookAtCamera camera;
	
	
	AmbientLight ambientLight;
	DirectionalLight directionalLight;
	Vertices3 cube;
	
	float translate = 0;
	
	float fTransX = 0;
	float fTransZ = 0;
	

	
	

	public WorldRenderer(GLGraphics glGraphics) {
		
		
		
		this.glGraphics = glGraphics;
		camera = new LookAtCamera(67, glGraphics.getWidth()
		/ (float) glGraphics.getHeight(), 0.1f, 100);
		camera.getPosition().set(0, 12, 4);
		camera.getLookAt().set(0, 0, 0);
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
		camera.getPosition().x = world.gengar2.position.x;
		camera.getPosition().z = world.gengar2.position.z-4;
		camera.getLookAt().x = world.gengar2.position.x;
		camera.getLookAt().z = world.gengar2.position.z;
		camera.setMatrices(gl);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		ambientLight.enable(gl);
		directionalLight.enable(gl, GL10.GL_LIGHT0);
		
	
		renderGengar(gl, world.gengar);
		renderGengar2(gl, world.gengar2);
		
	
		
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		}
	
	
	private void renderGengar(GL10 gl, GengarStatic gengar) {
	
		
		Assets.items.bind();
		
		Assets.gengarModel.bind();
		gl.glPushMatrix();
		
		
		gl.glScalef(5,5,5);
		Assets.gengarModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.gengarModel.getNumVertices());
		gl.glPopMatrix();
		
		Assets.gengarModel.unbind();
		
		
	}
	
private void renderGengar2(GL10 gl, Gengar gengar) {
	
		
		Assets.gengarTexture.bind();
		
		Assets.gengarModel.bind();
		gl.glPushMatrix();
		
		
		gl.glTranslatef(gengar.position.x, 0, gengar.position.z);
		
		gl.glRotatef(gengar.pitch, 0, 1, 0);
		
		gl.glScalef(5,5,5);
		Assets.gengarModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.gengarModel.getNumVertices());
		gl.glPopMatrix();
		
		Assets.gengarModel.unbind();
		
		
	}
	


	
	
	
	
	
}

