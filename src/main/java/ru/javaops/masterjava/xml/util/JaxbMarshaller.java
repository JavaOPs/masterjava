package ru.javaops.masterjava.xml.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.validation.Schema;
import java.io.StringWriter;
import java.io.Writer;

public class JaxbMarshaller {
    private Marshaller marshaller;

    public JaxbMarshaller(JAXBContext ctx) throws JAXBException {
        marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
    }

    public void setProperty(String prop, Object value) throws PropertyException {
        marshaller.setProperty(prop, value);
    }

    public synchronized void setSchema(Schema schema) {
        marshaller.setSchema(schema);
    }

    public String marshal(Object instance) throws JAXBException {
        StringWriter sw = new StringWriter();
        marshal(instance, sw);
        return sw.toString();
    }

    public synchronized void marshal(Object instance, Writer writer) throws JAXBException {
        marshaller.marshal(instance, writer);
    }

}
