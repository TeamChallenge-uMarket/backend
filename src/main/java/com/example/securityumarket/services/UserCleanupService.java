package com.example.securityumarket.services;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCleanupService {

    private final AppUserDAO appUserDAO;
    private final MailService mailService;
    private final DelayedMethodInvoker delayedMethodInvoker;

    public void scheduleCleanupUnconfirmedUsers() {
        if (mailService.userEmail != null) {
            delayedMethodInvoker.invokeDelayedMethod(this::cleanupUnconfirmedUser);
        }
    }

    private void cleanupUnconfirmedUser() {
        Optional<AppUser> appUserByEmail = appUserDAO.findAppUserByEmail(mailService.userEmail);
        if (!appUserByEmail.get().isConfirmEmail()) {
            appUserByEmail.ifPresent(appUser -> appUserDAO.deleteById(appUser.getId()));
        }
    }
}