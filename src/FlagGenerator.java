import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class FlagGenerator extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Random random = new Random();

	private final int flagCount = 9;
	private final int flagsPerRow = 3;
	private static final int flagHeight = 200;
	private static final int flagWidth = flagHeight * 2;
	private final int marginSize = 10;
	
	private Dimension calculateSize() {
		final int width = (flagWidth + marginSize) * flagsPerRow + marginSize;
		final int rowCount = (flagCount + flagsPerRow - 1) / flagsPerRow;
		final int height = (flagHeight + marginSize) * rowCount + marginSize;
		return new Dimension(width, height);
	}

	private final Dimension size = calculateSize();
	private Set<String> usedHorizontalColors = new HashSet<>();
	private Set<String> usedVerticalColors = new HashSet<>();
	private final List<Flag> remainingFlags = new ArrayList<>();
	private final List<Flag> currentFlags = new ArrayList<>();

	public FlagGenerator() {
		FlagLibrary.createFlags(flagHeight);
		remainingFlags.addAll(FlagLibrary.realFlags.values());
		updateFlags();
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}

	public void updateFlags() {
		currentFlags.clear();
		for (int i = 0; i < flagCount; ++i) {
			final Flag flag;
			if (remainingFlags.isEmpty()) {
				flag = createRandomFlag(flagHeight);
			} else {
				flag = remainingFlags.remove(0);
			}
			currentFlags.add(flag);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.GRAY);
		g.fillRect(0, 0, size.width, size.height);
		int column = 0;
		int row = 0;
		for (final Flag flag : currentFlags) {
			final int x = column * (flagWidth + marginSize) + marginSize;
			final int y = row * (flagHeight + marginSize) + marginSize;
			final int spacingX = (flagWidth - flag.getWidth()) / 2;
			flag.paint(g, x + spacingX, y);
			if (++column == flagsPerRow) {
				column = 0;
				++row;
			}
		}
	}

	private Palette getRandomColor(Set<Integer> usedColorIndices) {
		final int colorIndex = Palette.getRandomIndex(random, usedColorIndices);
		usedColorIndices.add(colorIndex);
		return Palette.values()[colorIndex];
	}

	public Flag createRandomFlag(int height) {
		final Set<Integer> usedColorIndices = new HashSet<>();
		final FlagPattern pattern = FlagPattern.getRandomPattern(random);
		return createRandomFlag(height, usedColorIndices, pattern);
	}

	public Flag createRandomFlag(int height, Set<Integer> usedColorIndices, FlagPattern pattern) {
		final int width = (int) (height * Math.min(2, Math.max(1, 2.5 - Math.abs(random.nextGaussian()))));
		final Flag flag = new Flag(width, height, getRandomColor(usedColorIndices).color);
		switch (pattern) {
			case EMPTY -> {
				// Add random left triangle
				if (random.nextInt(10) < 2) {
					final double midX = (random.nextInt(2) + 4) / 10.0;
					flag.addTriangle(Flag.Alignment.LEFT, 0, 1, midX, 0.5, getRandomColor(usedColorIndices).color);
				} else {
					flag.addCircle(0.5, 0.5, 0.5, getRandomColor(usedColorIndices).color);
				}
			}
			case TWO_HORIZONTAL_STRIPES -> {
				flag.addHorizontalStripe(0, 0.5, getRandomColor(usedColorIndices).color);
				if (random.nextInt(10) < 2) {
					final double midX = (random.nextInt(2) + 4) / 10.0;
					flag.addTriangle(Flag.Alignment.LEFT, 0, 1, midX, 0.5, getRandomColor(usedColorIndices).color);
				}
			}
			case THREE_HORIZONTAL_STRIPES -> {
				Color color = getRandomColor(usedColorIndices).color;
				flag.addHorizontalStripe(0, 0.33, color);
				if (random.nextInt(3) != 0) {
					color = getRandomColor(usedColorIndices).color;
				} else if (random.nextInt(3) == 0) {
					final Color color2 = getRandomColor(usedColorIndices).color;
					flag.addHorizontalStripe(0.64, 0.67, color2);
					flag.addHorizontalStripe(0.33, 0.36, color2);
				}
				flag.addHorizontalStripe(0.67, 1, color);
				if (random.nextInt(10) < 2) {
					final double midX = (random.nextInt(2) + 4) / 10.0;
					flag.addTriangle(Flag.Alignment.LEFT, 0, 1, midX, 0.5, getRandomColor(usedColorIndices).color);
				}
			}
			case FOUR_HORIZONTAL_STRIPES -> {
				flag.addHorizontalStripe(0, 0.5, getRandomColor(usedColorIndices).color);
				flag.addHorizontalStripe(0, 0.25, getRandomColor(usedColorIndices).color);
				flag.addHorizontalStripe(0.75, 1, getRandomColor(usedColorIndices).color);
			}
			case MANY_HORIZONTAL_STRIPES -> {
				// Many horizontal stripes with alternating colors
				final int amount = random.nextInt(3) + 3;
				final double size = 0.5 / amount;
				final Color color = getRandomColor(usedColorIndices).color;
				for (int i = 0; i < amount; ++i) {
					flag.addHorizontalStripe(i * 2 * size, (i * 2 + 1) * size, color);
				}
				final FlagPattern pattern2 = random.nextInt(3) == 0 ? FlagPattern.NORDIC_CROSS : FlagPattern.EMPTY;
				final Flag topLeft = createRandomFlag(height / 3, usedColorIndices, pattern2);
				flag.addSubFlag(topLeft, 0, 0);
			}
			case TWO_VERTICAL_STRIPES -> {
				flag.addVerticalStripe(0, 0.5, getRandomColor(usedColorIndices).color);
				flag.addCircle(0.5, 0.5, 0.5, getRandomColor(usedColorIndices).color);
			}
			case THREE_VERTICAL_STRIPES -> {
				Color color = getRandomColor(usedColorIndices).color;
				flag.addVerticalStripe(0, 0.33, color);
				if (random.nextInt(3) != 0) {
					color = getRandomColor(usedColorIndices).color;
				}
				flag.addVerticalStripe(0.67, 1, color);
			}
			case NORDIC_CROSS -> {
				final double midX = (random.nextInt(2) + 4) / 10.0;
				if (random.nextInt(2) == 0) {
					final Color color2 = getRandomColor(usedColorIndices).color;
					final double crossWidth2 = 0.13;
					final double verticalStripeWidth2 = crossWidth2 * height / width;
					flag.addVerticalStripe(midX - verticalStripeWidth2, midX + verticalStripeWidth2, color2);
					flag.addHorizontalStripe(0.5 - crossWidth2, 0.5 + crossWidth2, color2);
				}
				final Color color = getRandomColor(usedColorIndices).color;
				final double crossWidth = 0.1;
				final double verticalStripeWidth = crossWidth * height / width;
				flag.addVerticalStripe(midX - verticalStripeWidth, midX + verticalStripeWidth, color);
				flag.addHorizontalStripe(0.5 - crossWidth, 0.5 + crossWidth, color);
			}
			case DIAGONAL_STRIPES -> {
				final int pattern2 = random.nextInt(3);
				final boolean edges = random.nextInt(2) == 0;
				if (pattern2 == 0) {
					double w = 0.1;
					final Color color1 = getRandomColor(usedColorIndices).color;
					flag.addTriangle(Flag.Alignment.LEFT, w, 1, 1 - w, 1, color1);
					flag.addTriangle(Flag.Alignment.RIGHT, 0, 1 - w, 1 - w, 0, color1);
					if (edges) {
						w = 0.13;
						final Color color2 = getRandomColor(usedColorIndices).color;
						flag.addTriangle(Flag.Alignment.LEFT, w, 1, 1 - w, 1, color2);
						flag.addTriangle(Flag.Alignment.RIGHT, 0, 1 - w, 1 - w, 0, color2);
					}
				} else {
					double w = 0.1;
					final Color color1 = getRandomColor(usedColorIndices).color;
					flag.addTriangle(Flag.Alignment.RIGHT, w, 1, 1 - w, 1, color1);
					flag.addTriangle(Flag.Alignment.LEFT, 0, 1 - w, 1 - w, 0, color1);
					if (edges) {
						w = 0.13;
						final Color color2 = getRandomColor(usedColorIndices).color;
						flag.addTriangle(Flag.Alignment.RIGHT, w, 1, 1 - w, 1, color2);
						flag.addTriangle(Flag.Alignment.LEFT, 0, 1 - w, 1 - w, 0, color2);
					}
				}
			}
		}
		return flag;
	}

	public static void main(String[] args) {

		final FlagGenerator g = new FlagGenerator();
		final JFrame f = new JFrame("Flag Generator");
		f.setContentPane(g);
		f.pack();
		f.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					g.updateFlags();
					g.repaint();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		f.setVisible(true);
	}

}
