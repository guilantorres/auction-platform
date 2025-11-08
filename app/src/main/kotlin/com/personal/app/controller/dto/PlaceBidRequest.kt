package com.personal.app.controller.dto

import java.util.UUID

data class PlaceBidRequest(
    val bidderId: UUID,
    val bidAmountInCents: Long,
    val bidCurrencyCode: String
)
