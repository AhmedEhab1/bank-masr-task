package com.stc.domain.repo

import com.stc.domain.entity.CurrenciesResponse

interface PostsRepo {
    suspend fun getLatest(accessKey: String): CurrenciesResponse
}