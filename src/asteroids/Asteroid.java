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
public class Asteroid extends Entity {

	private Polygon shape2;
	private int size;

	public Asteroid() {
		this(getRandom().nextInt(3));
		if (Math.random() < 0.5) {
			x = -shape2.getBounds().width / 2;
			if (Math.random() < 0.5)
				x = shape2.getBounds().width / 2;
			y = Math.random() * shape2.getBounds().height;
		} else {
			x = Math.random() * shape2.getBounds().width;
			y = -shape2.getBounds().height / 2;
			if (Math.random() < 0.5)
				y = shape2.getBounds().height / 2;
		}
	}

	/**
	* 
	*/
	private Asteroid(int size) {
		// TODO Auto-generated constructor stub
		Random random = getRandom();
		shape2 = new Polygon();
		int minSize = 6 + (3 * size);
		int maxSize = 12 + (3 * size);
		int s = minSize + (int) (Math.random() * (maxSize - minSize));
		for (int i = 0; i < s; i++) {
			double theta = 2 * Math.PI / s * i;
			double r = maxSize + (int) (Math.random() * (maxSize - minSize));
			int pointX = (int) -Math.round(r * Math.sin(theta));
			int pointY = (int) Math.round(r * Math.cos(theta));
			shape2.addPoint(pointX, pointY);
		}
		shape = new Polygon2D(shape2.xpoints, shape2.ypoints, shape2.npoints);

		shape.translate((int) Math.round(x), (int) Math.round(y));
		this.size = size;
		rotation = random.nextInt(360);
		speed = random.nextInt(1) + 1;
	}

	public void update() {
		checkAndHandleIfOutOfScreen();
		x += Math.cos(Math.toRadians(rotation)) * speed;
		y += Math.sin(Math.toRadians(rotation)) * speed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see game.Entity#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics2D g2d) {
		g2d.rotate(rotation, x, y);
		// g.translate(100, 100);
		shape = new Polygon2D(shape2.xpoints, shape2.ypoints, shape2.npoints);
		shape.translate((int) Math.floor(x), (int) Math.floor(y));
		g2d.setColor(Color.white);
		g2d.drawPolygon(shape);
	}

	public int destroy() {
		switch (size) {
		case 0:
			break;

		case 1:
			if (Math.random() < 0.5) {
				Asteroid asteroid = new Asteroid(0);
				asteroid.x = this.x;
				asteroid.y = this.y;
			}
			break;

		case 2:
			for (int i = 0; i < getRandom().nextInt(2) + 1; i++) {
				Asteroid asteroid = new Asteroid(getRandom().nextInt(1));
				asteroid.x = this.x;
				asteroid.y = this.y;
			}
			break;

		default:
			break;
		}
		return size;
	}

	private static Random getRandom() {
		return new Random();
	}

}
