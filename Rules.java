package Shiritori;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Rules {
	public static void showRulesJap(MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("しりとりルール: \n"
				+ "\n"
				+"・まず黄金色殿が最初の仮名を決めて投稿する。以降の人は順番に、前の人が言った単語の最後の文字から始まる単語を言っていく \n"
				+"・日本語には「ん」で始まる単語がほぼ皆無に等しいため、通常は「ん」で終わる単語を言ってしまうと負けになる。\n"
				+"・挙げる単語は名詞に限る。しかし、スル動詞と形容動詞も使える。\n"
				+ "・最後の文字が拗音の場合（ぁ、ゃ、等）、そのまま使っていい：いしゃ　→　やり。ボットは勝手にコンバートするから"
				+ "このルールを知らなくても大丈夫ということ。\n").queue();
	}
	
	public static void showRulesEng(MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("Shiritori Rules: \n"
				+ "\n"
				+ "・First 黄金色殿 will choose the first kana. From there, taking turns, players will make a new word "
				+ "using the last character of the previously played word. \n"
				+ "・Japanese has no words that start with ん, meaning you will lose if you enter a word that ends with it. \n"
				+ "・The only words you can use are Nouns. This includes する verbs and na-adjectives \n"
				+ "・If the word ends in a small hiragana (ぁ、ゃ、etc), you can use it as is. The bot will automatically convert the"
				+ " kana to its normal form for you to use so you do not even have to be aware of this.").queue();
	}
}
