/*
Assignment : 01 
File Name : User.java
Full name of the student : Prateek Mahendrakar
*/

package assignment01;

public class User {
	private String firstName, middleInitial, lastName;
	private int age;
	private String city, state;
	
	public User(String line){
		parseUser(line);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	private void parseUser(String line){
		//should parse the user by splitting the line string (comma separated)
		String[] resultingTokens = line.split(",");
		for (int i = 0; i < resultingTokens.length; i++){
		//System.out.println(resultingTokens [i].trim());
		
		if (i==0) {
			setFirstName(resultingTokens [i].trim());
		} else if(i==1) {
			setMiddleInitial(resultingTokens [i].trim());
		}else if(i==2){
			setLastName(resultingTokens [i].trim());
		}else if(i==3){
		int x = Integer.parseInt(resultingTokens [i].trim());
		setAge(x);
		}else if(i==4){
		setCity(resultingTokens [i].trim());
		}
		else if(i==5){
		setState(resultingTokens [i].trim());
		}
		else{
		null;
		
		}
	}

	
	
}
