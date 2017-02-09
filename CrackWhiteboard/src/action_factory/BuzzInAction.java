package action_factory;

import frames.MainGUINetworked;
import game_logic.ServerGameData;
import messages.BuzzInMessage;
import messages.Message;
import server.Client;

public class BuzzInAction extends Action{

	@Override
	public void executeAction(MainGUINetworked mainGUI, ServerGameData gameData,
			Message message, Client client) {
		BuzzInMessage buzzMessage = (BuzzInMessage) message;
		client.getCurrentQuestion().setBuzzedIn(true);
		client.getCurrentQuestion().setBuzzerOut(false);
		client.getCurrentQuestion().setIncorrectAnswer(false);
		client.getCurrentQuestion().resetTimer();
		//update the team on the current question to be the one who buzzed in
		client.getCurrentQuestion().updateTeam(buzzMessage.getBuzzInTeam(), gameData);
		mainGUI.addUpdate(gameData.getTeamDataList().get(buzzMessage.getBuzzInTeam()).getTeamName() + " buzzed in to answer!");
	}

}
