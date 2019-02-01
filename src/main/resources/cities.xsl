<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
    <xsl:strip-space elements="*"/>
    <xsl:template match="/*[name()='Payload']/*[name()='Cities']/*[name()='City']">
        <xsl:copy-of select="."/>
        <xsl:text>&#xa;</xsl:text><!-- put in the newline -->
    </xsl:template>
    <xsl:template match="text()"/>
</xsl:stylesheet>