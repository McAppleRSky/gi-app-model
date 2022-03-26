
package ru.rob.integration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.rob.integration.AttachmentType;
import ru.rob.integration.Period;


/**
 * Данные подзапроса о наличии задолженности за ЖКУ, сформированного ГИС ЖКХ
 *
 * <p>Java class for DSRType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DSRType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="subrequestGUID" type="{http://dom.gosuslugi.ru/schema/integration/base/}GUIDType"/&gt;
 *         &lt;element name="requestInfo"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="requestGUID" type="{http://dom.gosuslugi.ru/schema/integration/base/}GUIDType"/&gt;
 *                   &lt;element name="requestNumber" type="{http://dom.gosuslugi.ru/schema/integration/drs/}RequestNumberType"/&gt;
 *                   &lt;element name="organization" type="{http://dom.gosuslugi.ru/schema/integration/drs/}OrganizationInfoType"/&gt;
 *                   &lt;element name="housingFundObject" type="{http://dom.gosuslugi.ru/schema/integration/drs/}ExportHousingFundObjectInfoType"/&gt;
 *                   &lt;element name="period" type="{http://dom.gosuslugi.ru/schema/integration/base/}Period"/&gt;
 *                   &lt;element name="status" type="{http://dom.gosuslugi.ru/schema/integration/drs/}RequestStatusType"/&gt;
 *                   &lt;element name="sentDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                   &lt;element name="responseDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                   &lt;element name="executorInfo" type="{http://dom.gosuslugi.ru/schema/integration/drs/}ExecutorInfoType"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="responseStatus" type="{http://dom.gosuslugi.ru/schema/integration/drs/}ResponseStatusType"/&gt;
 *         &lt;element name="responseData" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="hasDebt" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                   &lt;element name="debtInfo" type="{http://dom.gosuslugi.ru/schema/integration/drs/}DebtInfoType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="additionalFile" type="{http://dom.gosuslugi.ru/schema/integration/base/}AttachmentType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="description" minOccurs="0"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="1000"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="executorInfo" type="{http://dom.gosuslugi.ru/schema/integration/drs/}ExecutorInfoType"/&gt;
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
@XmlType(name = "DSRType", propOrder = {
        "subrequestGUID",
        "requestInfo",
        "responseStatus",
        "responseData"
})
public class DSRType {

    @XmlElement(required = true)
    protected String subrequestGUID;
    @XmlElement(required = true)
    protected RequestInfo requestInfo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ResponseStatusType responseStatus;
    protected ResponseData responseData;

    /**
     * Gets the value of the subrequestGUID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSubrequestGUID() {
        return subrequestGUID;
    }

    /**
     * Sets the value of the subrequestGUID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSubrequestGUID(String value) {
        this.subrequestGUID = value;
    }

    /**
     * Gets the value of the requestInfo property.
     *
     * @return
     *     possible object is
     *     {@link RequestInfo }
     *
     */
    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    /**
     * Sets the value of the requestInfo property.
     *
     * @param value
     *     allowed object is
     *     {@link RequestInfo }
     *
     */
    public void setRequestInfo(RequestInfo value) {
        this.requestInfo = value;
    }

    /**
     * Gets the value of the responseStatus property.
     *
     * @return
     *     possible object is
     *     {@link ResponseStatusType }
     *
     */
    public ResponseStatusType getResponseStatus() {
        return responseStatus;
    }

    /**
     * Sets the value of the responseStatus property.
     *
     * @param value
     *     allowed object is
     *     {@link ResponseStatusType }
     *
     */
    public void setResponseStatus(ResponseStatusType value) {
        this.responseStatus = value;
    }

    /**
     * Gets the value of the responseData property.
     *
     * @return
     *     possible object is
     *     {@link ResponseData }
     *
     */
    public ResponseData getResponseData() {
        return responseData;
    }

    /**
     * Sets the value of the responseData property.
     *
     * @param value
     *     allowed object is
     *     {@link ResponseData }
     *
     */
    public void setResponseData(ResponseData value) {
        this.responseData = value;
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
     *         &lt;element name="requestGUID" type="{http://dom.gosuslugi.ru/schema/integration/base/}GUIDType"/&gt;
     *         &lt;element name="requestNumber" type="{http://dom.gosuslugi.ru/schema/integration/drs/}RequestNumberType"/&gt;
     *         &lt;element name="organization" type="{http://dom.gosuslugi.ru/schema/integration/drs/}OrganizationInfoType"/&gt;
     *         &lt;element name="housingFundObject" type="{http://dom.gosuslugi.ru/schema/integration/drs/}ExportHousingFundObjectInfoType"/&gt;
     *         &lt;element name="period" type="{http://dom.gosuslugi.ru/schema/integration/base/}Period"/&gt;
     *         &lt;element name="status" type="{http://dom.gosuslugi.ru/schema/integration/drs/}RequestStatusType"/&gt;
     *         &lt;element name="sentDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *         &lt;element name="responseDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
     *         &lt;element name="executorInfo" type="{http://dom.gosuslugi.ru/schema/integration/drs/}ExecutorInfoType"/&gt;
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
            "requestGUID",
            "requestNumber",
            "organization",
            "housingFundObject",
            "period",
            "status",
            "sentDate",
            "responseDate",
            "executorInfo"
    })
    public static class RequestInfo {

        @XmlElement(required = true)
        protected String requestGUID;
        @XmlElement(required = true)
        protected String requestNumber;
        @XmlElement(required = true)
        protected OrganizationInfoType organization;
        @XmlElement(required = true)
        protected ExportHousingFundObjectInfoType housingFundObject;
        @XmlElement(required = true)
        protected Period period;
        @XmlElement(required = true)
        @XmlSchemaType(name = "string")
        protected RequestStatusType status;
        @XmlElement(required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar sentDate;
        @XmlElement(required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar responseDate;
        @XmlElement(required = true)
        protected ExecutorInfoType executorInfo;

        /**
         * Gets the value of the requestGUID property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getRequestGUID() {
            return requestGUID;
        }

        /**
         * Sets the value of the requestGUID property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setRequestGUID(String value) {
            this.requestGUID = value;
        }

        /**
         * Gets the value of the requestNumber property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getRequestNumber() {
            return requestNumber;
        }

        /**
         * Sets the value of the requestNumber property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setRequestNumber(String value) {
            this.requestNumber = value;
        }

        /**
         * Gets the value of the organization property.
         *
         * @return
         *     possible object is
         *     {@link OrganizationInfoType }
         *
         */
        public OrganizationInfoType getOrganization() {
            return organization;
        }

        /**
         * Sets the value of the organization property.
         *
         * @param value
         *     allowed object is
         *     {@link OrganizationInfoType }
         *
         */
        public void setOrganization(OrganizationInfoType value) {
            this.organization = value;
        }

        /**
         * Gets the value of the housingFundObject property.
         *
         * @return
         *     possible object is
         *     {@link ExportHousingFundObjectInfoType }
         *
         */
        public ExportHousingFundObjectInfoType getHousingFundObject() {
            return housingFundObject;
        }

        /**
         * Sets the value of the housingFundObject property.
         *
         * @param value
         *     allowed object is
         *     {@link ExportHousingFundObjectInfoType }
         *
         */
        public void setHousingFundObject(ExportHousingFundObjectInfoType value) {
            this.housingFundObject = value;
        }

        /**
         * Gets the value of the period property.
         *
         * @return
         *     possible object is
         *     {@link Period }
         *
         */
        public Period getPeriod() {
            return period;
        }

        /**
         * Sets the value of the period property.
         *
         * @param value
         *     allowed object is
         *     {@link Period }
         *
         */
        public void setPeriod(Period value) {
            this.period = value;
        }

        /**
         * Gets the value of the status property.
         *
         * @return
         *     possible object is
         *     {@link RequestStatusType }
         *
         */
        public RequestStatusType getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         *
         * @param value
         *     allowed object is
         *     {@link RequestStatusType }
         *
         */
        public void setStatus(RequestStatusType value) {
            this.status = value;
        }

        /**
         * Gets the value of the sentDate property.
         *
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *
         */
        public XMLGregorianCalendar getSentDate() {
            return sentDate;
        }

        /**
         * Sets the value of the sentDate property.
         *
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *
         */
        public void setSentDate(XMLGregorianCalendar value) {
            this.sentDate = value;
        }

        /**
         * Gets the value of the responseDate property.
         *
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *
         */
        public XMLGregorianCalendar getResponseDate() {
            return responseDate;
        }

        /**
         * Sets the value of the responseDate property.
         *
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *
         */
        public void setResponseDate(XMLGregorianCalendar value) {
            this.responseDate = value;
        }

        /**
         * Gets the value of the executorInfo property.
         *
         * @return
         *     possible object is
         *     {@link ExecutorInfoType }
         *
         */
        public ExecutorInfoType getExecutorInfo() {
            return executorInfo;
        }

        /**
         * Sets the value of the executorInfo property.
         *
         * @param value
         *     allowed object is
         *     {@link ExecutorInfoType }
         *
         */
        public void setExecutorInfo(ExecutorInfoType value) {
            this.executorInfo = value;
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
     *         &lt;element name="hasDebt" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *         &lt;element name="debtInfo" type="{http://dom.gosuslugi.ru/schema/integration/drs/}DebtInfoType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="additionalFile" type="{http://dom.gosuslugi.ru/schema/integration/base/}AttachmentType" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="description" minOccurs="0"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="1000"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="executorInfo" type="{http://dom.gosuslugi.ru/schema/integration/drs/}ExecutorInfoType"/&gt;
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
            "hasDebt",
            "debtInfo",
            "additionalFile",
            "description",
            "executorInfo"
    })
    public static class ResponseData {

        protected boolean hasDebt;
        protected List<DebtInfoType> debtInfo;
        protected List<AttachmentType> additionalFile;
        protected String description;
        @XmlElement(required = true)
        protected ExecutorInfoType executorInfo;

        /**
         * Gets the value of the hasDebt property.
         *
         */
        public boolean isHasDebt() {
            return hasDebt;
        }

        /**
         * Sets the value of the hasDebt property.
         *
         */
        public void setHasDebt(boolean value) {
            this.hasDebt = value;
        }

        /**
         * Gets the value of the debtInfo property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the debtInfo property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDebtInfo().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DebtInfoType }
         *
         *
         */
        public void addDebtInfo(DebtInfoType item){
            getDebtInfo().add(item);
        }

        public List<DebtInfoType> getDebtInfo() {
            if (debtInfo == null) {
                debtInfo = new ArrayList<DebtInfoType>();
            }
            return this.debtInfo;
        }

        /**
         * Gets the value of the additionalFile property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the additionalFile property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAdditionalFile().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AttachmentType }
         *
         *
         */
        public List<AttachmentType> getAdditionalFile() {
            if (additionalFile == null) {
                additionalFile = new ArrayList<AttachmentType>();
            }
            return this.additionalFile;
        }

        /**
         * Gets the value of the description property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setDescription(String value) {
            this.description = value;
        }

        /**
         * Gets the value of the executorInfo property.
         *
         * @return
         *     possible object is
         *     {@link ExecutorInfoType }
         *
         */
        public ExecutorInfoType getExecutorInfo() {
            return executorInfo;
        }

        /**
         * Sets the value of the executorInfo property.
         *
         * @param value
         *     allowed object is
         *     {@link ExecutorInfoType }
         *
         */
        public void setExecutorInfo(ExecutorInfoType value) {
            this.executorInfo = value;
        }

    }

}
