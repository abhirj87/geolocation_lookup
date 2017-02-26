/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyz.geolocation.service;

import com.xyz.geolocation.model.LatLng;
import com.xyz.geolocation.model.Result;
import java.util.List;

/**
 *
 * @author abhiram
 */
public interface GeoCoding {

    Result getAddressUsingGoogleMapsAPI(LatLng latLng) throws Exception;

    List<Result> getRecentLookUp();
    
}
