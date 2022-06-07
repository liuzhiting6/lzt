package com.lzt;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;

/**
 * @author lzt
 * @date 2022/6/7 23:15
 */
public class SimpleDemo
{
    public static final int SESSION_TIMEOUT = 2000;
    public static final String CONNECT_ADDRESS = "localhost:2181";
    //创建Zookeeper实例
    ZooKeeper zk;
    Watcher wh = event -> System.out.println(event.toString());

    private void createZKInstance()
    {
        try
        {
            zk = new ZooKeeper(CONNECT_ADDRESS, SESSION_TIMEOUT, this.wh);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void ZKOperations() throws InterruptedException, KeeperException
    {
        System.out.println("/n1. 创建 ZooKeeper 节点 (znode ： zoo2, 数据： myData2 ，权限： OPEN_ACL_UNSAFE ，节点类型： Persistent");
        zk.create("/zoo2", "myData2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("/n2. 查看是否创建成功： ");
        System.out.println(new String(zk.getData("/zoo2", false, null)));
        byte[] data = zk.getData("/zoo2", null, null);
        System.out.println("/n3. 修改节点数据 ");
        zk.setData("/zoo2", "hahahahaha".getBytes(), -1);
        System.out.println("/n4. 查看是否修改成功： ");
        System.out.println(new String(zk.getData("/zoo2", false, null)));
        System.out.println("/n5. 删除节点 ");
        zk.delete("/zoo2", -1);
        System.out.println("/n6. 查看节点是否被删除： ");
        System.out.println(" 节点状态： [" + zk.exists("/zoo2", false) + "]");
    }

    private void ZKClose() throws InterruptedException
    {
        zk.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException
    {
        SimpleDemo dm = new SimpleDemo();
        dm.createZKInstance();
        dm.ZKOperations();
        dm.ZKClose();
    }

}
