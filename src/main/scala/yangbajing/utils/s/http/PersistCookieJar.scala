package yangbajing.utils.s.http

import java.util

import okhttp3.{Cookie, CookieJar, HttpUrl}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-04-11.
  */
class PersistCookieJar extends CookieJar {
  private val cookies = new util.ArrayList[Cookie]()

  override def saveFromResponse(url: HttpUrl, cookies: util.List[Cookie]): Unit = synchronized {
    this.cookies.addAll(cookies)
  }

  override def loadForRequest(url: HttpUrl): util.List[Cookie] = synchronized {
//    val removedCookies: util.List[Cookie] = new util.ArrayList[Cookie]
    val validCookies: util.List[Cookie] = new util.ArrayList[Cookie]

    val it = cookies.iterator
    while (it.hasNext) {
      val currentCookie = it.next
      if (isCookieExpired(currentCookie)) {
//        removedCookies.add(currentCookie)
        it.remove()
      } else if (currentCookie.matches(url)) {
        validCookies.add(currentCookie)
      }
    }

    validCookies
  }

  def clear() = synchronized {
    cookies.clear()
  }

  private def isCookieExpired(cookie: Cookie): Boolean = {
    cookie.expiresAt < System.currentTimeMillis
  }

}
