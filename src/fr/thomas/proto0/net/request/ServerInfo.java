package fr.thomas.proto0.net.request;

import java.util.ArrayList;

public class ServerInfo {
	
	public static class ServerInfoRequest {
		public int gameID;
	}
	
	public static class ServerInfoResponse {
		public String name;
		public int onlinePlayer;
		public int gameStatus;
		public int maxPlayers;
		public ArrayList<Integer> players;
	}

}
