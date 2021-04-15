package com.epam.esm.controller;

import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.service.ServiceException;
import com.epam.esm.service.api.GiftCertificateDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GiftCertificateController {

    @Autowired
    private GiftCertificateDtoService giftCertificateDtoService;

    @GetMapping(value = "/certificates")
    @ResponseBody
    public List<GiftCertificateDto> findAll() {
        try {
            return giftCertificateDtoService.findAll();
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/certificates/{id}")
    @ResponseBody
    public GiftCertificateDto findById(@PathVariable int id) {
        try {
            Optional<GiftCertificateDto> optionalGiftCertificateDto =
                    giftCertificateDtoService.findById(id);
            return optionalGiftCertificateDto.orElseThrow(() -> new ControllerException(
                    "exception.message.40402", 40402, HttpStatus.NOT_FOUND));
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/certificates/search")
    @ResponseBody
    public List<GiftCertificateDto> searchByParams(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String part,
            @RequestParam(required = false) String nameSearchParam,
            @RequestParam(required = false) String nameSortParam,
            @RequestParam(required = false, defaultValue = "false") boolean orderDesc) {
        try {
            return giftCertificateDtoService.search(
                    tagName, part, nameSearchParam, nameSortParam, orderDesc);
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/certificates")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GiftCertificateDto save(@RequestBody GiftCertificateDto giftCertificateDto) {
        try {
            return giftCertificateDtoService.save(giftCertificateDto);
        } catch (DuplicateKeyException e) {
            throw new ControllerException("exception.message.40004", 40004,
                    HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            throw new ControllerException("exception.message.40005", 40005,
                    HttpStatus.BAD_REQUEST);
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/certificates")
    @ResponseBody
    public GiftCertificateDto update(@RequestBody GiftCertificateDto giftCertificateDto) {
        try {
            return giftCertificateDtoService.update(giftCertificateDto);
        } catch (DuplicateKeyException e) {
            throw new ControllerException("exception.message.40006", 40006,
                    HttpStatus.BAD_REQUEST);
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/certificates")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@RequestBody GiftCertificateDto giftCertificateDto) {
        try {
            boolean isRemoved = giftCertificateDtoService.remove(giftCertificateDto);
            if (!isRemoved) {
                throw new ControllerException("exception.message.40007", 40007,
                        HttpStatus.BAD_REQUEST);
            }
        } catch (ServiceException e) {
            throw new ControllerException("exception.message.50001", 50001,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}