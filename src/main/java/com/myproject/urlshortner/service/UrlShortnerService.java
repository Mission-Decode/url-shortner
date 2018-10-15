package com.myproject.urlshortner.service;

public interface UrlShortnerService {

    String shortenUrl(String localUrl, String longUrl);

    String getLongUrlFromId(String uniqueId);
}
