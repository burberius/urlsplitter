package net.troja.urlsplitter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class UrlSplitter {
    public static final String PROTOCOL_HTTP = "http";
    public static final String PROTOCOL_HTTPS = "https";
    public static final String PROTOCOL_FTP = "ftp";
    public static final int PORT_HTTP = 80;
    public static final int PORT_HTTPS = 443;
    public static final int PORT_FTP = 21;
    private static final List<String> PROTOCOLS = Arrays.asList(new String[] { PROTOCOL_HTTP, PROTOCOL_HTTPS, PROTOCOL_FTP });

    private String protocol;
    private String user;
    private String password;
    private String host;
    private Integer port;
    private String path;
    private String fragment;
    private String query;

    private final Pattern protocolPattern;
    private final Pattern userPasswordPattern;
    private final Pattern hostPattern;
    private final Pattern fragmentPattern;
    private final Pattern queryPattern;
    private final Map<String, String> queryMap = new HashMap<String, String>();

    public UrlSplitter() {
        protocolPattern = Pattern.compile("^([a-zA-Z]+):/*(.+)$");
        userPasswordPattern = Pattern.compile("^([^:]+):?(.*?)@(.+)$");
        hostPattern = Pattern.compile("^([-.a-z0-9]+):?(\\d*)/?(.*)$");
        fragmentPattern = Pattern.compile("^(.*)#(.+)$");
        queryPattern = Pattern.compile("^(.*)\\?(.+)$");
    }

    public void split(final String url) {
        reset();

        if (url == null) {
            return;
        }

        String work = extractProtocol(url);
        if (PROTOCOLS.contains(protocol) || protocol.isEmpty()) {
            work = extractUserAndPassword(work);
            work = extractFragment(work);
            work = extractQuery(work);
            work = extractHostPortPath(work.toLowerCase(Locale.ENGLISH));

            if (port == null) {
                if (PROTOCOL_HTTP.equals(protocol)) {
                    port = PORT_HTTP;
                } else if (PROTOCOL_HTTPS.equals(protocol)) {
                    port = PORT_HTTPS;
                } else if (PROTOCOL_FTP.equals(protocol)) {
                    port = PORT_FTP;
                }
            }
        } else {
            path = work;
        }
    }

    private String extractQuery(final String work) {
        final Matcher matcher = queryPattern.matcher(work);
        String result = work;
        if (matcher.matches()) {
            query = matcher.group(2);
            result = matcher.group(1);
            splitQuery();
        }
        return result;
    }

    private void splitQuery() {
        final String[] split = query.split("&");
        for (final String pair : split) {
            final String[] keyValue = pair.split("=");
            queryMap.put(keyValue[0], keyValue[1]);
        }
    }

    private String extractFragment(final String work) {
        final Matcher matcher = fragmentPattern.matcher(work);
        String result = work;
        if (matcher.matches()) {
            fragment = matcher.group(2);
            result = matcher.group(1);
        }
        return result;
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
            protocol = matcher.group(1).toLowerCase(Locale.ENGLISH);
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
        fragment = "";
        query = "";
        queryMap.clear();
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

    public String getFragment() {
        return fragment;
    }

    public String getQuery() {
        return query;
    }

    public Map<String, String> getQueryMap() {
        return queryMap;
    }
}
