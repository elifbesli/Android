package elif.besli.dictionary;

public class Bean {
	public int id;
	public String english;
	public String turkish;
	public String status;
	
	public Bean(String english, String turkish) {
		this.english = english;
		this.turkish = turkish;
	}
	
	public Bean(int id, String english, String turkish, String status) {
		this.id = id;
		this.english = english;
		this.turkish = turkish;
		this.status = status;
	}

	@Override
	public String toString() {
		return english +" : "+ turkish;
	}
}
