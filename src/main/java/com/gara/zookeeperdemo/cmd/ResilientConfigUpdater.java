package com.gara.zookeeperdemo.cmd;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: GaraYing
 * @create: 2018-09-26 18:24
 **/

public class ResilientConfigUpdater {

    public static final String  PATH="/config";

    private ChangedActiveKeyValueStore store;
    private Random random=new Random();

    public ResilientConfigUpdater(String hosts) throws InterruptedException,IOException {
        store = new ChangedActiveKeyValueStore();
        store.connect(hosts);
    }

    public void run() throws KeeperException,InterruptedException{
        while (true){
            String value=random.nextInt(100)+"";
            store.write(PATH, value);
            System.out.printf("Set %s to %s\n",PATH,value);
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        }
    }

    public static void main(String[] args) throws InterruptedException,IOException,KeeperException {
        String hosts = "localhost";
        ResilientConfigUpdater configUpdater = new ResilientConfigUpdater(hosts);
        configUpdater.run();
    }
}
