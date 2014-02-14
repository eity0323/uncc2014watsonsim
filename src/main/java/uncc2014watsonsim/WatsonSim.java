package uncc2014watsonsim;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phani Rahul
 */
public class WatsonSim {

    /**
     * @param args the command line arguments
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        final String indri_index = "/home/sean/deepqa/indri_index";
        final String lucene_index = "/home/sean/deepqa/lucene_index";
        final String luceneSearchField = "text";

        //read from the command line
        System.out.println("Enter the jeopardy text: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Question question = new Question(br.readLine());

        HashSet<String> ignoreSet = new HashSet<String>();
        ignoreSet.add("J! Archive");
        ignoreSet.add("Jeopardy");
        
        //initialize indri and query
        IndriSearch in = new IndriSearch();
        in.setIndex(indri_index);
        in.runQuery(question.text);
        for (int rank=0; rank < in.getResultCount(); rank++) {
        	question.add(new ResultSet(
    			in.getTitle(rank),
    			"indri",
    			rank,
    			in.getScore(rank),
    			false // correct? We don't know yet.
    			));
        }

        //initialize and query lucene
        LuceneSearch lu = new LuceneSearch(luceneSearchField);
        lu.setIndex(lucene_index);
        lu.runQuery(question.text);
        for (int rank=0; rank < in.getResultCount(); rank++) {
        	question.add(new ResultSet(
    			lu.getTitle(rank),
    			"lucene",
    			rank,
    			lu.getScore(rank),
    			false // correct? We don't know yet.
    			));
        }

        //initialize google search engine and query.
        WebSearchGoogle go = new WebSearchGoogle();
        go.runQuery(question.text);
        for (int rank=0; rank < in.getResultCount(); rank++) {
        	question.add(new ResultSet(
    			go.getTitle(rank),
    			"google",
    			rank,
    			rank,
    			false // correct? We don't know yet.
    			));
        }

        /*TODO: merge the result sets.
        HashSet<CombinedResult> merged = new HashSet<>();
        HashSet<ResultSet> intersect = new HashSet<>();
        intersect.addAll(indri);
        intersect.retainAll(lucene);
        intersect.retainAll(google);*/
        
        /* TODO: Handle inexact title matches
        HashSet<ResultSet> googleLeftovers = new HashSet<>();
        for (ResultSet rs : googleResult) {
            String[] goTitle = rs.getTitle().split(" ");
            for (CombinedResult every : merged) {
                String[] eveTitle = every.getTitle().split(" ");
                boolean merge = true;
                for (String eT : eveTitle) {
                    boolean exists = false;
                    for (String gT : goTitle) {

                        if (gT.equalsIgnoreCase(eT)) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        merge = false;
                    }
                }
                if (merge) {
                    every.setGoogleText(rs.getTitle());
                    every.setGoogleScore(rs.getScore());
                    every.setGoogleRank(rs.getRank());
                    every.setNormalizedGoogleScore(rs.getNormalizedScore());
                } else {
                    googleLeftovers.add(rs);
                }
            }
        }
        for (ResultSet rs : googleLeftovers) {
            CombinedResult c = new CombinedResult(rs.getTitle(), 0, 0, -1, -1, 0, 0);
            c.setGoogleText(rs.getTitle());
            c.setGoogleScore(rs.getScore());
            c.setGoogleRank(rs.getRank());
            c.setNormalizedGoogleScore(rs.getNormalizedScore());
            merged.add(c);

        }
        */

        //TODO: now finally sort the merged result
        /*ArrayList<CombinedResult> mergedList = new ArrayList<>(merged);
        Collections.sort(mergedList);*/

        //display results
//        for (CombinedResult c : mergedList) {
//            System.out.println(c);
//        }
        //System.out.println(indriResult);
        //System.out.println(luceneResult);
        //System.out.println(googleResult);
        //System.out.println("Done");
        new AverageScorer().test(question);
        for (ResultSet r : question) {
        	System.out.println(String.format("[%01f] %s", r.first("combined").score, r.getTitle()));
        }
    }
}
