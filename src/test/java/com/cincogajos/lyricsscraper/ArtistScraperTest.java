package com.cincogajos.lyricsscraper;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class ArtistScraperTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException, InterruptedException {
        List<String> artistsUrl = ImmutableList.of(
                "https://www.azlyrics.com/a/anderson-paak.html",
                "https://www.azlyrics.com/b/bjthechicagokid.html",
                "https://www.azlyrics.com/s/saba.html"
        );
        for (String url : artistsUrl)
            ArtistScraper.getArtist(url);


    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void someTest() throws IOException, InterruptedException {

        final ObjectMapper objectMapper = new ObjectMapper();

        final Artist artist = new Artist("Artist Name");
        final List<Song> songs = ImmutableList.of(
                new Song("some lyrics", "some title"),
                new Song("some lyrics", "some title"),
                new Song("some lyrics", "some title"),
                new Song("some lyrics", "some title"),
                new Song("some lyrics", "some title")
        );
        final Album album1 = new Album("algum_name", songs);
        artist.addAlbum(album1);
        File resultFile = new File("/Users/brunofgo/projects/lyricsscraper/" + artist.getName() + ".json");
        resultFile.createNewFile();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.writeValue(resultFile, artist);

    }
}
