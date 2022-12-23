package com.turku.schedules

import kotlinx.coroutines.runBlocking
import org.quartz.*
import org.quartz.impl.StdSchedulerFactory

fun ExampleSchedule() {
    class CreateQuartzJob : Job {
        @Throws(JobExecutionException::class)
        override fun execute(jExeCtx: JobExecutionContext?) {
            runBlocking {
                // Do something here
                println("TEst")
            }
        }
    }

    val job: JobDetail = JobBuilder.newJob(CreateQuartzJob::class.java)
        .withIdentity("exampleName", "exampleGroup")
        .build()


    val trigger: Trigger = TriggerBuilder.newTrigger()
        .withIdentity(TriggerKey.triggerKey("exampleNameTrigger", "exampleNameTriggerGroup"))
        .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(0, 0))
        // Runs at 00:00 everyday
        .build()

    val scheduler = StdSchedulerFactory.getDefaultScheduler()
    scheduler.scheduleJob(job, trigger)
    scheduler.start()
}
