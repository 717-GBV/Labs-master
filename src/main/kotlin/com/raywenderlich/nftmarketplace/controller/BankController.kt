package com.raywenderlich.nftmarketplace.controller

import com.raywenderlich.nftmarketplace.model.Bank
import com.raywenderlich.nftmarketplace.model.BankPatchRequest
import com.raywenderlich.nftmarketplace.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String): ResponseEntity<Bank> {
        val bank = service.getBank(accountNumber)
        return if (bank != null) {
            ResponseEntity.ok(bank)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank: Bank): Bank {
        try {
            val addedBank = service.addBank(bank)
            return addedBank
        } catch (ex: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.localizedMessage, ex)
        }
    }

    @PatchMapping("/{accountNumber}")
    fun updateBank(@PathVariable accountNumber: String, @RequestBody patchRequest: BankPatchRequest): ResponseEntity<Bank> {
        val updatedBank = service.updateBank(accountNumber, patchRequest)
        return if (updatedBank != null) {
            ResponseEntity.ok(updatedBank)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{accountNumber}")
    fun deleteBank(@PathVariable accountNumber: String): ResponseEntity<Bank> {
        val deletedBank = service.deleteBank(accountNumber)
        return if (deletedBank != null) {
            ResponseEntity.ok(deletedBank)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}