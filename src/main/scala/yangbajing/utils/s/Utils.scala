package yangbajing.utils.s

import java.lang.management.ManagementFactory

/**
  * Created by Yang Jing (yangbajing@gmail.com) on 2016-02-03.
  */
object Utils {

  def getPid = {
    val runtime = ManagementFactory.getRuntimeMXBean
    runtime.getName.split('@')(0)
  }

}
