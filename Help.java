package Commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Help {
	public static void helpCommand(MessageReceivedEvent event) {
		event.getTextChannel().sendMessage("黄金色 Commands: \n"
				+ "\n"
				+ "**--Basic Commands--** \n"
				+ "・\\help -- Pull up command screen \n"
				+ "・\\stop -- Stops bot. Don't use unless something goes wrong  \n"
				+ "\n"
				+ "**--Dictionary Commands--** \n"
				+ "・![word] -- Definition search. Will yield a max of 3 results. Search with the word's kanji if possible \n"
				+ "・!![word] -- Pull all search results for given word. \n"
				+ "\n"
				+ "**--Shiritori Commands--** \n"
				+ "・\\eng-rules -- Get the rules for Shiritori in English. \n"
				+ "・\\jp-rules -- Get the rules for Shiritori in Japanese. \n"
				+ "\n"
				+ "・\\start -- Start a shiritori game. \n"
				+ "・\\end -- End the current game. \n"
				+ "\n"
				+ "・\\clear -- Reset current played words \n"
				+ "・\\played -- Show all words played so far. Can get long. \n"
				+ "・\\reroll -- Reroll current kana. \n"
				+ "・\\kana -- Get current kana. \n"
				+ "・-[word] -- Play word. Use kanji if word has kanji. Katakana not supported at the moment. Enter all words in Kanji/Hiragana. \n"
				+ "\n"
				+ "**--Known Joke Commands--** \n"
				+ "・\\dajare - Print random pun. \n"
				+ "・侵略しなイカ？ - Won't you invade with me? \n"
				+ "・でゲソ - We better listen to イカ娘.").queue();
	}
}
