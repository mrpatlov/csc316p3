
@SuppressWarnings("serial")
public class Warning extends Exception {
	
	String message;

	public Warning(String string) {
		this.message= string;
	}
	
	public String getMessage(){
		return message;
	}

}
