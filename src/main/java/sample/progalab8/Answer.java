package sample.progalab8;

import java.io.Serializable;
import java.util.LinkedList;

public class Answer implements Serializable {
    private String s;
    private LinkedList list;

    public Answer(String s, LinkedList list){
        this.s = s;
        this.list = list;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public LinkedList getList() {
        return list;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "s='" + s + '\'' +
                "list" + list.size() +
                '}';
    }
}
