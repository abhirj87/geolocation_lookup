/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyz.controller;

/**
 *
 * @author abhiram
 */
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import com.xyz.geolocation.model.LatLng;
import com.xyz.geolocation.model.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import springfox.documentation.service.ResponseMessage;

@Api(basePath = "/geolocation", value = "geolocation", description = "Operations on geolocation reverse lookup", produces = "application/json")
@RestController
@RequestMapping(value = "/geolocation/")
public class GeoAccountController {

    public static final Logger LOGGER = LoggerFactory.getLogger(GeoAccountController.class);
    private static final Map<LatLng, Result> LOOK_UP_TABLE = Collections.synchronizedMap(new LinkedHashMap(10, 1.0f, false) {
        private static final int MAX_ENTRIES = 10;

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
        }
    });

    private static final String APIKEY
            = "AIzaSyCvx0STKKs0JeKEsONS3YcEsf3RuwlASIc";

    
    
    @RequestMapping(value = "/latlng/",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> getLocationAddress(@Valid @RequestBody LatLng latLng) {
        try {
            return new ResponseEntity(getAddressUsingGoogleMapsAPI(latLng),HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Error: " + ex.getMessage());
            return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

    @RequestMapping(value = "/latlng/{latlng}", method = RequestMethod.GET)
    public ResponseEntity<ResponseMessage> getLocationAddress(
            @PathVariable("latlng")
            @NotNull
            @Pattern(regexp = "-*[0-9]*[.]{1}[0-9]*,-*[0-9]*[.]{1}[0-9]*",
                    message = "Invalid Latitude/Longitude") String latLng) throws Exception {

        Result result;

        try {
            String[] parts = latLng.split(",");
            result = getAddressUsingGoogleMapsAPI(new LatLng(
                    Double.parseDouble(parts[0]),
                    Double.parseDouble(parts[1]))
            );

            return new ResponseEntity(result,HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Unable to process the give input: " + latLng
                    + " Encountered exception: " + e.getMessage());
            return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @RequestMapping(value = "/recent_lookups")
    private ResponseEntity<List<Result>> getRecentResults() {
            List<Result> results = new ArrayList<>();
            LOOK_UP_TABLE.values().forEach(x -> results.add(x));
            LOGGER.info("results for the lookup: " + results);
            return new ResponseEntity(results,HttpStatus.OK);
    }

    private Result getAddressUsingGoogleMapsAPI(LatLng latLng) throws Exception {

        Result result = LOOK_UP_TABLE.get(latLng);
        if (result != null) {
            LOGGER.info("Address found in the lookup: " + result);
        } else {
            LOGGER.info("Latlong received: " + latLng);
            GeoApiContext context = new GeoApiContext().setApiKey(APIKEY);
            GeocodingResult[] addresses = GeocodingApi.reverseGeocode(
                    context,
                    new com.google.maps.model.LatLng(
                            latLng.getLat(),
                            latLng.getLng())).await();

            String matchingAddress = addresses.length > 0
                    ? addresses[0].formattedAddress : null;
            result = new Result(latLng, matchingAddress, System.nanoTime());

            if (null != result) {
                LOOK_UP_TABLE.put(latLng, result);
            }
            LOGGER.info("result: " + addresses[0].formattedAddress);
        }
        return result;
    }

}
