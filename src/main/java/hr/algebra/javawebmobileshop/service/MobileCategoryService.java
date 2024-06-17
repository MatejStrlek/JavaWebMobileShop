package hr.algebra.javawebmobileshop.service;

import hr.algebra.javawebmobileshop.model.MobileCategory;
import hr.algebra.javawebmobileshop.repo.MobileCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MobileCategoryService {
    @Autowired
    private MobileCategoryRepository mobileCategoryRepository;

    public List<MobileCategory> getAllCategories() {
        return mobileCategoryRepository.findAll();
    }

    public Optional<MobileCategory> getCategoryById(Long id) {
        return mobileCategoryRepository.findById(id);
    }

    public MobileCategory saveCategory(MobileCategory mobileCategory) {
        return mobileCategoryRepository.save(mobileCategory);
    }

    public void deleteCategory(Long id) {
        mobileCategoryRepository.deleteById(id);
    }
}
