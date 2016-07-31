/**
 * 
 */
package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * @author Axel
 * 
 */
public class Player extends Entity {

	private Polygon shape2;
	private int attackTimer;

	/**
	 * 
	 */
	public Player() {
		// TODO Auto-generated constructor stub
		x = GameInit.getGameInstance().render.getWidth() / 2;
		y = GameInit.getGameInstance().render.getHeight() / 2;

		shape2 = new Polygon();
		for (int i = 0; i < 3; i++)
			shape2.addPoint((int) (11 * Math.cos(i * 2 * Math.PI / 3)), (int) (10 + 5 * Math.sin(i * 2 * Math.PI / 3)));
		shape = new Polygon2D(shape2.xpoints, shape2.ypoints, shape2.npoints);
		shape.translate((int) Math.round(x), (int) Math.round(y));

		attackTimer = 0;
	}

	public void update() {
		checkAndHandleIfOutOfScreen();
		x += Math.cos(Math.toRadians(rotation)) * speed;
		y += Math.sin(Math.toRadians(rotation)) * speed;
		if (attackTimer < 35)
			attackTimer++;

		boolean[] keys = GameInit.getGameInstance().pressedUsefulKeys;
		if (GameInit.getGameInstance().pressedUsefulKeys[0] == true)
			speed = 2;
		else if (speed >= 0.01)
			speed -= 0.01;
		else
			speed = 0;
		if (keys[1]) {
			rotation -= 2;
			if (rotation <= 0)
				rotation = 360;
		} else if (keys[2]) {
			rotation += 2;
			if (rotation >= 360)
				rotation = 0;
		}
		if (keys[3] && attackTimer >= 35) {
			attackTimer = 0;
			Bullet bullet = new Bullet(this);
			bullet.rotation = rotation;
			shape.findCenter();
			bullet.x = shape.xCenter;
			bullet.y = shape.yCenter;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see game.Entity#paint(java.awt.image.BufferedImage)
	 */
	@Override
	public void paint(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.rotate(Math.toRadians(rotation), x + 5, y + 10); // shape.rotate(rotation);
		//shape.findCenter(); g2d.rotate(Math.toRadians(rotation), shape.xCenter, shape.yCenter);
		shape = new Polygon2D(shape2.xpoints, shape2.ypoints, shape2.npoints);
		shape.translate((int) Math.round(x), (int) Math.round(y));
		g2d.setColor(Color.white);
		g2d.fillPolygon(shape);
	}

}
