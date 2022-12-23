package com.turku.plugins

import com.turku.schedules.ExampleSchedule

/**
 * CRON SEQUENCE FOR QUARTZ
 * Seconds | Minutes | Hours | Day-of-Month | Month | Day-of-Week | Year (optional field)
 * http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/tutorial-lesson-06.html
 * Use this site [http://www.cronmaker.com/] to generate cron expression.
 * Standard cron expression does not work with quartz scheduler.
 */
fun configureScheduler() {
    // Add Schedulers
    ExampleSchedule()
}
