package com.stc.data.repo

import com.stc.data.remote.ApiService
import com.stc.domain.entity.CurrenciesResponse
import com.stc.domain.repo.PostsRepo

class ResponseRepoImpl(private val apiService: ApiService) : PostsRepo {
    override suspend fun getLatest(accessKey: String):CurrenciesResponse =
        apiService.getLatest(accessKey)
}