package controller;

import com.google.gson.Gson;
import model.UserModel;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


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
        return "ok";
    }
}
