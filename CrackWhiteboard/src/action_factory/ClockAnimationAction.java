package action_factory;

import frames.MainGUINetworked;
import game_logic.ServerGameData;
import messages.Message;
import other_gui.QuestionGUIElementNetworked;
import other_gui.TeamGUIComponents;
import server.Client;

public class ClockAnimationAction extends Action {

	private QuestionGUIElementNetworked currentQuestion;
	private TeamGUIComponents currentTeam;

	public void executeAction(MainGUINetworked mainGUI, ServerGameData gameData, Message message,
			Client client) {
		
	}
}
