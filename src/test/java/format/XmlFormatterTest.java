package format;


import org.junit.Test;

/**
 * @see format.XmlFormatter
 */
public class XmlFormatterTest {

    @Test
    public void test() {
        final String unformattedXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><QueryMessage\n" +
                        "        xmlns=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/message\"\n" +
                        "        xmlns:query=\"http://www.SDMX.org/resources/SDMXML/schemas/v2_0/query\">\n" +
                        "                                        <Query>\n" +
                        "        <query:CategorySchemeWhere>\n" +
                        "   \t\t\t\t\t         <query:AgencyID>XXXXXXXXXX" +
                        "XXXXX</query:AgencyID>\n" +
                        "        </query:CategorySchemeWhere>\n" +
                        "    </Query>\n\n\n\n\n" +
                        "</QueryMessage>";

        String formattedXmlString = XmlFormatter.toPrettyString(unformattedXml);
        System.out.println(formattedXmlString);
    }
}