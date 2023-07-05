package Model;

public class Word {
    private int id;

    public Word() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Word(int id, String word_target, String phonetic, String word_explain) {
        this.id = id;
        this.word_target = word_target;
        this.phonetic = phonetic;
        this.word_explain = word_explain;
    }

    private String word_target;
    private String phonetic;
    private String word_explain;

    public Word(String word_target, String phonetic, String word_explain) {
        this.word_target = word_target;
        this.phonetic = phonetic;
        this.word_explain = word_explain;
    }

    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
}
