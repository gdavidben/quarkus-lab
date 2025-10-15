package com.gdavidben.quarkuslab.cache;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/v1/bpi/currentprice")
@RegisterRestClient(configKey = "coindesk")
public interface CurrentPriceClient {

  @GET
  @Path("eur.json")
  String getEurPrice();

  @GET
  @Path("gbp.json")
  String getGbpPrice();

  @GET
  @Path("cny.json")
  String getCnyPrice();
}
