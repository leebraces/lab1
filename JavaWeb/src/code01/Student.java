package code01;

public class Student {
	public String id;
	public String name;
	public String password;
	Student(){};
	Student(String id,String name,String password){
		this.id=id;
		this.name=name;
		this.password=password;
	}
	public void setName(String name) {
		this.name=name;
	}
	public void setId(String id) {
		this.id=id;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public String getName() {
		return this.name;
	}
	public String getId() {
		return this.id;
	}
	public String getPassword() {
		return this.password;
	}
}
