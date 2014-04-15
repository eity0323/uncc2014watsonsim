package uncc2014watsonsim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Score {
	public static final int MAX_PASSAGE_COUNT = 50;
	public static final List<String> answer_score_names = new ArrayList<>();
	public static final List<String> passage_score_names = new ArrayList<>();
	
	/** Register the answer score for automatically generated model data
	 * @param name	The ANSWER_SCORE (uppercase, with underscores)
	 * 
	 * This uses n^2 insertion but efficient iteration, because iteration is
	 * much more common.
	 */
	public static void registerAnswerScore(String name) {
		int index = Collections.binarySearch(answer_score_names, name);
		answer_score_names.add(index, name);
	}
	
	/** Register the passage score for automatically generated model data
	 * @param name	The PASSAGE_SCORE (uppercase, with underscores)
	 * 
	 * This uses n^2 insertion but efficient iteration, because iteration is
	 * much more common.
	 */
	public static void registerPassageScore(String name) {
		int index = Collections.binarySearch(passage_score_names, name);
		passage_score_names.add(index, name);
	}
}
