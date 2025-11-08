package com.personal.app.controller.dto

import java.util.UUID

data class CreateAuctionRequest(
    val sellerId: UUID,
    val startingMoneyInCents: Long,
    val startingMoneyCurrencyCode: String,
    val buyItNowPriceInCents: Long?,
    val buyItNowPriceCurrencyCode: String?,
    val itemTitle: String,
    val itemDescription: String?
)
