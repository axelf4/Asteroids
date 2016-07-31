package asteroids;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Random;

import javax.swing.JPanel;

public class Render extends JPanel {

	private ImageObserver imageObserver;
	private BufferedImage bufferedImage;
	public AffineTransform affineTransform;

	Font scoreFont;
	private Point[] stars;

	public Render() {
		setSize(800, 600);
		bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		affineTransform = g2d.getTransform();
		imageObserver = new ImageObserver() {

			public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		scoreFont = new Font("Helvetica", Font.BOLD, 12);
		Random random = new Random();
		stars = new Point[20];
		for (int i = 0; i < stars.length; i++)
			stars[i] = new Point(random.nextInt(getWidth()), random.nextInt(getHeight()));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		bufferedImage = (BufferedImage) createImage(getWidth(), getHeight());
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setColor(Color.white);
		g2d.drawString(new StringBuilder().append(GameInit.getGameInstance().getScore() * 25).toString(), 20, 20); // Draw
																													// score																													// in
																													// top-left
																													// corner
		if (!GameInit.getGameInstance().isPlaying) {
			FontMetrics fontMetrics = g2d.getFontMetrics();
			String string = "Game Over! Press any key to restart";
			g2d.drawString(string, getWidth() / 2 - (fontMetrics.stringWidth(string) / 2), getHeight() / 2);
		}
		// g2d.setColor(getForeground());
		for (int j = 0; j < stars.length; j++)
			g2d.drawRect(stars[j].x, stars[j].y, 1, 1);
		if(Entity.entities != null)
		for (int i = 0; i <= Entity.entities.length && Entity.entities[i] != null; i++) {
			g2d.setTransform(affineTransform);
			Entity.entities[i].paint(g2d);
		}
		g.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), imageObserver);
		g.dispose();
	}

}