package ru.hoty.utils;

import java.io.Serializable;

public class Answer implements Serializable{
    private String ans = null;
    private Object obj = null;

    public Answer(String ans, Object obj) {
        this.ans = ans;
        this.obj = obj;
    }

    public Answer(String ans) {
        this.ans = ans;
    }

    public boolean addAnswer(String ans) {
        if(ans == null || ans.equals("\0")) {
            return false;
        }
        this.ans += ans + "\n";
        return true;
    }

    public String getAnswer() {
        return ans;
    }

    public Object getObject() {
        return obj;
    }
}
