package yangbajing.utils.s.persist

import java.sql.{Connection, SQLException}

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-02-13.
  */
object SQLUtils {

  def insert(sql: String, value: Seq[Any])(implicit conn: Connection): Int = {
    val isAutoCommit = conn.getAutoCommit
    try {
      conn.setAutoCommit(false)
      val pstmt = conn.prepareStatement(sql)
      value.zipWithIndex.foreach { case (v, idx) =>
        println(idx + 1 + " " + v)
        pstmt.setObject(idx + 1, v)
      }
      val ret = pstmt.executeUpdate()
      conn.commit()
      ret
    } catch {
      case e: SQLException =>
        conn.rollback()
        throw e
    } finally {
      conn.setAutoCommit(isAutoCommit)
      conn.close()
    }
  }

  def insertBatch(sql: String, values: Seq[Array[_]])(implicit conn: Connection): Array[Int] = {
    val isAutoCommit = conn.getAutoCommit
    try {
      conn.setAutoCommit(false)
      val pstmt = conn.prepareStatement(sql)
      values.foreach { value =>
        value.zipWithIndex.foreach { case (v, idx) =>
          pstmt.setObject(idx + 1, v)
        }
        pstmt.addBatch()
      }
      val rets = pstmt.executeBatch()
      conn.commit()
      rets
    } catch {
      case e: SQLException =>
        conn.rollback()
        e.printStackTrace()
        throw e.getNextException
    } finally {
      conn.setAutoCommit(isAutoCommit)
      conn.close()
    }
  }

}
