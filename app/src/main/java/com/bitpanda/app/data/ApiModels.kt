package com.bitpanda.app.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FiatWalletsResponse(
    @SerialName("data") val data: List<FiatWallet>
)

@Serializable
data class FiatWallet(
    @SerialName("attributes") val attributes: FiatAttributes
)

@Serializable
data class FiatAttributes(
    @SerialName("fiat_symbol") val fiatSymbol: String,
    @SerialName("balance") val balance: String
)

@Serializable
data class TransactionsResponse(
    @SerialName("data") val data: List<Transaction>
)

@Serializable
data class Transaction(
    @SerialName("attributes") val attributes: TransactionAttributes,
    @SerialName("id") val id: String
)

@Serializable
data class TransactionAttributes(
    @SerialName("amount") val amount: String,
    @SerialName("time") val time: TransactionTime,
    @SerialName("tags") val tags: List<Tag>
)

@Serializable
data class TransactionTime(
    @SerialName("date_iso8601") val dateIso8601: String,
    @SerialName("unix") val unix: Long
)

@Serializable
data class Tag(
    @SerialName("attributes") val attributes: TagAttributes
)

@Serializable
data class TagAttributes(
    @SerialName("short_name") val shortName: String,
    @SerialName("name") val name: String
)
