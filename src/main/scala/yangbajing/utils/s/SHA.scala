package yangbajing.utils.s

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

import org.bouncycastle.util.encoders.Hex

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-04-11.
  */
object SHA {

  def getMessageDigestInstance(name: String) = MessageDigest.getInstance(name)

  def sha256(v: Array[Byte]) = {
    val digest = getMessageDigestInstance("SHA-256")
    digest.update(v)
    digest.digest()
  }

  def sha256Hex(v: String) = {
    Hex.toHexString(sha256(v.getBytes(StandardCharsets.UTF_8)))
  }

  def sha1(v: Array[Byte]) = {
    val digest = getMessageDigestInstance("SHA-1")
    digest.update(v)
    digest.digest()
  }

  def sha1Hex(v: String) = {
    Hex.toHexString(sha1(v.getBytes(StandardCharsets.UTF_8)))
  }

  def md5(v: Array[Byte]) = {
    val digest = getMessageDigestInstance("MD5")
    digest.update(v)
    digest.digest()
  }

  def md5Hex(v: String) = {
    Hex.toHexString(md5(v.getBytes(StandardCharsets.UTF_8)))
  }

}
