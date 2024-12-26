package BusinessLogicForMobile;

import com.tyss.optimize.common.util.CommonConstants;
import com.tyss.optimize.nlp.util.Nlp;
import com.tyss.optimize.nlp.util.NlpException;
import com.tyss.optimize.nlp.util.NlpRequestModel;
import com.tyss.optimize.nlp.util.NlpResponseModel;
import com.tyss.optimize.nlp.util.annotation.InputParam;
import com.tyss.optimize.nlp.util.annotation.InputParams;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LaunchAppUsingLocalPath implements Nlp {

    @InputParams({
        @InputParam(name = "Capability", type = "org.openqa.selenium.remote.DesiredCapabilities"),
        @InputParam(name = "HUB_URL", type = "java.lang.String")
    })
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
        AndroidDriver driver = null;

        try {
            String apkPath = System.getProperty("user.home") + File.separator + "Downloads";
            File[] apkFiles = new File(apkPath).listFiles((dir, name) -> name.endsWith(".apk"));
            
            System.out.print(" 1 " + apkFiles);
            if (apkFiles == null || apkFiles.length == 0) {
                nlpResponseModel.setStatus(CommonConstants.fail);
                nlpResponseModel.setMessage("No APK file found in Downloads.");
                return nlpResponseModel;
            }

            // Get the latest APK file
            File latestFile = apkFiles[0];
            for (File file : apkFiles) {
                if (file.lastModified() > latestFile.lastModified()) {
                    latestFile = file;
                }
            }
            System.out.print(" 2 "+ latestFile);
            apkPath = latestFile.getAbsolutePath();
            System.out.print(" 3 " + apkPath);

            DesiredCapabilities capabilities = (DesiredCapabilities) nlpRequestModel.getAttributes().get("Capability");
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "emulator-5554"); // Adjust device name as needed
            capabilities.setCapability("autoGrantPermissions", true);
            capabilities.setCapability("app", apkPath);
            capabilities.setCapability("noReset", true);

            String hubURL = (String) attributes.get("HUB_URL");
            if (hubURL == null || hubURL.isEmpty()) {
                nlpResponseModel.setStatus(CommonConstants.fail);
                nlpResponseModel.setMessage("Hub URL is missing.");
                return nlpResponseModel;
            }

            driver = new AndroidDriver(new URL(hubURL), capabilities);
            nlpRequestModel.setAndroidDriver(driver);

            nlpResponseModel.setStatus(CommonConstants.pass);
            nlpResponseModel.setMessage("Successfully launched the app.");

        } catch (Exception e) {
            nlpResponseModel.setStatus(CommonConstants.fail);
            nlpResponseModel.setMessage("Failed to open the app. Error: " + e.getMessage());
        }
        return nlpResponseModel;
    }
}

