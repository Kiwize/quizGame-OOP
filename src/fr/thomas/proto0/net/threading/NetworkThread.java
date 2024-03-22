package fr.thomas.proto0.net.threading;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import fr.thomas.proto0.controller.GameController;
import fr.thomas.proto0.net.INetCallback;
import fr.thomas.proto0.net.IServerInfoRefreshRequest;
import fr.thomas.proto0.net.object.OnlineGameNetObject;
import fr.thomas.proto0.net.object.PlayerNetObject;
import fr.thomas.proto0.net.request.Broadcast.ServerInfoRefresh;
import fr.thomas.proto0.net.request.Login;
import fr.thomas.proto0.net.request.ServerInfo.ServerCountDown;
import fr.thomas.proto0.net.request.ServerInfo.ServerInfoRequest;
import fr.thomas.proto0.net.request.ServerInfo.ServerInfoResponse;
import fr.thomas.proto0.net.request.ServerJoin.ServerJoinRequest;
import fr.thomas.proto0.net.request.ServerJoin.ServerJoinResponse;
import fr.thomas.proto0.net.request.ServerList.ServerListRequest;
import fr.thomas.proto0.net.request.ServerList.ServerListResponse;
import fr.thomas.proto0.net.request.ServerQuit.ServerQuitRequest;
import fr.thomas.proto0.net.request.ServerQuit.ServerQuitResponse;

public class NetworkThread implements Runnable {

	private Client client;
	private Kryo kryo;

	private volatile IServerInfoRefreshRequest serverInfoRefreshCallback;

	private volatile INetCallback callback;
	private volatile Object response;

	private long beginTime;
	private volatile long timeElapsedSinceRequest;

	private long timeout;

	private NetworkResponse lastResponseStatus;
	private GameController controller;

	public NetworkThread(GameController controller, String serverAddress) {
		this.controller = controller;
		client = new Client();
		kryo = client.getKryo();
		kryo.register(Login.LoginRequest.class);
		kryo.register(Login.LoginResponse.class);
		kryo.register(ServerListResponse.class);
		kryo.register(ServerListRequest.class);
		kryo.register(PlayerNetObject.class);
		kryo.register(OnlineGameNetObject.class);
		kryo.register(ArrayList.class);
		kryo.register(ServerJoinRequest.class);
		kryo.register(ServerJoinResponse.class);
		kryo.register(ServerInfoRequest.class);
		kryo.register(ServerInfoResponse.class);
		kryo.register(ServerQuitResponse.class);
		kryo.register(ServerQuitRequest.class);
		kryo.register(ServerInfoRefresh.class);
		kryo.register(ServerCountDown.class);

		client.start();

		try {
			client.connect(5000, serverAddress, 54555, 54777);
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
	 * 
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
					Login.LoginResponse response_object = (Login.LoginResponse) object;
					response = response_object;
				}

				if (object instanceof ServerListResponse) {
					ServerListResponse response_object = (ServerListResponse) object;
					response = response_object;
				}

				if (object instanceof ServerJoinResponse) {
					ServerJoinResponse response_object = (ServerJoinResponse) object;
					response = response_object;
				}

				if (object instanceof ServerInfoResponse) {
					ServerInfoResponse response_object = (ServerInfoResponse) object;
					response = response_object;
				}

				if (object instanceof ServerQuitResponse) {
					ServerQuitResponse response_object = (ServerQuitResponse) object;
					response = response_object;
				}

				if (object instanceof ServerInfoRefresh) {
					System.out.println("Déclenchement du callback peutetre null.");
					if (serverInfoRefreshCallback != null) {
						System.out.println("Déclenchement du callback.");
						serverInfoRefreshCallback.onServerInfoRefresh(object);
					}
				}
				
				if(object instanceof ServerCountDown) {
					int time = ((ServerCountDown) object).time;
					controller.updateTimeLeftBeforeGameStart(time);
				}

				if (callback != null)
					callback.onResponse(response);
			}
		});
	}

	public boolean updateForResponse() {
		timeElapsedSinceRequest = System.currentTimeMillis() - beginTime;
		if (response != null) {
			this.lastResponseStatus.setStatus(NetworkJobStatus.FINISHED);
			this.lastResponseStatus.setSuccedded(true);
			return true;
		} else {
			if (timeElapsedSinceRequest > timeout) {
				if (callback != null)
					callback.onTimeout();

				this.lastResponseStatus.setStatus(NetworkJobStatus.TIMEDOUT);
				this.lastResponseStatus.setSuccedded(false);
				return true;
			}

			if (response == null) {
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
	
	public void defineServerInfoRefreshCallback(IServerInfoRefreshRequest callback) {
		serverInfoRefreshCallback = callback;
	}

	public void deleteServerInfoRefreshCallback() {
		serverInfoRefreshCallback = null;
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
