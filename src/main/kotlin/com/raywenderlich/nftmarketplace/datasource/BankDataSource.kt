package com.raywenderlich.nftmarketplace.datasource

import com.raywenderlich.nftmarketplace.model.Bank
import com.raywenderlich.nftmarketplace.model.BankPatchRequest

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank?
    fun addBank(bank: Bank): Bank
    fun updateBank(accountNumber: String, patchRequest: BankPatchRequest): Bank?
    fun deleteBank(accountNumber: String): Bank?
}