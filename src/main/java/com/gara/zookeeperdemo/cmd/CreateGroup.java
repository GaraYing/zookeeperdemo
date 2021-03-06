package com.gara.zookeeperdemo.cmd;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @description: CreateGroup
 * @author: GaraYing
 * @create: 2018-09-26 10:46
 **/

public class CreateGroup implements Watcher {

    private static final int SESSION_TIMEOUT=5000;

    private ZooKeeper zk;
    private CountDownLatch connectedSignal=new CountDownLatch(1);



    @Override
    public void process(WatchedEvent event) {
        if (event.getState()==Event.KeeperState.SyncConnected){
            connectedSignal.countDown();
        }
    }

    private void close() throws InterruptedException {
        zk.close();
    }

    private void create(String groupName) throws Exception {
        String path="/"+groupName;
        if(zk.exists(path, false)== null){
            zk.create(path, null/*data*/, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("Created:"+path);
    }

    private void connect(String hosts) throws IOException, InterruptedException {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
        connectedSignal.await();
    }

    public static void main(String[] args) throws Exception {
        String host = "localhost:2181";
        String groupName = "zoo";
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect(host);
        createGroup.create(groupName);
        createGroup.close();
    }
}
