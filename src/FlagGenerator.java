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
				double topSize = 0.33;
				double bottomSize = 0.33;
				final double p = random.nextDouble();
				if (p < 0.02) {
					bottomSize = 0.5;
					topSize = 0.25;
				} else if (p < 0.06) {
					bottomSize = 0.25;
					topSize = 0.5;
				}
				final boolean topAndBottomMatch = random.nextInt(3) == 0;
				if (topAndBottomMatch) {
					final double p2 = random.nextDouble();
					if (p2 < 0.2) {
						final double w = random.nextDouble() * 0.08;
						topSize -= w;
						bottomSize -= w;
					}
					if (random.nextInt(3) == 0) {
						final Color color2 = getRandomColor(usedColorIndices).color;
						flag.addHorizontalStripe(1 - bottomSize - 0.03, 1, color2);
						flag.addHorizontalStripe(0, topSize + 0.03, color2);
					}
				}
				Color color = getRandomColor(usedColorIndices).color;
				flag.addHorizontalStripe(0, topSize, color);
				if (!topAndBottomMatch) {
					color = getRandomColor(usedColorIndices).color;
				}
				flag.addHorizontalStripe(1 - bottomSize, 1, color);
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
				double topSize = 0.33;
				double bottomSize = 0.33;
				Color color = getRandomColor(usedColorIndices).color;
				flag.addVerticalStripe(0, topSize, color);
				if (random.nextInt(3) != 0) {
					color = getRandomColor(usedColorIndices).color;
				}
				flag.addVerticalStripe(1 - bottomSize, 1, color);
			}
			case NORDIC_CROSS -> {
				double crossWidth = 0.08 + random.nextDouble() * 0.04;
				final double midX = (random.nextInt(2) + 4) / 10.0;
				if (random.nextInt(2) == 0) {
					final Color color2 = getRandomColor(usedColorIndices).color;
					final double crossWidth2 = crossWidth + random.nextDouble() * 0.01 + 0.02;
					final double verticalStripeWidth2 = crossWidth2 * height / width;
					flag.addVerticalStripe(midX - verticalStripeWidth2, midX + verticalStripeWidth2, color2);
					flag.addHorizontalStripe(0.5 - crossWidth2, 0.5 + crossWidth2, color2);
				}
				final Color color = getRandomColor(usedColorIndices).color;
				final double verticalStripeWidth = crossWidth * height / width;
				flag.addVerticalStripe(midX - verticalStripeWidth, midX + verticalStripeWidth, color);
				flag.addHorizontalStripe(0.5 - crossWidth, 0.5 + crossWidth, color);
			}
			case DIAGONAL_STRIPES -> {
				final int pattern2 = random.nextInt(3);
				final boolean edges = random.nextInt(2) == 0;
				if (pattern2 == 0) {
					double w = 0.05 + random.nextDouble() * 0.05;
					double z = w;
					switch (random.nextInt(3)) {
						case 0:
							break;
						case 1:
							w *= 2;
							z = 0;
							break;
						case 2:
							w = 0;
							z *= 2;
							break;
					}
					final Color color1 = getRandomColor(usedColorIndices).color;
					flag.addTriangle(Flag.Alignment.LEFT, z, 1, 1 - w, 1, color1);
					flag.addTriangle(Flag.Alignment.RIGHT, 0, 1 - z, 1 - w, 0, color1);
					if (edges) {
						final double d = 0.02 + random.nextDouble() * 0.01;
						w += d;
						z += d;
						final Color color2 = getRandomColor(usedColorIndices).color;
						flag.addTriangle(Flag.Alignment.LEFT, z, 1, 1 - w, 1, color2);
						flag.addTriangle(Flag.Alignment.RIGHT, 0, 1 - z, 1 - w, 0, color2);
					}
				} else if (pattern2 == 1) {
					double w = 0.05 + random.nextDouble() * 0.05;
					double z = w;
					switch (random.nextInt(3)) {
						case 0:
							break;
						case 1:
							w *= 2;
							z = 0;
							break;
						case 2:
							w = 0;
							z *= 2;
							break;
					}
					final Color color1 = getRandomColor(usedColorIndices).color;
					flag.addTriangle(Flag.Alignment.RIGHT, z, 1, 1 - w, 1, color1);
					flag.addTriangle(Flag.Alignment.LEFT, 0, 1 - z, 1 - w, 0, color1);
					if (edges) {
						final double d = 0.02 + random.nextDouble() * 0.01;
						w += d;
						z += d;
						final Color color2 = getRandomColor(usedColorIndices).color;
						flag.addTriangle(Flag.Alignment.RIGHT, z, 1, 1 - w, 1, color2);
						flag.addTriangle(Flag.Alignment.LEFT, 0, 1 - z, 1 - w, 0, color2);
					}
				} else {
					double w = 0.05 + random.nextDouble() * 0.05;
					final Color color1 = getRandomColor(usedColorIndices).color;
					flag.addTriangle(Flag.Alignment.RIGHT, w, 1 - w, 0.5 - w, 0.5, color1);
					flag.addTriangle(Flag.Alignment.LEFT, w, 1 - w, 0.5 - w, 0.5, color1);
					flag.addTriangle(Flag.Alignment.TOP, w, 1 - w, 0.5, 0.5 - w, color1);
					flag.addTriangle(Flag.Alignment.BOTTOM, w, 1 - w, 0.5, 0.5 - w, color1);
					if (edges) {
						w += 0.02 + random.nextDouble() * 0.01;
						Color color2 = getRandomColor(usedColorIndices).color;
						flag.addTriangle(Flag.Alignment.RIGHT, w, 1 - w, 0.5 - w, 0.5, color2);
						flag.addTriangle(Flag.Alignment.LEFT, w, 1 - w, 0.5 - w, 0.5, color2);
						if (random.nextInt(2) == 0) {
							color2 = getRandomColor(usedColorIndices).color;
						}
						flag.addTriangle(Flag.Alignment.TOP, w, 1 - w, 0.5, 0.5 - w, color2);
						flag.addTriangle(Flag.Alignment.BOTTOM, w, 1 - w, 0.5, 0.5 - w, color2);
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
