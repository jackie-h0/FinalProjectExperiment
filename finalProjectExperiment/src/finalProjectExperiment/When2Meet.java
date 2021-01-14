package finalProjectExperiment;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class When2Meet extends Application {
	Button btAddMember, btEditMember, btCreateEditGroup, btCheckAvailibility;
	ObservableList<Group> groups;
	ObservableList<String> namesOfGroups;
	ObservableList<Member> allMembers;
	ObservableList<String> times;
	Group selectedGroup;
	int x = 0, y = 0;
	Button[][] buttonAvailability;
	boolean[][] newTable = new boolean[48][7];

	@Override
	public void start(Stage primaryStage) {
		groups = FXCollections.observableArrayList(new Group("None")); // GROUPS TO BE NAMED
		namesOfGroups = FXCollections.observableArrayList(groups.get(0).getName()); // NAMES OF GROUPS
		groups.get(0).setStart(24);
		groups.get(0).setEnd(36);
		// groups.get(0).setAvailability(new boolean[48][7]);
		allMembers = FXCollections.observableArrayList(); // ALL MEMBERS

		times = FXCollections.observableArrayList("12:00AM", "12:30AM", "1:00AM", "1:30AM", "2:00AM", "2:30AM",
				"3:00AM", "3:30AM", "4:00AM", "4:30AM", "5:00AM", "5:30AM", "6:00AM", "6:30AM", "7:00AM", "7:30AM",
				"8:00AM", "8:30AM", "9:00AM", "9:30AM", "10:00AM", "10:30AM", "11:00AM", "11:30AM", "12:00PM",
				"12:30PM", "1:00PM", "1:30PM", "2:00PM", "2:30PM", "3:00PM", "3:30PM", "4:00PM", "4:30PM", "5:00PM",
				"5:30PM", "6:00PM", "6:30PM", "7:00PM", "7:30PM", "8:00PM", "8:30PM", "9:00PM", "9:30PM", "10:00PM",
				"10:30PM", "11:00PM", "11:30PM");

		// CREATE BUTTONS FOR HOMEPAGE MENU
		btAddMember = new Button("Add Member");
		btEditMember = new Button("Edit Member");
		btCreateEditGroup = new Button("Create Group");
		btCheckAvailibility = new Button("Check Availability");

		run(primaryStage, getHomepage(primaryStage)); // run first with homepage with options
	}

	// ----------------------------------------
	// DISPLAY DIFFERENT SCREENS / PANES
	// ----------------------------------------
	public Pane getHomepage(Stage primaryStage) {
		primaryStage.setTitle("HOMEPAGE: WHEN2MEET");

		HBox page = new HBox();

		VBox textStuff = new VBox(5); // SECTION 1: TEXT
		Label lbHeader = new Label("ABOUT");
		Label lbDescription = new Label(
				"This application allows you to create new groups and members and then set their availability separately. It will ultimately display the overall group availability with a count of which members are available.");
		lbDescription.setWrapText(true);
		textStuff.getChildren().addAll(lbHeader, lbDescription);
		textStuff.setMaxWidth(200);

		VBox buttonStuff = new VBox(5); // SECTION 2: BUTTON OPTIONS
		buttonStuff.getChildren().addAll(btAddMember, btEditMember, btCreateEditGroup, btCheckAvailibility);
		buttonStuff.setPadding(new Insets(5, 5, 5, 5));
		page.getChildren().addAll(textStuff, buttonStuff); // HOLDS BOTH SECTIONS

		// --------------------------------------------------
		// Clicking Button Brings You to Corresponding Panes
		// --------------------------------------------------
		btAddMember.setOnAction(e -> {
			run(primaryStage, getAddMember(primaryStage));
		});

		btEditMember.setOnAction(e -> {
			run(primaryStage, getEditMember(primaryStage));
		});

		btCreateEditGroup.setOnAction(e -> {
			run(primaryStage, getCreateEditGroup(primaryStage));
		});

		btCheckAvailibility.setOnAction(e -> {
			run(primaryStage, getCheckAvailibility(primaryStage));
		});
		return page;
	}

	public Pane getAddMember(Stage primaryStage) {
		primaryStage.setTitle("ADD MEMBER");

		VBox page = new VBox();
		page.setPadding(new Insets(11, 12, 13, 14));

		HBox nameStuff = new HBox(); // Section 1: Name Related Objects
		Label lbName = new Label("Name:");
		TextField tfName = new TextField();
		nameStuff.getChildren().addAll(lbName, tfName);

		HBox groupStuff = new HBox(); // Section 2: Group Related Objects
		Label lbGroup = new Label("Choose Group:");
		ComboBox<String> groupsMade = new ComboBox<String>(namesOfGroups);
		groupStuff.getChildren().addAll(lbGroup, groupsMade);

		HBox buttonStuff = new HBox(); // Section 4: Button Related Objects
		Button btSave = new Button("Save");
		Button btCancel = new Button("Cancel");
		buttonStuff.getChildren().addAll(btSave, btCancel);

		page.getChildren().addAll(nameStuff, groupStuff, /* availability, */ buttonStuff);

		groupsMade.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue ov, Object old_val, Object new_val) {
				selectedGroup = groups.get(0);
				String chosen = groupsMade.getValue();

				GridPane availability = new GridPane();
				int col = 0;
				buttonAvailability = getDisplayAvailability(new boolean[48][7]);

				for (int i = 0; i < groups.size(); i++) { // check for which group it matches to, can probably replace
															// code
					if (groups.get(i).getName().equals(chosen)) {
						selectedGroup = groups.get(i);
						i = groups.size();
					}
				}

				buttonAvailability = getDisplayAvailability(selectedGroup.getOrigAvailability())/* .clone() */;
				// newTable = selectedGroup.getAvailability().clone();

				for (int i = 0; i < 48; i++) {
					for (int j = 0; j < 7; j++)
						newTable[i][j] = selectedGroup.getCellOrigAvailability(i, j);
				}
				for (x = selectedGroup.getStart(); x <= selectedGroup.getEnd(); x++) {
					y = 0;
					for (y = 0; y < 7; y++) {
						availability.add(buttonAvailability[x][y], y, col);
						buttonAvailability[x][y].setOnAction(e -> {
							// Find where button is in the 2d array for row and column
							Button b = (Button) e.getSource();
							int row = 0, co = 0;
							for (int r = 0; r < 48; r++) {
								for (int c = 0; c < 7; c++) {
									if (b.equals(buttonAvailability[r][c])) {
										row = r;
										co = c;
									}

								}
							}

							// selectedGroup.setCellAvailability(row, co);
							newTable[row][co] = !newTable[row][co];
							if (newTable[row][co]) // true = busy selectedGroup.getCellAvailability(row, co)
								buttonAvailability[row][co].setStyle("-fx-background-color: red");
							else
								buttonAvailability[row][co].setStyle("-fx-background-color: green");
						});
					}

					col++;
				}

				VBox page1 = new VBox();
				page1.getChildren().addAll(nameStuff, groupStuff, availability, buttonStuff);
				run(primaryStage, page1);
			}

		});

		btSave.setOnAction(e -> {
			Member newMember = new Member(tfName.getText(), groups.get(namesOfGroups.indexOf(groupsMade.getValue())));
			newMember.setAvailability(newTable);
			allMembers.add(newMember);
			groups.get(namesOfGroups.indexOf(groupsMade.getValue())).addMember(newMember);
			run(primaryStage, getHomepage(primaryStage));
		});

		btCancel.setOnAction(e -> {
			/* DONT MAKE CHANGES */
			run(primaryStage, getHomepage(primaryStage));
		});

		return page;
	}

	public Pane getEditMember(Stage primaryStage) {
		primaryStage.setTitle("EDIT MEMBER");

		VBox page = new VBox();

		HBox groupStuff = new HBox(); // SECTION 1: CHOOSING GROUP
		Label lbGroup = new Label("Choose Group:");
		ComboBox<String> groupsMade = new ComboBox<String>(namesOfGroups);
		groupStuff.getChildren().addAll(lbGroup, groupsMade);

		Label lbMember = new Label("Choose Member to Edit:"); // SECTION 2: CHOOSING MEMBER
		ToggleGroup groupButton = new ToggleGroup(); // Set up for Radio Buttons of Members
		ArrayList<RadioButton> memberButtons = new ArrayList<RadioButton>(); // Set up for Radio Buttons of Members

		HBox buttonStuff = new HBox(); // SECTION 3: BUTTONS
		Button next = new Button("Next");
		Button btCancel = new Button("Cancel");
		buttonStuff.getChildren().addAll(next, btCancel);

		page.getChildren().addAll(groupStuff, lbMember, buttonStuff); // Run w/o radio buttons set yet

		// LISTENERS + HANDLERS BELOW
		groupsMade.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue ov, Object old_val, Object new_val) {
				memberButtons.clear();
				VBox paneForRadioButtons = new VBox();
				paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5));

				String chosen = groupsMade.getValue();
				// -------------------------------------------------------------------------
				selectedGroup = groups.get(0);
				for (int i = 0; i < groups.size(); i++) { // check for which group it matches to, can probably replace
															// code
					if (groups.get(i).getName().equals(chosen)) {
						selectedGroup = groups.get(i);
						i = groups.size();
					}
				}
				// ----------------------------------------------------------------------
				for (int i = 0; i < selectedGroup.getNumMembers(); i++) { // create and add radiobuttons to pane,
																			// arraylist
					memberButtons.add(new RadioButton(selectedGroup.getMembers().get(i).getName()));
					paneForRadioButtons.getChildren().add(memberButtons.get(i));
					memberButtons.get(i).setToggleGroup(groupButton);
				}

				GridPane availability = new GridPane();
				int col = 0;
				buttonAvailability = getDisplayAvailability(new boolean[48][7]);

				for (int i = 0; i < 48; i++) {
					for (int j = 0; j < 7; j++)
						newTable[i][j] = selectedGroup.getCellAvailability(i, j);
				}

				for (int i = 0; i < groups.size(); i++) { // check for which group it matches to, can probably replace
															// code
					if (groups.get(i).getName().equals(chosen)) {
						selectedGroup = groups.get(i);
						i = groups.size();
					}
				}

				VBox page1 = new VBox();

				page1.getChildren().addAll(groupStuff, lbMember, paneForRadioButtons, availability, buttonStuff); // with
																													// radiobuttons
				run(primaryStage, page1);
			}
		});

		next.setOnAction(e -> {
			for (int i = 0; i < selectedGroup.getNumMembers(); i++) { // figure out which button was selected finally
				RadioButton curButton = memberButtons.get(i);

				if (curButton.isSelected()) {
					Member temp = null;
					for (int j = 0; j < allMembers.size(); j++) {
						if (allMembers.get(j).getName().equals(curButton.getText())) // find corresponding member to
																						// button
							temp = allMembers.get(j);
					}
					run(primaryStage, getEditMember2(primaryStage, temp)); // go to helper method
				}

			}
		});

		btCancel.setOnAction(e -> {
			/* DONT MAKE CHANGES */
			run(primaryStage, getHomepage(primaryStage));
		});

		return page;
	}

	public Pane getEditMember2(Stage primaryStage, Member mem) {
		Group origGroup = mem.getGroup();
		String origName = mem.getName();
		boolean[][] origAvailability = mem.getAvailability();

		VBox page = new VBox();

		page.setPadding(new Insets(11, 12, 13, 14));

		HBox nameStuff = new HBox();
		Label lbName = new Label("Name:");
		TextField tfName = new TextField(mem.getName());
		nameStuff.getChildren().addAll(lbName, tfName);

		HBox groupStuff = new HBox();
		Label lbGroup = new Label("Choose Group:");
		ComboBox<String> groupsMade = new ComboBox<String>(namesOfGroups);
		groupStuff.getChildren().addAll(lbGroup, groupsMade);

		HBox buttonStuff = new HBox();
		Button btSave = new Button("Save");
		Button btCancel = new Button("Cancel");
		buttonStuff.getChildren().addAll(btSave, btCancel);

		page.getChildren().addAll(nameStuff, groupStuff, /* AVAILABILITY, */buttonStuff);

		newTable = new boolean[48][7];

		groupsMade.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue ov, Object old_val, Object new_val) {
				if (old_val != null)
					groups.get(namesOfGroups.indexOf((old_val))).getMembers().remove(mem);
				if (!((String) new_val).equals(mem.getGroup().getName())) // if current group is not chosen
					groups.get(namesOfGroups.indexOf((new_val))).getMembers().add(mem);
				mem.setGroup(groups.get(namesOfGroups.indexOf((new_val))));
				mem.setAvailability(new boolean[48][7]);

				String chosen = groupsMade.getValue();
				selectedGroup = groups.get(namesOfGroups.indexOf(chosen));
				// -------------------------------------------------------------------------
				selectedGroup = groups.get(0);
				for (int i = 0; i < groups.size(); i++) {
					if (groups.get(i).getName().equals(chosen)) {
						selectedGroup = groups.get(i);
						i = groups.size();
					}
				}

				// ----------------------------------------------------------------------
				GridPane availability = new GridPane();
				int col = 0;
				// newTable = new boolean[48][7];
				buttonAvailability = getDisplayAvailability(selectedGroup.getOrigAvailability()).clone();
				for (x = selectedGroup.getStart(); x <= selectedGroup.getEnd(); x++) {
					y = 0;
					for (y = 0; y < 7; y++) {
						availability.add(buttonAvailability[x][y], y, col);
						buttonAvailability[x][y].setOnAction(e -> {
							// Find where button is in the 2d array for row and column
							Button b = (Button) e.getSource();
							int row = 0, co = 0;
							for (int r = 0; r < 48; r++) {
								for (int c = 0; c < 7; c++) {
									if (b.equals(buttonAvailability[r][c])) {
										row = r;
										co = c;
									}

								}
							}

							// selectedGroup.setCellAvailability(row, co);
							newTable[row][co] = !newTable[row][co];
							mem.setAvailability(newTable);
							if (newTable[row][co]) // true = busy
								buttonAvailability[row][co].setStyle("-fx-background-color: red");
							else
								buttonAvailability[row][co].setStyle("-fx-background-color: green");
						});
					}
					col++;
				}
				// ----------------------------------------------------------------------

				mem.setName(tfName.getText()); // delete if doesnt work

				VBox page1 = new VBox();
				page1.getChildren().addAll(nameStuff, groupStuff, availability, buttonStuff);
				run(primaryStage, page1);
			}
		});

		btSave.setOnAction(e -> {
			/* MAKE CHANGES */
			run(primaryStage, getHomepage(primaryStage));
		});

		btCancel.setOnAction(e -> {
			/* DONT MAKE CHANGES */
			mem.setAvailability(origAvailability);
			mem.setName(origName);
			mem.getGroup().getMembers().remove(mem);
			mem.setGroup(origGroup);
			mem.getGroup().getMembers().add(mem);

			run(primaryStage, getHomepage(primaryStage));
		});

		return page;
	}

	public Pane getCreateEditGroup(Stage primaryStage) {
		primaryStage.setTitle("CREATE GROUP");
		boolean[][] availGroup = new boolean[48][7];

		VBox page = new VBox();

		HBox nameStuff = new HBox();
		Label lbName = new Label("Name:");
		TextField tfName = new TextField();
		nameStuff.getChildren().addAll(lbName, tfName);

		Label lbCheckDays = new Label("Check Days Available:");

		HBox paneForCheckBoxes = new HBox();
		paneForCheckBoxes.setPadding(new Insets(5, 5, 5, 5));
		CheckBox[] checkBoxArr = { new CheckBox("Sunday"), new CheckBox("Monday"), new CheckBox("Tuesday"),
				new CheckBox("Wednesday"), new CheckBox("Thursday"), new CheckBox("Friday"), new CheckBox("Saturday") };

		for (int i = 0; i < checkBoxArr.length; i++)
			paneForCheckBoxes.getChildren().add(checkBoxArr[i]);

		Label lbTimeInterval = new Label("Time Interval:");

		HBox timeStuff = new HBox();
		Label lbStart = new Label("Start Time:");
		ComboBox<String> cbStartTimes = new ComboBox<String>(times);
		Label lbEnd = new Label("End Time:");
		ComboBox<String> cbEndTimes = new ComboBox<String>(times);
		timeStuff.getChildren().addAll(lbStart, cbStartTimes, lbEnd, cbEndTimes);

		HBox buttonStuff = new HBox();
		Button btSave = new Button("Save");
		Button btCancel = new Button("Cancel");
		buttonStuff.getChildren().addAll(btSave, btCancel);

		btSave.setOnAction(e -> {
			/* MAKE CHANGES */

			// INSERT CHANGES AND SAVES TO BE MADE FOR AVAILABILITY
			for (int i = 0; i < checkBoxArr.length; i++) // Accounts for Days
				if (!checkBoxArr[i].isSelected())
					for (int t = 0; t < 48; t++)
						availGroup[t][i] = true;

			int indexOfStart = times.indexOf(cbStartTimes.getValue()); // Accounts for time interval
			int indexOfEnd = times.indexOf(cbEndTimes.getValue());
			for (int d = 0; d < 7; d++) {
				for (int t = 0; t < 48; t++) {
					if (t < indexOfStart || t > indexOfEnd)
						availGroup[t][d] = true;
				}
			}

			Group temp = new Group(tfName.getText());
			temp.setStart(indexOfStart);
			temp.setEnd(indexOfEnd);
			groups.add(temp);
			temp.setOrigAvailability(availGroup);
			temp.setAvailability(availGroup);
			namesOfGroups.add(tfName.getText());

			run(primaryStage, getHomepage(primaryStage));
		});

		btCancel.setOnAction(e -> {
			/* DONT MAKE CHANGES */
			run(primaryStage, getHomepage(primaryStage));
		});

		page.getChildren().addAll(nameStuff, lbCheckDays, paneForCheckBoxes, lbTimeInterval, timeStuff, buttonStuff);

		return page;
	}

	public Pane getCheckAvailibility(Stage primaryStage) {
		primaryStage.setTitle("CHECK AVAILABILITY");
		VBox page = new VBox();

		HBox groupStuff = new HBox();
		Label lbGroup = new Label("Choose Group:");
		ComboBox<String> groupsMade = new ComboBox<String>(namesOfGroups);
		groupStuff.getChildren().addAll(lbGroup, groupsMade);

		HBox buttonStuff = new HBox();
		Button btBack = new Button("Go Back");

		// INSERT AVAILABILITY CHART
		groupsMade.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			public void changed(ObservableValue ov, Object old_val, Object new_val) {
				selectedGroup = groups.get(0);
				String chosen = groupsMade.getValue();

				GridPane availability = new GridPane();
				int col = 0;
				buttonAvailability = getDisplayAvailability(new boolean[48][7]);

				for (int i = 0; i < groups.size(); i++) { // check for which group it matches to, can probably replace
															// code
					if (groups.get(i).getName().equals(chosen)) {
						selectedGroup = groups.get(i);
						i = groups.size();
					}
				}

				// ----------------------------------------------------------------------------------
				// Get actual availability for group from those of members

				int[][] numMemAvailable = new int[48][7];
				selectedGroup.setAvailability(selectedGroup.getOrigAvailability());
				// selectedGroup
				for (int i = 0; i < 48; i++) {
					for (int j = 0; j < 7; j++) {
						for (Member m : selectedGroup.getMembers()) {
							if (!m.getCellAvailability(i, j)) // free=false
								numMemAvailable[i][j]++;
							else
								selectedGroup.setCellAvailability(i, j, true);
						}
					}
				}
				// ----------------------------------------------------------------------------------

				buttonAvailability = getDisplayAvailability(selectedGroup.getAvailability()).clone();

				Label lblCellAvail = new Label("");
				for (x = selectedGroup.getStart(); x <= selectedGroup.getEnd(); x++) {
					y = 0;
					for (y = 0; y < 7; y++) {
						availability.add(buttonAvailability[x][y], y, col);
						buttonAvailability[x][y].setOnAction(e -> {
							// Find where button is in the 2d array for row and column
							Button b = (Button) e.getSource();
							int row = 0, co = 0;
							for (int r = 0; r < 48; r++) {
								for (int c = 0; c < 7; c++) {
									if (b.equals(buttonAvailability[r][c])) {
										row = r;
										co = c;
									}

								}
							}
							if (selectedGroup.getCellOrigAvailability(row, co))
								lblCellAvail.setText("0 members out of " + selectedGroup.getMembers().size()
										+ " are available at this time.");
							else if (numMemAvailable[row][co] == 1)
								lblCellAvail.setText(numMemAvailable[row][co] + " member out of "
										+ selectedGroup.getMembers().size() + " is available at this time.");
							else
								lblCellAvail.setText(numMemAvailable[row][co] + " members out of "
										+ selectedGroup.getMembers().size() + " are available at this time.");

							if (selectedGroup.getCellAvailability(row, co))
								buttonAvailability[row][co].setStyle("-fx-background-color: red");
							else
								buttonAvailability[row][co].setStyle("-fx-background-color: green");

						});
					}
					col++;
				}

				VBox page1 = new VBox();
				page1.getChildren().addAll(groupStuff, availability, lblCellAvail, btBack);
				run(primaryStage, page1);
			}

		});

		page.getChildren().addAll(groupStuff, btBack);

		btBack.setOnAction(e -> {
			run(primaryStage, getHomepage(primaryStage));
		});

		return page;
	}

	// ----------------------------------------
	// Availability Related Methods
	// ----------------------------------------

	public Button[][] getDisplayAvailability(boolean[][] table/* , int st, int end */) {
		Button[][] list = new Button[48][7];

		for (int t = 0; t < 48; t++) {
			//
			for (int d = 0; d < 7; d++) {
				list[t][d] = new Button(times.get(t));
				if (table[t][d]) // true = busy
					list[t][d].setStyle("-fx-background-color: red");
				else // false=free
					list[t][d].setStyle("-fx-background-color: green");
			}

		}
		return list;
	}

	// ----------------------------------------

	public void run(Stage primaryStage, Pane pane) {
		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
