import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizFromFile implements Serializable{

	String token = "";
	List<String> temps;
	
	@SuppressWarnings("resource")
	List<String> readFile() throws IOException{
	Scanner inFile1 = new Scanner(
			new File("quiz_question.txt")).useDelimiter(",\\l*");
	temps = new ArrayList<String>();
	while (inFile1.hasNext()) {
		token = inFile1.next();
		temps.add(token);
		}
	inFile1.close();
	return temps;
	}
	
	//this needs a hashmap but im dumb tbh -Zach
	
	
	
	
}
