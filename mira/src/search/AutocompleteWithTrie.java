package search;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import mira.Condition;
import mira.Parse;

public class AutocompleteWithTrie {
	
	 public static void main(String[] args) throws IOException {            
            Trie t = new Trie();  
            Map<String, Condition> l = Parse.loadData();
            for (Map.Entry<String, Condition> entry : l.entrySet()) {
                System.out.println(entry.getKey() + "/" + entry.getValue());
                t.insert(entry.getKey());
            }
          
			List<String> a= t.autocomplete("B");
			for (int i = 0; i < a.size(); i++) {
				System.out.println(a.get(i));
			}
	  }
}