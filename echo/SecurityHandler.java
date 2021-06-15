package echo;

import java.net.Socket;
import java.util.*;

public class SecurityHandler extends CacheHandler { 

	protected Correspondent peer;
	private static Security security = new Security(); 
	private boolean loggedIn = false;

	public SecurityHandler(Socket s) { super(s); }
	public SecurityHandler() { super(); }

	public void initPeer(String host, int port) {
		peer = new Correspondent();
		peer.requestConnection(host, port);
	}

	protected String response(String msg) throws Exception {
		String[] request = msg.split("\\s+");
		String response = " ";
		if(loggedIn == false){
			if(request[0].equalsIgnoreCase("new")){ //Add user
				if(security.userExists(request[1])){ 
					response = "user already exists!";
				}
				else{
					security.addUser(request[1],request[2]);
					response = "added user";
				}

			}
			else if(request[0].equalsIgnoreCase("login")){ //Login in
				if(security.validLogin(request[1],request[2])){ //Credentials match 
					loggedIn = true;
					response = "logged in";
				}
				else{ //Credentials don't match 
					response = "invalid login";
				}
			}
			else{
				response = "invalid input";
			}
			return response;
		}
		else{ //Once logged in, simply send to peer, security no longer needed. 
			peer.send(msg);
			return peer.receive();
			/*Connect pipelines 
			java echo.Server echo.MathHandler
			java echo.ProxyServer echo.CacheHandler 5555 6666
			java echo.ProxyServer echo.SecurityHandler 6666 7777
			java echo.SimpleClient 7777*/
		}
	}
}

class Security extends HashMap<String, String>{

	public synchronized void addUser(String user, String pass){
		System.out.println("from method: added user");
		this.put(user,pass);
	}

	public synchronized boolean validLogin(String user, String pass){
		if(this.get(user) != null && this.get(user).equals(pass)){ //Check if passwords match and check if user exists 
			System.out.println("from method: logged in");
			return true;
		}
		else{
			System.out.println("from method: invalid input");
			return false;
		}
	} 

	public synchronized boolean userExists(String user){
		if(this.containsKey(user)){
			return true;
		}
		else{
			return false;
		}
	}
}