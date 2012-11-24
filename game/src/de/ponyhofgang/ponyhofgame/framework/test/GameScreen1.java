package de.ponyhofgang.ponyhofgame.framework.test;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

import de.ponyhofgang.ponyhofgame.framework.Game;
import de.ponyhofgang.ponyhofgame.framework.Input.TouchEvent;
import de.ponyhofgang.ponyhofgame.framework.gl.AmbientLight;
import de.ponyhofgang.ponyhofgame.framework.gl.Camera2D;
import de.ponyhofgang.ponyhofgame.framework.gl.DirectionalLight;
import de.ponyhofgang.ponyhofgame.framework.gl.Material;
import de.ponyhofgang.ponyhofgame.framework.gl.PointLight;
import de.ponyhofgang.ponyhofgame.framework.gl.Texture;
import de.ponyhofgang.ponyhofgame.framework.gl.Vertices3;
import de.ponyhofgang.ponyhofgame.framework.impl.GLScreen;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;

class GameScreen1 extends GLScreen {
	Vertices3 cube;
	Texture texture;
	Camera2D guiCam;
	float angle = 0;
	float translate = -3;
	Vector2 touchPoint;
	AmbientLight ambientLight;
	DirectionalLight directionalLight;
	PointLight pointLight;
	Material material;

	public GameScreen1(Game game) {
		super(game);
		cube = createCube();
	
		texture = new Texture(glGame, "background.jpg");
		guiCam = new Camera2D(glGraphics, 1280, 720);
		touchPoint = new Vector2();

		ambientLight = new AmbientLight();
		ambientLight.setColor(0, 0.2f, 0, 1);
		pointLight = new PointLight();
		pointLight.setDiffuse(1, 0, 0, 1);
		pointLight.setPosition(3, 3, 0);
		directionalLight = new DirectionalLight();
		directionalLight.setDiffuse(0, 0, 1, 1);
		directionalLight.setDirection(1, 0, 0);
		material = new Material();

	}

	private Vertices3 createCube() {

		float[] vertices = { -0.5f, -0.5f, 0.5f, 0, 1, 0, 0, 1, 0.5f, -0.5f,
				0.5f, 1, 1, 0, 0, 1, 0.5f, 0.5f, 0.5f, 1, 0, 0, 0, 1, -0.5f,
				0.5f, 0.5f, 0, 0, 0, 0, 1, 0.5f, -0.5f, 0.5f, 0, 1, 1, 0, 0,
				0.5f, -0.5f, -0.5f, 1, 1, 1, 0, 0, 0.5f, 0.5f, -0.5f, 1, 0, 1,
				0, 0, 0.5f, 0.5f, 0.5f, 0, 0, 1, 0, 0, 0.5f, -0.5f, -0.5f, 0,
				1, 0, 0, -1, -0.5f, -0.5f, -0.5f, 1, 1, 0, 0, -1, -0.5f, 0.5f,
				-0.5f, 1, 0, 0, 0, -1, 0.5f, 0.5f, -0.5f, 0, 0, 0, 0, -1,
				-0.5f, -0.5f, -0.5f, 0, 1, -1, 0, 0, -0.5f, -0.5f, 0.5f, 1, 1,
				-1, 0, 0, -0.5f, 0.5f, 0.5f, 1, 0, -1, 0, 0, -0.5f, 0.5f,
				-0.5f, 0, 0, -1, 0, 0, -0.5f, 0.5f, 0.5f, 0, 1, 0, 1, 0, 0.5f,
				0.5f, 0.5f, 1, 1, 0, 1, 0, 0.5f, 0.5f, -0.5f, 1, 0, 0, 1, 0,
				-0.5f, 0.5f, -0.5f, 0, 0, 0, 1, 0, -0.5f, -0.5f, -0.5f, 0, 1,
				0, -1, 0, 0.5f, -0.5f, -0.5f, 1, 1, 0, -1, 0, 0.5f, -0.5f,
				0.5f, 1, 0, 0, -1, 0, -0.5f, -0.5f, 0.5f, 0, 0, 0, -1, 0 };
		short[] indices = { 0, 1, 2, 2, 3, 0, 4, 5, 6, 6, 7, 4, 8, 9, 10, 10,
				11, 8, 12, 13, 14, 14, 15, 12, 16, 17, 18, 18, 19, 16, 20, 21,
				22, 22, 23, 20, 24, 25, 26, 26, 27, 24 };
		Vertices3 cube = new Vertices3(glGraphics, vertices.length / 8,
				indices.length, false, true, true);
		cube.setVertices(vertices, 0, vertices.length);
		cube.setIndices(indices, 0, indices.length);
		return cube;
	}

	@Override
	public void resume() {
		texture.reload();
	}

	@Override
	public void update(float deltaTime) {
		angle += 45 * deltaTime;

		List<TouchEvent> events = game.getInput().getTouchEvents();
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			if (event.type != TouchEvent.TOUCH_UP)
				continue;
			guiCam.touchToWorld(touchPoint.set(event.x, event.y));

			translate += 3 * deltaTime;

		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glViewport(0, 0, glGraphics.getWidth(), glGraphics.getHeight());
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 67,
				glGraphics.getWidth() / (float) glGraphics.getHeight(), 0.1f,
				10.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, 3, 3, 0, 0, 0, -5, 0, 1, 0);

		gl.glEnable(GL10.GL_LIGHTING);
		ambientLight.enable(gl);
		pointLight.enable(gl, GL10.GL_LIGHT0);
		directionalLight.enable(gl, GL10.GL_LIGHT1);
		material.enable(gl);

		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		texture.bind();
		cube.bind();
		gl.glTranslatef(0, 0, translate);
		gl.glRotatef(angle, 0, 1, 0);
		cube.draw(GL10.GL_TRIANGLES, 0, 36);
		cube.unbind();
		pointLight.disable(gl);
		directionalLight.disable(gl);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_DEPTH_TEST);
	}

	@Override
	public void pause() {
	}

	@Override
	public void dispose() {
	}
}
