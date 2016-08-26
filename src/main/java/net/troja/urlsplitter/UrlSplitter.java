package net.troja.urlsplitter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class UrlSplitter {
    public static final String PROTOCOL_HTTP = "http";
    public static final String PROTOCOL_HTTPS = "https";
    public static final int PORT_HTTP = 80;
    public static final int PORT_HTTPS = 443;

    private String protocol;
    private String user;
    private String password;
    private String host;
    private Integer port;
    private String path;

    private final Pattern protocolPattern;
    private final Pattern userPasswordPattern;
    private final Pattern hostPattern;

    public UrlSplitter() {
        protocolPattern = Pattern.compile("^([a-z]+):/*(.+)$");
        userPasswordPattern = Pattern.compile("^([^:]+):?(.*?)@(.+)$");
        hostPattern = Pattern.compile("^([-.a-z0-9]+):?(\\d*)/?(.*)$");
    }

    public void split(final String url) {
        reset();

        if (url == null) {
            return;
        }

        String work = url.toLowerCase();
        work = extractProtocol(work);
        work = extractUserAndPassword(work);
        work = extractHostPortPath(work);

        if (port == null) {
            if (PROTOCOL_HTTP.equals(protocol)) {
                port = PORT_HTTP;
            } else if (PROTOCOL_HTTPS.equals(protocol)) {
                port = PORT_HTTPS;
            }
        }
    }

    private String extractHostPortPath(final String work) {
        final Matcher matcher = hostPattern.matcher(work);
        if (matcher.matches()) {
            host = matcher.group(1);
            final String portString = matcher.group(2);
            if (!StringUtils.isEmpty(portString)) {
                port = Integer.valueOf(portString);
            }
            path = matcher.group(3);
        }
        return work;
    }

    private String extractUserAndPassword(final String work) {
        final Matcher matcher = userPasswordPattern.matcher(work);
        String result = work;
        if (matcher.matches()) {
            user = matcher.group(1);
            password = matcher.group(2);
            result = matcher.group(3);
        }
        return result;
    }

    private String extractProtocol(final String work) {
        final Matcher matcher = protocolPattern.matcher(work);
        String result = work;
        if (matcher.matches()) {
            protocol = matcher.group(1);
            result = matcher.group(2);
        }
        return result;
    }

    private void reset() {
        protocol = "";
        user = "";
        password = "";
        host = "";
        port = null;
        path = "";
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }
}
