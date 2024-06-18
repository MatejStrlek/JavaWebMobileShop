package hr.algebra.javawebmobileshop.controller.mvc;

import hr.algebra.javawebmobileshop.model.Mobile;
import hr.algebra.javawebmobileshop.model.MobileCategory;
import hr.algebra.javawebmobileshop.publisher.CustomSpringEventPublisher;
import hr.algebra.javawebmobileshop.service.MobileCategoryService;
import hr.algebra.javawebmobileshop.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/mobilewebshop")
public class AdminController {
    private final CustomSpringEventPublisher publisher;
    private final MobileService mobileService;
    private final MobileCategoryService categoryService;

    @Autowired
    public AdminController(CustomSpringEventPublisher publisher, MobileService mobileService, MobileCategoryService categoryService) {
        this.publisher = publisher;
        this.mobileService = mobileService;
        this.categoryService = categoryService;
    }

    @GetMapping("/mobiles/list")
    public String listMobiles(Model model) {
        List<Mobile> mobiles = mobileService.getAllMobiles();
        model.addAttribute("mobiles", mobiles);
        publisher.publishCustomEvent("MobileController :: List mobiles screen displayed!");
        return "mobiles/list";
    }

    @PostMapping("/mobiles/search")
    public String searchMobiles(@RequestParam("query") String query, Model model) {
        List<Mobile> mobiles = mobileService.searchMobiles(query);
        model.addAttribute("mobiles", mobiles);
        publisher.publishCustomEvent("MobileController :: Search mobiles done!");
        return "mobiles/list";
    }

    @GetMapping("/mobiles/edit/{id}")
    public String editMobileForm(@PathVariable Long id, Model model) {
        Mobile mobile = mobileService.getMobileById(id).orElseThrow(() -> new IllegalArgumentException("Invalid mobile Id:" + id));
        model.addAttribute("mobile", mobile);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "mobiles/edit";
    }

    @PostMapping("/mobiles/edit/{id}")
    public String updateMobile(@PathVariable Long id, @ModelAttribute Mobile mobile) {
        mobileService.updateMobile(id, mobile);
        publisher.publishCustomEvent("MobileController :: Update mobile done!");
        return "redirect:/admin/mobilewebshop/mobiles/list";
    }

    @PostMapping("/mobiles/delete/{id}")
    public String deleteMobile(@PathVariable Long id) {
        mobileService.deleteMobile(id);
        publisher.publishCustomEvent("MobileController :: Delete mobile done!");
        return "redirect:/admin/mobilewebshop/mobiles/list";
    }

    @GetMapping("/mobiles/add")
    public String addMobileForm(Model model) {
        model.addAttribute("mobile", new Mobile());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "mobiles/add";
    }

    @PostMapping("/mobiles/add")
    public String saveMobile(@ModelAttribute Mobile mobile) {
        mobileService.saveMobile(mobile);
        publisher.publishCustomEvent("MobileController :: Add mobile done!");
        return "redirect:/admin/mobilewebshop/mobiles/list";
    }

    @GetMapping("/categories/list")
    public String listCategories(Model model) {
        List<MobileCategory> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @PostMapping("categories/search")
    public String searchCategories(@RequestParam("query") String query, Model model) {
        List<MobileCategory> categories = categoryService.searchCategories(query);
        model.addAttribute("categories", categories);
        publisher.publishCustomEvent("MobileController :: Search categories done!");
        return "categories/list";
    }

    @GetMapping("/categories/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new MobileCategory());
        return "categories/add";
    }

    @PostMapping("/categories/add")
    public String saveCategory(@ModelAttribute MobileCategory category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/mobilewebshop/categories/list";
    }

    @GetMapping("categories/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        MobileCategory category = categoryService.getCategoryById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("category", category);
        return "categories/edit";
    }

    @PostMapping("categories/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute MobileCategory category) {
        categoryService.updateCategory(id, category);
        return "redirect:/admin/mobilewebshop/categories/list";
    }

    @PostMapping("categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/mobilewebshop/categories/list";
    }
}
