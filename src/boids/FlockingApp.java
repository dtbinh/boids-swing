package boids;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;


// T+45mins for the point avoidance
public class FlockingApp {

	private static void createAndShowUI() {
		JFrame frame = new JFrame("SimpleSwingAnimation");
		frame.getContentPane().add(new FlockingPanel());
		frame.getContentPane().setPreferredSize(new Dimension(600, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowUI();
			}
		});
	}
}

@SuppressWarnings("serial")
class FlockingPanel extends JPanel {
	private static final int TIMER_DELAY = 10;

	private int numBoids = 30;

	private ArrayList<Boid> boids = new ArrayList<Boid>(numBoids);
	//private ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
	Hashtable<String, Behaviour> behaviours = new Hashtable<String, Behaviour>();

	private int align_radius = 50;
	private int cohes_radius = 100;
	private int separ_radius = 40;
	private double align_weight = 0.202;
	private double cohes_weight = 0.001;
	private double separ_weight = 0.8;//0.6;
	private double align_angle = Math.PI / 1.5;
	private double cohes_angle = Math.PI;// / 1.5;
	private double separ_angle = Math.PI;

	public FlockingPanel() {
		super();
		setBackground(Color.white);
		behaviours.put("Alignment", new Alignment(align_radius, align_angle, align_weight));
		behaviours.put("Cohesion", new Cohesion(cohes_radius, cohes_angle, cohes_weight));
		behaviours.put("Separation", new Separation(separ_radius, separ_angle, separ_weight));
		behaviours.put("Jitter", new Jitter(0, 0.0, 0.0025, 2.0, 2.0));
		behaviours.put("AvoidPoint", new AvoidPoint(100, 0.0, 1));
		//also add an inversion switch, so we can scatter?
		
		TouchListener tl = new TouchListener();
		addMouseListener(tl);
		addMouseMotionListener(tl);


		for (int i = 0; i < numBoids; ++i)
			boids.add(new Boid());

		javax.swing.Timer timer = new javax.swing.Timer(TIMER_DELAY,
				new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FlockingPanel.this.repaint();
			}
		});
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		AvoidPoint avoidPoint = (AvoidPoint)behaviours.get("AvoidPoint");
		behaviours.get("Cohesion").invert(avoidPoint.isActive());
		if (avoidPoint.isActive()) {
			g2.setColor(Color.blue);
			
			Vec2 point = avoidPoint.point();
			double r = avoidPoint.radius();
			int r2 = (int)(2 * r);
			
			g2.drawOval((int)(point.x() - r), (int)(point.y() - r), r2, r2);
		}
		
		for (Boid boid : boids)
			for (Behaviour b : behaviours.values())
				b.apply(boids, boid);

		for (Boid boid : boids) {
			boid.update();
			boid.boundPosition(-25, -25, 625, 625);
			boid.draw(g2);
			boid.acc(0.0, 0.0);
		}
	}

	private class TouchListener implements MouseListener, MouseMotionListener {

		private boolean mouseDown = false;
		
		@Override
		public void mouseDragged(MouseEvent event) {
			((AvoidPoint)behaviours.get("AvoidPoint")).updatePoint(event.getPoint().x, event.getPoint().y);
		}

		@Override
		public void mouseMoved(MouseEvent event) {
		}

		@Override
		public void mouseClicked(MouseEvent event) {
		}

		@Override
		public void mouseEntered(MouseEvent event) {
			((AvoidPoint)behaviours.get("AvoidPoint")).setActive(mouseDown);
		}

		@Override
		public void mouseExited(MouseEvent event) {
			((AvoidPoint)behaviours.get("AvoidPoint")).setActive(false);
		}

		@Override
		public void mousePressed(MouseEvent event) {
			((AvoidPoint)behaviours.get("AvoidPoint")).updatePoint(event.getPoint().x, event.getPoint().y);
			((AvoidPoint)behaviours.get("AvoidPoint")).setActive(true); // TODO probably want to check if it's in bounds first!
			mouseDown = true;
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			((AvoidPoint)behaviours.get("AvoidPoint")).setActive(false);
			mouseDown = false;
		}
	}
}

