# geolocation_reverse_lookup
geolocation_reverse lookup service

## Description

This is a Spring boot RESTful web service (HTTP) which is capable of looking up a physical
street address given a set of geographic coordinates (longitude and latitude values). For example,
given the latitude &#39;33.969601&#39; and longitude &#39;-84.100033&#39;, the service will return the address of
the NCR office in Duluth, GA (2651 Satellite Blvd, Duluth, GA 30096).  

This implementation will delegate to the geocoding API for Google Maps to perform the lookup. 

The service will accept latitude and longitude coordinates for a location on Earth.
and for a valid set of coordinates, it will return the full, street address (including city,
state/province, and zip/postal code) of the location at those coordinates.

The service will cache (locally) the last 10 lookups and provide an additional RESTful API
for retrieving this stored data.  The data returned from this API will be a collection of the
lookups performed, including the longitude and latitude values, the address found, and the
date/time of the lookup.

The API is documented in using swagger.

The application produces and consumes json and free text. 


Example: http://localhost:8001/swagger-ui.html#/geo-account-controller

## Requirements

 - Java 1.8 or later.

## Usage

The applciation can be run by the fowllowing mvn command from the parent directory.

mvn spring-boot:run

Detailed API documentation is availabel thru swagger.
Ex:
http://localhost:8001/swagger-ui.html#/geo-account-controller



Examples:
It supports GET and POST methods and application/json only.

1. Getting address via POST(sending the json via request body)

localhost:8001/geolocation/latlng/
{
               "lat" : 33.969601,
               "lng" : -84.100033
}

Response:

{
"latLng":{"lat":33.969601,"lng":-84.100033},
"geocodingAddress":"2651 Satellite Blvd, Duluth, GA 30096, USA",
"timeOfrequest":"Tue Jan 24 23:42:32 EST 2017"
}


2. Getting address via GET
http://localhost:8001/geolocation/latlng/40.714224,-73.961452 

Response:
{"latLng":{"lat":33.969601,"lng":-84.100033},"geocodingAddress":"2651 Satellite Blvd, Duluth, GA 30096, USA","timeOfrequest":"Tue Jan 24 23:42:32 EST 2017"}


3. Doing a lookup of recent 10 address searches

http://localhost:8080/geolocation/recent_lookups/

Response:

[{"latLng":{"lat":33.969601,"lng":-84.100033},"geocodingAddress":"2651 Satellite Blvd, Duluth, GA 30096, USA","timeOfrequest":"Tue Jan 24 23:42:32 EST 2017"},{"latLng":{"lat":40.714224,"lng":-73.0},"geocodingAddress":"Trustees Walk, Patchogue, NY 11772, USA","timeOfrequest":"Tue Jan 24 23:44:13 EST 2017"}]
