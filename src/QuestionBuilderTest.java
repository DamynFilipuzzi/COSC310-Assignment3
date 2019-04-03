package src;

import java.util.ArrayList;

public class QuestionBuilderTest {

	public static void main(String[] args) {
		ArrayList<Question> q;
		String folderName = "Questions";
		
		QuestionBuilder qb = new QuestionBuilder(folderName);
		qb.buildQuestions();
		q = new ArrayList<>(qb.getBuiltQuestions().values());
		
		q.get(0).printQuestion();
		q.get(1).printQuestion();
		
		EdgeBuilder eb = new EdgeBuilder(q);
		
		q = eb.buildEdges();
		
		q.get(0).printQuestion();
		q.get(1).printQuestion();
		q.get(2).printQuestion();
		q.get(0).getChild(1).printQuestion();
		
		
		
		

	}

}
