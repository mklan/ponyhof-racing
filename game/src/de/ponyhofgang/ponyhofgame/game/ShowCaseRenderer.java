package de.ponyhofgang.ponyhofgame.game;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import de.ponyhofgang.ponyhofgame.framework.gl.AmbientLight;
import de.ponyhofgang.ponyhofgame.framework.gl.DirectionalLight;
import de.ponyhofgang.ponyhofgame.framework.gl.LookAtCamera;
import de.ponyhofgang.ponyhofgame.framework.impl.GLGraphics;
import de.ponyhofgang.ponyhofgame.framework.math.Vector2;

public class ShowCaseRenderer {

	GLGraphics glGraphics;
	LookAtCamera camera;

	AmbientLight ambientLight;
	DirectionalLight directionalLight;
	public float rotation = 0;
	public static float rotation2 = 0;

	private Vector2 camDirection;

	public static final int SWIPE_LEFT = -1;
	public static final int SWIPE_RIGHT = 1;

	public ShowCaseRenderer(GLGraphics glGraphics) {

		camDirection = new Vector2();

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

	public void render(int swipe, float deltaTime) {

		GL10 gl = glGraphics.getGL();

		camDirection.set((float) Math.cos(rotation2 * Vector2.TO_RADIANS),
				(float) Math.sin(rotation2 * Vector2.TO_RADIANS)).nor();

		camera.getPosition().set(0, 0, 0)
				.sub(camDirection.x * 2.5f, -0.4f, camDirection.y * 2.5f);
		
		camera.getLookAt().set(0, 0, 0);

		camera.setMatrices(gl);

		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_COLOR_MATERIAL);
		gl.glEnable(GL10.GL_BLEND);

		ambientLight.enable(gl);
		directionalLight.enable(gl, GL10.GL_LIGHT0);

		if (swipe == SWIPE_LEFT && rotation2 % 90 != 0) {

			rotation2 = rotation2 -= 1.5f;

		}

		if (swipe == SWIPE_RIGHT && rotation2 % 90 != 0) {

			rotation2 = rotation2 += 1.5f;

		}

		if (rotation2 == 360)
			rotation2 = 0;
		if (rotation2 < 0)
			rotation2 = rotation2 + 360;

		rotation = rotation += deltaTime * 25;
		if (rotation > 360) {
			rotation = rotation - 360;
		}
		renderShowCase(gl);
		


		gl.glDisable(GL10.GL_TEXTURE_2D);

		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glDisable(GL10.GL_BLEND);
	}

	private void renderShowCase(GL10 gl) {

		Assets.ectoMobileTexture.bind();
		Assets.ectoMobileModel.bind();

		gl.glPushMatrix();

		gl.glTranslatef(0, 0.01f, -1.5f);

		gl.glRotatef(-rotation, 0, 1, 0);

		Assets.ectoMobileModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.ectoMobileModel.getNumVertices());
		gl.glPopMatrix();

		Assets.ectoMobileModel.unbind();

		Assets.batMobileTexture.bind();
		Assets.batMobileModel.bind();

		gl.glPushMatrix();

		gl.glTranslatef(0, 0.01f, 1.5f);

		gl.glRotatef(-rotation, 0, 1, 0);

		Assets.batMobileModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.batMobileModel.getNumVertices());
		gl.glPopMatrix();

		Assets.batMobileModel.unbind();

		Assets.mysteryMachineTexture.bind();
		Assets.mysteryMachineModel.bind();

		gl.glPushMatrix();

		gl.glTranslatef(-1.5f, 0.01f, 0);

		gl.glRotatef(-rotation, 0, 1, 0);

		Assets.mysteryMachineModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.mysteryMachineModel.getNumVertices());
		gl.glPopMatrix();

		Assets.mysteryMachineModel.unbind();

		Assets.podRacerTexture.bind();
		Assets.podRacerModel.bind();

		gl.glPushMatrix();

		gl.glTranslatef(1.5f, 0.01f, 0);

		gl.glRotatef(-rotation, 0, 1, 0);

		Assets.podRacerModel.draw(GL10.GL_TRIANGLES, 0,
				Assets.podRacerModel.getNumVertices());
		gl.glPopMatrix();

		Assets.podRacerModel.unbind();

	}

}
