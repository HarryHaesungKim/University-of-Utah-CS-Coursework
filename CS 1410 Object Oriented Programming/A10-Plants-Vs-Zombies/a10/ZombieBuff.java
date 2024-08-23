package a10;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class ZombieBuff extends Zombie{	
	
	private boolean isColliding;
	
	public ZombieBuff(Double startingPosition, Double initHitbox, BufferedImage img, int health, int coolDown, double speed,
			int attackDamage) {
		super(startingPosition, initHitbox, img, health, coolDown, speed, attackDamage);
		isColliding = false;
	}
	
	@Override
	public void move() {
		if (!isColliding || isColliding)
			shiftPosition(new Point2D.Double(getSpeed(), 0));
	}
}