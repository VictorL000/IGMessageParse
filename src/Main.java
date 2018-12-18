
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Main {
	public static Boolean filter(String parse, String p1, String p2){
		String[] banned = {p1, p2, "participants", "conversation", "sender", "created_at", "text", "media", "media_share_url", "media_share_caption", "media_owner", "Caption unavailable.", "likes", "username", "date", "media_url", "user"};
		for(int i = 0; i < banned.length; i++){
			if(parse.equals(banned[i]))		return false;
			
			if((parse.length()) > 8 && parse.charAt(0) == '2')return false;

			if(parse.length() > 5 && parse.substring(0, 5).equals("https"))return false;
		}

		return true;
	}
	public static void main(String[] args) throws IOException {
//		Scanner sc = new Scanner(new File("messages.json"));
		BufferedReader br = Files.newBufferedReader(Paths.get("messages.json"));
		PrintStream o = new PrintStream(new File("out.txt"));
		System.setOut(o); 
		Boolean print = false;
		String[] part = new String[2];
		int p = -1;
		Boolean isGif = false;
		String in = br.readLine();	
		String parse = "";	
		String sender = "";
		for(int i = 0; i < in.length()-1; i++){
			
			if(in.charAt(i) == '"'){
				//start / end of string
				if(!(in.charAt(i+1) == '\\')){
					print = !print;

				}
				if(!print){//closing string
					

					if(p >= 0 && p < 2){
						part[p] = parse;
						p++;
					}
					if(p == 2){
						p = -1;
						System.out.println("----------------------------------------------");
						System.out.println("p1 = " + part[0] + " p2 = "+part[1]);
						System.out.println();

					}
					if(p == 100){
						sender = parse;
						p = -1;
					}
					
					
					if(parse.equals("participants")){
						p = 0;
					}
					if(parse.equals("sender"))
						p = 100;
					
					
					if(parse.equals("animated_media_images")){
						isGif = true;
						System.out.println(sender+" sent a gif");
						System.out.println();
					}
					if(parse.equals("user"))isGif = false;
					
					if(parse.equals("}, {") || parse.equals(":") || parse.equals(","))print = !print;
					if(filter(parse, part[0], part[1])){	
						if(p != -1);

						if(!isGif){

								System.out.println(sender+" said:	" + parse.replaceAll("\\p{Punct}", ""));
								System.out.println();

						}
					}
				}
					
				parse = "";
			}
			else if(print){
				parse = parse+in.charAt(i);
			}
		}		
	}
}
