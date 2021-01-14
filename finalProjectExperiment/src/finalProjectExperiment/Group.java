package finalProjectExperiment;

import java.util.ArrayList;

public class Group {
	ArrayList<Member> members;
	String name;
	int numMembers;
	boolean[][] origAvailability;
	boolean[][] availability;
	int numTimes = 48, numDays = 7;
	int start, end;

	public Group(String name) {
		members = new ArrayList<Member>();
		this.name = name;
		availability = new boolean[numTimes][numDays];
		origAvailability = new boolean[numTimes][numDays];
		start = 0;
		end = 47;
	}

	// ----------------------------------------
	// Getters / Setters
	// ----------------------------------------
	public String getName() {
		return name;
	}

	public ArrayList<Member> getMembers() {
		return members;
	}

	public int getNumMembers() {
		return members.size();
	}

	public boolean[][] getAvailability() {
		return availability;
	}

	public boolean[][] getOrigAvailability() {
		return origAvailability;
	}

	public boolean getCellAvailability(int t, int d) {
		return availability[t][d];
	}

	public boolean getCellOrigAvailability(int t, int d) {
		return origAvailability[t][d];
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setAvailability(boolean[][] newAvailability) {
		for (int r = 0; r < 48; r++) {
			for (int c = 0; c < 7; c++) {
				availability[r][c] = newAvailability[r][c];
			}
		}
	}

	public void setOrigAvailability(boolean[][] newAvailability) {
		for (int r = 0; r < 48; r++) {
			for (int c = 0; c < 7; c++) {
				origAvailability[r][c] = newAvailability[r][c];
			}
		}
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setCellAvailability(int t, int d) {
		availability[t][d] = !availability[t][d];
	}

	public void setCellAvailability(int t, int d, boolean val) {
		availability[t][d] = val;
	}

	public void setCellOrigAvailability(int t, int d) {
		origAvailability[t][d] = !origAvailability[t][d];
	}

	public void setCellOrigAvailability(int t, int d, boolean val) {
		origAvailability[t][d] = val;
	}

	// ----------------------------------------
	// Other Methods
	// ----------------------------------------
	public void addMember(Member person) {
		members.add(person);
	}

	public void deleteMember(Member person) {
		members.remove(person);
	}

	public void deleteMember(String namePerson) {
		members.remove(indexOfMember(namePerson));
	}

	public int indexOfMember(String namePerson) {
		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).getName().equals(namePerson))
				return i;
		}
		return -1;
	}

}
