package ru.rob.integration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация об исполнителе
 *
 * <p>Java class for ExecutorInfoType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ExecutorInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GUID" type="{http://dom.gosuslugi.ru/schema/integration/base/}GUIDType"/&gt;
 *         &lt;element name="fio" type="{http://dom.gosuslugi.ru/schema/integration/drs/}PersonNameType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExecutorInfoType", propOrder = {
        "guid",
        "fio"
})
public class ExecutorInfoType {

    @XmlElement(name = "GUID", required = true)
    protected String guid;
    @XmlElement(required = true)
    protected String fio;

    /**
     * Gets the value of the guid property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getGUID() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setGUID(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the fio property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFio() {
        return fio;
    }

    /**
     * Sets the value of the fio property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFio(String value) {
        this.fio = value;
    }

}

