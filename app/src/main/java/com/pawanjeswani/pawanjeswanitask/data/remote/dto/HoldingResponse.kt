package com.pawanjeswani.pawanjeswanitask.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HoldingResponse(
    val data: HoldingData?
)

@JsonClass(generateAdapter = true)
data class HoldingData(
    val userHolding: List<HoldingDto>?
)
