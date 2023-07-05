package TrieUtil;

public class TestTrie {
    public static void main(String[] args){
        Trie test = new Trie();
        test.insert("hehe",1);
        System.out.print(test.search("hehe"));
        test.remove("hehe");
        System.out.print(test.search("hehe"));
    }
}
