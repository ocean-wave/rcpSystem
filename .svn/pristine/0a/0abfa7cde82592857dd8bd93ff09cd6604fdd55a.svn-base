package cn.com.cdboost.collect.util;

import java.util.concurrent.*;

/**
 * 线程池工具类,单例
 */
public final class ThreadPoolUtil {

    // 发送短信时，使用无限队列（这个峰值某个时间点可能会比较大）
    // 核心线程数量、非核心线程数量
    private static final ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    // 有界队列
    //private static final ExecutorService executorService2 = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(102400));

    /**
     * 获取java线程池
     * @return
     */
    public static final ExecutorService getExecutorService() {
        return executorService;
    }

}
