package com.personal.application

import com.personal.auction.Auction
import com.personal.auction.AuctionId
import com.personal.auction.AuctionRepository
import com.personal.auction.Money
import com.personal.auction.UserId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CreateAuctionService(
    private val auctionRepository: AuctionRepository,
) {
    private val logger = LoggerFactory.getLogger(CreateAuctionService::class.java)

    fun execute(
        sellerId: UserId,
        startingMoney: Money,
        buyItNowPrice: Money?,
        itemTitle: String,
        itemDescription: String?
    ): AuctionId {
        val newAuction = Auction(
            sellerId = sellerId,
            startingAmount = startingMoney,
            buyItNowPrice = buyItNowPrice,
            itemTitle = itemTitle,
            itemDescription = itemDescription
        )
        auctionRepository.save(newAuction)
        logger.info("Auction created ${newAuction.id}")
        return newAuction.id
    }
}