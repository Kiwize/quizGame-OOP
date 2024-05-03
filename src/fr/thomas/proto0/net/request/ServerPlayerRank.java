package fr.thomas.proto0.net.request;

import java.util.HashMap;

public class ServerPlayerRank {

	public static class PlayerRankRequest {
		public int gameid;
	}
	
	public static class PlayerRankResponse {
		public HashMap<String, Integer> playerRankMap;
	}
	
}
