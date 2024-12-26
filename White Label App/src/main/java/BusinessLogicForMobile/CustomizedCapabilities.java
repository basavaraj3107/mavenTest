package BusinessLogicForMobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.tyss.optimize.common.util.CommonConstants;
import com.tyss.optimize.nlp.util.Nlp;
import com.tyss.optimize.nlp.util.NlpException;
import com.tyss.optimize.nlp.util.NlpRequestModel;
import com.tyss.optimize.nlp.util.NlpResponseModel;
import com.tyss.optimize.nlp.util.annotation.InputParam;
import com.tyss.optimize.nlp.util.annotation.InputParams;
import com.tyss.optimize.nlp.util.annotation.ReturnType;



public class CustomizedCapabilities implements Nlp {

    @InputParams({
        @InputParam(name = "Desired Capability", type = "org.openqa.selenium.remote.DesiredCapabilities"),
        @InputParam(name = "Key", type = "java.lang.String"),
        @InputParam(name = "Value", type = "java.lang.String"),
        @InputParam(name = "Value Type", type = "java.lang.String")})
    @ReturnType(name = "cap", type = "org.openqa.selenium.remote.DesiredCapabilities")
    
    @Override
    public List<String> getTestParameters() throws NlpException {
        return new ArrayList<>();
    }

    @Override
    public StringBuilder getTestCode() throws NlpException {
        return new StringBuilder();
    }

    @Override
    public NlpResponseModel execute(NlpRequestModel nlpRequestModel) throws NlpException {
        NlpResponseModel nlpResponseModel = new NlpResponseModel();
        Map<String, Object> attributes = nlpRequestModel.getAttributes();

        DesiredCapabilities cap = (DesiredCapabilities) attributes.get("Desired Capability");
        String key = (String) attributes.get("Key");
        String value = (String) attributes.get("Value");
        String valueType = (String) attributes.get("Value Type");

        try {
            DesiredCapabilities tempCap = new DesiredCapabilities();
            
            switch (valueType) {
                case "String":
                    tempCap.setCapability(key, value);
                    break;
                case "Integer":
                    tempCap.setCapability(key, Integer.parseInt(value));
                    break;
                case "Boolean":
                    tempCap.setCapability(key, Boolean.parseBoolean(value));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown type: " + valueType);
            }

            cap.merge(tempCap);
            nlpResponseModel.setStatus(CommonConstants.pass);
            nlpResponseModel.setMessage("Capability is added in Desired Capability");
        } catch (Exception e) {
            nlpResponseModel.setStatus(CommonConstants.fail);
            nlpResponseModel.setMessage("Failed to set the capability: " + e.getMessage());
        }

        nlpResponseModel.setDesiredCapabilities(cap);
        nlpResponseModel.getAttributes().put("cap", cap);

        return nlpResponseModel;
    }
}
