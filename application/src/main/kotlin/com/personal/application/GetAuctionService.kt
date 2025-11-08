package com.personal.application

import com.personal.application.exceptions.AuctionNotFoundException
import com.personal.auction.Auction
import com.personal.auction.AuctionId
import com.personal.auction.AuctionRepository
import org.springframework.stereotype.Service

@Service
class GetAuctionService(
    private val auctionRepository: AuctionRepository
) {
    fun execute(auctionId: AuctionId): Auction {
        val auction = auctionRepository.findById(auctionId) ?: throw AuctionNotFoundException(auctionId)
        return auction
    }
}