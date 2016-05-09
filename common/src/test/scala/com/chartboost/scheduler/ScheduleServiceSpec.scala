package com.chartboost.scheduler

import java.util.UUID

import com.chartboost.scheduler.config.SchedulerConfig
import com.chartboost.scheduler.service.{SchedulerServiceImpl, SchedulerService}
import com.softwaremill.macwire._
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime
import org.scalatest.concurrent.ScalaFutures
import org.specs2.mutable.Specification
import scala.concurrent.duration._

/**
  * Test cases to run scheduler
  */
class ScheduleServiceSpec extends Specification with ScalaFutures with LazyLogging {

  lazy val config = ConfigFactory.load()
  lazy val schedulerConfig: SchedulerConfig = wire[SchedulerConfig]
  val schedulerService: SchedulerService = wire[SchedulerServiceImpl]

  "Scheduler Service" should {
    /**
      * Schedule a task to run every 100ms and cancel the task after 1000ms.
      */
    "schedule task every 100 ms" in {
      val taskId = UUID.randomUUID().toString
      val scheduledFuture = schedulerService.scheduleFixRate(id = taskId, startTime = new DateTime(), frequency = 100.millis)(work)
      scheduledFuture.isCancelled must_== false
      Thread.sleep(1000)
      scheduledFuture.cancel(true)
      scheduledFuture.isDone must_== true
    }
  }

  private def work(): Unit = {
    logger info "work is in progress..."
  }

}
