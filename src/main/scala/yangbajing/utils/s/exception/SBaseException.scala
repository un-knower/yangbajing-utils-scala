package yangbajing.utils.s.exception

import yangbajing.utils.s.TMessage

/**
  * 异常
  * Created by jingyang on 15/4/13.
  */
abstract class SBaseException(val message: String, val code: Int) extends RuntimeException(message) with TMessage {
  override def toString = code + ": " + super.toString
}

case class SException(override val code: Int,
                      override val message: String) extends SBaseException(message, code)

case class SUnauthorizedException(override val message: String = "Unauthorized",
                                  override val code: Int = 401) extends SBaseException(message, code)

case class SBadException(override val message: String = "Unauthorized",
                         override val code: Int = 400) extends SBaseException(message, code)

case class SInternalException(override val message: String = "Internal Exception in Fenjoy",
                              override val code: Int = 500) extends SBaseException(message, code)

case class SNotFoundException(override val message: String = "Not Found",
                              override val code: Int = 404) extends SBaseException(message, code)
