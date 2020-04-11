package com.cincogajos.lyricsscraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ArtistScraper {

    private final static String BASE_URL = "https://www.azlyrics.com/";

    private static Logger LOGGER = LoggerFactory.getLogger(ArtistScraper.class);

    public static Artist getArtist(final String artistHomePage, int interval) throws IOException, InterruptedException {

        final Document doc = Jsoup.connect(artistHomePage).get();

        final String artistName = doc.select("h1").text().replace(" Lyrics", "");
        LOGGER.info("Processing Artist {}", artistName);

        final Artist artist = new Artist(artistName);

        final Elements elements = doc.getElementsByAttributeValue("id", "listAlbum").select(" > div");

        String currentAlbumTitle = null;
        List<Song> currentSongList = null;
        for (Element element : elements) {
            if (element.hasClass("album")) {
                if (currentAlbumTitle != null) {
                    final Album album = new Album(currentAlbumTitle, currentSongList);
                    artist.addAlbum(album);
                }
                currentAlbumTitle = element.select("b").text().replaceAll("\"", "");
                currentSongList = new LinkedList<>();
                LOGGER.info("Processing Album {}", currentAlbumTitle);
            } else if (element.hasClass("listalbum-item")) {
                final String songUrl = BASE_URL + element.select("a").attr("href").replace("../", "");
                LOGGER.info("Processing Song {}", songUrl);
                try {
                    final Song song = SongScraper.getSong(songUrl);
                    currentSongList.add(song);
                } catch (Exception e){
                    LOGGER.error("Error while fetching song {}", songUrl, e);
                }
            }
            Thread.sleep(interval * 1000);
        }
        final Album album = new Album(currentAlbumTitle, currentSongList);
        artist.addAlbum(album);
        return artist;
    }
}
