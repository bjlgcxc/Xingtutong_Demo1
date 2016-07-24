package com.framework.utils;

import javax.servlet.ServletContextEvent;
import org.springframework.web.util.Log4jConfigListener;

public class Log4jConfigListenerExt extends Log4jConfigListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {

        //设置日志记录的路径，对应于log4j.properties中的$(log4j_path)
        System.setProperty("log4j_path",event.getServletContext().getRealPath("/"));
        super.contextInitialized(event);
    }    
}

