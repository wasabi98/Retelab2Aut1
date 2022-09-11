package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.domain.Note;
import hu.bme.aut.retelab2.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    @Autowired
    private AdRepository adRepository;

    @GetMapping
    public List<Ad> getAll(@RequestParam(required = false, defaultValue = "") String keyword) {
        return keyword.equals("") ? adRepository.findAll() : adRepository.findByKeyword(keyword);
    }

    @GetMapping("{id}")
    public ResponseEntity<Ad> getById(@PathVariable long id) {
        Ad ad = adRepository.findById(id);
        if (ad == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(ad);
    }

    @PostMapping
    public Ad create(@RequestBody Ad ad) {
        ad.setId(null);
        ad.setDateOfCreation(new Date());
        return adRepository.save(ad);
    }

    @PutMapping
    public ResponseEntity<Ad> update(@RequestBody Ad ad) {
        Ad n = adRepository.findById(ad.getId());
        if (n == null)
            return ResponseEntity.notFound().build();
        n = adRepository.save(ad);
        return ResponseEntity.ok(n);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Ad ad = adRepository.findById(id);
        if (ad == null)
            return ResponseEntity.notFound().build();
        else {
            adRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
    }
}
