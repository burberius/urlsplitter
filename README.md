# Url Splitter - The library for URL splitting

With this library you can split all kinds of URLs in their parts.

## Usage

```java
UrlSplitter splitter = new UrlSplitter();
splitter.split("http://max:muster@www.example.com");
String host = splitter.getHost();
```

For all protocols except _ftp_, _http_ and _https_ the content after the protocol
is stored in the _path_. Example:

```java
UrlSplitter splitter = new UrlSplitter();
splitter.split("mailto:maxmuster@example.com");
String email = splitter.getPath();
```

## Result parts of a split
The default is an empty string or null in case of the port. Each part can be accessed with a getter, like in the examples above.

- protocol 
- user
- password
- host = domain or ip
- port 
- path
- fragment = the navigation fragment (#fragment)
- query = the complete query part (?examplequery)
- queryMap = the query part split up and parsed into a map for easier access

## Build
To build this library you need maven and then you can run:

```shell
mvn clean install
```

## Links
- [Build system](http://didge.my-wan.de/jenkins/job/GitHub%20urlsplitter)
- [SonarQube result](http://didge-sonar.my-wan.de/overview?id=net.troja%3Aurlsplitter)