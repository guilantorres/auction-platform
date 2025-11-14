package com.personal.auction

import java.util.UUID

class Auction(
    val id: AuctionId = AuctionId(UUID.randomUUID()),
    val sellerId: UserId,
    val startingAmount: Money,
    val buyItNowPrice: Money?,
    private var status: AuctionStatus = AuctionStatus.CREATED,
    val itemTitle: String,
    val itemDescription: String?,
) {
    private var highestBidAmount: Money? = null
    private var listOfBids: MutableList<Bid> = mutableListOf()

    fun getStatus(): AuctionStatus = this.status

    fun getHighestBidAmount(): Money? = this.highestBidAmount

    fun getBids(): List<Bid> = this.listOfBids.toList()

    fun start() {
        if (this.status != AuctionStatus.CREATED) {
            throw IllegalStateException("Can't start Auction that was already opened")
        }
        this.status = AuctionStatus.OPEN
    }

    fun placeBid(
        bidderId: UserId,
        amount: Money,
    ) {
        require(this.status == AuctionStatus.OPEN) {
            "Bids can only be placed if AuctionStatus is OPEN"
        }

        val currencyToCheck = highestBidAmount?.currencyCode ?: startingAmount.currencyCode
        require(amount.currencyCode == currencyToCheck) {
            "Bid currency ${amount.currencyCode} must not be different than the auction currency $currencyToCheck"
        }

        val currentHighestAmount = highestBidAmount?.amountInCents ?: startingAmount.amountInCents
        require(amount.amountInCents > currentHighestAmount) {
            "Bid could not be placed. Amount from bid ${amount.amountInCents} is lower than current highest amount"
        }

        val newBid = Bid(bidderId, amount)
        this.listOfBids.add(newBid)
        this.highestBidAmount = amount

        if (buyItNowPrice != null && highestBidAmount!!.amountInCents >= buyItNowPrice.amountInCents) {
            this.status = AuctionStatus.PENDING_PAYMENT
        }
    }
}

