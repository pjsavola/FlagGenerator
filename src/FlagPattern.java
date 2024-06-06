import java.awt.*;
import java.util.Random;
import java.util.Set;


public enum FlagPattern {
	EMPTY(1),
	TWO_HORIZONTAL_STRIPES(3),
	THREE_HORIZONTAL_STRIPES(5),
	FOUR_HORIZONTAL_STRIPES(1),
	MANY_HORIZONTAL_STRIPES(2),
	TWO_VERTICAL_STRIPES(2),
	THREE_VERTICAL_STRIPES(4),
	NORDIC_CROSS(1),
	DIAGONAL_STRIPES(2);

	private final int weight;

	private FlagPattern(int weight) {
		this.weight = weight;
	}

	public static FlagPattern getRandomPattern(Random rng) {
		int totalWeight = 0;
		final FlagPattern[] patterns = values();
		for (FlagPattern flagPattern : patterns) {
			totalWeight += flagPattern.weight;
		}

		final int v = rng.nextInt(totalWeight);
		totalWeight = 0;
		for (FlagPattern pattern : patterns) {
			totalWeight += pattern.weight;
			if (v < totalWeight) {
				return pattern;
			}
		}

		// Fallback
		return patterns[rng.nextInt(patterns.length)];
	}
}
