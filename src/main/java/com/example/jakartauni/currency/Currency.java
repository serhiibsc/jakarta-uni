package com.example.jakartauni.currency;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Currency {
    private Long id;
    private String name;
    private String abbreviation;
}
