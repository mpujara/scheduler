package com.chartboost.scheduler.config

import com.typesafe.config.Config
import net.ceedubs.ficus.Ficus._
import scala.concurrent.duration.FiniteDuration

class SchedulerConfig(config: Config) {

  lazy val defaultDelay = config.as[FiniteDuration]("scheduler.delay")
  lazy val defaultFrequency = config.as[FiniteDuration]("scheduler.frequency")
  lazy val defaultCancel = config.as[FiniteDuration]("scheduler.cancel")
  lazy val defaultThreadPool = config.as[Int]("scheduler.threadpool")

}
