package com.raywenderlich.nftmarketplace.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.raywenderlich.nftmarketplace.model.Bank
import com.raywenderlich.nftmarketplace.model.BankPatchRequest
import org.springframework.http.MediaType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should return all banks`() {
        //when/then
        mockMvc.get(urlTemplate = "/api/banks")
            .andDo { print() }
            .andExpect { this
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].accountNumber") { value("1") }
                }
    }

    @Test
    fun `should return bank by number`() {
        val accountNumber = "1"
        mockMvc.get(urlTemplate = "/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect { this
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("accountNumber") { value(accountNumber) }
            }
    }

    @Test
    fun `should return page 404 when bank not found`() {
        mockMvc.get("/api/banks/null")
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun `should add a new bank`() {
        val newBank = Bank("9999", 1.0, 10)
        mockMvc.post("/api/banks") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(newBank)
        }
            .andExpect { this
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("accountNumber") { value("9999") }
                jsonPath("trust") { value(1.0) }
                jsonPath("transactionFee") { value(10) }
            }
    }

    @Test
    fun `should return 400 when adding bank with existing account number`() {
        val existingBank = Bank("1", 34.1, 1)
        mockMvc.post("/api/banks") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(existingBank)
        }
            .andExpect { this
                status { isBadRequest() }
            }
    }

    @Test
    fun `should update bank trust`() {
        val accountNumber = "1"
        val patchRequest = BankPatchRequest(trust = 5.0)
        mockMvc.patch("/api/banks/$accountNumber") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(patchRequest)
        }
            .andExpect { this
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("accountNumber") { value(accountNumber) }
                jsonPath("trust") { value(5.0) }
                jsonPath("transactionFee") { value(1) }
            }
    }

    @Test
    fun `should update bank transaction fee`() {
        val accountNumber = "1"
        val patchRequest = BankPatchRequest(transactionFee = 50)
        mockMvc.patch("/api/banks/$accountNumber") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(patchRequest)
        }
            .andExpect { this
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("accountNumber") { value(accountNumber) }
                jsonPath("trust") { value(5.0) }
                jsonPath("transactionFee") { value(50) }
            }
    }

    @Test
    fun `should return ok when delete bank`() {
        val accountNumber = "3"
        mockMvc.delete("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect { status { isOk() } }
    }

    @Test
    fun `should return 404 when delete not existet bank`() {
        val accountNumber = "5"
        mockMvc.delete("/api/banks/$accountNumber")
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }
}