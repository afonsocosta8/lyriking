package com.cincogajos.lyricsscraper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Main {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 2) {
            LOGGER.error("First argument should be the link and second argument the interval for requests in seconds.");
        }
        final String url = args[0];
        final int interval = Integer.parseInt(args[1]);
        LOGGER.info("I Will proccess {} with an interval of {}", url, interval);

        final Artist artist = ArtistScraper.getArtist(url, interval);

        File resultFile = new File(artist.getName().replace(" ", "_").toLowerCase() + ".json");
        resultFile.delete();
        resultFile.createNewFile();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.writeValue(resultFile, artist);
    }
}
