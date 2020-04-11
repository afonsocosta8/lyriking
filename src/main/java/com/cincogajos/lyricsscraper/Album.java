package com.cincogajos.lyricsscraper;

import java.util.List;
import java.util.Objects;

public class Album {

    final String title;
    final List<Song> songs;

    public Album(String title, List<Song> songs) {
        this.title = title;
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Album{" +
                "title='" + title + '\'' +
                ", songs=" + songs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return title.equals(album.title) &&
                songs.equals(album.songs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, songs);
    }

    public String getTitle() {
        return title;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
