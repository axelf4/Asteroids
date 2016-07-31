package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Random;

public class UFO extends Entity {

	private Polygon shape2;
	private int attackTimer;

	public UFO() {
		// TODO Auto-generated constructor stub
		shape2 = new Polygon();
		shape2.addPoint(0, 5);
		shape2.addPoint(8, 0);
		shape2.addPoint(16, 5);
		shape2.addPoint(8, 10);

		shape = new Polygon2D(shape2.xpoints, shape2.ypoints, shape2.npoints);
		shape.translate((int) Math.round(x), (int) Math.round(y));

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

		x = 100;
		y = 100;

		attackTimer = 0;
		speed = 1;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		checkAndHandleIfOutOfScreen();
		x += Math.cos(Math.toRadians(rotation)) * speed;
		y += Math.sin(Math.toRadians(rotation)) * speed;
		Random random = new Random();
		rotation += random.nextInt(10) - random.nextInt(10); // Random movement

		if (attackTimer < 35)
			attackTimer++;
		else {
			attackTimer = 0;
			Bullet bullet = new Bullet(this);
			shape.findCenter();
			bullet.x = shape.xCenter;
			bullet.y = shape.yCenter;
			if (Math.random() > 0.50)
				bullet.rotation = rotation;
			else
				bullet.rotation = Math.toDegrees(Math.atan2((Entity.entities[0].y - bullet.y), (Entity.entities[0].x - bullet.x)));
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		// TODO Auto-generated method stub
		shape = new Polygon2D(shape2.xpoints, shape2.ypoints, shape2.npoints);
		shape.translate((int) Math.round(x), (int) Math.round(y));
		g2d.setColor(Color.white);
		g2d.drawPolygon(shape);
	}

}
