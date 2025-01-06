package com.bruce.backend.common;

public class StringStack {

    protected String string;
    protected String delimeter;

    public StringStack(String string1, String string2) {
        this.string = string1;
        this.delimeter = string2;
    }

    public String toString() {
        return this.string;
    }

    public String pop() {
        int i = this.string.lastIndexOf(this.delimeter);
        if (i > -1) {
            String str1 = this.string.substring(i + 1, this.string.length());
            this.string = this.string.substring(0, i);
            return str1;
        }
        String str = this.string;
        this.string = "";
        return str;
    }
}
