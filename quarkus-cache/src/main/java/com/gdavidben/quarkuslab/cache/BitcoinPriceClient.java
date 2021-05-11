package com.gdavidben.quarkuslab.cache;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v1/bpi/currentprice")
@RegisterRestClient(configKey = "bitcoinprice")
public interface BitcoinPriceClient {
    
    @GET
    @Path("eur.json")
    String getEURBitcoinPrice();

    @GET
    @Path("gbp.json")
    String getGBPBitcoinPrice();

    @GET
    @Path("cny.json")
    String getCNYBitcoinPrice();

}
