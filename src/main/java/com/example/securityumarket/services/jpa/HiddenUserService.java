package com.example.securityumarket.services.jpa;

import com.example.securityumarket.dao.HiddenUserDAO;
import com.example.securityumarket.models.HiddenUser;
import com.example.securityumarket.models.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class HiddenUserService {
    private final HiddenUserDAO hiddenUserDAO;

    public HiddenUser save(Users user, Users hiddenUser) {
        HiddenUser buildHiddenUser = buildHiddenUser(user, hiddenUser);
        return hiddenUserDAO.save(buildHiddenUser);
    }

    public List<HiddenUser> findAllByUser(Users user) {
        return hiddenUserDAO.findAllByUser(user);
    }

    public Optional<HiddenUser> findByUserAndHiddenUser (Users user, Users hiddenUser) {
        return hiddenUserDAO.findByUserAndHiddenUser(user, hiddenUser);
    }

    private HiddenUser buildHiddenUser(Users user, Users hiddenUser) {
        return HiddenUser.builder()
                .user(user)
                .hiddenUser(hiddenUser)
                .build();
    }

    public void delete(HiddenUser hiddenUserByUserAndHiddenUser) {
        hiddenUserDAO.delete(hiddenUserByUserAndHiddenUser);
    }
}
