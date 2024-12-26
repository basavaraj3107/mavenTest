package BusinessLogicForMobile;



import com.tyss.optimize.common.util.CommonConstants;

import com.tyss.optimize.nlp.util.Nlp;

import com.tyss.optimize.nlp.util.NlpException;

import com.tyss.optimize.nlp.util.NlpRequestModel;

import com.tyss.optimize.nlp.util.NlpResponseModel;

import com.tyss.optimize.nlp.util.annotation.InputParam;

import com.tyss.optimize.nlp.util.annotation.InputParams;

import com.tyss.optimize.nlp.util.annotation.ReturnType;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;

import java.nio.file.Paths;

import java.util.ArrayList;

import java.util.List;

import java.util.Map;



public class DownloadAndInstallApp implements Nlp {



    @InputParams({@InputParam(name = "appURL", type = "java.lang.String")})

    @ReturnType(name = "string3", type = "java.lang.String")



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



        // Get the app URL from the request

        String url = (String) attributes.get("appURL");

        String downloadPath = Paths.get(System.getProperty("user.home"), "Downloads", "app-debug1.apk").toString();



        // Command to download the APK using curl and install using adb

        String[] commands = {

                "curl -o \"" + downloadPath + "\" \"" + url + "\"",

                "adb install \"" + downloadPath + "\""

        };



        try {

            for (String command : commands) {

                ProcessBuilder processBuilder = new ProcessBuilder(

                        System.getProperty("os.name").toLowerCase().contains("win") ? "cmd.exe" : "bash", "/c", command

                );

                System.out.println("Executing: " + command);

                Process process = processBuilder.start();



                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

                    String line;

                    while ((line = reader.readLine()) != null) {

                        System.out.println(line);

                    }

                }



                // Check if the command succeeded

                if (process.waitFor() != 0) {

                    nlpResponseModel.setStatus(CommonConstants.fail);

                    nlpResponseModel.setMessage("Failed to download or install the app.");

                    nlpResponseModel.getAttributes().put("string3", "false");

                    return nlpResponseModel;

                }

            }



            // If both download and install were successful

            nlpResponseModel.setStatus(CommonConstants.pass);

            nlpResponseModel.setMessage("App downloaded and installed successfully.");

            nlpResponseModel.getAttributes().put("string3", "true");



        } catch (IOException | InterruptedException e) {

            nlpResponseModel.setStatus(CommonConstants.fail);

            nlpResponseModel.setMessage("Error occurred during the download or installation process: " + e.getMessage());

            nlpResponseModel.getAttributes().put("string3", "false");

            throw new NlpException("Error executing download and install command");

        }



        return nlpResponseModel;

    }



//    public static void main(String[] args) throws NlpException {

//        NlpRequestModel nlpRequestModel = new NlpRequestModel();

//        Map<String, Object> attributes = nlpRequestModel.getAttributes();

//        attributes.put("appURL", "https://genie.fundexpert.in/media/com.kasliwalcapital.app/debugapk/app-debug.apk");

//

//        DownloadAndInstallApp appDownloader = new DownloadAndInstallApp();

//        NlpResponseModel response = appDownloader.execute(nlpRequestModel);

//

//        // Output the result: true or false

//        System.out.println(response.getAttributes().get("string3"));

//    }

}