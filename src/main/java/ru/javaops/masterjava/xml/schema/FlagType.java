
package ru.javaops.masterjava.xml.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for flagType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="flagType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="active"/>
 *     &lt;enumeration value="deleted"/>
 *     &lt;enumeration value="superuser"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "flagType", namespace = "http://javaops.ru")
@XmlEnum
public enum FlagType {

    @XmlEnumValue("active")
    ACTIVE("active"),
    @XmlEnumValue("deleted")
    DELETED("deleted"),
    @XmlEnumValue("superuser")
    SUPERUSER("superuser");
    private final String value;

    FlagType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FlagType fromValue(String v) {
        for (FlagType c: FlagType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
