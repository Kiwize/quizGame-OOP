package fr.thomas.proto0.net.request;

import fr.thomas.proto0.net.object.PlayerNetObject;

public class Login {

	public static class LoginRequest {
		public String username;
		public String password;
	}

	public static class LoginResponse {
		public boolean isConnected;
		public PlayerNetObject player;
	}

	public static class PlayerActivityRequest {

	}

	public static class PlayerActivityAnswer {
		public boolean isPlayerActive;
	}
}
