package com.gdavidben.quarkuslab.cache;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

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
