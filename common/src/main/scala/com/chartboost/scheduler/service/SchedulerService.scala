package com.chartboost.scheduler.service

import java.util.concurrent._
import java.util.concurrent.atomic.AtomicReference

import com.chartboost.scheduler.config.SchedulerConfig
import com.typesafe.scalalogging.LazyLogging
import org.joda.time.DateTime

import scala.concurrent.duration._
import scala.util.Try

/**
  * Scheduler Service Provides ability to schedule a specific task
  * which can be started at given time with specific duration.
  *
  * Scheduler also provides canceling a running task by scheduled task reference
  * or by specific id.
  */
trait SchedulerService {

  /**
    * Schedule a task to execute at specific start time and frequency
    *
    * This method returns a scheduled interface which allows to cancel
    * a running task
    *
    * @param id        ID of a specific task
    * @param startTime start time of a task
    * @param frequency frequency at task needs to be executed
    * @param work      A unit of work that needs to be executed
    * @return SchduledFuture which allows to cancel executing task
    */
  def scheduleFixRate(id: String, startTime: DateTime, frequency: Duration)(work: => Unit): ScheduledFuture[_]

  /**
    * Cancel a specific task by scheduled future
    *
    * @param scheduledTask
    */
  def cancel(scheduledTask: ScheduledFuture[_]): Unit

  /**
    * Cancel a specific task by Task Id
    *
    * @param id
    */
  def cancel(id: String): Unit

}

class SchedulerServiceImpl(config: SchedulerConfig) extends SchedulerService with LazyLogging {

  private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(config.defaultThreadPool)
  var scheduledTasks: Map[String, ScheduledFuture[_]] = Map()
  val idReference: AtomicReference[String] = new AtomicReference[String]

  override def scheduleFixRate(id: String, startTime: DateTime, frequency: Duration)(work: => Unit): ScheduledFuture[_] = {
    val delay = startTime.getMillis - new DateTime().getMillis
    logger info s"scheduling a task ${id} with delay of ${delay}"
    // define a runnable task which executes a unit of work
    val runnable = new Runnable {
      override def run() = {
        scheduledTasks -= (id)
        Try(work)
      }
    }
    // schedule a task at fixed rate - i.e. fixed interval (frequency)
    val scheduledFuture: ScheduledFuture[_] = scheduler.scheduleAtFixedRate(
      runnable,
      delay,
      frequency.toMillis,
      frequency.unit)
    scheduledTasks += (id -> scheduledFuture)

    scheduledFuture
  }

  override def cancel(scheduledTask: ScheduledFuture[_]): Unit = {
    logger info s"task ${scheduledTask} canceled by user"
    scheduledTask.cancel(false)
  }

  override def cancel(id: String): Unit = {
    val maybeTask = scheduledTasks get id
    maybeTask.fold(logger info s"nothing to cancel")(task => {
      task.cancel(true)
      logger info s"cancelled task by id ${id}"
    })
  }
}
