package ru.rob.integration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Экспортируемые сведения об объекте жилищного фонда (ОЖФ), по которому осуществляется запрос
 *
 * <p>Java class for ExportHousingFundObjectInfoType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ExportHousingFundObjectInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/drs/}HousingFundObjectInfoType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="fiasHouseGUID" type="{http://dom.gosuslugi.ru/schema/integration/base/}GUIDType"/&gt;
 *         &lt;element name="address"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="4000"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExportHousingFundObjectInfoType", propOrder = {
        "fiasHouseGUID",
        "address"
})
public class ExportHousingFundObjectInfoType
        extends HousingFundObjectInfoType
{

    @XmlElement(required = true)
    protected String fiasHouseGUID;
    @XmlElement(required = true)
    protected String address;

    /**
     * Gets the value of the fiasHouseGUID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFiasHouseGUID() {
        return fiasHouseGUID;
    }

    /**
     * Sets the value of the fiasHouseGUID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFiasHouseGUID(String value) {
        this.fiasHouseGUID = value;
    }

    /**
     * Gets the value of the address property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAddress(String value) {
        this.address = value;
    }

}
