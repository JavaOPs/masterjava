package ru.javaops.masterjava.xml.util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;

public class XPathProcessor {
    private static final DocumentBuilderFactory DOCUMENT_FACTORY = DocumentBuilderFactory.newInstance();
    private static final DocumentBuilder DOCUMENT_BUILDER;

    private static final XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
    private static final XPath XPATH = XPATH_FACTORY.newXPath();

    static {
        DOCUMENT_FACTORY.setNamespaceAware(true);
        try {
            DOCUMENT_BUILDER = DOCUMENT_FACTORY.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    private final Document doc;

    public XPathProcessor(InputStream is) {
        try {
            doc = DOCUMENT_BUILDER.parse(is);
        } catch (SAXException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static synchronized XPathExpression getExpression(String exp) {
        try {
            return XPATH.compile(exp);
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> T evaluate(XPathExpression expression, QName type) {
        try {
            return (T) expression.evaluate(doc, type);
        } catch (XPathExpressionException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
