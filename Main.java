/*    Written by Pierce Jensen on 7/17/2017
 *    I used this bot to teach myself a little Java
 *    Probably a mess
 */

package Main;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;

import Commands.Dictionary;
import Commands.Help;
import Commands.Joke;
import Commands.Stop;
import Shiritori.Rules;
import Shiritori.Shiritori;
import Shiritori.Start;
import Shiritori.Store;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {
	Boolean game = false;
	String firstKana = null;
	
	public static void main(String[] args)
			throws LoginException, RateLimitedException, InterruptedException
	{
		//Connect to discord application token
		JDA jda = new JDABuilder(AccountType.BOT).setToken(Token.token).buildBlocking();
		jda.addEventListener(new Main());
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		//DICTIONARY, WORD STUFF
		if (event.getMessage().getContent().startsWith("!")) {
			Dictionary.dictionaryCommand(event);
		}
		
		//-------------------------------------------------------------------------
		
		//BASIC COMMAND STUFF
		if (event.getMessage().getContent().equals("\\help")) {
			Help.helpCommand(event);
		}
		if (event.getMessage().getContent().equals("\\stop")) {
			event.getTextChannel().sendMessage("よくも黄金色を殺しやがった！"
					+ "\nBot shutting down").queue();
			Stop.stopBot();
		}
		
		//-------------------------------------------------------------------------
		
		//SHIRITORI STUFF
			
		//Game start and end commands
		if (event.getMessage().getContent().equals("\\start")) {
			game = Start.startGame();
			firstKana = Start.getKana();
			
			event.getTextChannel().sendMessage("Shiritori game started, the first kana is: "+firstKana).queue();
		}
		if (event.getMessage().getContent().equals("\\end")) {
			if (game == true){
				event.getTextChannel().sendMessage("Shiritori game has ended. Thanks for playing.").queue();
				game = false;
				Store.clearWords();
			} 
			else {
				event.getTextChannel().sendMessage("A shiritori game is not started.").queue();
			}
		}
		
		//-------------------------------------------------------------------------
		
		//Clear current played words from textfile.
		if (event.getMessage().getContent().equals("\\clear")) {
			Store.clearWords();
			event.getTextChannel().sendMessage("Played words text file has been cleared.").queue();
		}
		
		//-------------------------------------------------------------------------
		
		//Word input for Shiritori once a game has been started.
		//In hindsight, should have put in other methods, but ehh
		if (event.getMessage().getContent().startsWith("-")) {
			if (game == true) {
				String word = event.getMessage().getContent().replace("-", "");
				String url = "http://jisho.org/api/v1/search/words?keyword=";
				String newUrl = url + "\""+word.replace(" ", "")+"\"";
				
				//Output contains the raw text from jisho
				String output = Url.getUrlContents(newUrl);

				//Patterns for gathering the info from output
				Pattern reading = Pattern.compile("\"reading\":\"(.*?)\"");
				Matcher matcher = reading.matcher(output);
				
				Pattern part_speech = Pattern.compile("\"parts_of_speech\":\\[\"(.*?)\"\\]");
				Matcher part_matcher = part_speech.matcher(output);
				part_matcher.find();
				String partOfSpeech = part_matcher.group(1);
				
				try {
					if (matcher.find()){
						String kana = matcher.group(1);
						String kanaUnconverted = kana;
						kana = kana.replace("ゃ", "や").replace("ょ", "よ").replace("ゅ", "ゆ");
						Boolean check = Shiritori.checkKana(kana, firstKana);
						Boolean playedBefore = Store.playedWordsRead(word);
						
						if (check == true) {
							if (playedBefore != true) {
								if (kana.substring(kana.length() - 1).equals("ん")) {
									event.getTextChannel().sendMessage("That word ends in ん. Please enter another.").queue();
								}
								else if (partOfSpeech.startsWith("I-adjective")){
									event.getTextChannel().sendMessage("That is an i-adjective, which cannot be used. Try again.").queue();
								}
								else if (partOfSpeech.startsWith("Ichidan verb") || partOfSpeech.startsWith("Godan verb")) {
									event.getTextChannel().sendMessage("That is a verb, which cannot be used. Try again.").queue();
								}
								else {				
									try {
										Store.playedWordsWrite(word);
									} catch (IOException e) {
										e.printStackTrace();
									}
									firstKana = kana.substring(kana.length() - 1);
									event.getTextChannel().sendMessage("Reading: "+kanaUnconverted+"\n"
									+"The next kana is: "+kana.substring(kana.length() - 1)).queue();
								}
							}
							else {
								event.getTextChannel().sendMessage("That word has been played before. Please enter another.").queue();
							}
						}
						else {
							event.getTextChannel().sendMessage("That word does not start with "+firstKana+". Please enter another.").queue();
						}
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} 
			}
			else {
				event.getTextChannel().sendMessage("A Shiritori game is not started. Please enter \\start to begin a game.").queue();
			}
		}
		
		//-------------------------------------------------------------------------
		
		//Reroll kana
		if (event.getMessage().getContent().equals("\\reroll")) {
			if (game != false) {	
				firstKana = Start.getKana();
				event.getTextChannel().sendMessage("Kana rerolled to: "+firstKana).queue();
			}
			else {
				event.getTextChannel().sendMessage("A shiritori game is not started.").queue();
			}
		}
		
		//-------------------------------------------------------------------------
		
		//Get current kana
		if (event.getMessage().getContent().equals("\\kana")) {
			if (game != false) {
				event.getTextChannel().sendMessage("Current kana is: "+firstKana).queue();
			}
			else {
				event.getTextChannel().sendMessage("A shiritori game is not started.").queue();
			}
		}
		
		//-------------------------------------------------------------------------
		
		//Get Shiritori Rules
		if (event.getMessage().getContent().equals("\\eng-rules")) {
			Rules.showRulesEng(event);
		}
		if (event.getMessage().getContent().equals("\\jp-rules")) {
			Rules.showRulesJap(event);
		}
		
		//-------------------------------------------------------------------------
		
		//Get Played Words
		if (event.getMessage().getContent().equals("\\played")) {
			if (game == true) {
				Store.playedWordsSoFar(event);
			}
			else {
				event.getTextChannel().sendMessage("A shiritori game is not started.").queue();
			}
		}
		
		//-------------------------------------------------------------------------
		
		//Joke commands
		if (event.getMessage().getContent().equals("ぱないの")) {
			Joke.panaino(event);
		}
		if (event.getMessage().getContent().equals("でゲソ")) {
			Joke.geso(event);
		}
		if (event.getMessage().getContent().equals("侵略しなイカ？")) {
			Joke.ika(event);
		}
		if (event.getMessage().getContent().equals("\\dajare")) {
			Joke.pullJoke(event);
		} 
	}
}

