# Url Splitter - The library for URL splitting

With this library you can split all kinds of urls in there parts.

## Usage

```java
UrlSplitter splitter = new UrlSplitter();
splitter.split("http://max:muster@www.example.com");
String host = slitter.getHost();
```

For all protocols except _ftp_, _http_ and _https_ the content after the protocol
is stored in the _path_. Example:

```java
UrlSplitter splitter = new UrlSplitter();
splitter.split("mailto:maxmuster@example.com");
String email = slitter.getPath();
```

## Build
To build this library you need maven and then you can run:

```shell
mvn clean install
```

## Code Quality
[SonarQube result](http://didge-sonar.my-wan.de/overview?id=net.troja%3Aurlsplitter)