package com.gdavidben.quarkuslab.cache;

import java.util.Random;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;

@Path("/bitcoinprice")
@Produces(MediaType.APPLICATION_JSON)
public class BitcoinPriceResource {

    @Inject
    @RestClient
    BitcoinPriceClient bitcoinPriceClient;

    @GET
    @Path("/{currency}")
    @CacheResult(cacheName = "bitcoinprice")
    public Response getBitcoinPrice(@PathParam("currency") String currency) {
        try {
            return Response.ok(getPrice(currency)).build();
        } catch (Exception e) {
            invalidateBitcoinPriceCache();
            return Response.status(400).build();
        }
    }

    String getPrice(String currency) throws Exception {
        int random = new Random().nextInt(100);
        
        System.out.println(random);
        
        if (random > 80) {
            throw new Exception("Service failed");
        }

        switch (currency) {
            case "eur":
                return bitcoinPriceClient.getEURBitcoinPrice();
            case "gbp":
                return bitcoinPriceClient.getGBPBitcoinPrice();
            case "cny":
                return bitcoinPriceClient.getCNYBitcoinPrice();
            default:
                return bitcoinPriceClient.getEURBitcoinPrice();
        }
    }

    @CacheInvalidateAll(cacheName = "bitcoinprice")
    void invalidateBitcoinPriceCache() {
    }
}