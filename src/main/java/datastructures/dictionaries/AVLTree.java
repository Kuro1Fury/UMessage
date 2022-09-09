package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.ArrayStack;

import java.lang.reflect.Array;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {
    private boolean DEBUG = false;

    public class AVLNode extends BSTNode {
        public int height;

        public AVLNode(K key, V value, int h) {
            super(key, value);
            height = h;
        }
    }

    public AVLTree() {
        super();
    }

    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V old = find(key);
        if (old != null) {
            replaceOld(key, value, (AVLNode) root);
        } else {
            insertHelper(key, value);
            size += 1;
            checkAVL();
        }

        return old;
    }

    private void replaceOld(K key, V value, AVLNode cur) {
        if (cur.key.equals(key)) {
            cur.value = value;
            return;
        }
        if (cur.key.compareTo(key) > 0) {
            replaceOld(key, value, (AVLNode) cur.children[0]);
        } else {
            replaceOld(key, value, (AVLNode) cur.children[1]);
        }
    }

    private void insertHelper(K key, V value) {
        // Store the path
        ArrayStack<AVLNode> stk = new ArrayStack<AVLNode>();

        AVLNode cur = (AVLNode) root;

        while (cur != null) {
            stk.add(cur);
            if (cur.key.compareTo(key) > 0) {
                cur = (AVLNode) cur.children[0];
            } else {
                cur = (AVLNode) cur.children[1];
            }
        }
        cur = new AVLNode(key, value, 0);

        if (root == null) {
            root = cur;
            return;
        }

        // add the new leaf
        AVLNode temp = stk.peek();
        if (cur.key.compareTo(temp.key) < 0) {
            temp.children[0] = cur;
        } else {
            temp.children[1] = cur;
        }

        int heightL = 0;
        int heightR = 0;

        AVLNode problemNode = null;
        while(stk.size() > 0) {
            AVLNode p = stk.next();
            heightL = height((AVLNode) p.children[0]);
            heightR = height((AVLNode) p.children[1]);

            // If imbalanced:
            if (Math.abs(heightL - heightR) > 1) {
                problemNode = p;
                break;
            }
            p.height = Math.max(height((AVLNode) p.children[1]), height((AVLNode)p.children[0])) + 1;
        }

        if (problemNode != null) {
            // Need to rotation:
            if (heightL > heightR) {
                AVLNode p = (AVLNode) problemNode.children[0];
                heightL = height((AVLNode) p.children[0]);
                heightR = height((AVLNode) p.children[1]);
                if (heightL >= heightR) {
                    match(stk, RotateWithLeft(problemNode));
                    //checkAVL();
                } else {
                    match(stk, DoubleRotateWithLeft(problemNode));
                    //checkAVL();
                }
            } else {
                AVLNode p = (AVLNode) problemNode.children[1];
                heightL = height((AVLNode) p.children[0]);
                heightR = height((AVLNode) p.children[1]);
                if (heightR >= heightL) {
                    match(stk, RotateWithRight(problemNode));
                    //checkAVL();
                } else {
                    match(stk, DoubleRotateWithRight(problemNode));
                    //checkAVL();
                }
            }
        }
    }

    public AVLNode RotateWithLeft(AVLNode root) {
        AVLNode temp = (AVLNode) root.children[0];
        root.children[0] = temp.children[1];
        temp.children[1] = root;
        root.height = Math.max(height((AVLNode)root.children[1]), height((AVLNode)root.children[0])) + 1;
        temp.height = Math.max(height((AVLNode)temp.children[1]), height((AVLNode)temp.children[0])) + 1;
        root = temp;
        return root;
    }

    public AVLNode DoubleRotateWithLeft(AVLNode root) {
        root.children[0] = RotateWithRight((AVLNode) root.children[0]);
        root = RotateWithLeft(root);
        return root;
    }

    public AVLNode RotateWithRight(AVLNode root) {
        AVLNode temp = (AVLNode) root.children[1];
        root.children[1] = temp.children[0];
        temp.children[0] = root;
        root.height = Math.max(height((AVLNode)root.children[1]), height((AVLNode)root.children[0])) + 1;
        temp.height = Math.max(height((AVLNode)temp.children[1]), height((AVLNode)temp.children[0])) + 1;
        root = temp;
        return root;
    }

    public AVLNode DoubleRotateWithRight(AVLNode root) {
        root.children[1] = RotateWithLeft((AVLNode) root.children[1]);
        root = RotateWithRight(root);
        return root;
    }


    private void match(ArrayStack<AVLNode> stk, AVLNode child) {
        if (stk.size() == 0) {
            root = child;
            return;
        }
        AVLNode parent = stk.peek();
        if (child.key.compareTo(parent.key) < 0) {
            parent.children[0] = child;
        } else {
            parent.children[1] = child;
        }
    }

    private int height(AVLNode n) {
        if (n == null) {
            return -1;
        }
        return n.height;
    }

    private void checkAVL() {
        if (!DEBUG) {
            return;
        }
        checkAVLHelper((AVLNode) root);
    }

    private void checkAVLHelper(AVLNode root) {
        if (root == null) {
            return;
        }
        if (!check(root)) {
            throw new IllegalArgumentException("This is not a valid AVL Tree!!!");
        }
        checkAVLHelper((AVLNode) root.children[0]);
        checkAVLHelper((AVLNode) root.children[1]);
    }


    private int getAbsHeight(AVLNode root) {
        if (root == null) {
            return -1;
        }
        return 1 + Math.max(getAbsHeight((AVLNode) root.children[0]), getAbsHeight((AVLNode) root.children[1]));
    }

    private boolean check(AVLNode root) {
        if (root == null) {
            return true;
        }
        int L = getAbsHeight((AVLNode) root.children[0]);
        int R = getAbsHeight((AVLNode) root.children[1]);
        if (L != height((AVLNode) root.children[0])) {
            System.out.println("Height false!!!");
        }
        if (R != height((AVLNode) root.children[1])) {
            System.out.println("Height false!!!");
        }
        //System.out.println("Real LH:" + L + " Real RH:" + R + " Stored L:" + height((AVLNode) root.children[0]) + " Stored R:" + height((AVLNode) root.children[1]));
        if (Math.abs(L - R) <= 1) {
            return true;
        }
        return false;
    }

}
