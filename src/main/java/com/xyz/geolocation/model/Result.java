/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyz.geolocation.model;

import java.security.Timestamp;
import javax.validation.constraints.NotNull;

/**
 *
 * @author abhiram
 */
@lombok.Getter
@lombok.Setter
@lombok.ToString
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode
@lombok.AllArgsConstructor
public class Result {

    @NotNull
    private LatLng latLng;

    @NotNull
    private String geocodingAddress;
    
    @NotNull
    private Long timeOfrequest;

}
