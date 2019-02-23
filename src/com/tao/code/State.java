package com.tao.code;

public enum State {
    FREE(0, "free"),
    BUSY(1, "busy");

    State(int code, String state) {
        this.code = code;
        this.state = state;
    }

    private int code;
    private String state;

    public int getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}
