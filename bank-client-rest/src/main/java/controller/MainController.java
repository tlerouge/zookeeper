package controller;

import model.UserModel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
        //rest request
        return mav;
    }

}
