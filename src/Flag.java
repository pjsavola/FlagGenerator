import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Circle {
	private final int x;
	private final int y;
	private final int size;
	private final Color color;

	public Circle(int x, int y, int size, Color color) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
	}

	public void paint(Graphics g, int offsetX, int offsetY) {
		g.setColor(color);
		g.fillOval(offsetX + x, offsetY + y, size, size);
	}
}

class Shape {
	private final Polygon polygon;
	private final Color color;

	public Shape(Polygon polygon, Color color) {
		this.polygon = polygon;
		this.color = color;
	}

	public void paint(Graphics g, int offsetX, int offsetY) {
		g.setColor(color);
		final int[] pX = Arrays.copyOf(polygon.xpoints, polygon.npoints);
		final int[] pY = Arrays.copyOf(polygon.ypoints, polygon.npoints);
		for (int i = 0; i < polygon.npoints; ++i) {
			pX[i] += offsetX;
			pY[i] += offsetY;
		}
		g.fillPolygon(pX, pY, polygon.npoints);
	}
}

class Emblem {
	private final int x;
	private final int y;
	private final BufferedImage image;

	public Emblem(int x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.image = image;
	}

	public void paint(Graphics g, int offsetX, int offsetY) {
		g.drawImage(image, offsetX + x, offsetY + y, null);
	}
}

class SubFlag {
	private final Flag flag;
	private final int topLeftX;
	private final int topLeftY;

	public SubFlag(Flag flag, int topLeftX, int topLeftY) {
		this.flag = flag;
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
	}

	public void paint(Graphics g, int offsetX, int offsetY) {
		flag.paint(g, offsetX + topLeftX, offsetY + topLeftY);
	}
}

public class Flag {
	public enum Alignment { LEFT, TOP, RIGHT, BOTTOM };
	private final int width;
	private final int height;
	private final Color background;
	private final List<Shape> shapes = new ArrayList<>();
	private final List<Circle> circles = new ArrayList<>();
	private final List<Emblem> emblems = new ArrayList<>();
	private final List<SubFlag> subFlags = new ArrayList<>();
	
	public Flag(int width, int height, Color background) {
		this.width = width;
		this.height = height;
		this.background = background;
	}

	public int getWidth() {
		return width;
	}

	public void addHorizontalStripe(double top, double bottom, Color color) {
		final int t = (int) (top * height);
		final int b = (int) (bottom * height);
		final int[] pX = { 0, width, width, 0 };
		final int[] pY = { t, t, b, b };
		shapes.add(new Shape(new Polygon(pX, pY, 4), color));
	}

	public void addVerticalStripe(double left, double right, Color color) {
		final int l = (int) (left * width);
		final int r = (int) (right * width);
		final int[] pX = { l, r, r, l };
		final int[] pY = { 0, 0, height, height };
		shapes.add(new Shape(new Polygon(pX, pY, 4), color));
	}
	
	public void addCircle(double posX, double posY, double relativeSize, Color color) {
		final int size = (int) (relativeSize * Math.min(width, height));
		final int x = (int) (posX * width) - size / 2;
		final int y = (int) (posY * height) - size / 2;
		circles.add(new Circle(x, y, size, color));
	}

	public void addTriangle(Alignment alignment, double start, double end, double topX, double topY, Color color) {
		int[] pX = null;
		int[] pY = null;
		switch (alignment) {
		case LEFT:
			pX = new int[] { 0, (int) (width * topX), 0 };
			pY = new int[] { (int) (height * start), (int) (height * topY), (int) (height * end) };
			break;
		case TOP:
			pX = new int[] { (int) (width * start), (int) (width * topX), (int) (width * end) };
			pY = new int[] { 0, (int) (height * topY), 0 };
			break;
		case RIGHT:
			pX = new int[] { width, width - (int) (width * topX), width };
			pY = new int[] { (int) (height * start), (int) (height * topY), (int) (height * end) };
			break;
		case BOTTOM:
			pX = new int[] { (int) (width * start), (int) (width * topX), (int) (width * end) };
			pY = new int[] { height, height - (int) (height * topY), height };
			break;
		}
		shapes.add(new Shape(new Polygon(pX, pY, 3), color));
	}

	public void addEmblem(String emblemName, double midX, double midY) {
		final BufferedImage image = ImageCache.getEmblem(emblemName);
		final int x = (int) (width * midX) - image.getWidth() / 2;
		final int y = (int) (height * midY) - image.getHeight() / 2;
		emblems.add(new Emblem(x, y, image));
	}

	public void addSubFlag(Flag flag, int topLeftX, int topLeftY) {
		subFlags.add(new SubFlag(flag, topLeftX, topLeftY));
	}

	public void paint(Graphics g, int x, int y) {
		g.setColor(background);
		g.fillRect(x, y, width, height);
		for (int i = 0; i < shapes.size(); ++i) {
			shapes.get(i).paint(g, x, y);
		}
		for (int i = 0; i < circles.size(); ++i) {
			circles.get(i).paint(g, x, y);
		}
		for (int i = 0; i < emblems.size(); ++i) {
			emblems.get(i).paint(g, x, y);
		}
		for (int i = 0; i < subFlags.size(); ++i) {
			subFlags.get(i).paint(g, x, y);
		}
	}
}
