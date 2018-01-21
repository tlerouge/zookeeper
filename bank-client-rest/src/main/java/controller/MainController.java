package controller;

import com.google.gson.Gson;
import model.UserModel;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.apache.http.client.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
public class MainController {

    private static Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(value="/bank", method={RequestMethod.GET})
    public ModelAndView getMenu() {
        ModelAndView mav = new ModelAndView("menu");
        mav.addObject("userModel", new UserModel());
        logger.info("Menu displayed...");
        return mav;
    }

    @RequestMapping(value="/bank", method={RequestMethod.POST})
    public ModelAndView logIn(@ModelAttribute("userModel") UserModel userModel, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("portal");
        userModel.toString();
        try {
            String postUrl = "http://localhost:8080/server/login";
            Gson gson = new Gson();
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            String jsonString = gson.toJson(userModel);
            byte[] jsonBytes = jsonString.getBytes();
            os.write(jsonBytes);
            os.flush();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_BAD_GATEWAY) {
                logger.warn("La connexion a echoue (erreur 502)");
                throw new RuntimeException("Connection failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                logger.info("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    logger.info(output);
                }

            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            logger.warn("La requête a echoue");
        } catch (IOException e) {
            logger.warn("La requête a echoue");
        }

        return mav;
    }

}
