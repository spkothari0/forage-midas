package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionHandler {
    private final DatabaseConduit databaseConduit;
    private final IncentiveQuerier incentiveQuerier;

    public void handleTransaction(Transaction transaction) {
        // Validate the transaction
        TransactionRecord record = databaseConduit.isValid(transaction);

        if (record != null) {
            Incentive inc = incentiveQuerier.query(transaction);
            if (inc!= null) {
                record.setIncentive(inc.getAmount());
                System.out.println("The incentive is: " + inc.getAmount());
            }
            databaseConduit.save(record);
        }

        UserRecord u = databaseConduit.queryUser(Long.valueOf(9));
        System.out.println("Value of user after transaction: " + u);
    }
}
