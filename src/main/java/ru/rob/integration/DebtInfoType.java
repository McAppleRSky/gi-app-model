package ru.rob.integration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.rob.integration.AttachmentType;
import ru.rob.integration.NsiRef;


/**
 * Информация о задолженности, подтвержденной судебным актом
 *
 * <p>Java class for DebtInfoType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DebtInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="person"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="firstName" type="{http://dom.gosuslugi.ru/schema/integration/drs/}PersonNameType"/&gt;
 *                   &lt;element name="lastName" type="{http://dom.gosuslugi.ru/schema/integration/drs/}PersonNameType"/&gt;
 *                   &lt;element name="middleName" type="{http://dom.gosuslugi.ru/schema/integration/drs/}PersonNameType" minOccurs="0"/&gt;
 *                   &lt;element name="snils" type="{http://dom.gosuslugi.ru/schema/integration/drs/}SNILSType" minOccurs="0"/&gt;
 *                   &lt;element name="document" type="{http://dom.gosuslugi.ru/schema/integration/drs/}DocumentType" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="document" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="type" type="{http://dom.gosuslugi.ru/schema/integration/nsi-base/}nsiRef"/&gt;
 *                   &lt;element name="attachment" type="{http://dom.gosuslugi.ru/schema/integration/base/}AttachmentType"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
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
@XmlType(name = "DebtInfoType", propOrder = {
        "person",
        "document"
})
public class DebtInfoType {

    @XmlElement(required = true)
    protected Person person;
    protected List<Document> document;

    /**
     * Gets the value of the person property.
     *
     * @return
     *     possible object is
     *     {@link Person }
     *
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     *
     * @param value
     *     allowed object is
     *     {@link Person }
     *
     */
    public void setPerson(Person value) {
        this.person = value;
    }

    /**
     * Gets the value of the document property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the document property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocument().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Document }
     *
     *
     */
    public List<Document> getDocument() {
        if (document == null) {
            document = new ArrayList<Document>();
        }
        return this.document;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="type" type="{http://dom.gosuslugi.ru/schema/integration/nsi-base/}nsiRef"/&gt;
     *         &lt;element name="attachment" type="{http://dom.gosuslugi.ru/schema/integration/base/}AttachmentType"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "type",
            "attachment"
    })
    public static class Document {

        @XmlElement(required = true)
        protected NsiRef type;
        @XmlElement(required = true)
        protected AttachmentType attachment;

        /**
         * Gets the value of the type property.
         *
         * @return
         *     possible object is
         *     {@link NsiRef }
         *
         */
        public NsiRef getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         *
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *
         */
        public void setType(NsiRef value) {
            this.type = value;
        }

        /**
         * Gets the value of the attachment property.
         *
         * @return
         *     possible object is
         *     {@link AttachmentType }
         *
         */
        public AttachmentType getAttachment() {
            return attachment;
        }

        /**
         * Sets the value of the attachment property.
         *
         * @param value
         *     allowed object is
         *     {@link AttachmentType }
         *
         */
        public void setAttachment(AttachmentType value) {
            this.attachment = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="firstName" type="{http://dom.gosuslugi.ru/schema/integration/drs/}PersonNameType"/&gt;
     *         &lt;element name="lastName" type="{http://dom.gosuslugi.ru/schema/integration/drs/}PersonNameType"/&gt;
     *         &lt;element name="middleName" type="{http://dom.gosuslugi.ru/schema/integration/drs/}PersonNameType" minOccurs="0"/&gt;
     *         &lt;element name="snils" type="{http://dom.gosuslugi.ru/schema/integration/drs/}SNILSType" minOccurs="0"/&gt;
     *         &lt;element name="document" type="{http://dom.gosuslugi.ru/schema/integration/drs/}DocumentType" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "firstName",
            "lastName",
            "middleName",
            "snils",
            "document"
    })
    public static class Person {

        @XmlElement(required = true)
        protected String firstName;
        @XmlElement(required = true)
        protected String lastName;
        protected String middleName;
        protected String snils;
        protected DocumentType document;

        /**
         * Gets the value of the firstName property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * Sets the value of the firstName property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setFirstName(String value) {
            this.firstName = value;
        }

        /**
         * Gets the value of the lastName property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * Sets the value of the lastName property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setLastName(String value) {
            this.lastName = value;
        }

        /**
         * Gets the value of the middleName property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getMiddleName() {
            return middleName == null ? "" : middleName;
        }

        /**
         * Sets the value of the middleName property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setMiddleName(String value) {
            this.middleName = value;
        }

        /**
         * Gets the value of the snils property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getSnils() {
            return snils;
        }

        /**
         * Sets the value of the snils property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setSnils(String value) {
            this.snils = value;
        }

        /**
         * Gets the value of the document property.
         *
         * @return
         *     possible object is
         *     {@link DocumentType }
         *
         */
        public DocumentType getDocument() {
            return document;
        }

        /**
         * Sets the value of the document property.
         *
         * @param value
         *     allowed object is
         *     {@link DocumentType }
         *
         */
        public void setDocument(DocumentType value) {
            this.document = value;
        }

    }

}
