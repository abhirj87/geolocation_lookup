/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyz.geolocation.model;

import java.util.Date;
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
//@ApiModelProperty(notes = "Latitude Longitude", required = true)

    @NotNull
    private LatLng latLng;

//@ApiModelProperty(notes = "Address of Lat Lng", required = true)
    @NotNull
    private String geocodingAddress;

//@ApiModelProperty(notes = "Timestamp", required = true)
    @NotNull
    private String timeOfrequest;

}
