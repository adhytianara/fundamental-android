package bangkit.adhytia.github_user.api

import okhttp3.Interceptor
import okhttp3.Response

class GithubApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "token 57cb3aebb2f2dee3dde0b7b1d823f12ac1576caa")
            .build()
        return chain.proceed(request)
    }
}