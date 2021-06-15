package echo;

import java.net.*;
import echo.*;

public class MathHandler extends RequestHandler {

	public MathHandler(Socket sock) {
		super(sock);
	}

	public MathHandler() {
		super();
	}

	protected String response(String request) throws Exception {
		String[] userInput = request.split("\\s+");
		Double result = 0.0;
		if(userInput[0].equalsIgnoreCase("add")){
			for(int i=1; i<userInput.length; i++){
				result += Double.parseDouble(userInput[i]);
			}
		}
		else if(userInput[0].equalsIgnoreCase("sub")){
			result = Double.parseDouble(userInput[1]);
			for(int i=2; i<userInput.length; i++){
				result -= Double.parseDouble(userInput[i]);
			}
		}
		else if(userInput[0].equalsIgnoreCase("mul")){
			result = Double.parseDouble(userInput[1]);
			for(int i=2; i<userInput.length; i++){
				result *= Double.parseDouble(userInput[i]);
			}
		}
		else if(userInput[0].equalsIgnoreCase("div")){
			result = Double.parseDouble(userInput[1]);
			for(int i=2; i<userInput.length; i++){
				result /= Double.parseDouble(userInput[i]);
			}
		}
		return result.toString();
	}
}