/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.publish;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * 实现多线程站点任务发布
 * 
 * @author wangwei
 */
public class SiteMultiPublish extends SitePublish{

    private static final Logger logger = LoggerFactory.getLogger(SiteMultiPublish.class);
    private static final int DEFAULT_TASK_NUMBER = 4;
    private List<Future<Boolean>> tasks;

    private final ExecutorService executorService;
    
    public SiteMultiPublish(SiteServer server) {
        this(server, DEFAULT_TASK_NUMBER);
    }

    public SiteMultiPublish(SiteServer server, int taskNumber) {
        super(server);
        this.executorService = Executors.newFixedThreadPool(taskNumber);
    }

    private List<Future<Boolean>> submitTaskProcesses(
            CompletionService<Boolean> completionService,
            List<TaskProcessable> processes) throws TaskException {
      
        tasks = new ArrayList<Future<Boolean>>();
        for (final TaskProcessable process : processes) {
            Future<Boolean> task= completionService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return process.execute(operator);
                }
            });
            tasks.add(task);
        }
        return tasks;
    }
    
    @Override
    protected void execute(Taskable task)throws TaskException{
        List<TaskProcessable> processes =  task.toTaskProcess();
        CompletionService<Boolean> completionService = 
            new ExecutorCompletionService<Boolean>(executorService);
        submitTaskProcesses(completionService,processes);
        try{
            int count = processes.size();
            for(int i = 0 ; i < count ; i++){
                Future<Boolean> f = completionService.take();
                Boolean success = f.get();
                logger.debug("publish success is {}",success);
            }
        }catch(InterruptedException e){
            logger.error("publish is fail,{} ",e.toString());
            Thread.currentThread().interrupt();
        }catch(ExecutionException e){
            Throwable t = e.getCause();
            if(t instanceof RuntimeException){
                logger.error("publish is RuntimeException,{} ",e.toString());
                throw (RuntimeException)t;
            }else if(t instanceof Error){
                logger.error("publish is Error,{} ",e.toString());
                throw (Error)t;
            }else{
                throw new IllegalStateException("Not unchecked",t);
            }
        }finally{
            tasks = null;
        }
    }
    
    @Override
    public void cancelPublish() {
        if(tasks != null){
            for(Future<Boolean> t :  tasks){
                if(t.isCancelled() ||  t.isDone()){
                    continue;
                }
                t.cancel(false);
            }
        }
    }
}
