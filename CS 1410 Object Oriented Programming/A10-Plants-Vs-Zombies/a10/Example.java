package a10;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Plants vs Zombies type game with our own 'original' twist.
 * 
 * @author harrykim
 * @author connorbarry
 *
 */
public class Example extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private ArrayList<Actor> actors; // Plants and zombies all go in here
	private Random rand; // Random number

	BufferedImage plantImage; // Maybe these images should be in those classes, but easy to change here.
	BufferedImage plantBuffImage;
	BufferedImage zombieImage;
	BufferedImage zombieBuffImage;

	int numRows;
	int numCols;
	int cellSize;
	int randomRow;

	// Button stuff
	static JPanel ButtonPanel;

	static JButton pickPlant = new JButton("Zap 10R");
	static JButton pickPlantBuff = new JButton("Spray 50R");
	static JButton special = new JButton("Pesticide 300R");
	static JButton quit = new JButton("Quit");
	// Cheat Button
	static JButton cheat = new JButton("Cheat +100R");

	int action = 0;

	// Mouse stuff

	Double mousePressPosition;
	double mousePressPositionX;
	double mousePressPositionY;

	Boolean canPlace = false;

	// Score
	static JLabel resourceLabel;
	static int resource;

	// Spawn Rate
	double spawnRate = 98.5;

	/**
	 * The Example() method sets up the parameters of the game.
	 */
	public Example() {
		super();

		// Resources
		resource = 100;
		resourceLabel = new JLabel("resource: 0");
		this.add(resourceLabel);

		rand = new Random();
		// Define some quantities of the scene
		numRows = 5;
		numCols = 7;
		cellSize = 75;
		setPreferredSize(new Dimension(50 + numCols * cellSize, 55 + numRows * cellSize));

		addMouseListener(this);

		// Store all the plants and zombies in here.
		actors = new ArrayList<>();

		// Load images
		try {
			plantImage = ImageIO.read(new File("src/a10/Sprites-Icons/Zapper.png"));
			plantBuffImage = ImageIO.read(new File("src/a10/Sprites-Icons/BugSpray.png"));
			zombieImage = ImageIO.read(new File("src/a10/Sprites-Icons/Ant.png"));
			zombieBuffImage = ImageIO.read(new File("src/a10/Sprites-Icons/Wasp.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}

		// The timer updates the game each time it goes.
		// Get the javax.swing Timer, not from util.
		timer = new Timer(30, this);
		timer.start();

		// buttons
		ButtonPanel = new JPanel();

		ButtonPanel.add(pickPlant);
		ButtonPanel.add(pickPlantBuff);
		ButtonPanel.add(special);
		ButtonPanel.add(quit);
		ButtonPanel.add(cheat);

		pickPlant.addActionListener(this);
		pickPlantBuff.addActionListener(this);
		special.addActionListener(this);
		quit.addActionListener(this);
		cheat.addActionListener(this);

	}

	/***
	 * Implement the paint method to draw the plants
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Actor actor : actors) {
			actor.draw(g, 0);
			actor.drawHealthBar(g);
		}
	}

	/**
	 * The actionPerformed() method runs the actual game and is triggered by the
	 * timer. It is the game loop of this code.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// This method is getting a little long, but it is mostly loop code
		// Increment their cooldowns and reset collision status
		for (Actor actor : actors) {
			actor.update();
		}

		// Try to attack
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.attack(other);
			}
		}

		// Remove plants and zombies with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive())
				nextTurnActors.add(actor);
			else {
				actor.removeAction(actors); // any special effect or whatever on removal
				if (actor instanceof Zombie) {
					resource = resource + 10;
					if (actor instanceof ZombieBuff) {
						resource = resource + 10;
					}
				}
			}

		}
		actors = nextTurnActors;

		// Check for collisions between zombies and plants and set collision status
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.setCollisionStatus(other);
			}
		}

		// Move the actors.
		for (Actor actor : actors) {
			actor.move(); // for Zombie, only moves if not colliding.
		}

		// Random row position (for zombies only)
		int randomRow = rand.nextInt(5) * 75 + 55; // random 6 is 0 - 4;

		// Make a zombie (health, cooldown, speed, attackdamage)
		Zombie zombie = new Zombie(new Point2D.Double(500, randomRow),
				new Point2D.Double(plantImage.getWidth(), plantImage.getHeight()), zombieImage, 80, 50, -1, 10);
		// Make a zombieBuff
		ZombieBuff zombieBuff = new ZombieBuff(new Point2D.Double(500, randomRow),
				new Point2D.Double(plantImage.getWidth(), plantImage.getHeight()), zombieBuffImage, 120, 50, -0.65, 15);

		// Randomness of spawning regular zombie and zombie alt
		int x = rand.nextInt(10);

		// places zombies on random rows

		if (rand.nextInt(100) > spawnRate) {
			if (x > 8)
				actors.add(zombieBuff);
			else
				actors.add(zombie);
		}

		spawnRate = spawnRate - .001;

		// Stops game if a sprite reaches the left side.
		for (Actor actor : actors) {
			if (actor.getPosition().x <= 0) {
				timer.stop();
			}
		}

		// Button actions
		if (e.getSource() == pickPlant)
			action = 1;
		if (e.getSource() == pickPlantBuff)
			action = 2;
		if (e.getSource() == special)
			if (resource >= 300) {
				for (Actor actor : actors) {
					if (actor instanceof Zombie) {
						actor.changeHealth(-1000);
					}
				}
				resource = resource - 100;
			}
		if (e.getSource() == quit)
			System.exit(0);
		if (e.getSource() == cheat)
			resource = resource + 100;

		// "Snapping" plants into place.

		// Adding plant to game;
		// System.out.print(canPlace + "\n");

		// Updating resources
		resourceLabel.setText("Resource: " + resource + "R");

		// Redraw the new scene
		repaint();
	}

	/**
	 * Make the game and runs it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame app = new JFrame("Picnic vs. Bugs");
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Example panel = new Example();

				app.setContentPane(panel);
				app.pack();
				app.setVisible(true);

				app.add(ButtonPanel);
			}
		});
	}

	/**
	 * The mousePressed() method listens/reacts to mouse clicks and does specific
	 * actions such as creating and placing plants.
	 * 
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		mousePressPosition = new Point2D.Double(x, y);
		mousePressPositionX = new Point2D.Double(x, y).getX();
		mousePressPositionY = new Point2D.Double(x, y).getY() - 50;

		// Snap x position
		if (mousePressPositionX > 0 && mousePressPositionX <= 75) {
			mousePressPositionX = 1; // Setting it to 1 is a temp solution to prevent time.stop() when plant is
										// placed at 0.
		}
		if (mousePressPositionX > 75 && mousePressPositionX <= 75 * 2) {
			mousePressPositionX = 75;
		}
		if (mousePressPositionX > 75 * 2 && mousePressPositionX <= 75 * 3) {
			mousePressPositionX = 75 * 2;
		}
		if (mousePressPositionX > 75 * 3 && mousePressPositionX <= 75 * 4) {
			mousePressPositionX = 75 * 3;
		}
		if (mousePressPositionX > 75 * 4 && mousePressPositionX <= 75 * 5) {
			mousePressPositionX = 75 * 4;
		}
		if (mousePressPositionX > 75 * 5 && mousePressPositionX <= 75 * 6) {
			mousePressPositionX = 75 * 5;
		}
		if (mousePressPositionX > 75 * 6 && mousePressPositionX <= 75 * 7) {
			mousePressPositionX = 75 * 6;
		}
		if (mousePressPositionX > 75 * 7) {
			mousePressPositionX = 75 * 6;
		}

		// Snap y position
		if (mousePressPositionY <= 0) {
			mousePressPositionY = 55;
		}
		if (mousePressPositionY > 0 && mousePressPositionY <= 75) {
			mousePressPositionY = 55;
		}
		if (mousePressPositionY > 75 && mousePressPositionY <= 75 * 2) {
			mousePressPositionY = 55 + 75;
		}
		if (mousePressPositionY > 75 * 2 && mousePressPositionY <= 75 * 3) {
			mousePressPositionY = 55 + 75 * 2;
		}
		if (mousePressPositionY > 75 * 3 && mousePressPositionY <= 75 * 4) {
			mousePressPositionY = 55 + 75 * 3;
		}
		if (mousePressPositionY > 75 * 4 && mousePressPositionY <= 75 * 5) {
			mousePressPositionY = 55 + 75 * 4;
		}
		if (mousePressPositionY > 75 * 5) {
			mousePressPositionY = 55 + 75 * 4;
		}

		boolean dontPlace = false;

		for (Actor actor : actors) {
			if (actor.isCollidingPoint(mousePressPosition)) {
				dontPlace = true;
				break;
			}
		}
		if (!dontPlace) {
			if (action == 1 && resource >= 10) {
				// Make a plant (health, cooldown, attackdamage)
				// Zombie stats: 100, 50, -1, 10
				Plant plant = new Plant(new Point2D.Double(mousePressPositionX, mousePressPositionY),
						new Point2D.Double(plantImage.getWidth(), plantImage.getHeight()), plantImage, 200, 40, 20);
				actors.add(plant);
				canPlace = false;
				resource = resource - 10;
			}

			if (action == 2 && resource >= 50) {
				PlantBuff plantBuff = new PlantBuff(new Point2D.Double(mousePressPositionX, mousePressPositionY),
						new Point2D.Double(plantBuffImage.getWidth(), plantBuffImage.getHeight()), plantBuffImage, 150,
						10, 10);
				actors.add(plantBuff);
				canPlace = false;
				resource = resource - 50;
			}
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}