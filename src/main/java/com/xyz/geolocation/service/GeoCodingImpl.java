/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyz.geolocation.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.xyz.geolocation.model.LatLng;
import com.xyz.geolocation.model.Result;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 *
 * @author abhiram
 */
@Service
public class GeoCodingImpl implements GeoCoding {

    @Autowired
    public Environment env;

    private static final Map<LatLng, Result> LOOK_UP_TABLE = Collections.synchronizedMap(new LinkedHashMap(10, 1.0f, false) {
        private static final int MAX_ENTRIES = 10;

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
        }
    });

    public static final Logger LOGGER = LoggerFactory.getLogger(GeoCodingImpl.class);

    @Override
    public Result getAddressUsingGoogleMapsAPI(LatLng latLng) throws Exception {

        String APIKEY = env.getProperty("api.key");
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
            result = new Result(latLng, matchingAddress, new Date(System.currentTimeMillis()).toString());

            if (null != result) {
                LOOK_UP_TABLE.put(latLng, result);
            }
            LOGGER.info("result: " + addresses[0].formattedAddress);
        }
        return result;
    }

    @Override
    public List<Result> getRecentLookUp() {
        List<Result> results = new ArrayList<>();
        LOOK_UP_TABLE.values().forEach(x -> results.add(x));
        LOGGER.info("results for the lookup: " + results);
        return results;
    }

}
