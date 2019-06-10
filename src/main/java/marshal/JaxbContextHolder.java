package marshal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to create and store {@link JAXBContext}
 */
class JaxbContextHolder {

    private JaxbContextHolder() {
        // not used
    }

    private static Map<Object, JAXBContext> contextHolder = new ConcurrentHashMap<>();

    static JAXBContext getContextByKey(Class clazz) {
        JAXBContext context;
        if (contextHolder.containsKey(clazz)) {
            context = contextHolder.get(clazz);
        } else {
            try {
                context = JAXBContext.newInstance(clazz);
            } catch (JAXBException e) {
                throw new MarshalUtilsException(e);
            }
            contextHolder.put(clazz, context);
        }

        return context;
    }
}
