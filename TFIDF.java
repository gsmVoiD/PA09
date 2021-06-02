/*
 * NAME: Raghav Pant
 * PID:  A16671820
 */

import java.io.*;
import java.util.*;

/**
 *
 */
public class TFIDF {
    public static void main(String[] args) throws IOException {
        TFIDF a = new TFIDF("C:\\Users\\ragha\\Documents\\GitHub\\dsc30sp21-startercode\\PA09\\files\\test.txt");
        System.out.println(a.query("quick", 3));
    }
    File file;
    ArrayList<HashMap<String, Integer>> mapHold;
    int numDocs;
    public TFIDF(String path) throws IOException {
        /* TODO */
        file = new File(path);
        mapHold = new ArrayList<>();
        numDocs = 0;
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String[] wds = input.nextLine().toLowerCase(Locale.ROOT).split(" ");
                HashMap<String, Integer> lineMap = new HashMap<>();
                for (String word : wds) {
                    if (lineMap.containsKey(word)) {
                        int origVal = lineMap.get(word);
                        lineMap.replace(word, origVal+1);
                    }
                    else {
                        lineMap.put(word, 1);
                    }
                }
                numDocs++;
                mapHold.add(lineMap);
            }
        }
        catch (IOException e) {
            System.out.println("Uh oh");
        }
    }

    public int numDocuments() {
        /* TODO */
        return numDocs;
    }

    public double getTF(String word, int docID) {
        /* TODO */
        if (word == null) {
            throw new IllegalArgumentException();
        }
        if (docID < 0 || docID >= numDocs) {
            throw new IllegalArgumentException();
        }
        HashMap<String, Integer> a = mapHold.get(docID);
        double wordTotal = 0.0;
        for (int num : a.values()) {
            wordTotal+= num;
        }
        if (a.get(word) == null) {
            return Double.POSITIVE_INFINITY;
        }
        return (a.get(word)) / wordTotal;
    }

    public double getIDF(String word) {
        /* TODO */
        if (word == null) {
            throw new IllegalArgumentException();
        }
        double wordContains = 0.0;
        for (HashMap<String, Integer> map : mapHold) {
            if (map.containsKey(word)) {
                wordContains++;
            }
        }
        return Math.log10(numDocuments() / wordContains);
    }

    public double query(String word, int docID) {
        /* TODO */
        if (getIDF(word) == Double.POSITIVE_INFINITY) {
            return Double.NaN;
        }
        return getIDF(word) * getTF(word, docID);
    }
}
