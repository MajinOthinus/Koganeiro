package Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Main.Url;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Dictionary {
	public static void dictionaryCommand(MessageReceivedEvent event) {
		String output, newUrl;
		String word, strippedWord;
    
		String url = "http://jisho.org/api/v1/search/words?keyword=";
		List<String> allMatches = new ArrayList<String>();


		try {
			//Converting entered message to be added to url and then searched.
			word = event.getMessage().getContent();
			strippedWord = word.replace("!", "");
			newUrl = url + "\""+strippedWord+"\"";
		
			//Output contains the raw text from jisho
			output = Url.getUrlContents(newUrl);

			//Patterns for gathering the info from output
			Pattern reading = Pattern.compile("\"reading\":\"(.*?)\"");
			Matcher matcher = reading.matcher(output);

			Pattern def = Pattern.compile("\"english_definitions\":\\[\"(.*?)\"\\]");
			Matcher matcher2 = def.matcher(output);
		
			Pattern part_speech = Pattern.compile("\"parts_of_speech\":\\[\"(.*?)\"\\]");
			Matcher part_matcher = part_speech.matcher(output);
			
			Pattern kanji = Pattern.compile("\"word\":\"(.*?)\"");
			Matcher kanji_match = kanji.matcher(output);
			kanji_match.find();
		
			//Gather all of the instances of the English definitions pattern
			while (matcher2.find()) {
				allMatches.add(matcher2.group(1));
				if (allMatches.size() == 24) {
					break;
				}
			}	
			if (matcher.find() && part_matcher.find()) {	
			
				//Print
				event.getTextChannel().sendMessage("Kanji: "+kanji_match.group(1) + "\n"
						+"Reading: "+matcher.group(1)+"\n"
						+"Part of Speech: "+part_matcher.group(1).replace(",", "; ").replace("\"", "")
						+"\n"+"\n"+"Definitions:").queue();
					
				//Command to print all definitions.
				//If just one ! is used, it will print max 24 definitions based on the while loop on line 44
				int loops = Math.min(3, allMatches.size());
				if (event.getMessage().getContent().startsWith("!!")) 
						loops = allMatches.size();

				for (int i = 0; i < loops; i++) {
					event.getTextChannel().sendMessage((i + 1) + ".\\) " + allMatches.get(i).replace("\"", "").replace(",", "; ")
							.replace("[", " ").replace("]", "").replace("english_definitions:", "")).queue();
				}
			}
			else {
				event.getTextChannel().sendMessage("Word not found").queue();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
}
