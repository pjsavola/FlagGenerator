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
		realFlags.put("Akrotiri and Dhekhelia", createAkrotiriandDhekhelia(height));
		realFlags.put("Albania", createAlbania(height));
		realFlags.put("Algeria", createAlgeria(height));
		realFlags.put("American Samoa", createAmericanSamoa(height));
		realFlags.put("Andorra", createAndorra(height));
		realFlags.put("Angola", createAngola(height));
		realFlags.put("Anguilla", createAnguilla(height));
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
		flag.addEmblem("Afghanistan Emirate Emblem", 0.5, 0.5);
		
		return flag;
	}
		
		public static Flag createAkrotiriandDhekhelia(int height) {
			final int width = height * 3 / 2;
			final Flag flag = new Flag(width, height, new Color(0x009632));
			flag.addEmblem("Akrotiri and Dhekhelia Emblem", 0.3, 0.5);

		return flag;
	}
		public static Flag createAlbania(int height) {
			final int width = height * 7 / 5;
			final Flag flag = new Flag(width, height, new Color(0xDA291C));
			flag.addEmblem("Albania Emblem", 0.5, 0.5);

		return flag;
	}
		public static Flag createAlgeria(int height) {
			final int width = height * 3 / 2;
			final Flag flag = new Flag(width, height, new Color(0xFFFFFF));
			flag.addVerticalStripe(0.0, 0.5, new Color(0x006633));
			flag.addEmblem("Algeria Emblem", 0.5, 0.5);

		return flag;
	}
		public static Flag createAmericanSamoa(int height) {
			final int width = height * 2 / 1;
			final double w = 0.045;
			final Flag flag = new Flag(width, height, new Color(0x000066));
			flag.addTriangle(Flag.Alignment.RIGHT, 0, 1, 1, 0.5, new Color(0xBD1021));
			flag.addTriangle(Flag.Alignment.RIGHT, w, 1 - w, (1 - w * width / height), 0.5, new Color(0xFFFFFF));
			flag.addEmblem("American Samoa Emblem", 0.82, 0.5);

		return flag;
	}
		public static Flag createAndorra(int height) {
			final int width = height * 10 / 7;
			final Flag flag = new Flag(width, height, new Color(0xFEDD00));
			flag.addVerticalStripe(0.0, 0.333, new Color(0x10069F));
			flag.addVerticalStripe(0.667, 1.0, new Color(0xD50032));
			flag.addEmblem("Andorra Emblem", 0.5, 0.5);

		return flag;
	}
		public static Flag createAngola(int height) {
			final int width = height * 3 / 2;
			final Flag flag = new Flag(width, height, new Color(0xCC092F));
			flag.addHorizontalStripe(1.0, 0.5, new Color(0x000000));
			flag.addEmblem("Angola Emblem", 0.5, 0.5);

		return flag;
	}
		public static Flag createAnguilla(int height) {
			final int width = height * 2 / 1;
			final Flag flag = new Flag(width, height, new Color(0x012169));
			final int subFlagHeight = height / 2;
			final Flag subFlag = createUnitedKingdom(subFlagHeight);
			final int subFlagWidth = subFlag.getWidth();
			flag.addSubFlag(subFlag, 0, 0);
			flag.addEmblem("Anguilla Emblem", 0.75, 0.5);

		return flag;
		
	}
	
	
	
		

	public static Flag createUnitedKingdom(int height) {
		final int width = height * 2;
		final Flag flag = new Flag(width, height, Palette.WHITE.color);
		flag.addTriangle(Flag.Alignment.LEFT, 0, 0.334, 0.334, 0.334, new Color(0xC8102E));
		flag.addTriangle(Flag.Alignment.LEFT, 0.074, 0.334, 0.260, 0.334, Palette.WHITE.color);
		flag.addTriangle(Flag.Alignment.RIGHT, 1, 0.666, 0.334, 0.666, new Color(0xC8102E));
		flag.addTriangle(Flag.Alignment.RIGHT, 0.926, 0.666, 0.260, 0.666, Palette.WHITE.color);
		flag.addTriangle(Flag.Alignment.TOP, 1, 0.584, 0.584, 0.416, new Color(0xC8102E));
		flag.addTriangle(Flag.Alignment.TOP, 0.926, 0.584, 0.584, 0.342, Palette.WHITE.color);
		flag.addTriangle(Flag.Alignment.BOTTOM, 0, 0.416, 0.416, 0.416, new Color(0xC8102E));
		flag.addTriangle(Flag.Alignment.BOTTOM, 0.074, 0.416, 0.416, 0.342, Palette.WHITE.color);
		flag.addTriangle(Flag.Alignment.LEFT, 0.111, 0.334, 0.223, 0.334, new Color(0x012169));
		flag.addTriangle(Flag.Alignment.LEFT, 0.889, 0.666, 0.223, 0.666, new Color(0x012169));
		flag.addTriangle(Flag.Alignment.RIGHT, 0.111, 0.334, 0.223, 0.334,new Color(0x012169));
		flag.addTriangle(Flag.Alignment.RIGHT, 0.889, 0.666, 0.223, 0.666,new Color(0x012169));
		flag.addTriangle(Flag.Alignment.TOP, 0.112, 0.416, 0.416, 0.304,new Color(0x012169));
		flag.addTriangle(Flag.Alignment.TOP, 0.888, 0.584, 0.584, 0.304, new Color(0x012169));
		flag.addTriangle(Flag.Alignment.BOTTOM, 0.112, 0.416, 0.416, 0.304, new Color(0x012169));
		flag.addTriangle(Flag.Alignment.BOTTOM, 0.888, 0.584, 0.584, 0.304, new Color(0x012169));
		flag.addHorizontalStripe(0.334, 0.666, Palette.WHITE.color); // Remove too long red stripes
		flag.addHorizontalStripe(0.4, 0.6, new Color(0xC8102E));
		flag.addVerticalStripe(0.45, 0.55, new Color(0xC8102E));
		return flag;
	}
}
	
	
	
	

	