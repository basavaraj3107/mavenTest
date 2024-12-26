package BusinessLogicForMobile;

import com.tyss.optimize.common.util.CommonConstants;
import com.tyss.optimize.nlp.util.Nlp;
import com.tyss.optimize.nlp.util.NlpException;
import com.tyss.optimize.nlp.util.NlpRequestModel;
import com.tyss.optimize.nlp.util.NlpResponseModel;
import com.tyss.optimize.nlp.util.annotation.ReturnType;
import java.util.ArrayList;
import java.util.List;


public class DownloadPathFinder implements Nlp {

    @ReturnType(name = "downloadPath", type = "java.lang.String")
   
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
        nlpRequestModel.getAttributes();

        try {
            String os = System.getProperty("os.name").toLowerCase();
            String downloadPath;

            if (os.contains("win")) {
                downloadPath = System.getenv("USERPROFILE") + "\\Downloads";
            } else if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
                downloadPath = System.getProperty("user.home") + "/Downloads";
            } else {
                downloadPath = "Unsupported OS";
            }

            System.out.println("Download Path: " + downloadPath);

            nlpResponseModel.setStatus(CommonConstants.pass);
            nlpResponseModel.setMessage("Successfully captured the download path.");
            nlpResponseModel.getAttributes().put("downloadPath", downloadPath);

        } catch (Exception e) {
            nlpResponseModel.setStatus(CommonConstants.fail);
            nlpResponseModel.setMessage("Failed to capture the download path: " + e.getMessage());
        }
        return nlpResponseModel;
    }
}
    
    
    
    
