package com.mbp.sudoku.util;

public class Map {

    private int id;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Map{" +
                "id=" + id +
                ", time='" + time + '\'' +
                '}';
    }
}
