package com.personal.infrastructure.db.entities

import com.personal.auction.CurrencyCode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
data class BidEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val bidderId: UUID,
    @ManyToOne
    val auctionEntity: AuctionEntity,
    val amountInCents: Long,
    @Enumerated(EnumType.STRING)
    val amountCurrencyCode: CurrencyCode
)
