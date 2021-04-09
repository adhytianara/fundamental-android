package bangkit.adhytia.github_user.api

import bangkit.adhytia.github_user.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class GithubApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", BuildConfig.GITHUB_AUTHORIZATION_TOKEN)
            .build()
        return chain.proceed(request)
    }
}