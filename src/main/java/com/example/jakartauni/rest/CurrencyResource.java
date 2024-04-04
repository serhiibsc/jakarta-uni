package com.example.jakartauni.rest;

import com.example.jakartauni.currency.CurrencyService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/currency")
public class CurrencyResource {
    @EJB
    private CurrencyService currencyService;

    @GET
    @Path("/{currencyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("currencyId") Long currencyId) {
        return currencyService.findCurrencyById(currencyId)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(String.format("{\"error\": \"Currency with id [%d] not found.}", currencyId))
                )
                .build();
    }

}
