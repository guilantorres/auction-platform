package com.personal.auction

interface AuctionRepository {
    fun save(auction: Auction)
    fun findById(id: AuctionId): Auction?
}