package com.example.jakartauni.rest;

import com.example.jakartauni.currency.Currency;
import com.example.jakartauni.currency.CurrencyService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/currency")
public class CurrencyResource {
    @EJB
    private CurrencyService currencyService;

    @GET
    @Path("/{currencyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrency(@PathParam("currencyId") Long currencyId) {
        return currencyService.findCurrencyById(currencyId)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(String.format("{\"error\": \"Currency with id [%d] not found.\"}",
                                currencyId)))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCurrency(Currency currency) {
        if (currencyService.findCurrencyByName(currency.getName()).isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(String.format("{\"conflict\": \"Currency with name [%s] already " +
                                    "exists.\"}",
                            currency.getName()))
                    .build();
        }
        if (currencyService.findCurrencyByAbbreviation(currency.getAbbreviation()).isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(String.format("{\"conflict\": \"Currency with abbreviation [%s] " +
                                    "already exists.\"}",
                            currency.getAbbreviation()))
                    .build();
        }
        currencyService.createCurrency(currency);
        return Response.status(Response.Status.CREATED).entity(currencyService.findCurrencyByName(currency.getName())).build();
    }


    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCurrency(@PathParam("id") Long id, Currency updatedCurrency) {
        if (updatedCurrency.getName() == null || updatedCurrency.getAbbreviation() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Currency should have name and abbreviation.\"}")
                    .build();
        }
        return currencyService.findCurrencyById(id)
                .map(c -> {
                    currencyService.updateCurrency(id, updatedCurrency);
                    return Response.ok().entity(currencyService.findCurrencyById(id));
                })
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(String.format("{\"error\": \"Currency with id [%d] not found.\"}",
                                id)))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCurrency(@PathParam("id") Long id) {
        return currencyService.findCurrencyById(id)
                .map(existingCurrency -> {
                    currencyService.deleteCurrency(existingCurrency);
                    return Response.noContent();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(String.format("{\"error\": \"Currency with id [%d] not found.\"}",
                                id)))
                .build();
    }
}