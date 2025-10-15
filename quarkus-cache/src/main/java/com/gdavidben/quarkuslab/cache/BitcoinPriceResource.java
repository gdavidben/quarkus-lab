package com.gdavidben.quarkuslab.cache;

import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import static java.util.Optional.ofNullable;

@Path("/bitcoin/price")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
