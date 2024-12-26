package BusinessLogicForMobile;

import com.tyss.optimize.common.util.CommonConstants;
import com.tyss.optimize.nlp.util.Nlp;
import com.tyss.optimize.nlp.util.NlpException;
import com.tyss.optimize.nlp.util.NlpRequestModel;
import com.tyss.optimize.nlp.util.NlpResponseModel;
import com.tyss.optimize.nlp.util.annotation.ReturnType;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;




public class generateRandomPAN implements Nlp {
	@ReturnType(name = "panNumber", type = "java.lang.String")

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
		nlpRequestModel.getAttributes();

		String panNumber="";
		try {
			Random rand = new Random();

			String firstFiveChars = "";
			for (int i = 0; i < 5; i++) {
				char randomChar = (char) (rand.nextInt(26) + 'A');
				firstFiveChars += randomChar;
			}

			String nextFourDigits = "";
			for (int i = 0; i < 4; i++) {
				int randomDigit = rand.nextInt(10);
				nextFourDigits += randomDigit;
			}
			char lastChar = (char) (rand.nextInt(26) + 'A');

			panNumber=firstFiveChars+nextFourDigits+lastChar;

			nlpResponseModel.setStatus(CommonConstants.pass);
			nlpResponseModel.setMessage("Successfully Generated PAN Number");

		} catch (Exception e) {
			nlpResponseModel.setStatus(CommonConstants.fail);	
			nlpResponseModel.setMessage("PAN Number is not Generated");
		}

		nlpResponseModel.getAttributes().put("panNumber", panNumber);
		return nlpResponseModel;
	}
} 