import java.awt.Color;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


public enum Palette {
	WHITE(0xFFFFFF, 2),
	RED(0xE40303, 2),
	GREEN(0x008026, 2),
	BLUE(0x24408E, 2),
	YELLOW(0xFFED00, 2),
	BLACK(0x000000, 1),
	ORANGE(0xFF8C00, 1);

	public final Color color;
	private final int weight;

	private Palette(int rgb, int weight) {
		color = new Color(rgb);
		this.weight = weight;
	}

	public static int getRandomIndex(Random rng, Set<Integer> usedColorIndices) {
		int totalWeight = 0;
		final Palette[] colors = values();
		for (int i = 0; i < colors.length; ++i) {
			if (!usedColorIndices.contains(i)) {
				totalWeight += colors[i].weight;
			}
		}

		//System.err.println("Banned: " + usedColorIndices.stream().map(i -> colors[i]).map(Enum::toString).collect(Collectors.joining(",")));
		final int v = rng.nextInt(totalWeight);
		totalWeight = 0;
		for (int i = 0; i < colors.length; ++i) {
			if (!usedColorIndices.contains(i)) {
				totalWeight += colors[i].weight;
				if (v < totalWeight) {
					return i;
				}
			}
		}

		// Fallback
		return rng.nextInt(colors.length);
	}
}
