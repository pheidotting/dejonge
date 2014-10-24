package nl.dias.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartUpServlet implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        TestScheduleService service = new TestScheduleService();
        service.startAsync();
    }

}
