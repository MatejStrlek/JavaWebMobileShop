package hr.algebra.javawebmobileshop.service;

import hr.algebra.javawebmobileshop.model.Mobile;
import hr.algebra.javawebmobileshop.repo.MobileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MobileService {
    @Autowired
    private MobileRepository mobileRepository;

    public List<Mobile> getAllMobiles() {
        return mobileRepository.findAll();
    }

    public Optional<Mobile> getMobileById(Long id) {
        return mobileRepository.findById(id);
    }

    public Mobile saveMobile(Mobile mobile) {
        return mobileRepository.save(mobile);
    }

    public void deleteMobile(Long id) {
        mobileRepository.deleteById(id);
    }

    public List<Mobile> searchMobiles(String query) {
        List<Mobile> mobiles = mobileRepository.findAll();
        return mobiles.stream()
                .filter(mobile -> mobile.getName().toLowerCase().contains(query.toLowerCase()) ||
                        mobile.getBrand().toLowerCase().contains(query.toLowerCase()) ||
                        mobile.getCategory().getName().toLowerCase().contains(query.toLowerCase()) ||
                        String.valueOf(mobile.getPrice()).contains(query))
                .toList();
    }
}
