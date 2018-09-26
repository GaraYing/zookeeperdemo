package com.gara.zookeeperdemo.cmd;

import com.gara.zookeeperdemo.utils.ConnectionWatcher;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: GaraYing
 * @create: 2018-09-26 11:02
 **/

public class DeleteGroup extends ConnectionWatcher {
    public void delete(String groupName) throws InterruptedException, KeeperException {
        String path="/"+groupName;
        List<String> children;
        try {
            children = zk.getChildren(path, false);
            for(String child:children){
                zk.delete(path+"/"+child, -1);
            }
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }
    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        String host = "localhost:2181";
        String groupName = "zoo";
        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect(host);
        deleteGroup.delete(groupName);
        deleteGroup.close();
    }
}
