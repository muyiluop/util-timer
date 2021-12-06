package me.zyang.util.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author zyang
 */
public class TestTimer {


    private final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        TestTimer testTimer = new TestTimer();
        testTimer.run();
    }

    public void run() throws InterruptedException {
        long now = System.currentTimeMillis();
        SystemTimer timer = new SystemTimer("TestTimer",100L,20,now);
        System.out.printf("start time %s\n",now);
        timer.add(new TimerTask(100L,()->{
            System.out.printf("我100ms执行了 time %s\n",System.currentTimeMillis()-now);
        }));
        timer.add(new TimerTask(1000L,()->{
            System.out.printf("我1000ms执行了 time %s\n",System.currentTimeMillis()-now);
        }));
        timer.add(new TimerTask(3000L,()->{
            System.out.printf("我3000ms执行了 time %s\n",System.currentTimeMillis()-now);
        }));
        timer.add(new TimerTask(4000L,()->{
            System.out.printf("我3000ms执行了我很耗时 time %s\n",System.currentTimeMillis()-now);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {

                System.out.printf("我3000ms执行了我很耗时我执行完了 time %s\n",System.currentTimeMillis()-now);
            }
        }));

        timer.add(new TimerTask(5000L,()->{
            System.out.printf("我5000ms执行了 time %s\n",System.currentTimeMillis()-now);
        }));
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(()->{
            timer.advanceClock(100L);
        },0,100L,TimeUnit.MILLISECONDS);

        synchronized (lock) {
            lock.wait();
        }

    }
}
