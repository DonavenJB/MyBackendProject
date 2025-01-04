package com.bruce.backend.common;

import java.util.LinkedList;
import java.util.List;

public class StringStack {

    protected String string;

    protected String delimeter;

    public StringStack(String string) {
        this(string, " ");
    }

    public StringStack(String string1, String string2) {
        this.string = string1;
        this.delimeter = string2;
    }

    public void setDelimeter(String string) {
        this.delimeter = string;
    }
}