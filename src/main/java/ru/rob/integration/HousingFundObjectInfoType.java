package ru.rob.integration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Сведения об объекте жилищного фонда (ОЖФ), по которому осуществляется запрос
 *
 * <p>Java class for HousingFundObjectInfoType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="HousingFundObjectInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="houseGUID" type="{http://dom.gosuslugi.ru/schema/integration/base/}GUIDType"/&gt;
 *         &lt;element name="addressDetails" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="255"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HousingFundObjectInfoType", propOrder = {
        "houseGUID",
        "addressDetails"
})
@XmlSeeAlso({
        ExportHousingFundObjectInfoType.class
})
public class HousingFundObjectInfoType {

    @XmlElement(required = true)
    protected String houseGUID;
    protected String addressDetails;

    /**
     * Gets the value of the houseGUID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getHouseGUID() {
        return houseGUID;
    }

    /**
     * Sets the value of the houseGUID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setHouseGUID(String value) {
        this.houseGUID = value;
    }

    /**
     * Gets the value of the addressDetails property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAddressDetails() {
        return addressDetails;
    }

    /**
     * Sets the value of the addressDetails property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAddressDetails(String value) {
        this.addressDetails = value;
    }

}
