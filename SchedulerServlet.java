package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

@WebServlet("/SchedulerServlet")
public class SchedulerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Scheduler scheduler;

    @Override
    public void init() throws ServletException {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            throw new ServletException("스케줄러 초기화 실패", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("스케줄러(20초마다 포인트 1증가) 실행 시작".equals(action)) {
                if (scheduler.isStarted()) {
                    response.sendRedirect("admin.jsp");
                    return;
                }

                JobDetail jobDetail = JobBuilder.newJob(PointIncrementJob.class)
                        .withIdentity("pointIncrementJob", "group1")
                        .build();

                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity("pointIncrementTrigger", "group1")
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(20)
                                .repeatForever())
                        .forJob(jobDetail)
                        .build();

                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
                
                System.out.println("스케줄러가 시작되었음");
                response.sendRedirect("admin.jsp");

            } else if ("스케줄러 실행 종료".equals(action)) {
                if (scheduler != null && !scheduler.isShutdown()) {
                    scheduler.shutdown();
                    System.out.println("스케줄러의 실행이 종료되었음");
                }
                response.sendRedirect("admin.jsp");
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
            response.sendRedirect("admin.jsp");
        }
    }

    @Override
    public void destroy() {
        try {
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
