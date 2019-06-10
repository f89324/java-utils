package marshal;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBElement;

public class MarshallingUtilsTest {

    @Test
    public void marshalWithRootElementTest() {
        TestMarshalDto originalObject = new TestMarshalDto(2, true, 2.1, "test");

        JAXBElement<TestMarshalDto> rootElement = MarshallingUtils.createRootElement(originalObject);
        String marshaledObject = MarshallingUtils.marshal(rootElement);

        Assert.assertNotNull(marshaledObject);
        Assert.assertNotSame("", marshaledObject);

        TestMarshalDto unmarshaledObject = MarshallingUtils.unmarshal(TestMarshalDto.class, marshaledObject);

        Assert.assertNotNull(unmarshaledObject);
        Assert.assertEquals(originalObject, unmarshaledObject);
    }

    @Test
    public void unmarshalTest() {
        String originalStr = "" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<TestMarshalDto>\n" +
                "    <booleanValue>true</booleanValue>\n" +
                "    <doubleValue>2.1</doubleValue>\n" +
                "    <intValue>2</intValue>\n" +
                "    <stringValue>test</stringValue>\n" +
                "</TestMarshalDto>\n";

        TestMarshalDto unmarshaledObject = MarshallingUtils.unmarshal(TestMarshalDto.class, originalStr);

        Assert.assertNotNull(unmarshaledObject);

        JAXBElement<TestMarshalDto> rootElement = MarshallingUtils.createRootElement(unmarshaledObject);
        String marshaledObject = MarshallingUtils.marshal(rootElement);

        Assert.assertNotNull(marshaledObject);
        Assert.assertEquals(originalStr, marshaledObject);
    }
}
