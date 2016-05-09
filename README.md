# scheduler
A Scheduler that allows to schedule a task to execute for a given delay and frequency.

## Core Scheduler
Scheduler Service with an interface to schedule a task or cancel a running task by ID or Scheduled Future Object

Core module uses default configuration from application.conf which can be customize for each environment.

## Update User Activity
This is a test application that uses Core Scheduler to schedule a task with default configurations for initialDelay and frequency.

Each application can customize initial delay and frequncy to execute a specific task.

## Assumptions
1. No off the shelf scheduler framework such as Quartz or Akka Scheduler should be used.
2. No specific use case needs to be implemented.

## TODO
1. This implementation needs to enhance test suites for additional use cases
2. Improve scheduler tasks handling by implementing proper immutable data structure to store the tasks.


