package controller;

import com.google.gson.Gson;
import model.NodeModel;
import model.UserModel;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    private static Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(value="/login", method={RequestMethod.POST})
    @ResponseBody
    public String log(@RequestBody byte[] jsonBytes){
        Gson gson = new Gson();
        logger.info("log post method called");
        String jsonString = new String(jsonBytes);
        UserModel userModel = gson.fromJson(jsonString,UserModel.class);
        logger.info("user logged:\n" + userModel.toString());
        ZkUtils zkUtils = new ZkUtils();
        try {
            zkUtils.connect("127.0.0.1:2181,127.0.0.1:2882,127.0.0.1:2883");
            boolean logged = zkUtils.login("/client-" + userModel.getId(),userModel.getPwd().getBytes());
            if (logged) {
                logger.info("connecté");
                return new String(zkUtils.getData("/client-" + userModel.getId()));
            } else {
                logger.info("non connecté");
                return "KO";
            }
        } catch (Exception e) {
        }
        return "ok";
    }
}
