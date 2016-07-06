import org.apache.commons.lang3.builder.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.*;
import org.apache.commons.beanutils.BeanUtils;


public class Rowobject {
	String age = "";
	String income = "";
	String student ="";
	String credit_rating = "";
	String buys_computer = "";
	

	Rowobject(ArrayList<String> rowinput){
		this.age =rowinput.get(0);
		this.income = rowinput.get(1);
		this.student = rowinput.get(2);
		this.credit_rating = rowinput.get(3);
		this.buys_computer = rowinput.get(4);
	}
	
	/*public String toString() {
		  return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
		}	
	public HashMap getParameterMap()
    {
		HashMap<String,String> m = new HashMap<String,String>();
        try {
			 m=(HashMap) BeanUtils.describe(this);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return m;*/
}

