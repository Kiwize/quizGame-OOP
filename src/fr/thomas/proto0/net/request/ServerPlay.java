package fr.thomas.proto0.net.request;

import fr.thomas.proto0.net.object.AnswerNetObject;
import fr.thomas.proto0.net.object.QuestionNetObject;

public class ServerPlay {

	public static class ShowQuestion {
		public QuestionNetObject question;
		public int onlineGameID;
	}

	public static class AnswerTimeLeft {
		public int timeLeftToAnswer;
		public int maxTime;
	}

	public static class GetPlayerAnswer {
		public int playerID;
		public AnswerNetObject answer;
		public int onlineGameID;
	}

	public static class ServerEndGame {
		public int score;
	}
}
