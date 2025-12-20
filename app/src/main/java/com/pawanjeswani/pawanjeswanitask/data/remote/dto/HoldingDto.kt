package com.pawanjeswani.pawanjeswanitask.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HoldingDto(
    val symbol: String?,
    val quantity: Int?,
    val ltp: Double?,
    val avgPrice: Double?,
    val close: Double?
)
