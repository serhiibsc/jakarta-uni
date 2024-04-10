package com.example.jakartauni.security;

import com.example.jakartauni.entity.User;
import com.example.jakartauni.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.Optional;
import java.util.Set;

@ApplicationScoped
@Default
public class CustomIdentityStore implements IdentityStore {
    @Inject
    private UserService userService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (!(credential instanceof UsernamePasswordCredential)) {
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }

        UsernamePasswordCredential userCredential = (UsernamePasswordCredential) credential;
        Optional<User> userOptional = userService.findByUsername(userCredential.getCaller());
        if (userOptional.isEmpty()) {
            return CredentialValidationResult.INVALID_RESULT;
        }

        User user = userOptional.get();
        String password = String.copyValueOf(userCredential.getPassword().getValue());
        if (!Encryptor.encryptSha256(password).equals(user.getPassword())) {
            return CredentialValidationResult.INVALID_RESULT;
        }

        Set<String> roles = Set.of(user.getRole().getName());
        return new CredentialValidationResult(user.getLogin(), roles);
    }
}
