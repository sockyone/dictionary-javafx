package sample.model;

import java.util.Comparator;

public class Word  {
    public int id;
    public String word;
    public String meaning;
    public String pronounce;

    public static Comparator<Word> wordComparator = new Comparator<Word>() {
        @Override
        public int compare(Word o1, Word o2) {
            if (o1.word.length() < o2.word.length()) return -1;
            else if (o1.word.length() > o2.word.length()) return 1;
            else return o1.word.compareTo(o2.word);
        }
    };

    public Word() {
        this("default","default","default");
    }

    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        this.id = -1;
    }

    public Word(String word, String meaning,String pronounce) {
        this.word = word;
        this.meaning = meaning;
        this.pronounce = pronounce;
        this.id = -1;
    }

    public Word(String word, String meaning,String pronounce, int id) {
        this.word = word;
        this.meaning = meaning;
        this.pronounce = pronounce;
        this.id = id;
    }



    public String toString() {
        return this.word;
    }

}
