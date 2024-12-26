
package default_package;

import com.tyss.optimize.nlp.util.Nlp;
import com.tyss.optimize.nlp.util.NlpException;
import com.tyss.optimize.nlp.util.NlpRequestModel;
import com.tyss.optimize.nlp.util.NlpResponseModel;
import com.tyss.optimize.nlp.util.annotation.InputParam;
import com.tyss.optimize.nlp.util.annotation.InputParams;
import com.tyss.optimize.nlp.util.annotation.ReturnType;
import java.util.ArrayList;
import java.util.List;




public class DefaultNlp implements Nlp {
    @InputParams({@InputParam(name = "string1", type = "java.lang.String"), @InputParam(name = "string2", type = "java.lang.String")})
    @ReturnType(name = "string3", type = "java.lang.String")

      @Override
      public List<String> getTestParameters() throws NlpException {
        List<String> params = new ArrayList<>();
        return params;
      }

      @Override
      public StringBuilder getTestCode() throws NlpException {
        StringBuilder sb = new StringBuilder();
        return sb;
      }
      @Override
      public NlpResponseModel execute(NlpRequestModel nlpRequestModel) throws NlpException {
        
          NlpResponseModel nlpResponseModel = new NlpResponseModel();
       
          
          // Your program element business logic goes here ...

          String string3 = "Return Value";
          nlpResponseModel.getAttributes().put("string3", string3);
          return nlpResponseModel;
      }
  } 