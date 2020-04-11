package com.cincogajos.lyricsscraper;

import java.util.LinkedList;
import java.util.List;

public class Artist {

    final String name;

    final List<Album> albums;


    public Artist(String name) {
        this.name = name;
        this.albums = new LinkedList<>();
    }

    public List<Album> getAlbum() {
        return albums;
    }

    public String getName() {
        return name;
    }

    public void addAlbum(final Album album) {
        albums.add(album);
    }

    @Override
    public String toString() {
        return "ArtistScraper{" +
                "name='" + name + '\'' +
                ", albums=" + albums +
                '}';
    }
}
