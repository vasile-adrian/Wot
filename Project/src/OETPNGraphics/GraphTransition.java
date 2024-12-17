package OETPNGraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class GraphTransition implements OETPNShape {

	private Point location;
	private String transitionName;
	private Boolean activated;

	public GraphTransition(Point location, String transitionName, Boolean activated) {
		super();
		this.location = location;
		this.transitionName = transitionName;
		this.activated = activated;
	}

	public void Draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		int stringX = location.X - 2;
		int stringY = location.Y - 5;
		FontMetrics fm = g2.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(transitionName, g2);

		g.setColor(Color.white);
		g.fillRect(stringX, stringY - fm.getAscent(), (int) rect.getWidth(), (int) rect.getHeight());
		g2.setColor(Color.red);

		g2.drawString(transitionName, stringX, stringY);

		if (!activated) {
			g2.setColor(Color.black);
		}

		g2.setStroke(new BasicStroke(3));
		g2.drawRect(location.X, location.Y, 10, 35);
	}

	@Override
	public Point GetLocation() {
		return location;
	}

	@Override
	public void SetLocation(Point location) {
		this.location = location;
	}

	@Override
	public String GetName() {
		return transitionName;
	}

	@Override
	public void SetName(String name) {
		transitionName = name;
	}

	@Override
	public ShapeType GetType() {
		return ShapeType.Transition;
	}

	@Override
	public boolean GetFlag() {
		return activated;
	}

	@Override
	public void SetFlag(boolean flag) {
		activated = flag;
	}
}
