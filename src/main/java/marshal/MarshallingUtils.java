package marshal;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Utilities for simple and convenient marshal / unmarshal.
 */
public class MarshallingUtils {

    private static final String UTF_8 = "UTF-8";

    private MarshallingUtils() {
        // not used
    }

    /**
     * @param obj object for marshal
     * @return string with marshaled object
     */
    public static <T> String marshal(T obj) {
        Class clazz = getRealClass(obj);
        JAXBContext jc = getJaxbContext(clazz);

        StringWriter stringWriter = new StringWriter();

        try {
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, UTF_8);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(obj, stringWriter);
        } catch (JAXBException e) {
            throw new MarshalUtilsException(e);
        }

        return stringWriter.toString();
    }

    /**
     * @param clazz class for unmarshal
     * @param data  String with marshaled object
     * @return unmarshaled object
     */
    public static <T> T unmarshal(Class<T> clazz, String data) {
        if (data == null || data.isEmpty()) {
            throw new MarshalUtilsException("No data to unmarshal!");
        }

        JAXBContext context = getJaxbContext(clazz);

        StringReader reader = new StringReader(data);

        try {
            Unmarshaller unmarshaller = context.createUnmarshaller();
            if (clazz == null) {
                Object result = unmarshaller.unmarshal(new StreamSource(reader));
                if (result instanceof JAXBElement) {
                    return ((JAXBElement<T>) result).getValue();
                } else {
                    return (T) result;
                }
            } else {
                return unmarshaller.unmarshal(new StreamSource(reader), clazz).getValue();
            }
        } catch (JAXBException e) {
            throw new MarshalUtilsException(e);
        }
    }

    public static <T> JAXBElement<T> createRootElement(T value) {
        ClassLoader currentLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(MarshallingUtils.class.getClassLoader());

        QName qname = new QName("", value.getClass().getSimpleName());
        Class valueClass = value.getClass();
        JAXBElement<T> result = new JAXBElement<T>(qname, valueClass, null, value);

        Thread.currentThread().setContextClassLoader(currentLoader);

        return result;
    }

    private static <T> JAXBContext getJaxbContext(Class<T> contextClass) {
        ClassLoader currentLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(MarshallingUtils.class.getClassLoader());

        JAXBContext context = JaxbContextHolder.getContextByKey(contextClass);

        Thread.currentThread().setContextClassLoader(currentLoader);
        return context;
    }

    private static <T> Class getRealClass(T obj) {
        Class clazz;
        if (obj instanceof JAXBElement) {
            clazz = ((JAXBElement) obj).getValue().getClass();
        } else {
            clazz = obj.getClass();
        }
        return clazz;
    }
}