package controller;

import com.google.gson.Gson;
import model.NodeModel;
import model.UserModel;
import org.apache.log4j.Logger;

import model.AccountModel;
import org.apache.zookeeper.*;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ZkUtils {

    private static Logger logger = Logger.getLogger(ZkUtils.class);

    private ZooKeeper zk;
    private CountDownLatch connSignal = new CountDownLatch(1);

    //host should be 127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002
    public ZooKeeper connect(String host) throws Exception {
        zk = new ZooKeeper(host, 3000, new Watcher() {
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    connSignal.countDown();
                }
            }
        });
        connSignal.await();
        return zk;
    }

    public void close() throws InterruptedException {
        zk.close();
    }

    public boolean login(String path, byte[] data) throws Exception {


        /*
        logger.info("try to get into " + path + " with password " + new String(data) + " whereas password is " + new String(getData(path)));

        Gson g = new Gson();

        AccountModel a = new AccountModel();
        a.setBalance(100.00);
        a.setIban("FR0979817937687576351908");
        a.setName("tom");
        a.setType("courant");

        UserModel u = new UserModel();
        u.setId("tom");
        u.setPwd("tom");

        NodeModel nodeModel = new NodeModel();

        ArrayList arrayList = new ArrayList();
        arrayList.add(0,a);
        nodeModel.setAccounts(arrayList);
        nodeModel.setUser(u);

        String node = g.toJson(nodeModel);

        updateNode("/client-tom", node.getBytes());

        logger.info(node);

        logger.info(new String(getData("/client-tom")));

        logger.info(g.fromJson(new String(getData("/client-tom")),NodeModel.class).toString());
        */

        Gson g = new Gson();
        NodeModel nodeModel = g.fromJson(new String(getData(path)),NodeModel.class);
        String pwd = nodeModel.getUser().getPwd();

        if (pwd.equals(new String(data))) {
            logger.info(nodeModel.getUser().getId() + " logged");
            return true;
        } else {
            logger.info(nodeModel.getUser().getId() + " not logged");
            return false;
        }
    }

    public String createNode(String path, byte[] data) throws Exception
    {
        return zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public boolean exists(String path, byte[] data) throws KeeperException, InterruptedException {
        if (zk.exists(path,true) == null){
            return false;
        } else {
            return true;
        }
    }

    public void updateNode(String path, byte[] data) throws Exception
    {
        zk.setData(path, data, zk.exists(path, true).getVersion());
    }

    public void deleteNode(String path) throws Exception
    {
        zk.delete(path,  zk.exists(path, true).getVersion());
    }

    public byte[] getData(String path) throws KeeperException, InterruptedException {
        return zk.getData(path,true,zk.exists(path,true));
    }
}
