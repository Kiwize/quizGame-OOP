package fr.thomas.proto0.net.request;

import java.util.ArrayList;

import fr.thomas.proto0.net.object.OnlineGameNetObject;

public class ServerList {
	
	public static class ServerListRequest {
		
	}
	
	public static class ServerListResponse {
		public ArrayList<OnlineGameNetObject> servers;
	}

}
