import java.awt.Color;


public enum Palette {
	WHITE(0xFFFFFF),
	RED(0xE40303),
	GREEN(0x008026),
	BLUE(0x24408E),
	YELLOW(0xFFED00),
	BLACK(0x000000),
	ORANGE(0xFF8C00);

	public final Color color;

	private Palette(int rgb) {
		color = new Color(rgb);
	}
}
