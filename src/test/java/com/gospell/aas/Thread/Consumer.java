package com.gospell.aas.Thread;

import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */
public class Consumer implements Runnable {
    private List<PCData> queue;

    public Consumer(List<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true){
                if(Thread.currentThread().isInterrupted()){
                    break;
                }
                PCData data =null;
                synchronized (queue){
                    if(queue.size()==0){
                        queue.wait();
                        queue.notifyAll();
                    }else{
                      data = queue.remove(0);
                    }
                    System.out.println("Consumer:"+Thread.currentThread().getId()+"xf:"+data.get()*data.get());
                    Thread.sleep(100);
                }
            }
        } catch (InterruptedException  e) {
            e.printStackTrace();
        }
    }
}
