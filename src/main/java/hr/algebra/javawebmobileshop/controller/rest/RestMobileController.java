package hr.algebra.javawebmobileshop.controller.rest;

import hr.algebra.javawebmobileshop.model.Mobile;
import hr.algebra.javawebmobileshop.service.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/mobilewebshop/mobiles")
public class RestMobileController {
    @Autowired
    private MobileService mobileService;

    @GetMapping
    public ResponseEntity<List<Mobile>> getAllMobiles() {
        return ResponseEntity.ok(mobileService.getAllMobiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mobile> getMobileById(@PathVariable Long id) {
        return mobileService.getMobileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mobile> createMobile(@RequestBody Mobile mobile) {
        return ResponseEntity.ok(mobileService.saveMobile(mobile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mobile> updateMobile(@PathVariable Long id, @RequestBody Mobile mobile) {
        if (mobileService.getMobileById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mobile.setId(id);
        return ResponseEntity.ok(mobileService.saveMobile(mobile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMobile(@PathVariable Long id) {
        mobileService.deleteMobile(id);
        return ResponseEntity.noContent().build();
    }
}
