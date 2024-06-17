package hr.algebra.javawebmobileshop.controller.mvc;

import hr.algebra.javawebmobileshop.publisher.CustomSpringEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvc/mobilewebshop")
public class MobileController {
    private CustomSpringEventPublisher publisher;


}
