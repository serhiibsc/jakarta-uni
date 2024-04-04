package com.example.jakartauni.rest;

import com.example.jakartauni.currency.Currency;
import com.example.jakartauni.currency.CurrencyService;
import com.example.jakartauni.rest.dto.ResponseMessageDto;
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
                .orElse(responseCurrencyNotFound(currencyId))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCurrency(Currency currency) {
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