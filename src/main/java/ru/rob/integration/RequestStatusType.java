package ru.rob.integration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestStatusType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RequestStatusType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Sent"/&gt;
 *     &lt;enumeration value="Processed"/&gt;
 *     &lt;enumeration value="Revoked"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 *
 */
@XmlType(name = "RequestStatusType")
@XmlEnum
public enum RequestStatusType {

    @XmlEnumValue("Sent")
    SENT("Sent"),
    @XmlEnumValue("Processed")
    PROCESSED("Processed"),
    @XmlEnumValue("Revoked")
    REVOKED("Revoked");
    private final String value;

    RequestStatusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RequestStatusType fromValue(String v) {
        for (RequestStatusType c: RequestStatusType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
