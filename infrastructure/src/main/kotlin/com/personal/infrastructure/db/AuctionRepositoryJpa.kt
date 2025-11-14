package com.personal.infrastructure.db

import com.personal.infrastructure.db.entities.AuctionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface AuctionRepositoryJpa : JpaRepository<AuctionEntity, UUID>