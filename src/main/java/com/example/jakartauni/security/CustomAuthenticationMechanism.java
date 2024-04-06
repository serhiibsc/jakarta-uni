package com.example.jakartauni.security;

import com.example.jakartauni.user.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ApplicationScoped
@Default
public class CustomAuthenticationMechanism implements HttpAuthenticationMechanism {
    @Inject
    private IdentityStore identityStore;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request,
                                                HttpServletResponse response,
                                                HttpMessageContext httpMessageContext) {
        String command = request.getParameter("command");
        boolean isAuthenticationRequest = Objects.nonNull(command) && command.equals("login");
        if (!isAuthenticationRequest && !httpMessageContext.isProtected()) {
            CallerPrincipal callerPrincipal = new CallerPrincipal(User.UserRole.GUEST.getName());
            Set<String> setWithUserRole = new HashSet<>(List.of(User.UserRole.GUEST.getName()));
            return httpMessageContext.notifyContainerAboutLogin(callerPrincipal, setWithUserRole);
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            return httpMessageContext.responseUnauthorized();
        }
        if (!username.matches("[a-zA-Z0-9\\-]{3,10}")) {
//            return httpMessageContext.redirect("/jakarta-uni-1.0-SNAPSHOT/controller?command=login_page");
            return httpMessageContext.responseUnauthorized();
        }

        Credential credential = new UsernamePasswordCredential(username, new Password(password));
        CredentialValidationResult result = identityStore.validate(credential);

        if (result.getStatus() == CredentialValidationResult.Status.VALID) {
            return httpMessageContext.notifyContainerAboutLogin(result.getCallerPrincipal(),
                    result.getCallerGroups());
        }
        return httpMessageContext.responseUnauthorized();
    }
}