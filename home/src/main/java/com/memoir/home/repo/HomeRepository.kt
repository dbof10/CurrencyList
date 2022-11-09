package com.memoir.home.repo

import com.memoir.home.model.CurrencyInfo


open class HomeRepository(
    private val apiClient: CryptoApiClient,
    private val db: CryptoDao
) {


    open suspend fun fetch(): List<CurrencyInfo> {
        val local = db.getAll()

        if (local.isEmpty()) {
            val remote = apiClient.fetch()
            db.insert(remote)
            return remote
        }
        return local

    }


}