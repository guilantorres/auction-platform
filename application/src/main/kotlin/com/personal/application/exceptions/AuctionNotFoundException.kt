package com.personal.application.exceptions

import com.personal.auction.AuctionId

class AuctionNotFoundException(id: AuctionId): RuntimeException("Auction $id not found")