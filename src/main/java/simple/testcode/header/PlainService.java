package simple.testcode.header;

import org.springframework.stereotype.Service;

@Service
public class PlainService {

    String doService(String serviceType) {
        String result = "this_is_" + serviceType + "_env";
        if (serviceType.equals("DEV")) {
            // to do something at dev
        } else if (serviceType.equals("STG")) {
            // to do something at stg
        }
        return result;
    }
}
