package u1171639.shared.main.java.utils;

public class EnumParser {
	
	public static <T extends Enum<T>> T stringToEnum(Class<T> c, String string) {
        try {
            return Enum.valueOf(c, string);
        } catch(IllegalArgumentException ex) {
        	return null;
        }
	}
}
