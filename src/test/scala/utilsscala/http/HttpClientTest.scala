package utilsscala.http

import okhttp3.{OkHttpClient, Request}
import okhttp3.logging.HttpLoggingInterceptor
import org.scalatest.FunSpec
import yangbajing.utils.s.http.PersistCookieJar

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-04-11.
  */
class HttpClientTest extends FunSpec {
  describe("HttpClientTest") {
    val client = new OkHttpClient.Builder()
      .addNetworkInterceptor(new HttpLoggingInterceptor())
      .cookieJar(new PersistCookieJar())
      .build()

    val req = new Request.Builder().url("http://localhost:40001/api/mobile/techIndex?phone=23984732345")
      .build()

    val resp = client.newCall(req).execute()
    println(resp)
    println(resp.body().string())
  }
}
