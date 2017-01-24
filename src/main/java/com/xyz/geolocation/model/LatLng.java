/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyz.geolocation.model;

import javax.validation.constraints.NotNull;

/**
 *
 * @author abhiram
 */
/**
 * A place on Earth, represented by a Latitude/Longitude pair.
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class LatLng {

    /**
     * The latitude of this location.
     */
    @NotNull(message="Latitude cannot be null")
    public double lat;

    /**
     * The longitude of this location.
     */
    @NotNull(message="Longitude cannot be null")
    public double lng;

}
