import java.util.*;

/**
 * @author Anna
 */
public class Quiz {

	String question;
	List<String> quizAnswers;
	int correctAnswer;
	
	
	/**
	 * @param i input random num to get random question from pool, else can iterate through the questions 
	 */
	Quiz(int i){
		questionPool(i);
	}
	
	Quiz(String q, String a, String b, String c, int ans){
		question = q;
		quizAnswers = Arrays.asList(a,b,c);
		correctAnswer = ans;
	}
	
	Quiz(String q, List<String> qa, int ans){
		question = q;
		quizAnswers = qa;
		correctAnswer = ans;
	}
	
	void questionPool(int s){
		switch(s) {
			case 0: 
				question = "What do killdeer eat?";
				quizAnswers = Arrays.asList("Insects", "Fish", "Rodents", "Cats");
				correctAnswer = 0;
				break;
			case 1: 
				question = "Where do killdeer live?";
				quizAnswers = Arrays.asList("Australia", "North pole", "Only South Dakota", "North America");
				correctAnswer = 3;
				break;
			case 2: 
				question = "How do killdeer protect their young?";
				quizAnswers = Arrays.asList("Attack","Feign injury", "Spit acid");
				correctAnswer = 1;
				break;
			case 3: 
				question = "What environments do killdeer nest in?";
				quizAnswers = Arrays.asList("Tall trees", "Underground", "Fields or parking lots", "Skyscrapers");
				correctAnswer = 2;
				break;
			default: 
				question = "Do killdeer migrate?";
				quizAnswers = Arrays.asList("Always", "No", "Sometimes");
				correctAnswer = 2;
		}
	}
	public String getQuestion(){
		return question;
	}
	
	public int getCorrectAnswer(){
		return correctAnswer;
	}
	
	public Object [] getQuizAnswers() {
		return quizAnswers.toArray();
	}
	
}
