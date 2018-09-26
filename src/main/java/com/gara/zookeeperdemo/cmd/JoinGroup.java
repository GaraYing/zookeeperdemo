package com.gara.zookeeperdemo.cmd;

import com.gara.zookeeperdemo.utils.ConnectionWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;

/**
 * @description:
 * @author: GaraYing
 * @create: 2018-09-26 11:00
 **/

public class JoinGroup extends ConnectionWatcher {
    public void join(String groupName,String memberName) throws KeeperException, InterruptedException{
        String path="/"+groupName+"/"+memberName;
        String createdPath=zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Created:"+createdPath);
    }
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        String host = "localhost:2181";
        String groupName = "zoo";
        String memberName = "member";
        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect(host);
        joinGroup.join(groupName, memberName);

        //stay alive until process is killed or thread is interrupted
        Thread.sleep(Long.MAX_VALUE);
    }
}
