package com.pawanjeswani.pawanjeswanitask.data.remote.dto

import com.squareup.moshi.JsonClass

// Root response wrapper for holdings API
@JsonClass(generateAdapter = true)
data class HoldingResponse(
    val data: HoldingData?
)

// Data wrapper containing the list of user holdings
@JsonClass(generateAdapter = true)
data class HoldingData(
    val userHolding: List<HoldingDto>?
)
