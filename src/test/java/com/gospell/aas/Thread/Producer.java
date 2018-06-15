package com.gospell.aas.Thread;

import java.util.List;
import java.util.Random;

/**
 * 生产者
 */
public class Producer implements Runnable{

    private List<PCData> queue;
    private int length;

    public Producer(List<PCData> queue,int length){
        this.queue = queue;
        this.length = length;
    }

    @Override
    public void run() {
        try {
            while (true){
                if(Thread.currentThread().isInterrupted()){
                    break;
                }
                Random r = new Random();
                long temp = r.nextInt(100);
                System.out.println("Current Thread:"+Thread.currentThread().getId()+"produce:"+temp);
                PCData data = new PCData();
                data.set(temp);
                synchronized (queue){
                    if(queue.size()>=length){
                        queue.wait();
                        queue.notifyAll();
                    }else{
                        queue.add(data);
                    }
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException  e) {
            e.printStackTrace();
        }
    }
}
