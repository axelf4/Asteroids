package asteroids;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class GameInit {

	private static GameInit game;
	private JFrame jFrame;
	public Render render;
	public boolean[] pressedUsefulKeys;
	private int score = 0;
	public boolean isPlaying;

	public GameInit() {
		jFrame = new JFrame("Asteroids");
		jFrame.setSize(new Dimension(800, 600));
		render = new Render();
		jFrame.getContentPane().add(render);
		// jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pressedUsefulKeys = new boolean[4];
		// pressedUsefulKeys[0] = true;
		jFrame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!isPlaying) {
					score = 0;
					isPlaying = true;
				}
				if (e.getKeyCode() == KeyEvent.VK_UP)
					pressedUsefulKeys[0] = true;
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)
					pressedUsefulKeys[1] = true;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					pressedUsefulKeys[2] = true;
				else if (e.getKeyCode() == KeyEvent.VK_SPACE)
					pressedUsefulKeys[3] = true;
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP)
					pressedUsefulKeys[0] = false;
				else if (e.getKeyCode() == KeyEvent.VK_LEFT)
					pressedUsefulKeys[1] = false;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					pressedUsefulKeys[2] = false;
				else if (e.getKeyCode() == KeyEvent.VK_SPACE)
					pressedUsefulKeys[3] = false;
			}
		});
	}

	public static void main(String[] args) {
		game = new GameInit();
		game.beginGameLoop();
	}

	public static GameInit getGameInstance() {
		while (game == null) {
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// game = new GameInit();
		}
		return game;
	}

	private void beginGameLoop() {
		isPlaying = false;
		new Player();
		// new Asteroid(); // TODO DELETE and add wave system
		while (true) {
			render.repaint();
			int stAsteroidsLeft = 0;
			for (int i = 0; isPlaying && i <= Entity.entities.length && Entity.entities[i] != null; i++) { // Update
				// and
				// check
				// collisions
				Entity.entities[i].update();
				for (int j = 0; j <= Entity.entities.length && Entity.entities[j] != null; j++) {
					if (Entity.entities[i] == Entity.entities[j])
						continue;
					if (Entity.entities[i] instanceof Player) {
						if (Entity.entities[j] instanceof Asteroid && Entity.entities[i].shape.collide(Entity.entities[j].shape)) {

						} else if (Entity.entities[j] instanceof UFO && Entity.entities[i].shape.collide(Entity.entities[j].shape)) {} else if (Entity.entities[j] instanceof Bullet && !(((Bullet) Entity.entities[j]).getOwner() instanceof Player) && Entity.entities[i].shape.collide(Entity.entities[j].shape)) {

						} else
							continue;
						//System.out.println("Game Over!"); // The game is over
						isPlaying = false;
						Entity.entities = null;
						beginGameLoop();
						break;
						//Entity.delete(i);
						//Entity.delete(j - 1); // Player is first
					} else if (Entity.entities[i] instanceof Bullet && ((Bullet) Entity.entities[i]).getOwner() instanceof Player) {
						if (Entity.entities[j] instanceof Asteroid && Entity.entities[i].shape.collide(Entity.entities[j].shape)) {
							((Asteroid) Entity.entities[j]).destroy();
							score += 1;
						} else if (Entity.entities[j] instanceof UFO && Entity.entities[i].shape.collide(Entity.entities[j].shape)) {
							score += 2;
						} else
							continue;
						Explosion.explode(Entity.entities[j]);
						Entity.delete(i);
						Entity.delete(j);
					}
				}
				if (Entity.entities[i] instanceof Asteroid) {
					stAsteroidsLeft++;
				}
			}
			if (isPlaying && stAsteroidsLeft <= 0) {
				for (int i = 0; i < 8; i++)
					new Asteroid();
				if (score > 0) // if (Math.random() < 0.50)
					new UFO();
			}
			try {
				Thread.sleep(15);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public int getScore() {
		return score;
	}

}
