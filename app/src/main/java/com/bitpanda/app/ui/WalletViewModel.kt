package com.bitpanda.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bitpanda.app.data.ApiKeyManager
import com.bitpanda.app.data.BitPandaRepository
import com.bitpanda.app.data.Transaction
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WalletUiState(
    val isLoading: Boolean = false,
    val eurBalances: List<String> = emptyList(),
    val transactions: List<Transaction> = emptyList(),
    val error: String? = null,
    val needsApiKey: Boolean = false
)

class WalletViewModel(application: Application) : AndroidViewModel(application) {
    private val apiKeyManager = ApiKeyManager(application)
    
    private val _uiState = MutableStateFlow(WalletUiState())
    val uiState: StateFlow<WalletUiState> = _uiState.asStateFlow()

    init {
        if (apiKeyManager.hasApiKey()) {
            loadData()
        } else {
            _uiState.value = WalletUiState(needsApiKey = true)
        }
    }

    fun setApiKey(apiKey: String) {
        apiKeyManager.saveApiKey(apiKey)
        loadData()
    }

    fun loadData() {
        val apiKey = apiKeyManager.getApiKey()
        if (apiKey == null) {
            _uiState.value = _uiState.value.copy(needsApiKey = true)
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, needsApiKey = false)
            
            val repository = BitPandaRepository(apiKey)
            
            val balancesDeferred = async { repository.getEurWallets() }
            val transactionsDeferred = async { repository.getDebitCardTransactions() }
            
            val balancesResult = balancesDeferred.await()
            val transactionsResult = transactionsDeferred.await()
            
            val balances = balancesResult.getOrNull()
            val transactions = transactionsResult.getOrNull()
            val error = balancesResult.exceptionOrNull()?.message 
                ?: transactionsResult.exceptionOrNull()?.message
            
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                eurBalances = balances ?: emptyList(),
                transactions = transactions ?: emptyList(),
                error = error
            )
        }
    }

    fun loadWallets() {
        loadData()
    }
}
