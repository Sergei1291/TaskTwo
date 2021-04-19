package com.epam.esm.controller;

import com.epam.esm.mapper.GiftCertificateDto;
import com.epam.esm.service.api.GiftCertificateDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/certificates/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    @Autowired
    private GiftCertificateDtoService giftCertificateDtoService;

    @GetMapping
    @ResponseBody
    public List<GiftCertificateDto> findAll() {
        return giftCertificateDtoService.findAll();
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public GiftCertificateDto findById(@PathVariable int id) {
        return giftCertificateDtoService.findById(id);
    }

    @GetMapping(value = "sort")
    @ResponseBody
    public List<GiftCertificateDto> findAllSortedByParam(@RequestParam String param,
                                                         @RequestParam boolean orderDesc) {
        return giftCertificateDtoService.findAllSortedByParam(param, orderDesc);
    }

    @GetMapping(value = "search")
    @ResponseBody
    public List<GiftCertificateDto> searchByParams(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String part,
            @RequestParam(required = false) String nameSearchParam,
            @RequestParam(required = false) String nameSortParam,
            @RequestParam(required = false, defaultValue = "false") boolean orderDesc) {
        return giftCertificateDtoService.search(
                tagName, part, nameSearchParam, nameSortParam, orderDesc);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GiftCertificateDto save(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateDtoService.save(giftCertificateDto);
    }

    @PutMapping("{id}")
    @ResponseBody
    public GiftCertificateDto update(@PathVariable int id,
                                     @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        return giftCertificateDtoService.update(giftCertificateDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable int id) {
        giftCertificateDtoService.remove(id);
    }

}