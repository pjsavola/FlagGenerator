import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;


public class FlagLibrary {
	public static final Map<String, Flag> realFlags = new TreeMap<>();

	public static void createFlags(int height) {
		if (!realFlags.isEmpty()) {
			return;
		}

		realFlags.put("Afghanistan Republic", createAfghanistanRepublic(height));
		realFlags.put("Afghanistan Emirate", createAfghanistanEmirate(height));
		realFlags.put("United Kingdom", createUnitedKingdom(height));
	}

	public static Flag createAfghanistanRepublic(int height) {
		final int width = height * 3 / 2;
		final Flag flag = new Flag(width, height, new Color(0xD32011));
		flag.addVerticalStripe(0, 0.33, new Color(0x000000));
		flag.addVerticalStripe(0.67, 1, new Color(0x007A36));
		flag.addEmblem("Afghanistan Republic Emblem", 0.5, 0.5);

		/** Add Britain flag to bottom right
		final int subFlagHeight = height / 3;
		final Flag subFlag = createGreatBritain(subFlagHeight);
		final int subFlagWidth = subFlag.getWidth();
		flag.addSubFlag(subFlag, width - subFlagWidth, height - subFlagHeight);
		*/
		return flag;
	}
	
	public static Flag createAfghanistanEmirate(int height) {
		final int width = height * 3 / 2;
		final Flag flag = new Flag(width, height, new Color(0xFFFFFF));
		//flag.addEmblem("Afghanistan Emirate Emblem", 0.5, 0.5);

		return flag;
	}

	public static Flag createUnitedKingdom(int height) {
		final int width = height * 2;
		final Flag flag = new Flag(width, height, Palette.WHITE.color);
		flag.addTriangle(Flag.Alignment.LEFT, 0, 0.334, 0.334, 0.334, Palette.RED.color);
		flag.addTriangle(Flag.Alignment.LEFT, 0.074, 0.334, 0.260, 0.334, Palette.WHITE.color);
		flag.addTriangle(Flag.Alignment.RIGHT, 1, 0.666, 0.334, 0.666, Palette.RED.color);
		flag.addTriangle(Flag.Alignment.RIGHT, 0.926, 0.666, 0.260, 0.666, Palette.WHITE.color);
		flag.addTriangle(Flag.Alignment.TOP, 1, 0.584, 0.584, 0.416, Palette.RED.color);
		flag.addTriangle(Flag.Alignment.TOP, 0.926, 0.584, 0.584, 0.342, Palette.WHITE.color);
		flag.addTriangle(Flag.Alignment.BOTTOM, 0, 0.416, 0.416, 0.416, Palette.RED.color);
		flag.addTriangle(Flag.Alignment.BOTTOM, 0.074, 0.416, 0.416, 0.342, Palette.WHITE.color);
		flag.addTriangle(Flag.Alignment.LEFT, 0.111, 0.334, 0.223, 0.334, Palette.BLUE.color);
		flag.addTriangle(Flag.Alignment.LEFT, 0.889, 0.666, 0.223, 0.666, Palette.BLUE.color);
		flag.addTriangle(Flag.Alignment.RIGHT, 0.111, 0.334, 0.223, 0.334, Palette.BLUE.color);
		flag.addTriangle(Flag.Alignment.RIGHT, 0.889, 0.666, 0.223, 0.666, Palette.BLUE.color);
		flag.addTriangle(Flag.Alignment.TOP, 0.112, 0.416, 0.416, 0.304, Palette.BLUE.color);
		flag.addTriangle(Flag.Alignment.TOP, 0.888, 0.584, 0.584, 0.304, Palette.BLUE.color);
		flag.addTriangle(Flag.Alignment.BOTTOM, 0.112, 0.416, 0.416, 0.304, Palette.BLUE.color);
		flag.addTriangle(Flag.Alignment.BOTTOM, 0.888, 0.584, 0.584, 0.304, Palette.BLUE.color);
		flag.addHorizontalStripe(0.334, 0.666, Palette.WHITE.color); // Remove too long red stripes
		flag.addHorizontalStripe(0.4, 0.6, Palette.RED.color);
		flag.addVerticalStripe(0.45, 0.55, Palette.RED.color);
		return flag;
	}
}
