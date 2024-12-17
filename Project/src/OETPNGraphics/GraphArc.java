package OETPNGraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public class GraphArc implements OETPNShape {

	private static final Polygon ARROW_HEAD = new Polygon();

	static {
		ARROW_HEAD.addPoint(0, 0);
		ARROW_HEAD.addPoint(-5, -10);
		ARROW_HEAD.addPoint(5, -10);
	}
	public OETPNShape Start;
	public OETPNShape End;

	public GraphArc(OETPNShape start, OETPNShape end) {
		super();
		this.Start = start;
		this.End = end;
	}

	public void Draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		int startX = Start.GetLocation().X + 30;
		int startY = Start.GetLocation().Y + 15;

		if (Start.GetType() == ShapeType.Transition) {
			startX = Start.GetLocation().X + 15;
		}

		int endX = End.GetLocation().X - 5;
		int endY = End.GetLocation().Y + 15;

		if (Start.GetLocation().X > End.GetLocation().X) {
			startX = End.GetLocation().X - 20;
			startY = endY;
		}

		double angle = Math.atan2(endY - startY, endX - startX);

		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(2));

		g2.drawLine(startX, startY, (int) (endX - 10 * Math.cos(angle)), (int) (endY - 10 * Math.sin(angle)));

		AffineTransform tx1 = g2.getTransform();

		AffineTransform tx2 = (AffineTransform) tx1.clone();

		tx2.translate(endX, endY);
		tx2.rotate(angle - Math.PI / 2);

		g2.setTransform(tx2);
		g2.fill(ARROW_HEAD);

		g2.setTransform(tx1);

		if (Start.GetLocation().X > End.GetLocation().X) {

			int sX = Start.GetLocation().X + 30;
			int sY = Start.GetLocation().Y + 15;

			if (Start.GetType() == ShapeType.Transition) {
				sX = Start.GetLocation().X + 15;
			}

			int lenth = 100;
			g2.drawLine(sX, sY, sX + 20, sY);
			g2.drawLine(sX + 20, sY, sX + 20, sY + lenth);

			g2.drawLine(sX + 20, sY + lenth, startX, startY + lenth);
			g2.drawLine(startX, startY + lenth, startX, startY);
		}
	}

	@Override
	public Point GetLocation() {
		return null;
	}

	@Override
	public void SetLocation(Point location) {
	}

	@Override
	public String GetName() {
		return null;
	}

	@Override
	public void SetName(String name) {
	}

	@Override
	public ShapeType GetType() {
		return ShapeType.Arc;
	}

	@Override
	public boolean GetFlag() {
		return false;
	}

	@Override
	public void SetFlag(boolean flag) {

	}

}
