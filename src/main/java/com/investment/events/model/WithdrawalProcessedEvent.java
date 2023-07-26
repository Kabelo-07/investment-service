package com.investment.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.StringJoiner;

@Getter
@Setter
@AllArgsConstructor(staticName = "instanceOf")
public class WithdrawalProcessedEvent extends DomainEvent {
    private final String investorName;
    private final String investorEmail;
    private final BigDecimal openingBalance;
    private final BigDecimal closingBalance;
    private final String referenceNo;

    private final Instant dateProcessed;
    private final BigDecimal withdrawalAmount;

    @Override
    public DomainEventType eventType() {
        return DomainEventType.WITHDRAWAL;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WithdrawalProcessedEvent.class.getSimpleName() + "[", "]")
                .add("investorName='" + investorName + "'")
                .add("openingBalance='" + openingBalance + "'")
                .add("closingBalance='" + closingBalance + "'")
                .add("referenceNo='" + referenceNo + "'")
                .add("dateProcessed=" + dateProcessed)
                .add("withdrawalAmount='" + withdrawalAmount + "'")
                .add("id=" + id)
                .add("dateCreated=" + dateCreated)
                .toString();
    }


}
