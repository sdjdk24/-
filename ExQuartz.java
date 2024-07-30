package quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class ExQuartz {
    public static void main(String[] args) {
        try {
            // 스케줄러 인스턴스화
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            
            // JobDetail 설정
            JobDetail jobDetail = JobBuilder.newJob(quartz.PointIncrementJob.class)
                .withIdentity("pointIncrementJob", "group1")
                .build();
            
            // Trigger 설정 (20초마다 실행)
            Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("pointIncrementTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/20 * * * * ?")) // 매 20초마다 실행
                .forJob(jobDetail)
                .build();
            
            // 스케줄러에 Job 및 Trigger 등록
            scheduler.scheduleJob(jobDetail, trigger);
            
            System.out.println("스케줄러가 시작되었음");
            scheduler.start(); // 스케줄러 시작
            
            // 스케줄러를 일정 시간 후 종료
            Thread.sleep(60000); // 1분 동안 실행 (60초 = 20초 * 3회)
            
            scheduler.shutdown(); // 스케줄러 종료
            System.out.println("스케줄러의 실행이 종료되었음");
            
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
