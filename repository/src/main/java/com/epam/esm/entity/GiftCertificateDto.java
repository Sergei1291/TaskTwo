package com.epam.esm.entity;

import com.epam.esm.entity.identifiable.GiftCertificate;
import com.epam.esm.entity.identifiable.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class GiftCertificateDto {

    @Autowired
    private GiftCertificate giftCertificate;
    private List<Tag> tagList;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(GiftCertificate giftCertificate, List<Tag> tagList) {
        this.giftCertificate = giftCertificate;
        this.tagList = tagList;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GiftCertificateDto that = (GiftCertificateDto) o;
        return Objects.equals(giftCertificate, that.giftCertificate) &&
                Objects.equals(tagList, that.tagList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giftCertificate, tagList);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "giftCertificate=" + giftCertificate +
                ", tagList=" + tagList +
                '}';
    }

}