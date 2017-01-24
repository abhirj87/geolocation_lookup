/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xyz.geolocation.model;

import java.util.List;
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

public class Response {

    public static final String ERROR = "ERROR";
    public static final String SUCCESS = "SUCCESS";
    
    @NotNull
    private String ResponseStatus;
    private String ReasonCode;
    private List<Result> ResponseMessage;

}
