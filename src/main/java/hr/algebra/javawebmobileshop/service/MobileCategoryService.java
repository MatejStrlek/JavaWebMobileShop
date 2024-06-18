package hr.algebra.javawebmobileshop.service;

import hr.algebra.javawebmobileshop.exceptions.CategoryInUseException;
import hr.algebra.javawebmobileshop.model.MobileCategory;
import hr.algebra.javawebmobileshop.repo.MobileCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public void updateCategory(Long id, MobileCategory mobileCategory) {
        mobileCategory.setId(id);
        mobileCategoryRepository.save(mobileCategory);
    }

    public void deleteCategory(Long id) {
        try {
            mobileCategoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryInUseException("Category is in use and cannot be deleted");
        }
    }

    public List<MobileCategory> searchCategories(String query) {
        List<MobileCategory> categories = mobileCategoryRepository.findAll();
        return categories.stream()
                .filter(category -> category.getName().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }
}
