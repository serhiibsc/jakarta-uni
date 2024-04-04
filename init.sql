CREATE TABLE "User" (
                        id SERIAL PRIMARY KEY,
                        password VARCHAR(255),
                        role VARCHAR(255),
                        name VARCHAR(255),
                        login VARCHAR(255) UNIQUE
);

CREATE TABLE "Currency" (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) UNIQUE,
                            abbreviation VARCHAR(5) UNIQUE
);

CREATE TABLE "ExchangeRate" (
                                id SERIAL PRIMARY KEY,
                                source_currency_id INTEGER REFERENCES "Currency"(id),
                                target_currency_id INTEGER REFERENCES "Currency"(id),
                                rate DECIMAL(10, 4),
                                date DATE,
                                UNIQUE(source_currency_id, target_currency_id, date)
);