package de.roth.jsona.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PlayList implements Serializable {

    private String atomicId;
    private String name;
    private ArrayList<MusicListItem> items;

    public PlayList(String atomicId, String name) {
        this.atomicId = atomicId;
        this.name = name;
        this.items = new ArrayList<MusicListItem>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MusicListItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MusicListItem> items) {
        this.items = items;
    }

    public String getAtomicId() {
        return atomicId;
    }

    public void setAtomicId(String atomicId) {
        this.atomicId = atomicId;
    }

    @Override
    public String toString() {
        return name;
    }
}