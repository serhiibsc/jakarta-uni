package com.example.jakartauni.rest;

import com.example.jakartauni.entity.Currency;
import com.example.jakartauni.service.CurrencyService;
import com.example.jakartauni.entity.ExchangeRate;
import com.example.jakartauni.service.ExchangeRateService;
import com.example.jakartauni.rest.dto.ExchangeRateDto;
import com.example.jakartauni.rest.dto.ResponseMessageDto;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Path("/exchange-rate")
public class ExchangeRateResource {
    @EJB
    private ExchangeRateService exchangeRateService;
    @EJB
    private CurrencyService currencyService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCurrency(ExchangeRateDto exchangeRateDto) {
        Optional<Currency> optionalSourceCurrency =
                currencyService.findCurrencyByAbbreviation(exchangeRateDto.getSourceCurrencyAbbreviation());
        if (optionalSourceCurrency.isEmpty()) {
            String message = String.format("Currency with abbreviation [%s] does not exists.",
                    exchangeRateDto.getSourceCurrencyAbbreviation());
            ResponseMessageDto messageDto = ResponseMessageDto.builder().message(message).build();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(messageDto)
                    .build();
        }
        Optional<Currency> optionalTargetCurrency =
                currencyService.findCurrencyByAbbreviation(exchangeRateDto.getTargetCurrencyAbbreviation());
        if (optionalTargetCurrency.isEmpty()) {
            String message = String.format("Currency with abbreviation [%s] does not exists.",
                    exchangeRateDto.getTargetCurrencyAbbreviation());
            ResponseMessageDto messageDto = ResponseMessageDto.builder().message(message).build();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(messageDto)
                    .build();
        }
        if (Objects.isNull(exchangeRateDto.getDate())) {
            ResponseMessageDto messageDto = ResponseMessageDto.builder().message("Date shouldn't be null.").build();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(messageDto)
                    .build();
        }
        if (Objects.isNull(exchangeRateDto.getRate())) {
            ResponseMessageDto messageDto = ResponseMessageDto.builder().message("Rate shouldn't be null.").build();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(messageDto)
                    .build();
        }
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .date(exchangeRateDto.getDate())
                .sourceCurrency(optionalSourceCurrency.get())
                .targetCurrency(optionalTargetCurrency.get())
                .rate(exchangeRateDto.getRate())
                .build();
        return Response.status(Response.Status.CREATED)
                .entity(exchangeRateService.createExchangeRate(exchangeRate))
                .build();
    }


    @GET
    @Path("/paginated")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllExchangeRates(
            @QueryParam("from") String fromCurrency,
            @QueryParam("to") String toCurrency,
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        Optional<Currency> optionalFrom = currencyService.findCurrencyByAbbreviation(fromCurrency);
        Optional<Currency> optionalTo = currencyService.findCurrencyByAbbreviation(toCurrency);
        if (optionalTo.isEmpty() || optionalFrom.isEmpty()) {
            String message = String.format("Check if currencies are correct: %s and %s",
                    fromCurrency, toCurrency);
            ResponseMessageDto messageDto = ResponseMessageDto.builder().message(message).build();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(messageDto).build();
        }

        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);

        List<ExchangeRate> exchangeRates = exchangeRateService.findAllRates(optionalFrom.get(),
                optionalTo.get(), start, end);
        return Response.ok(paginate(exchangeRates, page, size)).build();
    }

    private List<ExchangeRate> paginate(List<ExchangeRate> exchangeRates, int page, int size) {
        int fromIndex = (page - 1) * size;
        if (fromIndex >= exchangeRates.size()) {
            return new ArrayList<>();
        }

        int toIndex = fromIndex + size;
        if (toIndex < exchangeRates.size()) {
            return exchangeRates.subList(fromIndex, toIndex);
        } else {
            return exchangeRates.subList(fromIndex, exchangeRates.size());
        }
    }
}
