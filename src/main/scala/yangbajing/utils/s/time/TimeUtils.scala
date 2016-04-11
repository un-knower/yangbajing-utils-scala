package yangbajing.utils.s.time

import java.sql.{Date => SQLDate, Timestamp => SQLTimestamp}
import java.util.Date

import org.joda.time.{Instant, LocalDate, LocalDateTime, LocalTime}
import org.joda.time.format.DateTimeFormat

import scala.util.Try

/**
  * DateTimeUtils
  * Created by yangjing on 15-11-6.
  */
object TimeUtils extends Serializable {

  //  val ZONE_OFFSET = ZoneOffset.ofHours(8)
  val formatterDateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
  val formatterMonth = DateTimeFormat.forPattern("yyyy-MM")
  val formatterDate = DateTimeFormat.forPattern("yyyy-MM-dd")
  val formatterDateHours = DateTimeFormat.forPattern("yyyy-MM-dd HH")
  val formatterDateMinutes = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
  val formatterMinutes = DateTimeFormat.forPattern("HH:mm")
  val formatterTime = DateTimeFormat.forPattern("HH:mm:ss")

  def now() = LocalDateTime.now()

  def parseDate(date: String): LocalDate =
    Try(LocalDate.parse(date, formatterDate)) getOrElse {
      val (year, month, day) = date.split('-') match {
        case Array(y, m, d) =>
          (y.toInt, m.toInt, d.toInt)
        case Array(y, m) =>
          (y.toInt, m.toInt, 1)
        case Array(y) =>
          (y.toInt, 1, 1)
        case _ =>
          throw new RuntimeException(s"$date is invalid iso date format")
      }

      if (year < 0 || year > 9999)
        throw new RuntimeException(s"$date is invalid iso date format ($year)")

      new LocalDate(year, month, day)
    }

  def parseTime(time: String): LocalTime =
    Try(LocalTime.parse(time, formatterTime)) getOrElse {
      val (hour, minute, second, nano) =
        time.split(':') match {
          case Array(h, m, s) =>
            s.split('.') match {
              case Array(sec, millis) =>
                (h.toInt, m.toInt, sec.toInt, millis.toInt * 1000 * 1000)
              case arr =>
                (h.toInt, m.toInt, arr(0).toInt, 0)
            }
          case Array(h, m) =>
            (h.toInt, m.toInt, 0, 0)
          case Array(h) =>
            (h.toInt, 0, 0, 0)
          case _ =>
            throw new RuntimeException(s"$time is invalid iso time format")
        }

      new LocalTime(hour, minute, second, nano)
    }

  def parseDateTime(date: String, time: String): LocalDateTime = {
    val d = parseDate(date)
    val t = parseTime(time)
    new LocalDateTime(d.getYear, d.getMonthOfYear, d.getDayOfMonth, t.getHourOfDay, t.getMinuteOfHour, t.getSecondOfMinute, t.getMillisOfSecond)
  }

  def parseDateTime(datetime: String): LocalDateTime =
    Try(LocalDateTime.parse(datetime, formatterDateTime)) getOrElse {
      datetime.split( """[ ]+""") match {
        case Array(date, time) =>
          parseDateTime(date, time)
        case _ =>
          throw new RuntimeException(s"$datetime is invalid iso datetime format")
      }
    }

  def parseDateTime(instant: Instant): LocalDateTime = {
    instant.toDateTime.toLocalDateTime
  }

  def toLocalDateTime(date: Date): LocalDateTime = {
    new LocalDateTime(date.getTime)
  }

  def toDate(ldt: LocalDateTime): Date = {
    ldt.toDate
  }

  def toEpochMilli(dt: LocalDateTime) = {
    dt.toDate.getTime
  }

  def toSqlTimestamp(dt: LocalDateTime) = new SQLTimestamp(toEpochMilli(dt))

  def toSqlDate(date: LocalDate) = new SQLDate(toEpochMilli(date.toDateTimeAtStartOfDay.toLocalDateTime))

  /**
    * @return 一天的开始：
    */
  def nowBegin(): LocalDateTime = {
    LocalDate.now().toLocalDateTime(new LocalTime(0, 0, 0))
  }

  /**
    * @return 一天的结尾：
    */
  def nowEnd(): LocalDateTime = {
    LocalDate.now().toLocalDateTime(new LocalTime(23, 59, 59))
  }

}