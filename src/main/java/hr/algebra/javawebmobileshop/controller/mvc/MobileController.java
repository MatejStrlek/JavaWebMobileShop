package hr.algebra.javawebmobileshop.controller.mvc;

import hr.algebra.javawebmobileshop.model.Mobile;
import hr.algebra.javawebmobileshop.publisher.CustomSpringEventPublisher;
import hr.algebra.javawebmobileshop.service.MobileCategoryService;
import hr.algebra.javawebmobileshop.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mvc/mobilewebshop/mobiles")
public class MobileController {
    private final CustomSpringEventPublisher publisher;
    private final MobileService mobileService;
    private final MobileCategoryService categoryService;

    @Autowired
    public MobileController(CustomSpringEventPublisher publisher, MobileService mobileService, MobileCategoryService categoryService) {
        this.publisher = publisher;
        this.mobileService = mobileService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listMobiles(Model model) {
        List<Mobile> mobiles = mobileService.getAllMobiles();
        model.addAttribute("mobiles", mobiles);
        publisher.publishCustomEvent("MobileController :: List mobiles screen displayed!");
        return "mobiles/list";
    }

    @PostMapping("/search")
    public String searchMobiles(@RequestParam("query") String query, Model model) {
        List<Mobile> mobiles = mobileService.searchMobiles(query);
        model.addAttribute("mobiles", mobiles);
        publisher.publishCustomEvent("MobileController :: Search mobiles done!");
        return "mobiles/list";
    }

    @GetMapping("/edit/{id}")
    public String editMobileForm(@PathVariable Long id, Model model) {
        Mobile mobile = mobileService.getMobileById(id).orElseThrow(() -> new IllegalArgumentException("Invalid mobile Id:" + id));
        model.addAttribute("mobile", mobile);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "mobiles/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMobile(@PathVariable Long id, @ModelAttribute Mobile mobile, Model model) {
        mobileService.updateMobile(id, mobile);
        return "redirect:/mvc/mobilewebshop/mobiles/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteMobile(@PathVariable Long id) {
        mobileService.deleteMobile(id);
        return "redirect:/mvc/mobilewebshop/mobiles/list";
    }
}
