package com.personal.infrastructure.db

import com.personal.auction.Auction
import com.personal.auction.AuctionId
import com.personal.auction.AuctionRepository
import com.personal.auction.Bid
import com.personal.auction.Money
import com.personal.auction.UserId
import com.personal.infrastructure.db.entities.AuctionEntity
import com.personal.infrastructure.db.entities.BidEntity
import org.springframework.stereotype.Repository

@Repository
class AuctionRepositoryImpl(
    private val auctionRepositoryJpa: AuctionRepositoryJpa,
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
        var auctionEntity =
            AuctionEntity(
                auction.id.value,
                auction.sellerId.value,
                auction.startingAmount.amountInCents,
                auction.startingAmount.currencyCode,
                auction.buyItNowPrice?.amountInCents,
                auction.buyItNowPrice?.currencyCode,
                auction.getStatus(),
                mutableListOf(),
                auction.itemTitle,
                auction.itemDescription,
            )

        var bidEntities = auction.getBids().map { bid ->
            BidEntity(
                bidderId = bid.bidderId.value,
                amountInCents = bid.amount.amountInCents,
                amountCurrencyCode = bid.amount.currencyCode,
                auctionEntity = auctionEntity,
            )
        }

        auctionEntity.bids.addAll(bidEntities)

        return auctionEntity
    }


    private fun entityToAuction(auctionEntity: AuctionEntity): Auction {
        val startingAmount =
            Money(
                auctionEntity.startingAmountInCents,
                auctionEntity.startingCurrencyCode,
            )

        val buyItNowPrice =
            if (auctionEntity.buyItNowPriceInCents != null &&
                auctionEntity.buyItNowPriceCurrencyCode != null
            ) {
                Money(
                    auctionEntity.buyItNowPriceInCents,
                    auctionEntity.buyItNowPriceCurrencyCode,
                )
            } else {
                null
            }

        var auction =
            Auction(
                AuctionId(auctionEntity.id),
                UserId(auctionEntity.sellerId),
                startingAmount,
                buyItNowPrice,
                auctionEntity.status,
                auctionEntity.itemTitle,
                auctionEntity.itemDescription,
            )

        /*
           TODO: Return the list of bids to Auction
           How to? What is the approach? separate constructor?
        */

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