package com.personal.app.controller.dto

import java.util.UUID

data class GetAuctionResponse(
    val auctionId: UUID,
    val sellerId: UUID,
    val startingMoneyInCents: Long,
    val startingMoneyCurrencyCode: String,
    val buyItNowPriceInCents: Long?,
    val buyItNowPriceCurrencyCode: String?,
    val highestBidAmountInCents: Long?,
    val highestBidAmountCurrencyCode: String?,
    val auctionStatus: String,
    val itemTitle: String,
    val itemDescription: String?
)
