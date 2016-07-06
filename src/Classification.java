import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
public class Classification {
	
	ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> datainfo = new ArrayList<ArrayList<String>>();
	ArrayList<String> classvariables = new ArrayList<String>();
	ArrayList<String> AttributeList = new ArrayList<String>();
	HashMap<String,Integer> classvariablecount = new HashMap<String,Integer>();
	// Function to print updated list
	int totaltuples = 0;
	double tinfod = 0;
	HashMap<String,ArrayList<String>> AttributelistMap = new HashMap<String,ArrayList<String>>(); 
	HashMap<Integer,Rowobject> Dataobjects = new HashMap<Integer,Rowobject>();
	HashMap<Integer,HashMap<String,String>> Datamap = new HashMap<Integer,HashMap<String,String>>();
	// Reading input data file
		void readdata() throws FileNotFoundException{
					 Scanner infile = new Scanner(new File("/home/sagar/Documents/Pattern Recognition/Decison Tree Induction/Example/Input.txt"));
					 while(infile.hasNextLine()){
						 ArrayList<String> list = new ArrayList<String>(); 
						 String line = infile.nextLine();
						 Collections.addAll(list, line.split(","));
						 data.add(list);
					 }
					 infile.close();
		}
		// Reading input data file
		void readclassvariables() throws FileNotFoundException{
			Scanner infile = new Scanner(new File("/home/sagar/Documents/Pattern Recognition/Decison Tree Induction/Example/Datainfo.txt"));
			while(infile.hasNextLine()){
				 ArrayList<String> list = new ArrayList<String>(); 
				 String line = infile.nextLine();
				 Collections.addAll(list, line.split(","));
				 datainfo.add(list);
			 }
			 infile.close();
		}
	
	public void findattributelist(){
		for(int i = 0 ; i < datainfo.size();i++){
			ArrayList<String> temp = new ArrayList<String>(datainfo.get(i));
			String attribute = temp.get(0);
			AttributeList.add(attribute);
			ArrayList<String> temp1 = new ArrayList<String>();
			for(int j = 1; j < temp.size();j++){
				temp1.add(temp.get(j));
			}
			
			AttributelistMap.put(attribute,temp1);
		}
		
		
		System.out.println("------------------AttributeList-------------------");
		for(String str: AttributeList){
			System.out.print(str + " ");
		}
		System.out.println();
		System.out.println("------------------AttributeListMap-------------------");
		for(String key: AttributelistMap.keySet()){
			ArrayList<String> value = new ArrayList<String>(AttributelistMap.get(key));
			
			System.out.println(key);
			for(String str:value){
				System.out.print(str + " ");
			}
			System.out.println();
		}
		
	}
	void printlist(){
			 for (ArrayList<String> l1 : data ) {
				   for (String n: l1) {
				       System.out.print(n + " "); 
				   }

				   System.out.println();
				}
		}
	
	//Print data info list
	private void printdatainfolist() {
		for (ArrayList<String> l1 : datainfo ) {
			   for (String n: l1) {
			       System.out.print(n + " "); 
			   }
			   System.out.println();
			}
	}
	
	//Get Class variables
	public void obtainclassvariables(){
		
		ArrayList<String> temp = datainfo.get(datainfo.size()-1);
		int len  = temp.size();
		System.out.println("---------------Class Variables-----------");
		for(int i = 1 ; i < len ; i++){
			classvariables.add(temp.get(i));
			System.out.println(temp.get(i));
		}
	}
	
	//Entorpy calculation of Data Set
	public double infoD(ArrayList<Integer>cvariablescountset,int tuplecount){
		//System.out.println("Inside IndoD");
		//System.out.println("Tuplecount "+tuplecount);
		/*
		for(Integer ii : cvariablescountset){
			System.out.println("Classvariable count " + ii + " ");
		}*/
		
		double info = 0;
				
		for(int i = 0 ; i < cvariablescountset.size() ; i++){
			double var1 = ((double)cvariablescountset.get(i) / tuplecount); 
			info += - (var1 * (Math.log(var1) / Math.log(2)));  
		}
		
		return info;
	}
	
	public void informationgain() {
		
		totaltuples = data.size();
		// Counting number of tuples with unique class variable frequency
		for(ArrayList<String> x : data){
			String cvar = x.get(x.size() - 1);
			if(!classvariablecount.containsKey(cvar)){
				classvariablecount.put(cvar, 1);
			}else{
				classvariablecount.put(cvar,classvariablecount.get(cvar)+1);
			}
		}
		
		ArrayList<Integer> cvariablescountset = new ArrayList<Integer>();
		
		for(String key : classvariablecount.keySet()){
			cvariablescountset.add(classvariablecount.get(key));
		}
		
		tinfod = infoD(cvariablescountset,totaltuples);
		/* Printing class variable count map
		System.out.println("Class Variable Count ");
		for(String key : classvariablecount.keySet()){
			System.out.println( key + " " + classvariablecount.get(key));
		}*/
		
		//System.out.println("Total " + totaltuples );
		
		System.out.println("Info(D) " + tinfod );
		double gain = 0;
		double currentgain = 0;
		String splitattribute = "";
		for(int k = 0 ;k < AttributeList.size() -1 ; k++){
			String attn = AttributeList.get(k);
			//System.out.println("Attribute: " +  attn);
			double infovalue = informationgainattribute(attn);
			currentgain = tinfod - infovalue;
			if(currentgain > gain){
				gain = currentgain;
				splitattribute = attn; 
				
			}
		}
		
		System.out.println("Selected Attribute = " + splitattribute);
		System.out.println("Selected Gain = " + gain);
		
		
		//Now find individual information
		
		//ArrayList selectedatri = attriselector();
	}
	
	public double informationgainattribute(String attriname) {
			HashMap<String,Integer> attributetypefreq = new HashMap<String,Integer>();
			ArrayList<String> attributesubvalues = AttributelistMap.get(attriname);
			HashMap<String,HashMap<String,Integer>> countofclassvalues = new HashMap<String,HashMap<String,Integer>>();
			for(String eachvalue:attributesubvalues){
				HashMap<String,Integer> tempstorage = new HashMap<String,Integer>();
				for(int rownumber:Datamap.keySet()){
					HashMap<String,String> selecrow = (HashMap<String,String>) Datamap.get(rownumber);
					
						if(selecrow.get(attriname).equals(eachvalue)){
						if(tempstorage.containsKey(selecrow.get("classes"))){
							tempstorage.put(selecrow.get("classes"),tempstorage.get(selecrow.get("classes"))+1);
						}else{
									tempstorage.put(selecrow.get("classes"),1);	
							}
					}
					
				countofclassvalues.put(eachvalue, tempstorage);	
							
				}
			}
			
			for(int rownumber:Datamap.keySet()){
				HashMap<String,String> selecrow = (HashMap<String,String>) Datamap.get(rownumber);
				String attritype = selecrow.get(attriname);
				if(!attributetypefreq.containsKey(attritype)){
					attributetypefreq.put(attritype, 1);
				}else{
					attributetypefreq.put(attritype,attributetypefreq.get(attritype)+1);
				}
				
			}
			double attriinfo = 0;
			double pi = 0;
			double attrieachsubtypeinfo = 0;
			
			for(String aname : attributetypefreq.keySet()) /* eg: middleage,youth and senior in age*/{
				int countname = attributetypefreq.get(aname);
				ArrayList<Integer> classvariableval = new ArrayList<Integer>(); // yes no for each aname
				if(countofclassvalues.containsKey(aname)){
					HashMap<String,Integer> classvariablemap =new HashMap<String,Integer>(countofclassvalues.get(aname));
					for(String eachclassvariable: classvariablemap.keySet() ){
						classvariableval.add(classvariablemap.get(eachclassvariable));
					}
				}
				
				
			    pi = (double)countname/Datamap.keySet().size();
				
			    
			  //  System.out.println("Attribute : " + attriname);
			//	System.out.println("Attribute SubType: " + aname);
				attrieachsubtypeinfo = infoD(classvariableval,countname);
				attriinfo +=(double)attrieachsubtypeinfo*pi;
				
				//System.out.println(" Attribute Inforamtom : " + attriinfo);
				//System.out.println();
				
			}
			
			
			
			
			
			// Printing Hashmap of HashMap
			/*for(String a : countofclassvalues.keySet()){
					System.out.println("Attribute Type" + " " + a);
					System.out.println();
					HashMap<String,Integer> classnames = (HashMap<String,Integer>) countofclassvalues.get(a);
					for(String b:classnames.keySet()){
						System.out.println(b + " ");
						int ans = (int)classnames.get(b);
						System.out.print(ans);
						System.out.println();
					}
				}*/
			
			//Printing Hashmap of attributetype and count
			/*	
			for(String s : attributetypefreq.keySet()){
				System.out.println("String " + s );
				System.out.println("Integer " + attributetypefreq.get(s));
			}*/
			
			
			/*for(int rownumber:Datamap.keySet()){
				HashMap<String,String> selecrow = (HashMap<String,String>) Datamap.get(rownumber);
				
				String attritype = selecrow.get(attriname);
				if(!attributetypefreq.containsKey(attritype)){
					attributetypefreq.put(attritype, 1);
			}
				else{
					attributetypefreq.put(attritype,attributetypefreq.get(attritype)+1);
				}
		}*/
			return attriinfo;
	}
		
	/*public ArrayList attriselector(){
		
		
		
		return result;
	}*/
	
	/*public void createrowobjectmap(){
		int count = 1;
		for(int i = 0 ; i < data.size() ;i++){
			ArrayList<String> eachrow = new ArrayList<String>(data.get(i));
			Rowobject p = new Rowobject(eachrow);			
			Dataobjects.put(count, p);
			count++;
		}
		
		/*System.out.println("---------Printint Object Map------");
		for(int key: Dataobjects.keySet()){
			System.out.print(key + " ");
			Rowobject val = (Rowobject)Dataobjects.get(key);
			System.out.print(val.age + " " + val.income);
			System.out.println();
		}
	}*/
	
	public void createdatamap(){
		int count = 1;
		for(int i = 0 ; i < data.size() ; i++){
			ArrayList<String> eachrow = new ArrayList<String>(data.get(i));
			HashMap<String,String> rowmap = new HashMap<String,String>();
			for(int j =0 ; j < AttributeList.size() ; j++ ){
				rowmap.put(AttributeList.get(j), eachrow.get(j));
			}
			Datamap.put(count, rowmap);
			count++;
		}
		
		/*for(int cnt:Datamap.keySet()){
			System.out.print(cnt);
			HashMap<String,String> getobj = (HashMap<String,String>) Datamap.get(cnt);
			System.out.println(getobj.get());
		}*/
	}
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		System.out.println("Hello");
		Classification obj = new Classification();
		
		 obj.readclassvariables();
		 obj.readdata();
		 obj.printlist();
		 System.out.println("---------------Data Info-----------");
		 obj.printdatainfolist();
		 obj.findattributelist();
		 obj.obtainclassvariables();
		 obj.createdatamap();
		// obj.createrowobjectmap();
		 System.out.println("---------------Info Gain-----------");
		 obj.informationgain();
	}
}
