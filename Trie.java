import java.io.*;
import java.util.*;
import java.lang.*;

public class Trie {
    /*
    make max a global variable and then set it in suggestions and delete it there
    figure out for loop for search
    what to do about actually inserting word into hashmap
    */
    // node class
    private static class TrieNode {
        boolean isWord;
        String word;
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    }

    // root node
    private TrieNode root = new TrieNode();

    // method to implement
    public HashMap<String, Integer> suggestions(String target, int dist, int max)
    {
        HashMap<String, Integer> words = new HashMap<String, Integer>();

        if(contains(target)){
        	words.put(target, 0);
        }
        HashMap<String, Integer> simWords = new HashMap<>();
        simWords = search(target, dist); 
        return simWords;
    }
    
    public HashMap<String, Integer> search(String target, int d){
    	ArrayList<Integer> current = new ArrayList<>();
    	for(int i = 0; i < target.length(); i++){
    		current.add(i);
    	}
    	HashMap<String, Integer> fin = new HashMap<>();
    	TreeMap<Integer, ArrayList<String>> result = new TreeMap<>();
    	//idk about this loop here
    	for(int letter = 0; letter < root.children.size(); letter++){
    		recursiveSearch(root.children(letter), letter, target, current, fin, d);
    	}
    	// change into a hashmap from a treemap here or in suggestions idk which yet
    	return fin;
    }
    
    private void recursiveSearch(TrieNode n, int letter, String target, ArrayList<Integer> previous, TreeMap<Integer, ArrayList<String>> result, int d){
    	int insert, delete, replace;
    	int columns = target.length() + 1;
    	TrieNode cur = n;
    	ArrayList<Integer> current = new ArrayList<>();
    	for(int column = 0; column < columns; column++){
    		insert = current.get(column-1) + 1;
    		delete = previous.get(column) + 1;
    		if(target.charAt(column - 1) != letter){
    			replace = previous.get(column-1) + 1;
    		}else{
    			replace = previous.get(column-1);
    		}
    	}
    	int min = Math.min(delete, insert);
    	min = Math.min(min, replace);
    	current.add(min);
    	if(current.get(current.size()-1) <= d && n.isWord){
    		ArrayList<String> wordsAtD = new ArrayList<>();
    		wordsAtD = result.get(current.get(current.size() -1));
    		wordsAtD.add(n.word);
    		result.put( current.get(current.size() -1), wordsAtD);
    	}
    	// need a way to find the min of the arraylist cant call min function
    	if(Math.min(current) <= d){
    		recursiveSearch(n.children.get(letter), letter, target, current, result, d);
    	}
    }

    // method to add a string
    /* make a node.word at the end of the word*/
    public boolean add(String s) {
        s = s.trim().toLowerCase();

        TrieNode current = root;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                TrieNode child = current.children.get(c);
                if (child == null) {
                    child = new TrieNode();
                    current.children.put(c, child);
                }
                current = child;
            }
        }
        if(current.isWord){
        	current.word = s;
        }
        if (current.isWord)
            return false;
        
        current.isWord = true;
        return true;
    }

    // method to check if a string has been added
    public boolean contains(String s) {
        s = s.trim().toLowerCase();

        TrieNode current = root;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                TrieNode child = current.children.get(c);
                if (child == null) {
                    return false;
                }
                current = child;
            }
        }

        return current.isWord;
    }

    // empty constructor
    public Trie() {
        super();
    }

    // constructor to add words from a stream, like standard input
    public Trie(InputStream source) {
        Scanner scan = new Scanner(source);
        addWords(scan);
        scan.close();
    }

    // constructor to add words from a file
    public Trie(String filename) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        addWords(scan);
        scan.close();
    }

    // helper function to add words from a scanner
    private void addWords(Scanner scan) {
        while (scan.hasNext()) {
            add(scan.next());
        }
    }

    // main function for testing
    public static void main(String[] args) {

        Trie dictionary;

        if (args.length > 0) {
            try {
                dictionary = new Trie(args[0]);
            } catch (FileNotFoundException e) {
                System.err.printf("could not open file %s for reading\n", args[0]);
                return;
            }
        }
        else {
            dictionary = new Trie(System.in);
        }

        System.out.println(dictionary.contains("cat"));
    }

}


/*
method to find new words
method to check total distance
*/
 
