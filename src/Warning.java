/**
 * class defining the warning exception used in the SocialNetwork program
 * @author Jeremy Vanderwall
 *
 */
@SuppressWarnings("serial")
public class Warning extends Exception {
	
	String message;

	/**
	 * contructor for Warning objects
	 * @param string the error message
	 */
	public Warning(String string) {
		this.message= string;
	}
	/**
	 * returns the error message
	 */
	public String getMessage(){
		return message;
	}

}
