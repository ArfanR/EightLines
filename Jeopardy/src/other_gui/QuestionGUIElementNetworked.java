package other_gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

import frames.MainGUI;
import game_logic.GameData;
import messages.BuzzInMessage;
import messages.NoBuzzInActionMessage;
import messages.QuestionAnsweredMessage;
import messages.QuestionClickedMessage;
import messages.BuzzInPeriodMessage;
import server.Client;

//inherits from QuestionGUIElement
public class QuestionGUIElementNetworked extends QuestionGUIElement {

	private Client client;
	//very similar variables as in the AnsweringLogic class
	public Boolean hadSecondChance;
	private TeamGUIComponents currentTeam;
	private TeamGUIComponents originalTeam;
	int teamIndex;
	int numTeams;
	//stores team index as the key to a Boolean indicating whether they have gotten a chance to answer the question
	private HashMap<Integer, Boolean> teamHasAnswered;
	private Timer timer;
	private Task task;
	private boolean buzzerOut, buzzedIn, incorrectAnswer;

	public QuestionGUIElementNetworked(String question, String answer, String category, int pointValue, int indexX, int indexY) {
		super(question, answer, category, pointValue, indexX, indexY);
		buzzerOut = false;
		buzzedIn = false;
		incorrectAnswer = false;
	}
	
	//set the client and also set the map with the booleans to all have false
	public void setClient(Client client, int numTeams){
		this.client = client;
		this.numTeams = numTeams;
		teamIndex = client.getTeamIndex();
		teamHasAnswered = new HashMap<>();
		for (int i = 0; i< numTeams; i++) teamHasAnswered.put(i, false);
	}
	
	//returns whether every team has had a chance at answering this question
	public Boolean questionDone(){
		Boolean questionDone = true;
		for (Boolean currentTeam : teamHasAnswered.values()) questionDone = questionDone && currentTeam;
		return questionDone;
	}
	
	private class Task extends TimerTask {
		int elapsedSeconds = 20;
	    
		@Override
		public void run() {
			timerLabel.setText(":" + elapsedSeconds);
			// No teams buzz in, goes back to main panel
        	if ((buzzerOut && elapsedSeconds == 0 && !incorrectAnswer)) {
        		if (getCurrentTeam().getTeamIndex() == client.getTeamIndex()) {
        			client.sendMessage(new NoBuzzInActionMessage(true));
        		}
        		else {
        			client.sendMessage(new NoBuzzInActionMessage(false));
        		}
        		buzzerOut = false;
        	}
        	// Team is buzzed in
        	else if (buzzedIn) {
        		if (getCurrentTeam().getTeamIndex() == client.getTeamIndex()) {
        			updateAnnouncements("answer within 20 seconds!");
        		}
 
        		buzzedIn = false;
        	}
        	else if (incorrectAnswer) {
        		elapsedSeconds = 20;
        		incorrectAnswer = false;
        	}
        	// Team does not answer, goes to buzz in action period
        	else if (!buzzerOut && elapsedSeconds == 0) {
				elapsedSeconds = 20;
				if (getCurrentTeam().getTeamIndex() == client.getTeamIndex()) {
					client.sendMessage(new BuzzInPeriodMessage());
				}
				buzzerOut = true;
				//incorrectAnswer = false;
        	}

        	elapsedSeconds--;
            timerLabel.setText(":" + elapsedSeconds);
			
		}
		
	}
	
	public void startTimer() {
		if (getCurrentTeam().getTeamIndex() == client.getTeamIndex()) {
			updateAnnouncements("answer within 20 seconds!");
		}
		buzzerOut = false;
		buzzedIn = false;
		incorrectAnswer = false;
		timer = new Timer();
		task = new Task();
	    timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	public void resetTimer() {
		//buzzerOut = false;
		//buzzedIn = false;
		task.cancel();
		task = new Task();
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	public void stopTimer() {
		timer.cancel();
	}
	
	public void setBuzzedIn(boolean buzzed) {
		buzzedIn = buzzed;
	}
	
	public void setBuzzerOut(boolean buzzed) {
		buzzerOut = buzzed;
	}
	
	public void setIncorrectAnswer(boolean answer) {
		incorrectAnswer = answer;
	}
	
	public boolean getIncorrectAnswer() {
		return incorrectAnswer;
	}
	
	//overrides the addActionListeners method in super class
	@Override
	public void addActionListeners(MainGUI mainGUI, GameData gameData){
	    
		passButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//send a buzz in message
				client.sendMessage(new BuzzInMessage(teamIndex));
				buzzedIn = true;
				buzzerOut = false;
			}
			
		});
		
		gameBoardButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//send a question clicked message
				client.sendMessage(new QuestionClickedMessage(getX(), getY()));
			}
		});
		
		submitAnswerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//send question answered message
				client.sendMessage(new QuestionAnsweredMessage(answerField.getText()));
			}
			
		});
	}
	
	//override the resetQuestion method
	@Override
	public void resetQuestion(){
		super.resetQuestion();
		hadSecondChance = false;
		currentTeam = null;
		originalTeam = null;
		teamHasAnswered.clear();
		//reset teamHasAnswered map so all teams get a chance to anaswer again
		for (int i = 0; i< numTeams; i++) teamHasAnswered.put(i, false);
	}
	
	@Override
	public void populate(){
		super.populate();
		passButton.setText("Buzz In!");
	}
	
	public int getOriginalTeam(){
		return originalTeam.getTeamIndex();
	}

	public void updateAnnouncements(String announcement){
		announcementsLabel.setText(announcement);
	}
	
	public void setOriginalTeam(int team, GameData gameData){
		originalTeam = gameData.getTeamDataList().get(team);
		updateTeam(team, gameData);
	}
	
	//update the current team of this question
	public void updateTeam(int team, GameData gameData){
		currentTeam = gameData.getTeamDataList().get(team);
		passButton.setVisible(false);
		answerField.setText("");
		//if the current team is this client
		if (team == teamIndex){
			AppearanceSettings.setEnabled(true, submitAnswerButton, answerField);
			announcementsLabel.setText("It's your turn to try to answer the question!");
		}
		//if the current team is an opponent
		else{
			AppearanceSettings.setEnabled(false, submitAnswerButton, answerField);
			announcementsLabel.setText("It's "+currentTeam.getTeamName()+"'s turn to try to answer the question.");
		}
		//mark down that this team has had a chance to answer
		teamHasAnswered.replace(team, true);
		hadSecondChance = false;
		teamLabel.setText(currentTeam.getTeamName());
	}
	
	//called from QuestionAnswerAction when there is an illformated answer
	public void illFormattedAnswer(){
		
		if (currentTeam.getTeamIndex() == teamIndex){
			announcementsLabel.setText("You had an illformatted answer. Please try again");
		}
		else{
			announcementsLabel.setText(currentTeam.getTeamName()+" had an illformatted answer. They get to answer again.");
		}
	}
	
	//set the gui to be a buzz in period, also called from QuestionAnswerAction
	public void setBuzzInPeriod(){
		
		passButton.setVisible(true);
		teamLabel.setText("");
		
		if (teamHasAnswered.get(teamIndex)){
			AppearanceSettings.setEnabled(false, submitAnswerButton, answerField, passButton);
			announcementsLabel.setText("It's time to buzz in! But you've already had your chance..");
		}
		else{
			announcementsLabel.setText("Buzz in to answer the question!");
			passButton.setEnabled(true);
			AppearanceSettings.setEnabled(false, submitAnswerButton, answerField);
		}
	}
	
	
	public TeamGUIComponents getCurrentTeam(){
		return currentTeam;
	}
}
