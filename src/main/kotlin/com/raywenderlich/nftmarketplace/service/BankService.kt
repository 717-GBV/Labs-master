package com.raywenderlich.nftmarketplace.service

import com.raywenderlich.nftmarketplace.datasource.BankDataSource
import com.raywenderlich.nftmarketplace.model.Bank
import com.raywenderlich.nftmarketplace.model.BankPatchRequest
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()

    fun getBank(accountNumber: String): Bank? = dataSource.retrieveBank(accountNumber)

    fun addBank(bank: Bank): Bank = dataSource.addBank(bank)

    fun updateBank(accountNumber: String, patchRequest: BankPatchRequest): Bank? =
        dataSource.updateBank(accountNumber, patchRequest)

    fun deleteBank(accountNumber: String): Bank? = dataSource.deleteBank(accountNumber)
}