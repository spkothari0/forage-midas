package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TransactionRecord {
    @Id
    @GeneratedValue()
    private long id;

    @ManyToOne
    private UserRecord sender;

    @ManyToOne
    private UserRecord recipient;

    @Column(nullable = false)
    private float amount;

    @Column(nullable = false)
    private float incentive;

    public TransactionRecord(UserRecord sender, UserRecord recipient, float amount){
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public TransactionRecord(){}
}
