package yangbajing.utils.s

import java.nio.charset.StandardCharsets
import java.security.{Key, MessageDigest, Security}
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

import com.typesafe.config.ConfigFactory
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Hex

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-02-07.
  */

class TripleDesUtils {
  val key = ConfigFactory.load().getString("play.crypto.secret")
  private val TRIPLE_DES_TRANSFORMATION = "DESede/ECB/PKCS7Padding"
  private val ALGORITHM = "DESede"
  private val BOUNCY_CASTLE_PROVIDER = "BC"
  private val UNICODE_FORMAT = StandardCharsets.UTF_8.name()
  val PASSWORD_HASH_ALGORITHM = "SHA"

  Security.addProvider(new BouncyCastleProvider())

  private def encode(input: Array[Byte], key: String): Array[Byte] = {
    val encrypter: Cipher = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION, BOUNCY_CASTLE_PROVIDER)
    encrypter.init(Cipher.ENCRYPT_MODE, buildKey(key.toCharArray))
    encrypter.doFinal(input)
  }

  private def decode(input: Array[Byte], key: String): Array[Byte] = {
    val decrypter: Cipher = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION, BOUNCY_CASTLE_PROVIDER)
    decrypter.init(Cipher.DECRYPT_MODE, buildKey(key.toCharArray))
    decrypter.doFinal(input)
  }

  private def getByte(string: String): Array[Byte] = {
    string.getBytes(UNICODE_FORMAT)
  }

  private def getString(byteText: Array[Byte]): String = {
    new String(byteText)
  }

  private def buildKey(password: Array[Char]): Key = {
    val digester: MessageDigest = MessageDigest.getInstance(PASSWORD_HASH_ALGORITHM)
    digester.update(String.valueOf(password).getBytes(UNICODE_FORMAT))
    val keys: Array[Byte] = digester.digest
    val keyDes: Array[Byte] = java.util.Arrays.copyOf(keys, 24)
    new SecretKeySpec(keyDes, ALGORITHM)
  }

  def encrypt(plainText: String, key: String): String = {
    val encryptedByte: Array[Byte] = encode(getByte(plainText), key)
    Hex.toHexString(encryptedByte)
  }

  def encrypt(plainText: String): String = {
    encrypt(plainText, key)
  }

  def decrypt(cipherText: String, key: String): String = {
    val bys = cipherText.getBytes(UNICODE_FORMAT)
    val decryptedByte: Array[Byte] = decode(bys, key)
    getString(decryptedByte)
  }

  def decrypt(cipherText: String): String = {
    decrypt(cipherText, key)
  }

  def generateSHA(password: String): String = {
    val digester: MessageDigest = MessageDigest.getInstance(PASSWORD_HASH_ALGORITHM)
    digester.update(String.valueOf(password.toCharArray).getBytes(UNICODE_FORMAT))
    val keys: Array[Byte] = digester.digest
    Hex.toHexString(keys)
  }

}