package com.cincogajos.lyricsscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class SongScraper {

    public static Song getSong(final String songPageUrl) throws IOException {

        final Document doc = Jsoup.connect(songPageUrl).get();

        final String songTitle = doc.select(".ringtone + b").text().replace("\"", "");
        final String rawHtmlLyric = doc.select(".ringtone ~ div").first().toString();

        return new Song(songTitle, rawHtmlLyric);
    }
}
