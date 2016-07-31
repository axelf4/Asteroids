package asteroids;

import java.awt.Point;
import java.awt.Polygon;

public class Polygon2D extends Polygon {

	public int xCenter, yCenter;

	public Polygon2D() { // Empty constructor
		// TODO Auto-generated constructor stub
	}

	public Polygon2D(int[] xpoints, int[] ypoints, int npoints) {
		super(xpoints, ypoints, npoints);
	}

	public void rotate(double rotation) {
		// findCenter(); if you leave this on each time, the polygon will shift
		// slightly if rotated a lot

		for (int i = 0; i < npoints; i++) {
			double Xo = xpoints[i]; // temp X location for use in y vertices
			// modification
			xpoints[i] = (int) (xCenter + ((Xo - xCenter) * Math.cos(rotation) - (ypoints[i] - yCenter) * Math.sin(rotation)));
			ypoints[i] = (int) (yCenter + ((Xo - xCenter) * Math.sin(rotation) + (ypoints[i] - yCenter) * Math.cos(rotation)));
		}
	}
	
	/*@Override
	public void translate(int dx, int dy) {
		super.translate(dx, dx);
		findCenter();
	}*/

	public void findCenter() {

		double area = 0;
		int xSum = 0, ySum = 0;

		// find Area
		for (int i = 0; i < npoints - 1; i++) {
			area += xpoints[i] * ypoints[i + 1] - xpoints[i + 1] * ypoints[i];
			xSum += (xpoints[i] + xpoints[i + 1]) * (xpoints[i] * ypoints[i + 1] - xpoints[i + 1] * ypoints[i]);
			ySum += (ypoints[i] + ypoints[i + 1]) * (xpoints[i] * ypoints[i + 1] - xpoints[i + 1] * ypoints[i]);
		}
		area += xpoints[npoints - 1] * ypoints[0] - xpoints[0] * ypoints[npoints - 1];
		xSum += (xpoints[npoints - 1] + xpoints[0]) * (xpoints[npoints - 1] * ypoints[0] - xpoints[0] * ypoints[npoints - 1]);
		ySum += (ypoints[npoints - 1] + ypoints[0]) * (xpoints[npoints - 1] * ypoints[0] - xpoints[0] * ypoints[npoints - 1]);

		area = area / 2;

		xCenter = (int) (xSum / (6 * area));
		yCenter = (int) (ySum / (6 * area));
	}

	public boolean collide(Polygon2D poly) {
		// test separation axes of current polygon
		for (int j = npoints - 1, i = 0; i < npoints; j = i, i++) {
			// Separate axis is perpendicular to the
			// edge
			Point axis = new Point(ypoints[i] - ypoints[j], xpoints[i] - xpoints[j]);

			if (separatedByAxis(axis, poly))
				return false;
		}

		// test separation axes of other polygon
		for (int j = poly.npoints - 1, i = 0; i < poly.npoints; j = i, i++) {
			// Separate axis is perpendicular to the
			// edge
			Point axis = new Point(-poly.ypoints[i] - poly.ypoints[j], poly.xpoints[i] - poly.xpoints[j]);

			if (separatedByAxis(axis, poly))
				return false;
		}
		return true;
	}

	float min, max, mina, maxa, minb, maxb;

	public void calculateInterval(Point axis) {
		this.min = this.max = (float) xpoints[0] * axis.x + ypoints[0] * axis.y;

		for (int i = 1; i < npoints; i++) {
			float d = (float) xpoints[i] * axis.x + ypoints[i] * axis.y;
			if (d < this.min)
				this.min = d;
			else if (d > this.max)
				this.max = d;
		}
	}

	public boolean intervalsSeparated(float mina, float maxa, float minb, float maxb) {
		return (mina > maxb) || (minb > maxa);
	}

	public boolean separatedByAxis(Point axis, Polygon2D poly) {
		calculateInterval(axis);
		mina = min;
		maxa = max;
		poly.calculateInterval(axis);
		minb = poly.min;
		maxb = poly.max;
		return intervalsSeparated(mina, maxa, minb, maxb);
	}

}
