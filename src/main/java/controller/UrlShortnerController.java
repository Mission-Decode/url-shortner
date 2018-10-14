package controller;

import common.UrlValidator;
import dto.InputRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UrlShortnerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
public class UrlShortnerController {

    @Autowired
    UrlShortnerService urlShortnerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlShortnerController.class);

    @RequestMapping(value = "/shorten", method = RequestMethod.POST)
    public ResponseEntity<String> shortenUrl(@RequestBody @Valid InputRequest inputRequest, HttpServletRequest request) {
        LOGGER.info("Received Request : {}" + inputRequest.getUrl());
        if (UrlValidator.isValidUrl(inputRequest.getUrl())) {
            String longUrl = inputRequest.getUrl();
            String localUrl = request.getRequestURL().toString();

            String shortenUrl = urlShortnerService.shortenUrl(localUrl, longUrl);
            return new ResponseEntity<>(shortenUrl, HttpStatus.OK);
        }
        return new ResponseEntity<>("URL Not Valid", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method=RequestMethod.GET)
    public ResponseEntity<String> redirectUrl(@PathVariable String id) {
        LOGGER.info("Received shortened url to redirect: " + id);
        String redirectUrlString = urlShortnerService.getLongUrlFromId(id);
        LOGGER.info("Original URL: " + redirectUrlString);
        return new ResponseEntity<>(redirectUrlString, HttpStatus.OK);
    }

}
