package com.gara.zookeeperdemo.cmd;

import com.gara.zookeeperdemo.utils.ConnectionWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;

/**
 * @description:
 * @author: GaraYing
 * @create: 2018-09-26 15:13
 **/

public class ActiveKeyValueStore extends ConnectionWatcher {
    private static final Charset CHARSET=Charset.forName("UTF-8");

    public void write(String path, String value) throws KeeperException,InterruptedException {
        Stat stat = zk.exists(path,false);
        if (stat != null){
            zk.setData(path,value.getBytes(CHARSET),-1);
        }else{
            zk.create(path,value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }
    }

    public String read(String path, Watcher watcher) throws KeeperException,InterruptedException{
        byte[] data = zk.getData(path,watcher,null);
        return new String(data,CHARSET);
    }
}
