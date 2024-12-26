package BusinessLogicForMobile;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;


import com.tyss.optimize.common.util.CommonConstants;
import com.tyss.optimize.nlp.util.Nlp;
import com.tyss.optimize.nlp.util.NlpException;
import com.tyss.optimize.nlp.util.NlpRequestModel;
import com.tyss.optimize.nlp.util.NlpResponseModel;
import com.tyss.optimize.nlp.util.annotation.InputParam;
import com.tyss.optimize.nlp.util.annotation.InputParams;
import com.tyss.optimize.nlp.util.annotation.ReturnType;

import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Word;
import net.sourceforge.tess4j.util.LoadLibs;


public class ToClickByText implements Nlp {
	@InputParams({ @InputParam(name = "Image Path", type = "java.lang.String"),
			@InputParam(name = "Expected Text", type = "java.lang.String"),
			@InputParam(name = "Index", type = "java.lang.Integer") })
	@ReturnType(name = "FilePath", type = "java.lang.String")
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
		Map<String, Object> attributes = nlpRequestModel.getAttributes();
		String subImagePath = (String) attributes.get("Image Path");
		String ExpectedText = (String) attributes.get("Expected Text");
		Integer index = (Integer) attributes.get("Index");
		try {
			AndroidDriver driver = nlpRequestModel.getAndroidDriver();
			convertWhiteToBlack(subImagePath);
			ITesseract tesseract = new Tesseract();
			tesseract.setDatapath(LoadLibs.extractTessResources("tessdata").getAbsolutePath());
			tesseract.setLanguage("eng");

			InputStream ip = new FileInputStream(subImagePath);
			BufferedImage bufferedImage = ImageIO.read(ip);
			List<Word> words = tesseract.getWords(bufferedImage, TessAPI.TessPageSegMode.PSM_AUTO);
			List<Word> AddWord = new ArrayList<Word>();

			for (Word word : words) {
				if (word.getText().toLowerCase().contains(ExpectedText.toLowerCase())) {
					AddWord.add(word);
				}
			}

			Word word = AddWord.get(index);
			int x = word.getBoundingBox().x;
			int y = word.getBoundingBox().y;
			int width = word.getBoundingBox().width;
			int height = word.getBoundingBox().height;
			System.out.println("Found 'add' at coordinates: (" + x + ", " + y + ")");
			int xCoordinate = x + (width / 2);
			int yCoordinate = y + (height / 2);
			int screenX = (xCoordinate * driver.manage().window().getSize().getWidth()) / bufferedImage.getWidth();
			int screenY = (yCoordinate * driver.manage().window().getSize().getHeight()) / bufferedImage.getHeight();

			PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
			Sequence clickSequence = new Sequence(finger, 1);
			clickSequence.addAction(
					finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), screenX, screenY));
			clickSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
			clickSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

			driver.perform(java.util.Arrays.asList(clickSequence));

			nlpResponseModel.setMessage("Successfully clicked on Given Text");
			nlpResponseModel.setStatus(CommonConstants.pass);
		} catch (Exception e) {
			nlpResponseModel.setMessage("Text is not found in the main image exception" + e.getMessage());
			nlpResponseModel.setStatus(CommonConstants.fail);
		}
		return nlpResponseModel;
	}
	
	

    public static void convertWhiteToBlack(String inputImagePath) throws IOException {
        // Load the image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // Create an output image with the same dimensions and type
        BufferedImage outputImage = new BufferedImage(
                inputImage.getWidth(),
                inputImage.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        // Iterate over each pixel
        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                // Get the RGB value of the current pixel
                int rgb = inputImage.getRGB(x, y);

                // Extract the color components
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // Check if the pixel is white
                if (red >= 240 && green >= 240 && blue >= 240) {
                    // Set it to black in the output image
                    outputImage.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    // Set it to white in the output image
                    outputImage.setRGB(x, y, Color.WHITE.getRGB());
                }
            }
        }

        // Save the output image
        File outputFile = new File(inputImagePath);
        ImageIO.write(outputImage, "png", outputFile);
    }
	
	
}