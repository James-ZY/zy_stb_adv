package com.gospell.aas.netty;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/14.
 */
public class DemoTest {
    public static void main(String[] args) {
        new Derived();
       /* Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        map.put("d",4);
        map.put("e",5);
        for (Map.Entry<String,Integer> entry: map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        Iterator<Map.Entry<String,Integer>> it = map.entrySet().iterator();
        while (it.hasNext()){
            System.out.println(it.next().getKey());
        }*/

    }
}
class Base{
    public int i = 2;
    public Base(){
        System.out.println(i);
        display();
        System.out.println(i);
    }
    public  void display(){
        System.out.println(i);
    }
}

class Derived extends Base{
    private  int i = 22;
    public Derived(){
        display();
    }
    public  void display(){
        System.out.println(i);
    }
}
