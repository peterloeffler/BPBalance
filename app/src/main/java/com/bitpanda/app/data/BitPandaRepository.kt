package com.bitpanda.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class BitPandaRepository(
    private val apiKey: String
) {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    companion object {
        private const val BASE_URL = "https://api.bitpanda.com/v1/fiatwallets"
        private const val TRANSACTIONS_URL = "https://api.bitpanda.com/v1/fiatwallets/transactions"
    }

    suspend fun getEurWallets(): Result<List<String>> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url(BASE_URL)
                .addHeader("X-Api-Key", apiKey)
                .get()
                .build()

            val response = client.newCall(request).execute()
            
            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("API error: ${response.code}"))
            }

            val body = response.body?.string()
                ?: return@withContext Result.failure(Exception("Empty response"))

            val wallets = json.decodeFromString<FiatWalletsResponse>(body)
            val eurBalances = wallets.data
                .filter { it.attributes.fiatSymbol == "EUR" }
                .map { it.attributes.balance }

            Result.success(eurBalances)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDebitCardTransactions(): Result<List<Transaction>> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url(TRANSACTIONS_URL)
                .addHeader("X-Api-Key", apiKey)
                .get()
                .build()

            val response = client.newCall(request).execute()
            
            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("API error: ${response.code}"))
            }

            val body = response.body?.string()
                ?: return@withContext Result.failure(Exception("Empty response"))

            val transactions = json.decodeFromString<TransactionsResponse>(body)
            val debitCardTransactions = transactions.data
                .filter { tx ->
                    tx.attributes.tags.any { it.attributes.shortName == "debit_card.debit" }
                }
                .sortedByDescending { it.attributes.time.unix }

            Result.success(debitCardTransactions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
