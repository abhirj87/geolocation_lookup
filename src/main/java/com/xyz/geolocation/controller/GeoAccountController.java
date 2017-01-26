/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyz.geolocation.controller;

/**
 *
 * @author abhiram
 */
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.xyz.geolocation.model.LatLng;
import com.xyz.geolocation.model.Result;
import com.xyz.geolocation.utils.GeoCoding;
import io.swagger.annotations.Api;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.ResponseMessage;

/**
 *
 * @author abhiram
 */
@Api(basePath = "/geolocation", value = "geolocation", description = "Operations on geolocation reverse lookup", produces = "application/json")
@RestController
@RequestMapping(value = "/geolocation/")
public class GeoAccountController {

    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(GeoAccountController.class);

    @Autowired
    private Environment env;


    /**
     *
     * @param latLng
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/latlng/",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> getLocationAddress(
            @Valid
            @RequestBody LatLng latLng) throws Exception {
        GeoCoding g = new GeoCoding(env,latLng);
        return new ResponseEntity(g.getAddressUsingGoogleMapsAPI(),
                HttpStatus.OK);
    }

    /**
     *
     * @param latLng
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/latlng/{latlng}", method = RequestMethod.GET)
    public ResponseEntity<ResponseMessage> getLocationAddress(
            @PathVariable("latlng")
            @NotNull
            @Pattern(regexp = "-*[0-9]*[.]{1}[0-9]*,-*[0-9]*[.]{1}[0-9]*",
                    message = "Invalid Latitude/Longitude") String latLng) throws Exception {

        Result result;

        String[] parts = latLng.split(",");
        result = new GeoCoding(env,new LatLng(
                Double.parseDouble(parts[0]),
                Double.parseDouble(parts[1]))
        ).getAddressUsingGoogleMapsAPI();

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/recent_lookups", method = RequestMethod.GET)
    private ResponseEntity<List<Result>> getRecentResults() {
        return new ResponseEntity(GeoCoding.getRecentLookUp(), HttpStatus.OK);
    }

}
