package TrieUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrieNode {
    char data;
    LinkedList<TrieNode> children;
    TrieNode parent;
    boolean isEnd;
    int ID;

    public TrieNode(char c) {
        data = c;
        children = new LinkedList<TrieNode>();
        isEnd = false;
        ID = -1;
    }

    public boolean isEmpty(){
        if(children == null) return true;
        for(TrieNode eachChild : children)
        {
            if(eachChild != null) return false;
        }
        return true;
    }

    public TrieNode getChild(char c) {
        if (children != null)
            for (TrieNode eachChild : children)
                if (eachChild.data == c)
                    return eachChild;
        return null;
    }

    protected List<String> getWords() {
        List<String> list = new ArrayList<String>();
        if (isEnd) {
            list.add(toString());
        }

        if (children != null) {
            for (int i=0; i< children.size(); i++) {
                if (children.get(i) != null) {
                    list.addAll(children.get(i).getWords());
                }
            }
        }
        return list;
    }

    public void setNull(){
        this.children = null;
    }

    public String toString() {
        if (parent == null) {
            return "";
        } else {
            return parent.toString() + new String(new char[] {data});
        }
    }
}