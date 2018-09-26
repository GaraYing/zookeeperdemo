package com.gara.zookeeperdemo.cmd;

import com.gara.zookeeperdemo.utils.ConnectionWatcher;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: GaraYing
 * @create: 2018-09-26 11:01
 **/

public class ListGroup extends ConnectionWatcher {
    public void list(String groupName) throws KeeperException, InterruptedException{
        String path ="/"+groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            if(children.isEmpty()){
                System.out.printf("No memebers in group %s\n",groupName);
                System.exit(1);
            }
            for(String child:children){
                System.out.println(child);
            }
        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist \n", groupName);
            System.exit(1);
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String host = "localhost";
        String groupName = "zoo";
        ListGroup listGroup = new ListGroup();
        listGroup.connect(host);
        listGroup.list(groupName);
        listGroup.close();
    }
}
