package com.stc.ahmedehabtask.di

import com.stc.ahmedehabtask.utilities.Constants.ACCEPT
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Accept", ACCEPT)
                .build()
        )
    }
}