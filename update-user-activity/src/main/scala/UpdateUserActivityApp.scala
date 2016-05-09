import java.util.UUID

import com.chartboost.scheduler.config.SchedulerConfig
import com.chartboost.scheduler.service.{SchedulerServiceImpl, SchedulerService}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import com.softwaremill.macwire._
import org.joda.time.DateTime
import scala.concurrent.duration._

/**
  * Basic interface to load configurations and wire scheduler service
  */

trait AppLoader extends LazyLogging {

  // load application.conf configuration
  lazy val config = ConfigFactory.load()
  // load scheduler specific configurations
  lazy val schedulerConfig: SchedulerConfig = wire[SchedulerConfig]
  // wire schedule service
  lazy val schedulerService: SchedulerService = wire[SchedulerServiceImpl]

}

/**
  * An Application that allows to schedule a Update User Activity Task
  * with initial delay of 50ms and frequency of 100ms.
  */
object UpdateUserActivityApp extends AppLoader {

  def main(args: Array[String]): Unit = {
    val startTime = new DateTime().plusMillis(50)
    val frequency: FiniteDuration = schedulerConfig.defaultFrequency
    // generate random string for task id
    val id = UUID.randomUUID().toString
    // schedule a task with 50ms delay and frequency of 100ms
    val scheduledFuture = schedulerService.scheduleFixRate(id = id, startTime, frequency = frequency)(work = updateUserActivity())
    // for testing purpose, sleep current thread to 3 seconds
    Thread.sleep(3000)
    // cancel task execution by a specific id
    //schedulerService.cancel(id)
    // cancel task execution by scheduled future
    schedulerService.cancel(scheduledFuture)

  }

  /**
    * stubbed implementation of update user activity
    * this can be enhanced to perform actual updates of user activity
    */
  private def updateUserActivity(): Unit = {
    logger info "update user activity here..."
  }
}
