package yangbajing.utils.s.persist

import org.scalatest.{BeforeAndAfterAll, FunSuite}

import scala.io.Source

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-04-13.
  */
class SqlHelperTest extends FunSuite with BeforeAndAfterAll {
  val h2Helper = SqlHelper.fromH2("jdbc:h2:~/utils-test", "sc", "")

  test("testUsing") {

  }

  test("testFromPG") {

  }

  test("testFromH2") {
    h2Helper.using { conn =>
      val metaData = conn.getMetaData
      val pn = metaData.getDatabaseProductName
      println(pn)

      val sql = "CREATE TABLE IF NOT EXISTS setting_data(key VARCHAR(255), data TEXT)"
      val stmt = conn.createStatement()
      stmt.executeUpdate(sql)
      val p2pSite = Source.fromFile("/data/mobile/sichuan/p2psite2.txt").getLines().mkString("\n")
      //      println(p2pSite)

      val rs = stmt.executeQuery("SELECT COUNT(1) FROM setting_data")
      rs.next()
      if (rs.getInt(1) == 0) {
        val pstmt = conn.prepareStatement("INSERT INTO setting_data(key, data) VALUES(?, ?)")
        pstmt.setString(1, "p2p-site")
        pstmt.setString(2, p2pSite)
        val insertRet = pstmt.executeUpdate()
        println(s"insertRet: $insertRet")
      }
    }
  }

  test("testFromMySQL") {

  }

  override protected def afterAll(): Unit = {
    h2Helper.close()
  }

}
