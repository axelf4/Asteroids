/**
 * 
 */
package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Random;

/**
 * @author Axel
 *
 */
public class Explosion extends Entity {
	
	private Polygon shape2;
	private int totalLifeTime;

	/**
	 * 
	 */
	public Explosion() {
		// TODO Auto-generated constructor stub
		shape2 = new Polygon(new int[] {0, 15, 15, 0}, new int[] {0, 0, 1, 1}, 4);
		speed = 3;
	}

	/* (non-Javadoc)
	 * @see asteroids.Entity#paint(java.awt.Graphics2D)
	 */
	@Override
	public void paint(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.rotate(Math.toRadians(rotation), x, y);
		shape = new Polygon2D(shape2.xpoints, shape2.ypoints, shape2.npoints);
		shape.translate((int) Math.round(x), (int) Math.round(y));
		g2d.setColor(Color.getHSBColor((float) (totalLifeTime * 0.1), 1.0F, 1.0F));
		g2d.fillPolygon(shape);
	}

	/* (non-Javadoc)
	 * @see asteroids.Entity#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		//checkAndHandleIfOutOfScreen();
		x += Math.cos(Math.toRadians(rotation)) * speed;
		y += Math.sin(Math.toRadians(rotation)) * speed;
		if (++totalLifeTime >= 250) {
			int i;
			for (i = 0; i <= Entity.entities.length && Entity.entities[i] != this; i++) {}
			Entity.delete(i);
		}
	}
	
	public static void explode(Entity entity) {
		Random random = new Random();
		for(int i = 0; i < random.nextInt(4) + 3; i++) {
			Explosion explosion = new Explosion();
			explosion.rotation = random.nextInt(360);
			entity.shape.findCenter();
			explosion.x = entity.shape.xCenter;
			explosion.y = entity.shape.yCenter;
		}
	}

}
