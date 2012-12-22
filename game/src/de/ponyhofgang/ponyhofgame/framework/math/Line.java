package de.ponyhofgang.ponyhofgame.framework.math;



public class Line {
	
	
	public final Vector2 p1;
	public final Vector2 p2;
	
	
	public final Vector2 position;
	
	public final Vector2 direction;
	public final Vector2 lotDirection;
	
	public float diagonal, angle, width, height;
	

	
	
	public Line(float x, float y, float angle, float width, float height) {
		
		this.width = width;
		this.height = height;
		
		position = new Vector2(x,y);
		direction = new Vector2();
		lotDirection = new Vector2();
		
		this.angle = angle;
		
		direction.set((float)Math.cos(angle *  Vector2.TO_RADIANS), (float)Math.sin(angle * Vector2.TO_RADIANS)).nor();
		lotDirection.set((float)Math.cos(angle + 90 * Vector2.TO_RADIANS), (float)Math.sin(angle +  90 * Vector2.TO_RADIANS)).nor();

        this.p1 = new Vector2();
		this.p2 = new Vector2();
		
		
       p1.set(position.x, position.y).add(direction.x * height/2, direction.y * height/2 ).sub(lotDirection.x * width/2, lotDirection.y * width/2 );
	  	
       p2.set(position.x, position.y).sub(direction.x * height/2, direction.y * height/2 ).sub(lotDirection.x * width/2, lotDirection.y * width/2 );
        
       
	  	
		
		
	}
	
}


