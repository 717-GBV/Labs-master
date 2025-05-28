package com.raywenderlich.nftmarketplace.datasource.mock

import org.springframework.stereotype.Repository
import com.raywenderlich.nftmarketplace.datasource.BankDataSource
import com.raywenderlich.nftmarketplace.model.Bank
import com.raywenderlich.nftmarketplace.model.BankPatchRequest


@Repository
class MockBankDataSource : BankDataSource{

    private val banks =  mutableListOf(
        Bank("1", 34.1, 1),
        Bank("2", 12.0, 0),
        Bank("3", 0.0, 100),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank? =
        banks.find { it.accountNumber == accountNumber }

    override fun addBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Bank with number ${bank.accountNumber} already exist")
        }
        banks.plus(bank)
        return bank
    }

    override fun updateBank(accountNumber: String, patchRequest: BankPatchRequest): Bank? {
        val bank = banks.find { it.accountNumber == accountNumber } ?: return null
        val updatedBank = bank.copy(
            trust = patchRequest.trust ?: bank.trust,
            transactionFee = patchRequest.transactionFee ?: bank.transactionFee
        )
        val index = banks.indexOf(bank)
        banks[index] = updatedBank
        return updatedBank
    }
}