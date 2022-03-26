package ru.rob.integration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Сведения об организации
 *
 * <p>Java class for OrganizationInfoType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="OrganizationInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="orgRootGUID" type="{http://dom.gosuslugi.ru/schema/integration/base/}GUIDType"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganizationInfoType", propOrder = {
        "orgRootGUID",
        "name",
        "tel"
})
public class OrganizationInfoType {

    @XmlElement(required = true)
    protected String orgRootGUID;
    @XmlElement(required = true)
    protected String name;
    protected String tel;

    /**
     * Gets the value of the orgRootGUID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOrgRootGUID() {
        return orgRootGUID;
    }

    /**
     * Sets the value of the orgRootGUID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOrgRootGUID(String value) {
        this.orgRootGUID = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the tel property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTel() {
        return tel;
    }

    /**
     * Sets the value of the tel property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTel(String value) {
        this.tel = value;
    }

}

