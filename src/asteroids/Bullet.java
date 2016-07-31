package asteroids;

import java.awt.Color;
import java.awt.Graphics2D;

import asteroids.Entity;

public class Bullet extends Entity {

	private Entity owner;
	private int totalLifeTime;

	public Bullet(Entity owner) {
		// TODO Auto-generated constructor stub
		this.owner = owner;
		totalLifeTime = 0;
		shape = new Polygon2D(new int[] { 0, 0, 1, 1 }, new int[] { 0, 1, 0, 1 }, 4); // Just
		// a point
		// shape = new Polygon2D(new int[] { 0 }, new int[] { 0 }, 1);
		speed = 3;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		checkAndHandleIfOutOfScreen();
		x += Math.cos(Math.toRadians(rotation)) * speed;
		y += Math.sin(Math.toRadians(rotation)) * speed;
		shape = new Polygon2D(new int[] { 0, 0, 1, 1 }, new int[] { 0, 1, 0, 1 }, 4); // Just
		shape.translate((int) Math.round(x), (int) Math.round(y));
		if (++totalLifeTime >= 250) {
			int i;
			for (i = 0; i <= Entity.entities.length && Entity.entities[i] != this; i++) {}
			Entity.delete(i); // Entity.entities[i] = null;
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.setColor(Color.white);
		g2d.drawPolygon(shape); // g2d.drawRect(((int) Math.round(x)), ((int) Math.round(y)), 0, 0);
	}

	public Entity getOwner() {
		return owner;
	}

}
