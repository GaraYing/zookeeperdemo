package com.gara.zookeeperdemo.cmd;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: GaraYing
 * @create: 2018-09-26 18:24
 **/

public class ConfigUpdater {

    public static final String  PATH="/config";

    private ActiveKeyValueStore store;
    private Random random=new Random();

    public ConfigUpdater(String hosts) throws InterruptedException,IOException {
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void run() throws KeeperException,InterruptedException{
        while (true){
            String value=random.nextInt(100)+"";
            store.write(PATH, value);
            System.out.printf("Set %s to %s\n",PATH,value);
            TimeUnit.SECONDS.sleep(random.nextInt(100));
        }
    }

    public static void main(String[] args) throws InterruptedException,IOException,KeeperException {
        String hosts = "localhost";
        ConfigUpdater configUpdater = new  ConfigUpdater(hosts);
        configUpdater.run();
    }
}
