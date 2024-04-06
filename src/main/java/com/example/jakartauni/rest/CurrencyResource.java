package com.example.jakartauni.rest;

import com.example.jakartauni.currency.Currency;
import com.example.jakartauni.currency.CurrencyService;
import com.example.jakartauni.rest.dto.ResponseMessageDto;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/currency")
public class CurrencyResource {
    @EJB
    private CurrencyService currencyService;
    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/{currencyId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrency(@PathParam("currencyId") Long currencyId) {
        return currencyService.findCurrencyById(currencyId)
                .map(Response::ok)
                .orElse(responseCurrencyNotFound(currencyId))
                .build();
    }

    @POST
    @Path("/transaction-test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transactionTest(Currency currency) {
        currencyService.transactionTest(currency);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCurrency(Currency currency) {
        boolean isAdmin = securityContext.getUserPrincipal().getName().equals("admin");
        if (!isAdmin) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (currencyService.findCurrencyByName(currency.getName()).isPresent()) {
            String message = String.format("Currency with name [%s] already exists.",
                    currency.getAbbreviation());
            ResponseMessageDto messageDto = ResponseMessageDto.builder().message(message).build();
            return Response.status(Response.Status.CONFLICT)
                    .entity(messageDto)
                    .build();
        }
        if (currencyService.findCurrencyByAbbreviation(currency.getAbbreviation()).isPresent()) {
            String message = String.format("Currency with abbreviation [%s] already exists.",
                    currency.getAbbreviation());
            ResponseMessageDto messageDto = ResponseMessageDto.builder().message(message).build();
            return Response.status(Response.Status.CONFLICT)
                    .entity(messageDto)
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
        boolean isAdmin = securityContext.getUserPrincipal().getName().equals("admin");
        if (!isAdmin) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if (updatedCurrency.getName() == null || updatedCurrency.getAbbreviation() == null) {
            String message = "Currency should have name and abbreviation";
            ResponseMessageDto messageDto = ResponseMessageDto.builder().message(message).build();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(messageDto)
                    .build();
        }
        return currencyService.findCurrencyById(id)
                .map(c -> {
                    currencyService.updateCurrency(id, updatedCurrency);
                    return Response.ok().entity(currencyService.findCurrencyById(id));
                })
                .orElse(responseCurrencyNotFound(id))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCurrency(@PathParam("id") Long id) {
        boolean isAdmin = securityContext.getUserPrincipal().getName().equals("admin");
        if (!isAdmin) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return currencyService.findCurrencyById(id)
                .map(existingCurrency -> {
                    currencyService.deleteCurrency(existingCurrency);
                    return Response.noContent();
                })
                .orElse(responseCurrencyNotFound(id))
                .build();
    }

    private static Response.ResponseBuilder responseCurrencyNotFound(Long currencyId) {
        String message = String.format("Currency with id [%d] not found", currencyId);
        ResponseMessageDto messageDto = ResponseMessageDto.builder().message(message).build();
        return Response.status(Response.Status.NOT_FOUND)
                .entity(messageDto);
    }
}