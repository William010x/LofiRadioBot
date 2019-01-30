package constants;
import java.awt.Color;
import java.util.Random;

public class ColorGenerator {
	public static Color randomColor() {
		Random random = new Random();
		int roll = random.nextInt(5) + 1;
		
		if (roll == 1) {
			return Color.GREEN;
		}
		else if (roll == 2) {
			return Color.RED;
		}
		else if (roll == 3) {
			return Color.MAGENTA;
		}
		else if (roll == 4) {
			return Color.BLACK;
		}
		else {
			return Color.WHITE;
		}
	}
}
