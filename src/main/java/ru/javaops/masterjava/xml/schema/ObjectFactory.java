
package ru.javaops.masterjava.xml.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.javaops.masterjava.xml.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _City_QNAME = new QName("http://javaops.ru", "City");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.javaops.masterjava.xml.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Payload }
     * 
     */
    public Payload createPayload() {
        return new Payload();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link Payload.Cities }
     * 
     */
    public Payload.Cities createPayloadCities() {
        return new Payload.Cities();
    }

    /**
     * Create an instance of {@link Payload.Users }
     * 
     */
    public Payload.Users createPayloadUsers() {
        return new Payload.Users();
    }

    /**
     * Create an instance of {@link CityType }
     * 
     */
    public CityType createCityType() {
        return new CityType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CityType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://javaops.ru", name = "City")
    public JAXBElement<CityType> createCity(CityType value) {
        return new JAXBElement<CityType>(_City_QNAME, CityType.class, null, value);
    }

}
