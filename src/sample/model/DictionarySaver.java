package sample.model;

import java.util.ArrayList;
import java.util.Collections;

public class DictionarySaver {

    private Node root;

    public DictionarySaver() {
        this.root = new Node();
        root.setWord(new Word("NullDefault","NullDefault"));
    }
    public DictionarySaver(String name) {
        this.root = new Node();
        root.setWord(new Word(name,"NullDefault"));
    }


    public void put(Word word) {
        Node stand = this.root;
        String temp = word.word.toLowerCase();
        StringBuilder wordName = new StringBuilder();
        int n = temp.length();
        for (int i=0;i<n;i++) {
            char c = temp.charAt(i);
            if (c<='z' && c>='a') wordName.append(c);
        }
        n = wordName.length();
        for (int i=0; i<n;i++) {
            if (i==(n-1)) {
                if (stand.nodeList[wordName.charAt(i)-'a'] == null) {
                    stand.nodeList[wordName.charAt(i)-'a'] = new Node();
                    stand.nodeList[wordName.charAt(i)-'a'].word = word;
                } else {
                    stand.nodeList[wordName.charAt(i)-'a'].word = word;
                }
            } else {
                if (stand.nodeList[wordName.charAt(i)-'a']!= null)
                    stand = stand.nodeList[wordName.charAt(i)-'a'];
                else {
                    stand.nodeList[wordName.charAt(i)-'a'] = new Node();
                    stand = stand.nodeList[wordName.charAt(i)-'a'];
                }
            }
        }
    }

    public void modifyWord(String word,String pronounce,String description) {
        word = word.toLowerCase();
        StringBuilder wordBuilder = new StringBuilder();
        int n = word.length();
        for (int i=0;i<n;i++) {
            char c = word.charAt(i);
            if (c<='z' && c>='a') wordBuilder.append(c);
        }
        Node stand = this.root;
        n = wordBuilder.length();
        for (int i=0;i<n;i++) {
            stand = stand.nodeList[wordBuilder.charAt(i)-'a'];
        }
        stand.word.pronounce = pronounce;
        stand.word.meaning = description;

    }

    public ArrayList<Word> getPrefix(String prefix) {
        ArrayList<Word> result = new ArrayList<>();
        if (prefix=="") {
            for (int i=0;i<26;i++) {
                if (root.nodeList[i]!=null)
                    deepSearch(root.nodeList[i],result);
            }
            return result;
        }
        Node stand = this.root;
        prefix = prefix.toLowerCase();
        StringBuilder prefixes = new StringBuilder();
        int n = prefix.length();
        for (int i=0;i<n;i++) {
            char c = prefix.charAt(i);
            if (c<='z' && c>='a') prefixes.append(c);
        }
        n = prefixes.length();
        for (int i=0;i<n;i++) {
            if (stand.nodeList[prefixes.charAt(i)-'a'] == null) return result;
            else {
                stand = stand.nodeList[prefixes.charAt(i)-'a'];
            }
        }

        deepSearch(stand,result);


        Collections.sort(result, Word.wordComparator);


        return result;
    }

    private void deepSearch(Node stand,ArrayList<Word> result) {
        if (stand.word!=null) {
            result.add(stand.word);
        }
        for (int i=0;i<26;i++) {
            if (stand.nodeList[i] != null) {
                deepSearch(stand.nodeList[i],result);
            }
        }
    }

    public boolean isContain(String word) {
        word = word.toLowerCase();
        StringBuilder wordBuilder = new StringBuilder();
        int n = word.length();
        for (int i=0;i<n;i++) {
            char c = word.charAt(i);
            if (c<='z' && c>='a') wordBuilder.append(c);
        }
        Node stand = this.root;
        n = wordBuilder.length();
        for (int i=0;i<n;i++) {
            if (i==n-1) {
                if (stand.nodeList[wordBuilder.charAt(i)-'a']==null) return false;
                else if (stand.nodeList[wordBuilder.charAt(i)-'a'].word == null) return false;
                else return true;
            } else {
                if (stand.nodeList[wordBuilder.charAt(i)-'a'] == null) return false;
                else stand = stand.nodeList[wordBuilder.charAt(i)-'a'];
            }
        }
        return false;
    }


    private class Node {
        public Node[] nodeList;
        public Word word;

        public Node() {
            nodeList = new Node[26];
            word = null;
        }

        public void setWord(Word word) {
            this.word = word;
        }


    }
}
