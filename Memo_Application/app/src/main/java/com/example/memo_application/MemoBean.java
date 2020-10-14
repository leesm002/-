package com.example.memo_application;

public class MemoBean {
    private int sequenceNumber;
    private String title;
    private String contents;

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
