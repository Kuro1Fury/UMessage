package p2.wordcorrector;

import cse332.datastructures.containers.Item;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.HashTrieMap;

import java.util.Iterator;

public class AutocompleteTrie extends HashTrieMap<Character, AlphabeticString, Integer> {

    public AutocompleteTrie() {
        super(AlphabeticString.class);
    }

    public String autocomplete(String key) {
        @SuppressWarnings("unchecked")
        HashTrieNode current = (HashTrieNode) this.root;
        for (Character item : key.toCharArray()) {
//            if (!current.pointers.containsKey(item)) {
//                return null;
//            }
//            else {
//                current = current.pointers.get(item);
//            }
            if (current.pointers.find(item) == null) {
                return null;
            }
            else {
                current = current.pointers.find(item);
            }
        }

        StringBuilder result = new StringBuilder(key);

        while (current.pointers.size() == 1) {
            if (current.value != null) {
                return null;
            }
//            result.append(current.pointers.keySet().iterator().next());
//            current = current.pointers.values().iterator().next();
            Iterator itr = current.pointers.iterator();
            Item<Character, HashTrieNode> it = (Item<Character, HashTrieNode>)itr.next();
            result.append(it.key);
            current = it.value;
        }

        if (current.pointers.size() != 0) {
            return result.toString();
        }
        return result.toString();
    }
}