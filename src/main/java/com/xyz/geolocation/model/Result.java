/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyz.geolocation.model;

import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

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
@Component
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
