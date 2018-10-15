package com.myproject.urlshortner.service.impl;

import com.myproject.urlshortner.common.IDConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.myproject.urlshortner.repository.UrlRepository;
import com.myproject.urlshortner.service.UrlShortnerService;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class UrlShortnerServiceImpl implements UrlShortnerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlShortnerServiceImpl.class);
    private final UrlRepository urlRepository;

    @Autowired
    public UrlShortnerServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public String shortenUrl(String localUrl, String longUrl) {
        LOGGER.info("Shortening URL: " + longUrl);
        Long id = urlRepository.incrementID();
        String uniqueId = IDConverter.createUniqueId(id);
        urlRepository.saveUrl("url:"+id, longUrl);
        String baseString = formatLocalURLFromShortener(localUrl);
        String shortenedURL = baseString + uniqueId;
        return shortenedURL;
    }

    private static String formatLocalURLFromShortener(String localUrl) {
        StringBuilder sb = new StringBuilder();
        URL url;
        try {
            url = new URL(localUrl);
            sb.append(url.getProtocol());
            sb.append(":");
            sb.append("//");
            sb.append(url.getHost());
            sb.append("/");
        } catch (MalformedURLException e) {
            LOGGER.error("Invalid url");
        }
        return sb.toString();
    }

    @Override
    public String getLongUrlFromId(String uniqueId) {
        Long id = IDConverter.getIdForUrl(uniqueId);
        String url = urlRepository.getUrl(id);
        LOGGER.info("Retrieved: "+url +" for: "+ id);
        return url;
    }
}
