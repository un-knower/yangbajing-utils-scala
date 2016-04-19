package yangbajing.utils.s.persist

import java.sql._
import java.util.Properties

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import org.joda.time.{LocalDate, LocalDateTime}
import yangbajing.utils.s.persist.SqlHelper.ROW_TYPE
import yangbajing.utils.s.time.TimeUtils

import scala.collection.mutable

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-03-14.
  */
class SqlHelper(ds: HikariDataSource) extends AutoCloseable {

  override def close(): Unit = ds.close()

  def getLines(sql: String, values: Seq[Any]): Seq[ROW_TYPE] = {
    query(sql, values) { (labels, rs) =>
      val buffer = mutable.ListBuffer.empty[ROW_TYPE]
      while (rs.next()) {
        val line = labels.map { case (label, labelType) =>
          val value = labelType match {
            case Types.INTEGER =>
              rs.getInt(label)
            case Types.BIGINT =>
              rs.getLong(label)
            case Types.DATE =>
              val d = rs.getDate(label)
              if (d == null) "" else TimeUtils.formatterDate.print(new LocalDate(d)) //d.toLocalDate.format(TimeUtils.formatterDate)
            case Types.TIMESTAMP =>
              val dt = rs.getTimestamp(label)
              if (dt == null) "" else TimeUtils.formatterDateTime.print(new LocalDateTime(dt)) //dt.toLocalDateTime.format(TimeUtils.formatterDateTime)
            case Types.DECIMAL | Types.NUMERIC =>
              rs.getDouble(label)
            case _ =>
              rs.getString(label)

          }
          label -> value
        }
        buffer.append(line.toMap)
      }
      buffer
    }
  }

  def query[R](sql: String, values: Seq[Any])(func: (Seq[(String, Int)], ResultSet) => R): R = {
    val conn = ds.getConnection()
    val pstmt = conn.prepareStatement(sql)
    values.zipWithIndex.foreach { case (value, i) =>
      pstmt.setObject(i + 1, value)
    }
    val rs = pstmt.executeQuery()
    try {
      val labels = getLabelsFromMetadata(rs.getMetaData)
      func(labels, rs)
    } finally {
      rs.close()
      pstmt.close()
      conn.close()
    }
  }

  def using[R](func: Connection => R) = {
    val conn = ds.getConnection
    try {
      func(conn)
    } finally {
      conn.close()
    }
  }

  def getLabelsFromMetadata(md: ResultSetMetaData): Seq[(String, Int)] = {
    (1 to md.getColumnCount).map(idx => md.getColumnLabel(idx) -> md.getColumnType(idx))
  }

  def prepare(sql: String): PreparedStatement = {
    val conn = ds.getConnection()
    try {
      conn.prepareStatement(sql)
    } finally {
      conn.close()
    }
  }

}

object SqlHelper {
  type ROW_TYPE = Map[String, Any]

  def fromMySQL(jdbcUrl: String): SqlHelper = {
    val ds = {
      val props = new Properties()
      props.setProperty("dataSourceClassName", "com.mysql.jdbc.jdbc2.optional.MysqlDataSource")
      props.setProperty("dataSource.url", jdbcUrl)
      props.setProperty("dataSource.cachePrepStmts", "true")
      new HikariDataSource(new HikariConfig(props))
    }
    new SqlHelper(ds)
  }

  def fromPG(serverName: String, dbName: String, user: String, pass: String): SqlHelper = {
    val ds = {
      val props = new Properties()
      props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
      props.setProperty("dataSource.serverName", serverName)
      props.setProperty("dataSource.databaseName", dbName)
      props.setProperty("dataSource.user", user)
      props.setProperty("dataSource.password", pass)
      new HikariDataSource(new HikariConfig(props))
    }
    println(ds.toString)
    new SqlHelper(ds)
  }

  def fromH2(url: String, user: String, password: String): SqlHelper = {
    val ds = {
      val props = new Properties()
      props.setProperty("dataSourceClassName", "org.h2.jdbcx.JdbcDataSource")
      props.setProperty("dataSource.url", url)
      props.setProperty("dataSource.user", user)
      props.setProperty("dataSource.password", password)
      new HikariDataSource(new HikariConfig(props))
    }
    new SqlHelper(ds)
  }

}
