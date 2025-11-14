package com.personal.infrastructure.db.entities

import com.personal.auction.AuctionStatus
import com.personal.auction.CurrencyCode
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.UUID

@Entity
data class AuctionEntity(
    @Id
    val id: UUID,
    val sellerId: UUID,
    val startingAmountInCents: Long,
    @Enumerated(EnumType.STRING)
    val startingCurrencyCode: CurrencyCode,
    val buyItNowPriceInCents: Long?,
    @Enumerated(EnumType.STRING)
    val buyItNowPriceCurrencyCode: CurrencyCode?,
    @Enumerated(EnumType.STRING)
    val status: AuctionStatus,
    @OneToMany(
        mappedBy = "auction",
        cascade = [CascadeType.ALL]
    )
    val bids: MutableList<BidEntity> = mutableListOf(),
    val itemTitle: String,
    val itemDescription: String?
)