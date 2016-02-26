package yangbajing.utils.s.exception

import yangbajing.utils.s.TMessage

/**
  * 异常
  * Created by jingyang on 15/4/13.
  */
abstract class SBaseException(val msg: String, val code: Int, val cause: Throwable) extends RuntimeException(msg, cause) with TMessage {
  override def toString = code + ": " + super.toString
}

//case class SException(override val code: Int,
//                      override val msg: String,
//                      override val cause: Throwable = null) extends SBaseException(msg, code, cause)

case class SUnauthorizedException(override val msg: String = "Unauthorized",
                                  override val code: Int = 401,
                                  override val cause: Throwable = null) extends SBaseException(msg, code, cause)

case class SBadException(override val msg: String = "Unauthorized",
                         override val code: Int = 400,
                         override val cause: Throwable = null) extends SBaseException(msg, code, cause)

case class SForbiddenException(override val msg: String,
                                override val code: Int = 403,
                                override val cause: Throwable = null) extends SBaseException(msg, code, cause)

case class SNotFoundException(override val msg: String = "Not Found",
                              override val code: Int = 404,
                              override val cause: Throwable = null) extends SBaseException(msg, code, cause)

case class SInternalErrorException(override val msg: String = "Internal Exception Error",
                                   override val code: Int = 500,
                                   override val cause: Throwable = null) extends SBaseException(msg, code, cause)
