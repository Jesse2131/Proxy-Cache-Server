package echo;

import java.net.Socket;
import java.util.*;

public class CacheHandler extends ProxyHandler {

	protected Correspondent peer;
	private static Cache cache = new Cache(); 

	public CacheHandler(Socket s) { super(s); }
	public CacheHandler() { super(); }

	public void initPeer(String host, int port) {
		peer = new Correspondent();
		peer.requestConnection(host, port);
	}

	protected String response(String msg) throws Exception {
		System.out.println("Searching cache..." + msg);
	
		if(cache.search(msg) != null){ //Exists within the cache 
			return cache.search(msg) + " from cache";
		}
		else{ //Not in cache, update cache
			peer.send(msg);
			String response = peer.receive();
			cache.update(msg,response);
			return response + " from peer";
		}
	}
}


class Cache extends HashMap<String, String>{

	public synchronized String search(String request){
		return this.get(request);
	}

	public synchronized void update(String request, String response){
		this.put(request, response);
	}
}

