package service.impl;

import common.IDConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.UrlRepository;
import service.UrlShortnerService;

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
        LOGGER.info("Shortening URL: {}" + longUrl);
        Long id = urlRepository.incrementID();
        String uniqueId = IDConverter.createUniqueId(id);
        urlRepository.saveUrl("url:"+id, longUrl);
        String baseString = formatLocalURLFromShortener(localUrl);
        String shortenedURL = baseString + uniqueId;
        return shortenedURL;
    }

    private String formatLocalURLFromShortener(String localUrl) {
        String[] addressComponents = localUrl.split("/");
        // remove the endpoint (last index)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addressComponents.length - 1; ++i) {
            sb.append(addressComponents[i]);
        }
        sb.append('/');
        return sb.toString();
    }

    @Override
    public String getLongUrlFromId(String uniqueId) {
        Long id = IDConverter.getIdForUrl(uniqueId);
        String url = urlRepository.getUrl(id);
        LOGGER.info("Retrieved {} for {}", url, id);
        return url;
    }
}
