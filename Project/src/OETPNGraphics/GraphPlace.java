package OETPNGraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class GraphPlace implements OETPNShape {
	private Point location;
	private String plcaeName;
	private Boolean haveToken;

	public GraphPlace(Point location, String plcaeName, Boolean haveToken) {
		super();
		this.location = location;
		this.plcaeName = plcaeName;
		this.haveToken = haveToken;
	}

	@Override
	public void Draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.white);
		g2.fillOval(location.X, location.Y, 30, 30);
		
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(3));
		g2.drawOval(location.X, location.Y, 30, 30);
		if (haveToken) {
			g2.fillOval(location.X + 10, location.Y + 10, 10, 10);
		}
		
		int stringX = location.X + 5;
		int stringY = location.Y - 6;
		FontMetrics fm = g2.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(plcaeName, g2);

		g.setColor(Color.white);
		g.fillRect(stringX, stringY - fm.getAscent(), (int) rect.getWidth(), (int) rect.getHeight());
		g2.setColor(Color.red);

		g2.drawString(plcaeName, stringX, stringY);
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
		return plcaeName;
	}

	@Override
	public void SetName(String name) {
		plcaeName = name;
	}

	@Override
	public ShapeType GetType() {
		return ShapeType.Place;
	}

	@Override
	public boolean GetFlag() {
		return haveToken;
	}

	@Override
	public void SetFlag(boolean flag) {
		haveToken = flag;
	}
}
