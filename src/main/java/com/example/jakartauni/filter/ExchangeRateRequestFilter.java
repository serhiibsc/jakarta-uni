package com.example.jakartauni.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Provider
public class ExchangeRateRequestFilter implements ContainerRequestFilter {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        UriInfo uriInfo = requestContext.getUriInfo();
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();

        if (!uriInfo.getPath().equals("exchange-rate/paginated")) {
            return;
        }

        StringBuilder errorMessage = new StringBuilder();
        Integer page = null;
        try {
            page = Integer.parseInt(queryParameters.getFirst("page"));
        } catch (NumberFormatException e) {
            errorMessage.append("'page' parameter is incorrect.\n");
        }

        if (page != null && page <= 0) {
            errorMessage.append("'page' parameter should be more than 0.\n");
        }

        Integer size = null;
        try {
            size = Integer.parseInt(queryParameters.getFirst("size"));
        } catch (NumberFormatException e) {
            errorMessage.append("'size' parameter is incorrect.\n");
        }

        if (size != null && size <= 0) {
            errorMessage.append("'size' parameter should be more than 0.\n");
        }

        String from = queryParameters.getFirst("from");
        if (from == null || from.isEmpty()) {
            errorMessage.append("'fromCurrency' parameter is missing or empty.\n");
        }

        String to = queryParameters.getFirst("to");
        if (to == null || to.isEmpty()) {
            errorMessage.append("'toCurrency' parameter is missing or empty.\n");
        }

        String startDate = queryParameters.getFirst("startDate");
        if (startDate == null || startDate.isEmpty()) {
            errorMessage.append("'startDate' parameter is missing or empty.\n");
        } else {
            try {
                DATE_FORMATTER.parse(startDate);
            } catch (DateTimeParseException e) {
                errorMessage.append("'startDate' parameter is not in correct format. Expected " +
                        "format is '").append(DATE_PATTERN).append("'.\n");
            }
        }

        String endDate = queryParameters.getFirst("endDate");
        if (endDate == null || endDate.isEmpty()) {
            errorMessage.append("'endDate' parameter is missing or empty.\n");
        } else {
            try {
                DATE_FORMATTER.parse(endDate);
            } catch (DateTimeParseException e) {
                errorMessage.append("'endDate' parameter is not in correct format. Expected " +
                        "format is '").append(DATE_PATTERN).append("'.\n");
            }
        }

        if (errorMessage.length() > 0) {
            requestContext.abortWith(
                    Response.status(Response.Status.BAD_REQUEST).entity(errorMessage.toString()).build());
        }
    }
}