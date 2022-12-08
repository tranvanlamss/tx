package com.vietsoft.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.vietsoft.common.Constants;

@Configuration
public class ThreadPoolConfig {

	@Bean
	@Qualifier("getThreadPoolTaskExecutor")
	public TaskExecutor getThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Constants.THREADING_POOL_SIZE);
		executor.setMaxPoolSize(Constants.THREADING_POOL_SIZE);
		executor.setThreadNamePrefix("app_task_executor_thread");
		executor.initialize();

		return executor;
	}
	
	@Bean
	@Qualifier("getThreadPoolTaskScheduler")
	public ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
		ThreadPoolTaskScheduler executorTaskScheduler = new ThreadPoolTaskScheduler();
		executorTaskScheduler.setPoolSize(6);
		executorTaskScheduler.setThreadNamePrefix("app_task_executor_thread_schedule");
		executorTaskScheduler.initialize();

		return executorTaskScheduler;
	}

	@Bean
	@Qualifier("getThreadPoolLogTaskExecutor")
	public TaskExecutor getThreadPoolLogTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Constants.THREADING_POOL_SIZE);
		executor.setMaxPoolSize(Constants.THREADING_POOL_SIZE);
		executor.setThreadNamePrefix("log_task_executor_thread");
		executor.initialize();

		return executor;
	}
	@Bean
	@Qualifier("getThreadPoolTransactionLogTaskExecutor")
	public TaskExecutor getThreadPoolTransactionLogTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Constants.THREADING_POOL_SIZE);
		executor.setMaxPoolSize(Constants.THREADING_POOL_SIZE + 2);
		executor.setThreadNamePrefix("transaction_log_task_executor_thread");
		executor.initialize();

		return executor;
	}
	@Bean
	@Qualifier("getThreadReportTaskExecutor")
	public TaskExecutor getThreadPoolReportTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Constants.THREADING_POOL_SIZE);
		executor.setMaxPoolSize(Constants.THREADING_POOL_SIZE);
		executor.setThreadNamePrefix("report_task_executor_thread");
		executor.initialize();
		return executor;
	}
}
