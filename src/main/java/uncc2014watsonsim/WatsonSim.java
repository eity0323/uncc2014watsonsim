package uncc2014watsonsim;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;

import uncc2014watsonsim.search.*;
import uncc2014watsonsim.scoring.*;
/**
 *
 * @author Phani Rahul
 */
public class WatsonSim {
	static final Searcher[] searchers = new Searcher[]{
		new LuceneSearcher(),
		new IndriSearcher(),
		//new GoogleSearcher()
	};
	static final Researcher[] researchers = {
		new MergeResearcher(),
		new PersonRecognitionResearcher()
	};
	static final Learner learner = new WekaLearner();

    /**
     * @param args the command line arguments
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

        //read from the command line
        System.out.println("Enter the jeopardy text: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String command = br.readLine();
        
    	while (!command.isEmpty()) {
            Question question = new Question(command);
	        HashSet<String> ignoreSet = new HashSet<String>();
	        ignoreSet.add("J! Archive");
	        ignoreSet.add("Jeopardy");
	        
	        System.out.println("This is a " + question.getType() + " Question");
	        
        	// Query every engine
	        for (Searcher s: searchers)
	        	question.addAll(s.runQuery(question.text));
	        
        	for (Researcher r : researchers) {
        		r.research(question);
        	}
        	
	        learner.test(question);
	        // Not a range-based for because we want the rank
	        for (int i=0; i<question.size(); i++) {
	        	Answer r = question.get(i);
                /*String title = r.getTitle();
                String aW[] = title.split(" ");
                String qW[] = question.text.split(" ");
                StringBuilder newTitle = new StringBuilder();
                for(String a : aW ){
                    boolean there = false;
                    for(String q:qW){
                        if(q.equalsIgnoreCase(a)){
                            there = true;
                            break;
                        }
                    }
                    if(!there){
                        newTitle.append(a);
                        newTitle.append(" ");
                    }
                }
                r.setTitle(newTitle.toString());*/
	        	System.out.println(String.format("%2d: %s", i, r));
	        }
	        
	
	        //read from the command line
	        System.out.println("Enter [0-9]+ to inspect full text, a question to search again, or enter to quit\n>>> ");
	        command = br.readLine();
	        while (command.matches("[0-9]+")) {
	        	Answer rs = question.get(Integer.parseInt(command));
	        	System.out.println("Full text for [" + rs.getTitle() + "]: \n" + rs.getFullText() + "\n");
	        	command = br.readLine();
	        }
    	}
    }
}
