package boids;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Boid {

	private Vec2 p, v, a;
	private Color boidColour = Color.red;

	private static final double velLimit = 1.0 * 1;
	private static final double accLimit = 0.02 * 1;

	public Boid() {
		p = new Vec2(Math.random() * 525 + 25, Math.random() * 525 + 25);

		double vx = ((Math.random() * 20) - 10);
		double vy = ((Math.random() * 20) - 10);
		v = new Vec2(vx, vy);

		a = new Vec2();
	}

	public Vec2 pos() { return p; }
	public Vec2 vel() { return v; }

	public void acc(double x, double y) { a = new Vec2(x, y); }

	public void update() {
		double length = a.magnitude();
		if (length > accLimit) a = a.scale(1 / (length / accLimit));
		v = v.plus(a);

		length = v.magnitude();
		if (length > velLimit) v = v.scale(1 / (length / velLimit));
		p = p.plus(v);
	}

	public void applyForce(Vec2 force) {
		if (null == force) return;
		a = a.plus(force); // Assume a mass of one unit 
	}

	public void boundPosition(int xMin, int yMin, int xMax, int yMax) {
		double x = p.x(), y = p.y();
		if (x < xMin) x = xMax;
		else if (x > xMax) x = xMin;

		if (y < yMin) y = yMax;
		else if (y > yMax) y = yMin;

		p = new Vec2(x, y);
	}

	public void draw(Graphics2D g2) {
		g2.setColor(boidColour);

		double height = 5, half = height / 2;
		int x[] = {
				(int)(p.x() + height * Math.sin(getDirection())),
				(int)(p.x() + height * Math.sin(getDirection() + half)),
				(int)(p.x() + height * Math.sin(getDirection() - half))
		};
		int y[] = {
				(int)(p.y() + height * Math.cos(getDirection())),
				(int)(p.y() + height * Math.cos(getDirection() + half)),
				(int)(p.y() + height * Math.cos(getDirection() - half))
		};
		g2.fill(new Polygon(x, y, x.length));
	}

	private double getDirection() {
		double adjust180 = 0;
		if (v.y() < 0) adjust180 = Math.PI;

		return Math.atan(v.x() / v.y()) + adjust180; //tan(theta) = opp/adj
	}

}
