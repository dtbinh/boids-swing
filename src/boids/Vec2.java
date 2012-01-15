package boids;

public class Vec2 {
	
	private double x, y;
	
	public Vec2() {
		x = 0.0;
		y = 0.0;
	}
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2(Vec2 v) {
		x = v.x();
		y = v.y();
	}
	
	public double x() { return x; }
	public double y() { return y; }
	
	public Vec2 plus(Vec2 v) {
		return new Vec2(this.x + v.x(), this.y + v.y());
	}

	public Vec2 minus(Vec2 v) {
		return new Vec2(this.x - v.x(), this.y - v.y());
	}
	
	public Vec2 scale(double s) {
		return new Vec2(this.x * s, this.y * s);
	}
	
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}
	
	public double angleBetween(Vec2 v) {
		double adjust180 = 0;
		Vec2 ab = v.minus(this);
		if (ab.y() < 0) adjust180 = Math.PI;

		return Math.atan(ab.x() / ab.y()) + adjust180;
	}
	
	public double absAngleBetween(Vec2 v) {
		double result = angleBetween(v);
		return (0 > result) ? result * -1 : result;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
