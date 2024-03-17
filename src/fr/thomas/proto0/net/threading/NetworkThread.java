package fr.thomas.proto0.net.threading;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.model.Player;
import fr.thomas.proto0.net.INetCallback;
import fr.thomas.proto0.net.Login;
import fr.thomas.proto0.net.object.PlayerNetObject;

public class NetworkThread implements Runnable {

	private Client client;
	private Kryo kryo;
	
	private volatile INetCallback callback;
	private volatile Object response;
	
	private long beginTime;
	private volatile long timeElapsedSinceRequest;
	
	private long timeout;
	
	private NetworkResponse lastResponseStatus;

	public NetworkThread() {
		client = new Client();
		kryo = client.getKryo();
		kryo.register(Login.LoginRequest.class);
		kryo.register(Login.LoginResponse.class);
		kryo.register(PlayerNetObject.class);

		client.start();

		try {
			client.connect(5000, "127.0.0.1", 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		} // Timeout, IP, TCP port, UDP port
	}

	public Client getClient() {
		return client;
	}

	public void retry() {
		try {
			client.reconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendTCPRequest(Object object, INetCallback callback, long timeout) {
		this.callback = callback;
		beginTime = System.currentTimeMillis();
		this.timeout = timeout;
		response = null;
		client.sendTCP(object);
		
		this.lastResponseStatus = new NetworkResponse(false, NetworkJobStatus.WAITING);
	}
	
	/**
	 * Sends TCP request with 3 seconds before timeout by default
	 * @param object
	 * @param callback
	 */
	public void sendTCPRequest(Object object, INetCallback callback) {
		sendTCPRequest(object, callback, 3000);
	}
	
	public void sendTCPRequest(Object object) {
		sendTCPRequest(object, null);
	}

	@Override
	public void run() {
		client.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof Login.LoginResponse) {
					Login.LoginResponse rcv_msg = (Login.LoginResponse) object;
					response = rcv_msg;
					if(callback != null) callback.onResponse(rcv_msg);
				}
			}
		});
	}
	
	public boolean updateForResponse() {
		timeElapsedSinceRequest = System.currentTimeMillis() - beginTime;
		if(response != null) {
			this.lastResponseStatus.setStatus(NetworkJobStatus.FINISHED);
			this.lastResponseStatus.setSuccedded(true);
			return true;
		} else {
			if(timeElapsedSinceRequest > timeout) {
				if(callback != null) callback.onTimeout();
				
				this.lastResponseStatus.setStatus(NetworkJobStatus.TIMEDOUT);
				this.lastResponseStatus.setSuccedded(false);
				return true;
			}
			
			if(response == null) {
				return false;
			}
		}
		
		return false;
	}
	
	public INetCallback getResponseCallback() {
		return callback;
	}
	
	public NetworkResponse getCurrentJobStatus() {
		return lastResponseStatus;
	}
	
	public Object getResponse() {
		return response;
	}
	
	public long getBeginTime() {
		return beginTime;
	}
	
	public long getTimeElapsedSinceRequest() {
		timeElapsedSinceRequest = System.currentTimeMillis() - beginTime;
		return timeElapsedSinceRequest;
	}
	
	public enum NetworkJobStatus {
		WAITING, FINISHED, TIMEDOUT;
	}
	
	public class NetworkResponse {
		
		private boolean isSuccedded;
		private NetworkJobStatus status;
		
		public NetworkResponse(boolean isSuccedded, NetworkJobStatus status) {
			this.isSuccedded = isSuccedded;
			this.status = status;
		}
		
		public NetworkJobStatus getStatus() {
			return status;
		}
		
		public boolean isSuccedded() {
			return isSuccedded;
		}
		
		public void setStatus(NetworkJobStatus status) {
			this.status = status;
		}
		
		public void setSuccedded(boolean isSuccedded) {
			this.isSuccedded = isSuccedded;
		}
	}
}
