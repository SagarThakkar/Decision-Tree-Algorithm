import java.util.ArrayList;

public class Rowobject1 {
	String buying = "";
	String maint = "";
	String doors ="";
	String persons = "";
	String lug_boot = "";
	String safety ="";
	String classvalues = "";
	
	Rowobject1(ArrayList<String> rowinput){
		this.buying = rowinput.get(0);
		this.maint = rowinput.get(1);
		this.doors = rowinput.get(2);
		this.persons = rowinput.get(3);
		this.lug_boot = rowinput.get(4);
		this.safety = rowinput.get(5);
		this.classvalues = rowinput.get(6);
	}
}
