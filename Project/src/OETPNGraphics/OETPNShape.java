package OETPNGraphics;

import java.awt.Graphics;

public interface OETPNShape {
	public void Draw(Graphics g);

	public Point GetLocation();

	public void SetLocation(Point location);

	public String GetName();

	public void SetName(String name);
	
	public ShapeType GetType();
	
	public boolean GetFlag();

	public void SetFlag(boolean flag);
}
