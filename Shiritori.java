package Shiritori;

public class Shiritori {
	public static Boolean checkKana(String word,String kana) {
		if (word.substring(0,  1).equals(kana)) {
			return true;
		}
		else {
			return false;
		}
	}
}
