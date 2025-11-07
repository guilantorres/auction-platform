package com.personal.application

import com.personal.application.exceptions.AuctionNotFoundException
import com.personal.auction.AuctionId
import com.personal.auction.AuctionRepository
import com.personal.auction.Money
import com.personal.auction.UserId
import org.springframework.stereotype.Service

@Service
class PlaceBidService(
    private val auctionRepository: AuctionRepository
) {
    fun execute(auctionId: AuctionId, bidderId: UserId, money: Money) {
        val persistedAuction =
            auctionRepository.findById(auctionId) ?: throw AuctionNotFoundException(auctionId)

        persistedAuction.placeBid(bidderId, money)
        auctionRepository.save(persistedAuction)
    }
}