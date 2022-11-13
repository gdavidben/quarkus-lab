package com.gdavidben.quarkuslab.cache;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/bitcoin/price")
@ApplicationScoped
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class BitcoinPriceResource {

  @Inject
  @RestClient
  CurrentPriceClient coindeskCurrentPriceClient;

  @GET
  @Path("/{symbol}")
  @CacheResult(cacheName = "bitcoinprice")
  public Response getBitcoinPrice(@PathParam("symbol") final String symbol) {
    return ofNullable(symbol)
      .map(this::getPrice)
      .map(Response::ok)
      .map(Response.ResponseBuilder::build)
      .orElseThrow(InternalServerErrorException::new);
  }

  String getPrice(final String symbol) {
    switch (symbol) {
      case "gbp":
        return coindeskCurrentPriceClient.getGbpPrice();
      case "cny":
        return coindeskCurrentPriceClient.getCnyPrice();
      case "eur":
      default:
        return coindeskCurrentPriceClient.getEurPrice();
    }
  }

  @CacheInvalidateAll(cacheName = "bitcoinprice")
  void invalidateBitcoinPriceCache() {}

}
