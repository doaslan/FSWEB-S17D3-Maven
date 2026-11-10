package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private final Map<Integer, Koala> koalas = new ConcurrentHashMap<>();

    @GetMapping
    public List<Koala> getAllKoalas() {
        return new ArrayList<>(koalas.values());
    }

    @GetMapping("/{id}")
    public Koala getKoalaById(@PathVariable int id) {
        Koala koala = koalas.get(id);

        if (koala == null) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        return koala;
    }

    @PostMapping
    public Koala saveKoala(@RequestBody Koala koala) {
        if (koala.getId() <= 0 || koala.getName() == null || koala.getGender() == null) {
            throw new ZooException("Invalid koala data", HttpStatus.BAD_REQUEST);
        }

        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala updateKoala(@PathVariable int id, @RequestBody Koala koala) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        koala.setId(id);
        koalas.put(id, koala);
        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala deleteKoala(@PathVariable int id) {
        Koala deleted = koalas.remove(id);

        if (deleted == null) {
            throw new ZooException("Koala not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        return deleted;
    }
}
