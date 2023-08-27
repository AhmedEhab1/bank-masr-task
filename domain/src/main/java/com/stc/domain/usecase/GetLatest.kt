package com.stc.domain.usecase

import com.stc.domain.repo.PostsRepo

class GetLatest(private val postsRepo: PostsRepo) {
    suspend operator fun invoke(accessKey: String) = postsRepo.getLatest(accessKey)
}
