
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import listeners.ExitWindowListener;
import other_gui.AppearanceConstants;
import other_gui.AppearanceSettings;

public class StartGUI extends JFrame{
	
	private JPanel mainPanel;
	//private JFileChooser fileChooser;
	//private JButton fileChooserButton;
	private List<JTextField> teamNameTextFields;
	private List<JLabel> teamNameLabels;
	private static final int MAX_NUMBER_OF_TEAMS = 4;
	private int numberOfTeams;
	private JButton startGameButton;
	private JButton clearDataButton;
	private JButton exitButton;
	private JSlider slider;
	//private JLabel fileNameLabel;
	private JButton logoutButton;
	private JCheckBox quickPlay;
	private Boolean haveNames;
	//private Boolean haveValidFile;
	//private GameData gameData;
	//logged in user
	//private User loggedInUser;
	
	public StartGUI(){
		
		super("Crack the WhiteBoard");
		//loggedInUser = user;
		numberOfTeams = -1;
		haveNames = false;
		//haveValidFile = false;
		
		initializeComponents();
		createGUI();
		addListeners();
	}
	
	//private methods
	private void initializeComponents(){
		mainPanel = new JPanel(new GridLayout(4, 1));
		//fileChooser = new JFileChooser();
		teamNameTextFields = new ArrayList<>(4);
		teamNameLabels = new ArrayList<>(4);
		//fileNameLabel = new JLabel("");
		logoutButton = new JButton("Logout");
		//gameData = new GameData();
		//fileRating = new JLabel("");
		quickPlay = new JCheckBox("Quick Play?");
		
		for (int i = 0; i<MAX_NUMBER_OF_TEAMS; i++){
			teamNameTextFields.add(new JTextField());
			teamNameLabels.add(new JLabel("Please name Team "+(i+1)));
		}
		
		startGameButton = new JButton("Start Coding");
		clearDataButton = new JButton("Clear Choices");
		exitButton = new JButton("Exit");
		//fileChooserButton = new JButton("Choose File");
		slider = new JSlider();
		
	}
	
	private void createGUI(){
		//setting appearance of member variable gui components
		//setting background colors
		AppearanceSettings.setBackground(Color.darkGray, exitButton, logoutButton, clearDataButton, startGameButton, slider,
				teamNameLabels.get(0), teamNameLabels.get(1), teamNameLabels.get(2), teamNameLabels.get(3));
		
		AppearanceSettings.setBackground(AppearanceConstants.lightBlue, teamNameTextFields.get(0), teamNameTextFields.get(1), teamNameTextFields.get(2),
				teamNameTextFields.get(3));
		
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, mainPanel);
	
		//setting fonts
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, teamNameTextFields.get(0), teamNameTextFields.get(1), teamNameTextFields.get(2), teamNameTextFields.get(3),
				teamNameLabels.get(0), teamNameLabels.get(1), teamNameLabels.get(2), teamNameLabels.get(3), 
				exitButton, clearDataButton, logoutButton, startGameButton);
		
		//other
		AppearanceSettings.setForeground(Color.lightGray, exitButton, logoutButton, clearDataButton, startGameButton,
				teamNameLabels.get(0), teamNameLabels.get(1), teamNameLabels.get(2), teamNameLabels.get(3),
				slider);

		AppearanceSettings.setOpaque(exitButton, clearDataButton, logoutButton, startGameButton, slider,
				teamNameLabels.get(0), teamNameLabels.get(1), teamNameLabels.get(2), teamNameLabels.get(3));

		AppearanceSettings.setSize(180, 70, exitButton, clearDataButton, startGameButton, logoutButton);
		AppearanceSettings.setSize(150, 80, 
				teamNameTextFields.get(0), teamNameTextFields.get(1), teamNameTextFields.get(2), teamNameTextFields.get(3));
		
		AppearanceSettings.setSize(250, 100, teamNameLabels.get(0), teamNameLabels.get(1), teamNameLabels.get(2), teamNameLabels.get(3));
		
		AppearanceSettings.unSetBorderOnButtons(exitButton, logoutButton, clearDataButton, startGameButton);
		
		AppearanceSettings.setTextAlignment(teamNameLabels.get(0), teamNameLabels.get(1), teamNameLabels.get(2), teamNameLabels.get(3));

		setAllInvisible(teamNameTextFields, teamNameLabels);
		//check box settings
		quickPlay.setFont(AppearanceConstants.fontSmallest);
		quickPlay.setHorizontalTextPosition(SwingConstants.LEFT);
		quickPlay.setPreferredSize(new Dimension(200, 30));
		/*
		//file chooser settings
		fileChooser.setPreferredSize(new Dimension(400, 500));
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fileChooser.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
		*/
		//slider settings
		AppearanceSettings.setSliders(1, MAX_NUMBER_OF_TEAMS, 1, 1, AppearanceConstants.fontSmallest, slider);
		slider.setSnapToTicks(true);
		slider.setPreferredSize(new Dimension(500, 50));
		startGameButton.setEnabled(false);

		createMainPanel();
		
		setBackground(AppearanceConstants.darkBlue);
		add(mainPanel, BorderLayout.CENTER);
		setSize(800, 825);
	}
	
	//sets the label and textField visible again
	private void setVisible(JLabel label, JTextField textField){
		//the first text field is always shown so we can use their border 
		Border border = teamNameTextFields.get(0).getBorder();
		
		textField.setBackground(AppearanceConstants.lightBlue);
		textField.setForeground(Color.black);
		textField.setBorder(border);

		label.setBackground(Color.darkGray);
		label.setForeground(Color.lightGray);
	}
	//I wanted to user BoxLayout for resizability but if you simply set a components invisble with
		// setVisible(false), it changes the size of the components that are visible. This is my way aroung that
	private void setInvisible(JLabel label, JTextField textField){
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, textField, label);
		AppearanceSettings.setForeground(AppearanceConstants.darkBlue, textField, label);
		textField.setBorder(AppearanceConstants.blueLineBorder);
	}
	//used in the constructor to set everything invisible (except the first label and text field)
	private void setAllInvisible(List<JTextField> teamNameTextFields, List<JLabel> teamNameLabels){
		
		for (int i = 1; i<4; i++) setInvisible(teamNameLabels.get(i), teamNameTextFields.get(i));
	}
	
	private void createMainPanel(){
		//initialize local panels
		JPanel teamNamesPanel = new JPanel();
		JPanel teamLabelsPanel1 = new JPanel();
		JPanel teamLabelsPanel2 = new JPanel();
		JPanel teamTextFieldsPanel1 = new JPanel();
		JPanel teamTextFieldsPanel2 = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel teamsAndFilePanel = new JPanel();
		JPanel numberOfTeamsPanel = new JPanel();
		//JPanel fileChooserPanel = new JPanel();
		JPanel northPanel = new JPanel();
		JPanel welcomePanel = new JPanel(new BorderLayout());
		JPanel titlePanel = new JPanel(new BorderLayout());
		//initialize labels
		JLabel welcomeLabel = new JLabel("Crack the WhiteBoard!");
		JLabel explainContentLabel = new JLabel("Choose the game file, number of teams, and team names before starting the game.");
		JLabel numberOfTeamsLabel = new JLabel("Please choose the number of teams that will be playing on the slider below.");
		//JLabel chooseGameFileLabel = new JLabel("Please choose a game file.");
				
		//set appearances on local variables
		AppearanceSettings.setBackground(AppearanceConstants.lightBlue, welcomeLabel, explainContentLabel, welcomePanel, titlePanel);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, explainContentLabel, numberOfTeamsLabel);
		AppearanceSettings.setTextAlignment(welcomeLabel, explainContentLabel, numberOfTeamsLabel);
		
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, numberOfTeamsLabel, numberOfTeamsPanel, teamsAndFilePanel,
				buttonPanel, teamNamesPanel, teamLabelsPanel1, teamLabelsPanel2, teamTextFieldsPanel1, teamTextFieldsPanel2);
		AppearanceSettings.setForeground(Color.lightGray, numberOfTeamsLabel);
		
		AppearanceSettings.setSize(800, 60, welcomePanel, explainContentLabel);
		AppearanceSettings.setSize(800, 100, buttonPanel, numberOfTeamsPanel);
		
		welcomeLabel.setFont(AppearanceConstants.fontLarge);
		numberOfTeamsLabel.setSize(700, 60);

		//setting box layouts of panels
		AppearanceSettings.setBoxLayout(BoxLayout.LINE_AXIS, buttonPanel, teamLabelsPanel1, teamLabelsPanel2, teamTextFieldsPanel1, teamTextFieldsPanel2);
		AppearanceSettings.setBoxLayout(BoxLayout.PAGE_AXIS, northPanel, teamNamesPanel, teamsAndFilePanel, numberOfTeamsPanel);

		//method iterates through components and add glue after each one is added, bool indicates whether glue should be added at the initially as well
		AppearanceSettings.addGlue(teamLabelsPanel1, BoxLayout.LINE_AXIS, true, teamNameLabels.get(0), teamNameLabels.get(1));
		AppearanceSettings.addGlue(teamLabelsPanel2, BoxLayout.LINE_AXIS, true, teamNameLabels.get(2), teamNameLabels.get(3));
		AppearanceSettings.addGlue(teamTextFieldsPanel1, BoxLayout.LINE_AXIS, true, teamNameTextFields.get(0), teamNameTextFields.get(1));
		AppearanceSettings.addGlue(teamTextFieldsPanel2, BoxLayout.LINE_AXIS, true, teamNameTextFields.get(2), teamNameTextFields.get(3));
		AppearanceSettings.addGlue(teamNamesPanel, BoxLayout.PAGE_AXIS, true, teamLabelsPanel1, teamTextFieldsPanel1, teamLabelsPanel2, teamTextFieldsPanel2);
		
		//don't want to pass in fileNameLabel since I don't want glue after it
		//AppearanceSettings.addGlue(fileChooserPanel, BoxLayout.LINE_AXIS, true, chooseGameFileLabel, fileChooserButton);
		//fileChooserPanel.add(fileNameLabel);
		
		//don't want to pass in fileChooserPanel since I don't want glue after it
		AppearanceSettings.addGlue(teamsAndFilePanel, BoxLayout.PAGE_AXIS, true, numberOfTeamsPanel);
		//teamsAndFilePanel.add(fileChooserPanel);
		
		AppearanceSettings.addGlue(buttonPanel, BoxLayout.LINE_AXIS, true, startGameButton, clearDataButton, logoutButton, exitButton);
		
		//add other components to other containers
		welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
		welcomePanel.add(quickPlay, BorderLayout.EAST);

		titlePanel.add(welcomePanel, BorderLayout.NORTH);
		titlePanel.add(explainContentLabel, BorderLayout.SOUTH);
		
		northPanel.add(titlePanel);
		
		numberOfTeamsPanel.add(numberOfTeamsLabel);
		numberOfTeamsPanel.add(slider);
		
		mainPanel.add(northPanel);
		mainPanel.add(teamsAndFilePanel);
		mainPanel.add(teamNamesPanel);
		mainPanel.add(buttonPanel);
	}
	
	/*
	//determines whether the chosen file is valid
	private void setHaveValidFile(File file){
		
		//if they had already chosen a valid file, but want to replace it, need to clear stored data
		if (haveValidFile) gameData.clearData();
	
		try{
			//try parsing this file; the parseFile method could throw an exception here, in which case we know it was invalid
			gameData.parseFile(file.getAbsolutePath());
			haveValidFile = true;
			
			if (gameData.getGameFile().getNumberOfRatings() == -1) fileNameLabel.setText(file.getName() + "    no rating");

			else fileNameLabel.setText(file.getName() + "    average rating: "+gameData.getGameFile().getAverageRating()+"/5");
			//check if the user can start the game
			startGameButton.setEnabled(haveValidFile && haveNames());
		}
		
		catch (Exception e){
			haveValidFile = false;
			startGameButton.setEnabled(false);
			fileNameLabel.setText("");
			//pop up with error message
			JOptionPane.showMessageDialog(this,
					e.getMessage(),
					"File Reading Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	*/
	private void addListeners(){
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new ExitWindowListener(this));
		
		//adding a document listener to each text field. This will allow us to determine if the user has entered team names
		for (int i = 0; i<MAX_NUMBER_OF_TEAMS; i++){
			teamNameTextFields.get(i).getDocument().addDocumentListener(new MyDocumentListener());
		}
		
		slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				//sets appropriate text fields and labels invisible
				for (int i = slider.getValue(); i<MAX_NUMBER_OF_TEAMS; i++){
					setInvisible(teamNameLabels.get(i), teamNameTextFields.get(i));
				}
				//sets appropriate text fields and labels visible
				for (int i = 0; i<slider.getValue(); i++){
					setVisible(teamNameLabels.get(i), teamNameTextFields.get(i));
				}
				//check if the user can start the game
				startGameButton.setEnabled(haveNames());
			}
			
		});
		
		startGameButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				numberOfTeams = slider.getValue();
				List<String> teamNames = new ArrayList<>(numberOfTeams);
				//getting the text in each of the visible text fields and storing them in a list
				for (int i = 0; i< numberOfTeams; i++) {
					teamNames.add(teamNameTextFields.get(i).getText());
				}
				/*
				//initializing TeamGUIComponents objects
				gameData.setTeams(teamNames, numberOfTeams);
				gameData.setNumberOfQuestions(quickPlay.isSelected() ? 5 : 25);
				*/
				new MainGUI().setVisible(true);
			
				dispose();
				
			}
			
		});
		
		exitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
			}
			
		});
		
		clearDataButton.addActionListener(new ActionListener(){

			//reseting all data
			@Override
			public void actionPerformed(ActionEvent e) {
				haveNames = false;
				//haveValidFile = false;
				//gameData.clearData();
				quickPlay.setSelected(false);
				//start index at 1, we still was to show the 0th elements (team 1)
				for (int i = 1; i<MAX_NUMBER_OF_TEAMS; i++){
					setInvisible(teamNameLabels.get(i), teamNameTextFields.get(i));
					teamNameTextFields.get(i).setText("");
				}
				
				startGameButton.setEnabled(false);
				teamNameTextFields.get(0).setText("");
				slider.setValue(1);
			}
			
		});
		
		logoutButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//new LoginGUI().setVisible(true);
				dispose();
			}
			
		});
		
	}
	
	//updates and returns the haveNames member variable of whether all teams have been named
	private boolean haveNames(){
		
		haveNames = true;
		//check to see if all relevant text fields have text in them
		for (int i = 0; i<slider.getValue(); i++){
			
			if (teamNameTextFields.get(i).getText().trim().equals("")) haveNames = false;
		}
		
		return haveNames;
	}
	
	//document listener; in each method, simply checking whether the user can start the game
	private class MyDocumentListener implements DocumentListener{
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			startGameButton.setEnabled(haveNames());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			startGameButton.setEnabled(haveNames());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			startGameButton.setEnabled(haveNames());
		}
	}
	
	public static void main(String[] args) {
		new StartGUI().setVisible(true);
	}
} 
