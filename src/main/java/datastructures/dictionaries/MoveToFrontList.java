package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;

import java.util.Collection;
import java.util.Iterator;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private FrontNode front;

    public MoveToFrontList() {
        front = null;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("key or value is null!!");
        }

        FrontNode temp = new FrontNode(key, value);
        if (front == null) {
            front = temp;
            size++;
            return null;
        }

        temp.next = front;
        front = temp;

        V val = null;
        FrontNode cur = front;
        while (cur.next != null) {
            if (cur.next.key.equals(key)) {
                val = cur.next.value;
                cur.next = cur.next.next;
                size--;
                break;
            }
            cur = cur.next;
        }
        size++;
        return val;

    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null!!");
        }
        if (front == null) {
            return null;
        }
        if (front.key.equals(key)) {
            return front.value;
        }
        FrontNode temp = front;
        while (temp.next != null) {
            if (temp.next.key.equals(key)) {
                V val = temp.next.value;
                FrontNode n = temp.next;
                temp.next = temp.next.next;
                n.next = front;
                front = n;
                return val;
            }
            temp = temp.next;
        }
        return null;
    }

    /*@Override
    public Iterator<Item<K, V>> iterator() {

    }*/
    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontList.FrontIterator();
    }

    private class FrontIterator extends SimpleIterator<Item<K, V>> {
        private MoveToFrontList.FrontNode current;

        public FrontIterator() {
//            if (MoveToFrontList.this.front != null) {
//                this.current = MoveToFrontList.this.front;
//            }
            this.current = MoveToFrontList.this.front;
        }

        @Override
        public boolean hasNext() {
            //return this.current.next != null;
            return this.current != null;
        }

        @Override
        public Item<K, V> next() {
            //Item<K, V> it = new Item(current.next.key, current.next.value);
            Item<K, V> it = new Item(current.key, current.value);
            current = current.next;
            return it;
        }
    }

    private class FrontNode {
        public K key;
        public V value;
        public FrontNode next;

        public FrontNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public FrontNode(K key, V value, FrontNode next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
