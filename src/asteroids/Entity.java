/**
 * 
 */
package asteroids;

import java.awt.Graphics2D;

/**
 * @author Axel
 * 
 */
public abstract class Entity {
	public static Entity[] entities;
	protected double x, y, rotation, speed;
	protected Polygon2D shape;

	protected Entity() {
		if(entities == null) entities = new Entity[25];
		int i;
		for (i = 0; entities[i] != null; i++) {}
		if (i == entities.length - 1) {
			Entity[] tmp = new Entity[entities.length * 2];
			System.arraycopy(entities, 0, tmp, 0, entities.length);
			entities = tmp;
		}
		entities[i] = this;
	}

	public abstract void update();

	public abstract void paint(Graphics2D g2d);

	protected void checkAndHandleIfOutOfScreen() {
		Render render = GameInit.getGameInstance().render;
		if (x + shape.getBounds().width < 0)
			x = render.getWidth();
		else if (x > render.getWidth())
			x = 0;
		if (y + shape.getBounds().height < 0)
			y = render.getHeight();
		else if (y > render.getHeight())
			y = 0;
	}

	public static void delete(int i) {
		entities[i] = null;
		for (; ++i < entities.length;)
			entities[i - 1] = entities[i];
	}

	public Polygon2D getShape() {
		return shape;
	}
}
