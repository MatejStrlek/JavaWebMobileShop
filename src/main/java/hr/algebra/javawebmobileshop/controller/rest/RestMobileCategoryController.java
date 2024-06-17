package hr.algebra.javawebmobileshop.controller.rest;

import hr.algebra.javawebmobileshop.model.MobileCategory;
import hr.algebra.javawebmobileshop.service.MobileCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/mobilewebshop/categories")
public class RestMobileCategoryController {
    @Autowired
    private MobileCategoryService mobileCategoryService;

    @GetMapping
    public ResponseEntity<List<MobileCategory>> getAllCategories() {
        return ResponseEntity.ok(mobileCategoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MobileCategory> getCategoryById(@PathVariable Long id) {
        return mobileCategoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MobileCategory> createCategory(@RequestBody MobileCategory mobileCategory) {
        return ResponseEntity.ok(mobileCategoryService.saveCategory(mobileCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MobileCategory> updateCategory(@PathVariable Long id, @RequestBody MobileCategory mobileCategory) {
        if (mobileCategoryService.getCategoryById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mobileCategory.setId(id);
        return ResponseEntity.ok(mobileCategoryService.saveCategory(mobileCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        mobileCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
