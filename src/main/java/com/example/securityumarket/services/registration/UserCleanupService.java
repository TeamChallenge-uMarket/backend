package com.example.securityumarket.services.registration;

import com.example.securityumarket.dao.AppUserDAO;
import com.example.securityumarket.models.entities.AppUser;
import com.example.securityumarket.services.MailService;
import com.example.securityumarket.services.registration.DelayedMethodInvoker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCleanupService {

    public UserCleanupService(AppUserDAO appUserDAO, MailService mailService, DelayedMethodInvoker delayedMethodInvoker) {
        this.appUserDAO = appUserDAO;
        this.mailService = mailService;
        this.delayedMethodInvoker = delayedMethodInvoker;
    }

    private String email;

    private final AppUserDAO appUserDAO;
    private final MailService mailService;
    private final DelayedMethodInvoker delayedMethodInvoker;

    public void scheduleCleanupUnconfirmedUsers() {
        if (mailService.getUserEmail() != null) {
            email = mailService.getUserEmail();
            delayedMethodInvoker.invokeDelayedMethod(this::cleanupUnconfirmedUser);
        }
    }

    private void cleanupUnconfirmedUser() {
        Optional<AppUser> appUserByEmail = appUserDAO.findAppUserByEmail(email);
        if (!appUserByEmail.get().isConfirmEmail()) {
            appUserByEmail.ifPresent(appUser -> appUserDAO.deleteById(appUser.getId()));
        }
    }
}