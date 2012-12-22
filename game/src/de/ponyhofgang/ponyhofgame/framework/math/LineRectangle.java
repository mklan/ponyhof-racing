package de.ponyhofgang.ponyhofgame.framework.math;



public class LineRectangle {
	
	
	public final Vector2 p1;
	public final Vector2 p2;
	public final Vector2 p3;
	public final Vector2 p4;
	
	public final Vector2 position;
	
	public final Vector2 direction;
	public final Vector2 lotDirection;
	
	public float diagonal, angle, width, length;
	

	
	
	public LineRectangle(float x, float y, float angle, float width, float length) {
		
		this.width = width;
		this.length = length;
		
		position = new Vector2(x,y);
		direction = new Vector2();
		lotDirection = new Vector2();
		
		this.angle = angle;
		
		direction.set((float)Math.cos(angle *  Vector2.TO_RADIANS), (float)Math.sin(angle * Vector2.TO_RADIANS)).nor();
		lotDirection.set((float)Math.cos((angle + 90) * Vector2.TO_RADIANS), (float)Math.sin((angle +  90) * Vector2.TO_RADIANS)).nor();

        this.p1 = new Vector2();
		this.p2 = new Vector2();
		this.p3 = new Vector2();
	    this.p4 = new Vector2();
		
       p1.set(position.x, position.y).add(direction.x * length/2, direction.y * length/2 ).sub(lotDirection.x * width/2, lotDirection.y * width/2 );
	  	
       p2.set(position.x, position.y).sub(direction.x * length/2, direction.y * length/2 ).sub(lotDirection.x * width/2, lotDirection.y * width/2 );
        
       p3.set(position.x, position.y).sub(direction.x * length/2, direction.y * length/2 ).add(lotDirection.x * width/2, lotDirection.y * width/2 );
        
       p4.set(position.x, position.y).add(direction.x * length/2, direction.y * length/2 ).add(lotDirection.x * width/2, lotDirection.y * width/2 );
	  	
	
		
		
	}
	
}


