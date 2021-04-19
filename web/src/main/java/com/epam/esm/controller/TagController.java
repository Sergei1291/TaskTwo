package com.epam.esm.controller;

import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    @ResponseBody
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @GetMapping("{id}")
    @ResponseBody
    public Tag findById(@PathVariable int id) {
        return tagService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Tag save(@RequestBody Tag tag) {
        return tagService.save(tag);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable int id) {
        tagService.remove(id);
    }

}