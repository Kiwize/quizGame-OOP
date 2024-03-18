package fr.thomas.proto0.net.request;

import fr.thomas.proto0.net.object.OnlineGameNetObject;
import fr.thomas.proto0.net.object.PlayerNetObject;

public class ServerJoin {
	
	public static class ServerJoinRequest {
		public OnlineGameNetObject game;
		public PlayerNetObject player;
	}
	
	public static class ServerJoinResponse {
		public boolean isJoinable;
	}

}
