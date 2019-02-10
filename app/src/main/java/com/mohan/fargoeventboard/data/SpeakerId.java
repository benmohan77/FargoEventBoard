package com.mohan.fargoeventboard.data;

/**
 * Serves as a holder for Speaker IDs for Retrofit callbacks.
 */
public class SpeakerId {

    private int id;

    public SpeakerId(int id){
        this.id = id;
    }

    public SpeakerId(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
