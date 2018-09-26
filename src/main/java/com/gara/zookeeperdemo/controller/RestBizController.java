package com.gara.zookeeperdemo.controller;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: rest响应Controller
 * @author: GaraYing
 * @create: 2018-09-25 16:11
 **/

@RestController
public class RestBizController {

    @GetMapping("/zkGet")
    public String zkGet(){
        Watcher watcher = (watchedEvent -> {
            System.out.println("receive event "+watchedEvent);
        });

        String value = null;


        try {
            final ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181",99999,watcher);
            final byte[] data = zooKeeper.getData("/node_1",watcher,null);
            value = new String(data);
            System.out.println("init value: " + value);
            byte[] dataUpdate ={97,98,99};
            zooKeeper.setData("/node_1",dataUpdate,zooKeeper.exists("/node_1",watcher).getVersion());

//            System.out.println("updated value: " + new String(zooKeeper.getData("/node_1"),watcher,null));
            value = new String(zooKeeper.getData("/node_1",watcher,null));

            System.out.println("updated value: " + value);

            zooKeeper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "get value from zookeeper [" + value + "]";
    }
}
