package ru.javaops.masterjava.xml.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

public class JaxbUnmarshaller {
    private Unmarshaller unmarshaller;

    public JaxbUnmarshaller(JAXBContext ctx) throws JAXBException {
        unmarshaller = ctx.createUnmarshaller();
    }

    public synchronized void setSchema(Schema schema) {
        unmarshaller.setSchema(schema);
    }

    public synchronized Object unmarshal(InputStream is) throws JAXBException {
        return unmarshaller.unmarshal(is);
    }

    public synchronized Object unmarshal(Reader reader) throws JAXBException {
        return unmarshaller.unmarshal(reader);
    }

    public Object unmarshal(String str) throws JAXBException {
        return unmarshal(new StringReader(str));
    }
}
