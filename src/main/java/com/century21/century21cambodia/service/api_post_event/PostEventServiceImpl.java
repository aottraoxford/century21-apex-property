package com.century21.century21cambodia.service.api_post_event;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_modify_event_status.ModifyEventStatusRepo;
import com.century21.century21cambodia.repository.api_post_event.PostEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

@EnableScheduling
@Service
public class PostEventServiceImpl implements PostEventService, SchedulingConfigurer {
    @Autowired
    private PostEventRepo postEventRepo;
    @Autowired
    private ModifyEventStatusRepo modifyEventStatusRepo;
    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    private ScheduledFuture<?> job;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        this.scheduledTaskRegistrar=scheduledTaskRegistrar;
    }
    public void job(TaskScheduler scheduler,Timestamp timestamp,int id) {
        job=scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                modifyEventStatusRepo.updateStatus(id,false);
                job.cancel(true);
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(new Date(timestamp.getTime()));
                int eventMonth = calendar.get(Calendar.MONTH)+1;
                int eventDate = calendar.get(Calendar.DATE);
                int eventMinute = calendar.get(Calendar.MINUTE);
                int eventHour=calendar.get(Calendar.HOUR_OF_DAY);
                String cronExp = "0 "+eventMinute+" "+eventHour+" "+eventDate+" "+eventMonth +" ?";
                return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
            }
        });
    }

    @Override
    public Integer postEvent(String title, String description,String eventDate, String banner) {

        Timestamp date=null;
        if(eventDate!=null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date parsedDate = dateFormat.parse(eventDate);
                Timestamp timestamp = new Timestamp(parsedDate.getTime());
                date = timestamp;
            } catch (Exception e) {
                throw new CustomRuntimeException(400, e.getMessage());
            }
        }
        Integer id=postEventRepo.postEvent(title,description,date,banner);
        if(id==null){
            throw new CustomRuntimeException(400,"Can not post events");
        }

        //job
        if(date!=null){
            ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
            threadPoolTaskScheduler.setThreadNamePrefix("scheduler-thread");
            threadPoolTaskScheduler.initialize();
            job(threadPoolTaskScheduler, date, id);
            scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
        }

        return id;
    }
}
