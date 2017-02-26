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
import com.xyz.geolocation.model.LatLng;
import com.xyz.geolocation.model.Result;
import com.xyz.geolocation.service.GeoCodingImpl;
import io.swagger.annotations.Api;
import java.util.List;
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

    @Autowired
    private GeoCodingImpl geoCoding;

    /**
     *
     * @param latLng
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/latlng/",
            method = {RequestMethod.POST, RequestMethod.GET},
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessage> getLocationAddress(
            @Valid
            @RequestBody LatLng latLng) throws Exception {
        return new ResponseEntity(geoCoding.getAddressUsingGoogleMapsAPI(latLng),
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
        result = geoCoding.getAddressUsingGoogleMapsAPI(new LatLng(
                Double.parseDouble(parts[0]),
                Double.parseDouble(parts[1]))
        );

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/recent_lookups", method = RequestMethod.GET)
    private ResponseEntity<List<Result>> getRecentResults() {
        return new ResponseEntity(geoCoding.getRecentLookUp(), HttpStatus.OK);
    }

}
