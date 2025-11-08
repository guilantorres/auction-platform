package com.personal.infrastructure.db

import com.personal.auction.Auction
import com.personal.auction.AuctionId
import com.personal.auction.AuctionRepository
import com.personal.auction.Money
import com.personal.auction.UserId
import com.personal.infrastructure.db.entities.AuctionEntity
import org.springframework.stereotype.Repository

@Repository
class AuctionRepositoryImpl(
    private val auctionRepositoryJpa: AuctionRepositoryJpa
) : AuctionRepository {
    override fun save(auction: Auction) {
        auctionRepositoryJpa.save(auctionToEntity(auction))
    }

    override fun findById(id: AuctionId): Auction? {
        val auction = auctionRepositoryJpa.findById(id.value)
        return auction
            .map { entityToAuction(it) }
            .orElse(null)
    }

    private fun auctionToEntity(auction: Auction): AuctionEntity {
        return AuctionEntity(
            auction.id.value,
            auction.sellerId.value,
            auction.startingAmount.amountInCents,
            auction.startingAmount.currencyCode,
            auction.buyItNowPrice?.amountInCents,
            auction.buyItNowPrice?.currencyCode,
            auction.getStatus(),
            auction.itemTitle,
            auction.itemDescription
        )
    }

    private fun entityToAuction(auctionEntity: AuctionEntity): Auction {
        val startingAmount = Money(
            auctionEntity.startingAmountInCents,
            auctionEntity.startingCurrencyCode
        )

        val buyItNowPrice = if (auctionEntity.buyItNowPriceInCents != null &&
            auctionEntity.buyItNowPriceCurrencyCode != null
        ) {
            Money(
                auctionEntity.buyItNowPriceInCents,
                auctionEntity.buyItNowPriceCurrencyCode
            )
        } else {
            null
        }

        return Auction(
            AuctionId(auctionEntity.id),
            UserId(auctionEntity.sellerId),
            startingAmount,
            buyItNowPrice,
            auctionEntity.status,
            auctionEntity.itemTitle,
            auctionEntity.itemDescription
        )
    }
}