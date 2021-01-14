package finalProjectExperiment;

public class Member {
	String name;
	Group group;
	boolean[][] availability;
	int numTimes = 48, numDays = 7;
	int start, end;

	public Member(String name, Group group) {
		this.name = name;
		this.group = group;
		availability = new boolean[numTimes][numDays];
		start = 0;
		end = 47;
	}

	// ----------------------------------------
	// Getters / Setters
	// ----------------------------------------
	public String getName() {
		return name;
	}

	public Group getGroup() {
		return group;
	}

	public boolean[][] getAvailability() {
		return availability;
	}

	public boolean getCellAvailability(int t, int d) {
		return availability[t][d];
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

	public void setGroup(Group newGroup) {
		group = newGroup;
	}

	public void setAvailability(boolean[][] newAvailability) {
		for (int r = 0; r < 48; r++) {
			for (int c = 0; c < 7; c++) {
				availability[r][c] = newAvailability[r][c];
			}
		}
	}

	public void setCellAvailibility(int t, int d) {
		availability[t][d] = !availability[t][d];
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int compareTo(Member other) {
		return getName().compareToIgnoreCase(other.getName());
	}

	public boolean equals(Member other) {
		return getName().equalsIgnoreCase(other.getName());
	}
}
