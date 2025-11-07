package com.personal.application

import com.personal.application.exceptions.AuctionNotFoundException
import com.personal.auction.AuctionId
import com.personal.auction.AuctionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StartAuctionService(
    private val auctionRepository: AuctionRepository
) {
    private val logger = LoggerFactory.getLogger(StartAuctionService::class.java)

    fun execute(auctionId: AuctionId) {
        val persistedAuction = auctionRepository.findById(auctionId)
            ?: throw AuctionNotFoundException(auctionId)
        persistedAuction.start()
        auctionRepository.save(persistedAuction)
        logger.info("Auction $auctionId has started!")
    }
}