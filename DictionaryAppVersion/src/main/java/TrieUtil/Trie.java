package TrieUtil;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode(' ');
    }


    public void insert(String word, int ID) {
        if (search(word) != -1) {
            return;
        }
        TrieNode current = root;
        TrieNode pre;
        for (char ch : word.toCharArray()) {
            pre = current;
            TrieNode child = current.getChild(ch);
            if (child != null) {
                current = child;
                child.parent = pre;
            } else {
                current.children.add(new TrieNode(ch));
                current = current.getChild(ch);
                current.parent = pre;
            }
        }
        current.isEnd = true;
        current.ID = ID;
    }

    public int search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            if (current.getChild(ch) == null)
                return -1;
            else {
                current = current.getChild(ch);
            }
        }
        return current.ID;
    }

    public void remove(String word) {
        if (search(word) == -1) return;
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        current.ID = -1;
        TrieNode par = current.parent;
        while (current != root) {
            if (current.isEmpty() && current.ID == -1) {
                par.setNull();
            }
            else break;
            current = par;
            par = current.parent;
        }
    }

    public List<String> autocomplete(String prefix) {
        TrieNode lastNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            lastNode = lastNode.getChild(prefix.charAt(i));
            if (lastNode == null)
                return new ArrayList<>();
        }
        return lastNode.getWords();
    }
}