package com.jpmc.midascore.service;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.foundation.Balance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    public final DatabaseConduit database;

    public Balance getBalance(Long userId) {
        float balance = database.queryUserBalance(userId);
        return new Balance(balance);
    }
}
