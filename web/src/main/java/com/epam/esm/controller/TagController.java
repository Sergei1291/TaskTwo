package com.epam.esm.controller;

import com.epam.esm.entity.identifiable.Tag;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(value = "/tags")
    @ResponseBody
    public List<Tag> findAll() {
        try {
            return tagService.findAll();
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tags/{id}")
    @ResponseBody
    public Tag findById(@PathVariable int id) {
        try {
            Optional<Tag> optionalTag = tagService.findById(id);
            return optionalTag.orElseThrow(() -> new ControllerException(
                    "exception.message.40401", 40401, HttpStatus.NOT_FOUND));
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tags/search")
    @ResponseBody
    public Tag findByName(@RequestParam(required = false, defaultValue = "") String name)
            throws ServiceException {
        try {
            Optional<Tag> optionalTag = tagService.findByName(name);
            return optionalTag.orElseThrow(() -> new ControllerException(
                    "exception.message.40401", 40401, HttpStatus.NOT_FOUND));
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Tag save(@RequestBody Tag tag) {
        try {
            return tagService.save(tag);
        } catch (DuplicateKeyException e) {
            throw new ControllerException("exception.message.40003", 40003,
                    HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            throw new ControllerException("exception.message.40001", 40001,
                    HttpStatus.BAD_REQUEST);
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tags")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@RequestBody Tag tag) {
        try {
            boolean isRemovedTag = tagService.remove(tag);
            if (!isRemovedTag) {
                throw new ControllerException("exception.message.40002", 40002,
                        HttpStatus.BAD_REQUEST);
            }
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}