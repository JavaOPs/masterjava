package ru.javaops.masterjava.xml.util;

import com.google.common.io.Resources;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringReader;
import java.net.URL;


public class Schemas {

    // SchemaFactory is not thread-safe
    private static final SchemaFactory SCHEMA_FACTORY = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    public static synchronized Schema ofString(String xsd) {
        try {
            return SCHEMA_FACTORY.newSchema(new StreamSource(new StringReader(xsd)));
        } catch (SAXException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static synchronized Schema ofClasspath(String resource) {
        // http://digitalsanctum.com/2012/11/30/how-to-read-file-contents-in-java-the-easy-way-with-guava/
        return ofURL(Resources.getResource(resource));
    }

    public static synchronized Schema ofURL(URL url) {
        try {
            return SCHEMA_FACTORY.newSchema(url);
        } catch (SAXException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static synchronized Schema ofFile(File file) {
        try {
            return SCHEMA_FACTORY.newSchema(file);
        } catch (SAXException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
