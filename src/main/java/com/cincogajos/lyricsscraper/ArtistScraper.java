package com.cincogajos.lyricsscraper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.RE;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ArtistScraper {

    private final static String BASE_URL = "https://www.azlyrics.com/";

    private final static String RESULT_FILE_PATH_BASE = "/Users/brunofgo/projects/lyricsscraper/";

    private static Logger LOGGER = LoggerFactory.getLogger(ArtistScraper.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void getArtist(final String artistHomePage) throws IOException, InterruptedException {

        final Document doc = Jsoup.connect(artistHomePage).get();

        final String artistName = doc.select("h1").text().replace(" Lyrics", "");
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
                final Song song = SongScraper.getSong(songUrl);
                try {
                    currentSongList.add(song);
                } catch (Exception e){
                    LOGGER.error("Error while fetching song {}", songUrl, e);
                }
            }
            Thread.sleep(10000);
        }
        final Album album = new Album(currentAlbumTitle, currentSongList);
        artist.addAlbum(album);

        File resultFile = new File("/Users/brunofgo/projects/lyricsscraper/" + artist.getName().replace(" ", "_").toLowerCase() + ".json");
        resultFile.createNewFile();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.writeValue(resultFile, artist);
    }
}
