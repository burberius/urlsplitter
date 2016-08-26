package net.troja.urlsplitter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class UrlSplitterTest {
    private static final String PROTOCOL_MAILTO = "mailto";
    private static final String PROTOCOL_FILE = "file";
    private static final String EMAIL = "donald@mailinator.com";
    private static final String IP = "127.0.0.1";
    private static final String DOMAIN = "www.troja.net";
    private static final String DOMAIN_CAPS = "wWw.TrOjA.nEt";
    private static final String SOME_PATH = "a-long/path";
    private static final String USER = "resu";
    private static final String PASSWORD = "topsecret";
    private static final String FILE = "c:/verzeichnis/unterverzeichnis/datei.txt";

    private UrlSplitter classToTest;

    @Before
    public void setUp() {
        classToTest = new UrlSplitter();
    }

    @Test
    public void nullInput() {
        classToTest.split(null);

        assertThat(classToTest.getProtocol(), is(equalTo("")));
        assertThat(classToTest.getHost(), is(equalTo("")));
    }

    @Test
    public void emptyInput() {
        classToTest.split("");

        assertThat(classToTest.getProtocol(), is(equalTo("")));
        assertThat(classToTest.getHost(), is(equalTo("")));
    }

    @Test
    public void ipOnly() {
        classToTest.split(IP);

        assertThat(classToTest.getHost(), is(equalTo(IP)));
    }

    @Test
    public void domainOnly() {
        classToTest.split(DOMAIN);

        assertThat(classToTest.getHost(), is(equalTo(DOMAIN)));
    }

    @Test
    public void ignoreCaps() {
        classToTest.split(DOMAIN_CAPS);

        assertThat(classToTest.getHost(), is(equalTo(DOMAIN)));
    }

    @Test
    public void domainWithPort() {
        classToTest.split(DOMAIN + ":" + UrlSplitter.PORT_HTTPS);

        assertThat(classToTest.getHost(), is(equalTo(DOMAIN)));
        assertThat(classToTest.getPort(), is(equalTo(UrlSplitter.PORT_HTTPS)));
    }

    @Test
    public void httpWithoutPortIs80() {
        classToTest.split(UrlSplitter.PROTOCOL_HTTP + "://" + DOMAIN);

        assertThat(classToTest.getProtocol(), is(equalTo(UrlSplitter.PROTOCOL_HTTP)));
        assertThat(classToTest.getHost(), is(equalTo(DOMAIN)));
        assertThat(classToTest.getPort(), is(equalTo(UrlSplitter.PORT_HTTP)));
    }

    @Test
    public void notHttpWithoutPortIsNull() {
        classToTest.split(DOMAIN);

        assertThat(classToTest.getHost(), is(equalTo(DOMAIN)));
        assertThat(classToTest.getPort(), is(equalTo(null)));
    }

    @Test
    public void domainWithPath() {
        classToTest.split(UrlSplitter.PROTOCOL_HTTP + "://" + DOMAIN + "/" + SOME_PATH);

        assertThat(classToTest.getProtocol(), is(equalTo(UrlSplitter.PROTOCOL_HTTP)));
        assertThat(classToTest.getHost(), is(equalTo(DOMAIN)));
        assertThat(classToTest.getPath(), is(equalTo(SOME_PATH)));
    }

    @Test
    public void domainWithUser() {
        classToTest.split(UrlSplitter.PROTOCOL_HTTP + "://" + USER + "@" + DOMAIN);

        assertThat(classToTest.getProtocol(), is(equalTo(UrlSplitter.PROTOCOL_HTTP)));
        assertThat(classToTest.getHost(), is(equalTo(DOMAIN)));
        assertThat(classToTest.getUser(), is(equalTo(USER)));
    }

    @Test
    public void domainWithUserPassword() {
        classToTest.split(UrlSplitter.PROTOCOL_HTTP + "://" + USER + ":" + PASSWORD + "@" + DOMAIN);

        assertThat(classToTest.getHost(), is(equalTo(DOMAIN)));
        assertThat(classToTest.getUser(), is(equalTo(USER)));
        assertThat(classToTest.getPassword(), is(equalTo(PASSWORD)));
    }

    @Test
    public void httpsWithoutPortIs443() {
        classToTest.split(UrlSplitter.PROTOCOL_HTTPS + "://" + DOMAIN);

        assertThat(classToTest.getProtocol(), is(equalTo(UrlSplitter.PROTOCOL_HTTPS)));
        assertThat(classToTest.getHost(), is(equalTo(DOMAIN)));
        assertThat(classToTest.getPort(), is(equalTo(UrlSplitter.PORT_HTTPS)));
    }

    @Test
    public void eachRunResetsResult() {
        classToTest.split(UrlSplitter.PROTOCOL_HTTP + "://" + DOMAIN);
        classToTest.split(DOMAIN);

        assertThat(classToTest.getProtocol(), not(equalTo(UrlSplitter.PROTOCOL_HTTP)));
    }

    @Test
    public void ftp() {
        classToTest.split(UrlSplitter.PROTOCOL_FTP + "://" + USER + ":" + PASSWORD + "@" + DOMAIN);

        assertThat(classToTest.getProtocol(), is(equalTo(UrlSplitter.PROTOCOL_FTP)));
        assertThat(classToTest.getPassword(), is(equalTo(PASSWORD)));
        assertThat(classToTest.getPort(), is(equalTo(UrlSplitter.PORT_FTP)));
    }

    @Test
    public void file() {
        classToTest.split(PROTOCOL_FILE + ":///" + FILE);

        assertThat(classToTest.getProtocol(), is(equalTo(PROTOCOL_FILE)));
        assertThat(classToTest.getPath(), is(equalTo(FILE)));
    }

    @Test
    public void mailto() {
        classToTest.split(PROTOCOL_MAILTO + ":" + EMAIL);

        assertThat(classToTest.getProtocol(), is(equalTo(PROTOCOL_MAILTO)));
        assertThat(classToTest.getPath(), is(equalTo(EMAIL)));
    }
}
