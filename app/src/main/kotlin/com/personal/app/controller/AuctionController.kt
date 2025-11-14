package com.personal.app.controller

import com.personal.app.controller.dto.CreateAuctionRequest
import com.personal.app.controller.dto.GetAuctionResponse
import com.personal.app.controller.dto.PlaceBidRequest
import com.personal.application.CreateAuctionService
import com.personal.application.GetAuctionService
import com.personal.application.PlaceBidService
import com.personal.auction.Auction
import com.personal.auction.AuctionId
import com.personal.auction.CurrencyCode
import com.personal.auction.Money
import com.personal.auction.UserId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID

@RestController
@RequestMapping("/auctions")
class AuctionController(
    private val createAuctionService: CreateAuctionService,
    private val getAuctionService: GetAuctionService,
    private val placeBidService: PlaceBidService,
) {
    @PostMapping
    fun createAuction(
        @RequestBody createAuctionRequest: CreateAuctionRequest,
    ): ResponseEntity<Any> {
        val startingMoneyCurrencyCode = createAuctionRequest.startingMoneyCurrencyCode.uppercase().trim()
        val startingMoney =
            Money(
                createAuctionRequest.startingMoneyInCents,
                CurrencyCode.valueOf(startingMoneyCurrencyCode),
            )
        val buyItNowPrice =
            if (createAuctionRequest.buyItNowPriceInCents != null &&
                createAuctionRequest.buyItNowPriceCurrencyCode != null
            ) {
                val buyItNowPriceCurrencyCode = createAuctionRequest.buyItNowPriceCurrencyCode.uppercase().trim()
                Money(
                    createAuctionRequest.buyItNowPriceInCents,
                    CurrencyCode.valueOf(buyItNowPriceCurrencyCode),
                )
            } else {
                null
            }

        val auctionId =
            createAuctionService.execute(
                UserId(createAuctionRequest.sellerId),
                startingMoney,
                buyItNowPrice,
                createAuctionRequest.itemTitle,
                createAuctionRequest.itemDescription,
            )

        val location =
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(auctionId.value)
                .toUri()
        return ResponseEntity.created(location).build()
    }

    @GetMapping("/{id}")
    fun getAuction(
        @PathVariable id: UUID,
    ): GetAuctionResponse {
        val auction = getAuctionService.execute(AuctionId(id))
        return mapGetAuctionResponse(auction)
    }

    @PostMapping("/{id}/bids")
    fun placeBid(
        @PathVariable("id") auctionId: UUID,
        @RequestBody placeBidRequest: PlaceBidRequest,
    ): ResponseEntity<GetAuctionResponse> {
        val bidCurrencyCode = placeBidRequest.bidCurrencyCode.uppercase().trim()
        val bidMoney =
            Money(
                placeBidRequest.bidAmountInCents,
                CurrencyCode.valueOf(bidCurrencyCode),
            )
        val auction =
            placeBidService.execute(
                AuctionId(auctionId),
                UserId(placeBidRequest.bidderId),
                bidMoney,
            )
        return ResponseEntity.ok(mapGetAuctionResponse(auction))
    }

    private fun mapGetAuctionResponse(auction: Auction): GetAuctionResponse =
        GetAuctionResponse(
            auction.id.value,
            auction.sellerId.value,
            auction.startingAmount.amountInCents,
            auction.startingAmount.currencyCode.name,
            auction.buyItNowPrice?.amountInCents,
            auction.buyItNowPrice?.currencyCode?.name,
            auction.getHighestBidAmount()?.amountInCents,
            auction.getHighestBidAmount()?.currencyCode?.name,
            auction.getStatus().name,
            auction.itemTitle,
            auction.itemDescription,
        )
}

