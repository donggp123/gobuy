package com.cndinuo.domain;

import com.cndinuo.annotation.Name;

import java.io.Serializable;

public class MrhtInfo implements Serializable {

    @Name("ID")
    private Integer id;

    @Name("商户ID")
    private Integer mrhtId;

    @Name("门店图")
    private String storeImage;

    @Name("联系电话")
    private String mobile;

    @Name("省")
    private Integer province;

    @Name("市")
    private Integer city;

    @Name("区")
    private Integer district;

    @Name("详细地址")
    private String address;

    @Name("经度")
    private String lng;

    @Name("纬度")
    private String lat;

    @Name("合同编号")
    private String compactNo;

    @Name("合同名称")
    private String compactName;

    @Name("合同有效期")
    private String compactExpiryDate;

    @Name("合同照片")
    private String compactImage;

    @Name("营业执照")
    private String busLicense;

    @Name("经营许可证")
    private String busCert;

    @Name("烟草证")
    private String tobaccoLicense;

    @Name("法人身份证")
    private String legalPersonCard;

    @Name("财务联系人")
    private String financialContact;

    @Name("财务电话")
    private String contactNum;

    @Name("开户行")
    private String openBank;

    @Name("开户行支行")
    private String accountName;

    @Name("账号")
    private String accountNum;

    @Name("是否有烟草证")
    private Byte hasTobacco;

    @Name("营业时间")
    private String officeTime;

    @Name("商品分类")
    private String goodsType;

    private String typeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMrhtId() {
        return mrhtId;
    }

    public void setMrhtId(Integer mrhtId) {
        this.mrhtId = mrhtId;
    }

    public String getStoreImage() {
        return storeImage;
    }

    public void setStoreImage(String storeImage) {
        this.storeImage = storeImage == null ? null : storeImage.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getCompactNo() {
        return compactNo;
    }

    public void setCompactNo(String compactNo) {
        this.compactNo = compactNo == null ? null : compactNo.trim();
    }

    public String getCompactName() {
        return compactName;
    }

    public void setCompactName(String compactName) {
        this.compactName = compactName == null ? null : compactName.trim();
    }

    public String getCompactExpiryDate() {
        return compactExpiryDate;
    }

    public void setCompactExpiryDate(String compactExpiryDate) {
        this.compactExpiryDate = compactExpiryDate == null ? null : compactExpiryDate.trim();
    }

    public String getCompactImage() {
        return compactImage;
    }

    public void setCompactImage(String compactImage) {
        this.compactImage = compactImage == null ? null : compactImage.trim();
    }

    public String getBusLicense() {
        return busLicense;
    }

    public void setBusLicense(String busLicense) {
        this.busLicense = busLicense == null ? null : busLicense.trim();
    }

    public String getBusCert() {
        return busCert;
    }

    public void setBusCert(String busCert) {
        this.busCert = busCert == null ? null : busCert.trim();
    }

    public String getTobaccoLicense() {
        return tobaccoLicense;
    }

    public void setTobaccoLicense(String tobaccoLicense) {
        this.tobaccoLicense = tobaccoLicense;
    }

    public String getLegalPersonCard() {
        return legalPersonCard;
    }

    public void setLegalPersonCard(String legalPersonCard) {
        this.legalPersonCard = legalPersonCard == null ? null : legalPersonCard.trim();
    }

    public String getFinancialContact() {
        return financialContact;
    }

    public void setFinancialContact(String financialContact) {
        this.financialContact = financialContact == null ? null : financialContact.trim();
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum == null ? null : contactNum.trim();
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank == null ? null : openBank.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum == null ? null : accountNum.trim();
    }

    public Byte getHasTobacco() {
        return hasTobacco;
    }

    public void setHasTobacco(Byte hasTobacco) {
        this.hasTobacco = hasTobacco;
    }

    public String getOfficeTime() {
        return officeTime;
    }

    public void setOfficeTime(String officeTime) {
        this.officeTime = officeTime;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}