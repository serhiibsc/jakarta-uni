package com.example.jakartauni.command;

import com.example.jakartauni.command.concretecommand.*;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.Set;

@Singleton
@Startup
public class CommandFactory {
    private final Set<Command> commands = new HashSet<>();

    @PostConstruct
    public void init() {
        try {
            InitialContext ic = new InitialContext();
            commands.add((Command) ic.lookup("java:module/ExchangeRateSpecificCommand"));
            commands.add((Command) ic.lookup("java:module/LoginCommand"));
            commands.add((Command) ic.lookup("java:module/LoginPageCommand"));
            commands.add((Command) ic.lookup("java:module/MainPageCommand"));
            commands.add((Command) ic.lookup("java:module/LogoutCommand"));
            commands.add((Command) ic.lookup("java:module/CurrenciesPageCommand"));
        } catch (NamingException e) {
            throw new IllegalStateException(e);
        }
    }

    public Command getCommand(String commandName) {
        return commands.stream()
                .filter(command -> command.getCommandName().name().equalsIgnoreCase(commandName))
                .findFirst()
                .orElse(new UnknownCommand());
    }
}
