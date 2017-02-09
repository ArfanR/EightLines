package action_factory;

import frames.MainGUINetworked;
import game_logic.ServerGameData;
import messages.Message;
import messages.BuzzInPeriodMessage;
import other_gui.QuestionGUIElementNetworked;
import other_gui.TeamGUIComponents;
import server.Client;

public class BuzzInPeriodAction extends Action {

	private QuestionGUIElementNetworked currentQuestion;
	private TeamGUIComponents currentTeam;

	public void executeAction(MainGUINetworked mainGUI, ServerGameData gameData, Message message,
			Client client) {
		BuzzInPeriodMessage qTimerResetMessage = (BuzzInPeriodMessage) message;
		currentQuestion = client.getCurrentQuestion();
		currentTeam = currentQuestion.getCurrentTeam();
		
	
		currentTeam.deductPoints(currentQuestion.getPointValue());
		mainGUI.addUpdate(currentTeam.getTeamName()+" got the answer wrong! $"+currentQuestion.getPointValue()+" will be deducted from their total. ");
		
		// set buzz in period
		currentQuestion.setBuzzInPeriod();
		mainGUI.addUpdate("Another team can buzz in within the next 20 seconds to answer!");
	}
	
}
