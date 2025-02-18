package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseConduit {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    public void save(TransactionRecord tRecord) {

        UserRecord sender = tRecord.getSender();
        UserRecord receiver = tRecord.getRecipient();
        if (sender == null) {
            throw new RuntimeException("Sender not found");
        }
        if (receiver == null) {
            throw new RuntimeException("Receiver not found");
        }
        sender.setBalance(sender.getBalance() - tRecord.getAmount());
        receiver.setBalance(receiver.getBalance() + tRecord.getAmount() + tRecord.getIncentive());
        save(sender);
        save(receiver);

        transactionRepository.save(tRecord);
    }

    public TransactionRecord isValid(Transaction transaction) {

        TransactionRecord transactionRecord;

        UserRecord sender = queryUser(transaction.getSenderId());
        if (sender == null) {
            return null;
        }
        UserRecord recipient = queryUser(transaction.getRecipientId());
        if (recipient == null) {
            return null;
        }

        transactionRecord = new TransactionRecord(sender, recipient, transaction.getAmount());

        return !(sender.getBalance() < transaction.getAmount()) ? transactionRecord : null;
    }

    public UserRecord queryUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public float queryUserBalance(Long userId) {
        UserRecord userRecord = queryUser(userId);
        if (userRecord == null) {
            return 0;
        } else {
            return userRecord.getBalance();
        }
    }

}
