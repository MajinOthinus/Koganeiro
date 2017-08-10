package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Joke {
	public static void panaino(MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("お前様よ。儂が極秘ルートから入手した情報によると・・\n" + 
				"ミスタードーナツが今、全品百円セールを行っておるらしいのじゃ！！ これは今すぐ行かんと、売り切れてしまうぞ！！"
				+ "\n "
				+"https://www.youtube.com/watch?v=hD9wN5dnu1s").queue();
	}
	public static void geso(MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("跪け愚民共！海の使者である私を敬うでゲソ！").queue();
	}
	public static void ika(MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("へ？侵略って？ダメダメ、もう日が落ちているじゃなイカ。"
				+ "侵略の営業時間は午後5時で終了でゲソ。").queue();
	}
	
	public static void pullJoke(MessageReceivedEvent event) {
		List<String> puns = new ArrayList<String>(Arrays.asList("日本語史を入れることに本腰を入れることにした。",
				"エルフが得る麩",
				"虚心坦懐という言葉、今日知ったんかい！",
				"賞与でしょうよ",
				"日本のお米に本能を込め",
				"マークⅡは、まぁ苦痛",
				"視覚ならぬ 死角の移動術",
				"あなたの意図は、ここで切れます",
				"秋田に飽きた",
				"新しいものがあったらしい",
				"家と言え",
				"戦に行くさ",
				"この衣装いいっしょ？",
				"英語で考えいごと",
				"女がいっぱいおんなー",
				"これは貝かい？",
				"教会に行くのは今日かい？",
				"死体をどうしたい？",
				"新人を信じん",
				"短剣を探検にもって行く",
				"ハワイで歯はいい",
				"スパイダは酸っぱいだ",
				"邦画の方が人気",
				"本棚に置くのは本だな",
				"胃がいい"));
		
		Random rand = new Random();
		int n = rand.nextInt(puns.size() + 1);
		event.getTextChannel().sendMessage(puns.get(n)).queue();
		
	}	
}
