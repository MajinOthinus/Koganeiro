package Shiritori;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Store {
	public static void playedWordsWrite(String word) 
			throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Words.txt"), true));
		writer.write(word+"\n");
		writer.newLine();
		writer.close();
	}
	
	//Check if user input is already a played word
	public static Boolean playedWordsRead(String word) {
		File file = new File("Words.txt");
		Boolean result = false;
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.equals(word)) {
					result = true;
				}
			}
			scanner.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//Display all words played so far
	public static void playedWordsSoFar(MessageReceivedEvent event) {
			try {
				BufferedReader br = new BufferedReader(new FileReader("Words.txt")); 
				StringBuilder builder = new StringBuilder();
				String line;
				if (br.readLine() != null) {
					while ((line = br.readLine()) != null) {
						//For some reason this is printing an empty line
						//I can't seem to find where its coming from
						if (!line.equals("")) {
							builder.append(line.replace("\n", "").replace("\r", "")).append("; ");
						}
					} 
				}
				else {
					event.getTextChannel().sendMessage("No words have been played.").queue();
				}
				br.close();
				/*try {
					event.getTextChannel().sendMessage(builder.toString()).queue();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} */
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	
	//Clear words.txt
	public static void clearWords() {
		try {
			PrintWriter pw = new PrintWriter("Words.txt");
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
