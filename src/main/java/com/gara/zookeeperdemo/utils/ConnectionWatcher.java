package com.gara.zookeeperdemo.utils;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @description: 用于等待建立与ZooKeeper连接的辅助类
 * @author: GaraYing
 * @create: 2018-09-26 10:39
 **/

public class ConnectionWatcher implements Watcher {

    private static final int SESSION_TIMEOUT = 5000;

    protected ZooKeeper zk;

    CountDownLatch connectedSignal = new CountDownLatch(1);

    public void connect(String host) throws IOException,InterruptedException {

//        Watcher watcher = (watchedEvent)->{
//            System.out.println("received Event -> "+watchedEvent);
//        };

        zk = new ZooKeeper(host,SESSION_TIMEOUT,this);
        connectedSignal.await();
    }

    @Override
    public void process(WatchedEvent event) {
        connectedSignal.countDown();
    }

    public void close() throws InterruptedException{
        zk.close();
    }
}
