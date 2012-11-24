package de.ponyhofgang.ponyhofgame.framework.test;

import android.opengl.Matrix;
import de.ponyhofgang.ponyhofgame.framework.DynamicGameObject3D;
import de.ponyhofgang.ponyhofgame.framework.math.Vector3;


public class Gengar extends DynamicGameObject3D {
	

	public static final int GENGAR_VELOCITY = 30;
	public boolean finished = false;
	public float currentAngle = 0;
	
	public float fTransX;
	public float fTransZ;
	
	float pitch = 0;

	public Gengar(float x, float y, float z, float width, float height, float depth) {
		super(x, y, z, width, height, depth);
		// TODO Auto-generated constructor stub
	}

	
	public void update(float deltaTime, float accel, float angle) {
		
		velocity.set(accel / 10 * GENGAR_VELOCITY, 0, 0);
		rotate(angle);
		
		if (accel < 0)
		position.add((float) Math.sin(pitch * Math.PI/180)/5, 0, (float) Math.cos(pitch * Math.PI/180)/5);
		if (accel > 0)
		position.sub((float) Math.sin(pitch * Math.PI/180)/5, 0, (float) Math.cos(pitch * Math.PI/180)/5);
		
		//position.add(velocity.x * deltaTime, 0, 0);
		
		bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2, bounds.depth / 2 );
		bounds.lowerLeft.rotate(pitch, 0 , 1, 0 );
	    
	
		
		//stateTime += deltaTime;
		}
	
	final float[] matrix = new float[16];
	final float[] inVec = { 0, 0, -1, 1 };
	final float[] outVec = new float[4];
	final Vector3 direction = new Vector3();

	
	public Vector3 getDirection() {
		Matrix.setIdentityM(matrix, 0);
		
		Matrix.rotateM(matrix, 0, pitch, 1, 0, 0);
		Matrix.multiplyMV(outVec, 0, matrix, 0, inVec, 0);
		direction.set(outVec[0], outVec[1], outVec[2]);
		return direction;
	}
	
	
	public void rotate(float pitchInc) {
		
		this.pitch += pitchInc;
		if (pitch > 360)
			pitch = pitch - 360;
		if (pitch < -360)
			pitch = pitch + 360;
	}
	
	
	

}
