package com.personal.auction

data class Money(
    val amountInCents: Long,
    val currencyCode: CurrencyCode
) {
    init {
        require(amountInCents >= 0) { "Money amount cannot be negative. Received: $amountInCents" }
    }
}
