package a10;

import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

public class PlantBuff extends Plant {
	
	public PlantBuff(Double startingPosition, Double initHitbox, BufferedImage img, int health, int coolDown,
			int attackDamage) {
		super(startingPosition, initHitbox, img, health, coolDown, attackDamage);
	}
	
	/**
	 * An attack means the two hotboxes are overlapping and the
	 * Actor is ready to attack again (based on its cooldown).
	 * 
	 * Plants only attack Zombies.
	 * 
	 * @param other
	 */
	@Override
	public void attack(Actor other) {
		if (other instanceof Zombie) {
			super.attack(other);
			super.slowDown(other);
		}
	}

}
