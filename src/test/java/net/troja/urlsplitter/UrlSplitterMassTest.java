package net.troja.urlsplitter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UrlSplitterMassTest {
    private final UrlSplitter classToTest = new UrlSplitter();

    private static final String INPUT_PATH = "inputurls.txt";

    private URI inputLocation;

    @Before
    public void setUp() throws URISyntaxException {
        inputLocation = this.getClass().getClassLoader().getResource(INPUT_PATH).toURI();
    }

    @Test
    public void run() throws IOException {
        final List<String> urls = Files.readAllLines(Paths.get(inputLocation));

        for (final String url : urls) {
            final String cleanUrl = URLDecoder.decode(url, "UTF-8");
            classToTest.split(cleanUrl);
            assertThat(classToTest.buildUrl(), is(equalTo(cleanUrl)));
        }
    }
}
