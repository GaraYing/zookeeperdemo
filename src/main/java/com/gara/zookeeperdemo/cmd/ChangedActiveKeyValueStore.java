package com.gara.zookeeperdemo.cmd;

import ch.qos.logback.core.util.TimeUtil;
import com.gara.zookeeperdemo.utils.ConnectionWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: GaraYing
 * @create: 2018-09-27 10:37
 **/

public class ChangedActiveKeyValueStore extends ConnectionWatcher {
    private static final Charset CHARSET=Charset.forName("UTF-8");
    private static final int MAX_RETRIES = 5;
    private static final long RETRY_PERIOD_SECONDS = 5;

    public void write(String path, String value) throws KeeperException,InterruptedException {
        int retries = 0;
        while (true){
            try {
                Stat stat = zk.exists(path,false);
                if (stat != null){
                    zk.setData(path,value.getBytes(CHARSET),-1);
                }else{
                    zk.create(path,value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
                }
            } catch (KeeperException.SessionExpiredException e) {
                throw e;
            } catch (KeeperException e){
              if (retries++==MAX_RETRIES){
                  throw e;
              }
                //sleep then retry
                TimeUnit.SECONDS.sleep(RETRY_PERIOD_SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String read(String path,Watcher watch) throws KeeperException, InterruptedException{
        byte[] data = zk.getData(path, watch, null);
        return new String(data,CHARSET);
    }
}
