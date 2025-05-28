package com.raywenderlich.nftmarketplace.model

data class BankPatchRequest(
    val trust: Double? = null,
    val transactionFee: Int? = null
)