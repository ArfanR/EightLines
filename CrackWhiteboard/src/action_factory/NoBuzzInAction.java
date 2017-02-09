package action_factory;

import frames.MainGUINetworked;
import game_logic.ServerGameData;
import messages.Message;
import messages.NoBuzzInActionMessage;
import messages.QuestionClickedMessage;
import messages.BuzzInPeriodMessage;
import other_gui.QuestionGUIElementNetworked;
import other_gui.TeamGUIComponents;
import server.Client;

public class NoBuzzInAction extends Action {

	private QuestionGUIElementNetworked currentQuestion;
	private TeamGUIComponents currentTeam;

	public void executeAction(MainGUINetworked mainGUI, ServerGameData gameData, Message message,
			Client client) {
		NoBuzzInActionMessage bTimerResetMessage = (NoBuzzInActionMessage) message;
		currentQuestion = client.getCurrentQuestion();
		currentQuestion.stopTimer();
		currentTeam = currentQuestion.getCurrentTeam();
		gameData.setNextTurn(gameData.nextTurn(currentQuestion.getOriginalTeam()));
		if (bTimerResetMessage.getUpdate()) {
			mainGUI.addUpdate("No one answered the question correctly. The correct answer was: "+currentQuestion.getAnswer());
		}
		// final jeopardy check
		if (gameData.readyForFinalJeoaprdy()){
			mainGUI.startFinalJeopardy();
		}
		else {
			//add update to Game Progress, determine whether the game board buttons should be enabled or disabled, and change the main panel
			mainGUI.addUpdate("It is "+gameData.getCurrentTeam().getTeamName()+"'s turn to choose a question.");
			if (gameData.getCurrentTeam().getTeamIndex() != client.getTeamIndex()) mainGUI.disableAllButtons();
			mainGUI.showMainPanel();
			mainGUI.startTimer();
		}
	}
	
}
