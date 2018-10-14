package service;

public interface UrlShortnerService {

    String shortenUrl(String localUrl, String longUrl);

    String getLongUrlFromId(String uniqueId);
}
