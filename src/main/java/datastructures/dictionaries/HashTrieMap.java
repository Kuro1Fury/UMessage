package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import datastructures.worklists.ArrayStack;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>, HashTrieNode> {

        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(() -> new MoveToFrontList<>());
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            ArrayStack<Entry<A, HashTrieNode>> arrayEntry = new ArrayStack<>();

            for (Item<A, HashTrieNode> item: this.pointers) {
                arrayEntry.add(new AbstractMap.SimpleEntry<>(item.key, item.value));
            }
            return arrayEntry.iterator();
        }
    }


    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        if (value == null || key == null) {
            throw new IllegalArgumentException();
        }
        Iterator<A> it = key.iterator();
        HashTrieNode cur = (HashTrieNode) this.root;
        while (it.hasNext()) {
            A temp = it.next();
//            if (!cur.pointers.containsKey(temp)) {
//                cur.pointers.put(temp, new HashTrieNode(null));
//            }
//            cur = cur.pointers.get(temp);
            if (cur.pointers.find(temp) == null) {
                cur.pointers.insert(temp, new HashTrieNode(null));
            }
            cur = cur.pointers.find(temp);
        }
        V pre = cur.value;
        cur.value = value;
        if (pre == null) {
            size++;
        }
        return pre;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Iterator<A> it = key.iterator();
        HashTrieNode cur = (HashTrieNode) root;
        while (it.hasNext()) {
            A temp = it.next();
//            if (!cur.pointers.containsKey(temp)) {
//                return null;
//            }
//            cur = cur.pointers.get(temp);
            if (cur.pointers.find(temp) == null) {
                return null;
            }
            cur = cur.pointers.find(temp);
        }
        return cur.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Iterator<A> it = key.iterator();
        HashTrieNode cur = (HashTrieNode) root;
        while (it.hasNext()) {
            A temp = it.next();
//            if (!cur.pointers.containsKey(temp)) {
//                return false;
//            }
//            cur = cur.pointers.get(temp);
            if (cur.pointers.find(temp) == null) {
                return false;
            }
            cur = cur.pointers.find(temp);
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if (!findPrefix(key)) {
            return;
        }
        delete((HashTrieNode) root, key.iterator());
        size--;
    }

    private void delete(HashTrieNode cur, Iterator<A> it) {
//        if (!it.hasNext()) {
//            cur.value = null;
//            return;
//        }
//        A temp = it.next();
//        delete(cur.pointers.get(temp), it);
//        if (cur.pointers.get(temp).value == null && cur.pointers.get(temp).pointers.isEmpty()) {
//            cur.pointers.remove(temp);
//        }
//        delete(cur.pointers.find(temp), it);
//        if (cur.pointers.find(temp).value == null && cur.pointers.find(temp).pointers.isEmpty()) {
//            // Trow new No..exception
//            cur.pointers.delete(temp);
//        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
        //this.root = new HashTrieNode();
    }
}
