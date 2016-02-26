package yangbajing.utils.s.exception

import yangbajing.utils.s.TMessage

/**
  * 异常
  * Created by jingyang on 15/4/13.
  */
abstract class SBaseException(val msg: String, val code: Int) extends RuntimeException(msg) with TMessage {
  override def toString = code + ": " + super.toString
}

case class SException(override val code: Int,
                      override val msg: String) extends SBaseException(msg, code)

case class SUnauthorizedException(override val msg: String = "Unauthorized",
                                  override val code: Int = 401) extends SBaseException(msg, code)

case class SBadException(override val msg: String = "Unauthorized",
                         override val code: Int = 400) extends SBaseException(msg, code)

case class SInternalErrorException(override val msg: String = "Internal Exception Error",
                                   override val code: Int = 500) extends SBaseException(msg, code)

case class SNotFoundException(override val msg: String = "Not Found",
                              override val code: Int = 404) extends SBaseException(msg, code)
