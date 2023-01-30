package sba.sms.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.hibernate.mapping.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

//@Data
//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "student")


public class Student {
	
	@Column(columnDefinition = "varchar(50)",name ="email")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	String email;
	@NonNull
	@Column(columnDefinition = "varchar(50)", name ="name")
	String name;
	@NonNull
	@Column(columnDefinition = "varchar(50)",name ="password")
	String password;
	
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH},
			fetch = FetchType.EAGER)
	@JoinTable(name ="student_courses",joinColumns= @JoinColumn(name = "student_email"),
	inverseJoinColumns = @JoinColumn(name = "courses_id"))
	List<Course> courses = new LinkedList<Course>();
	//Set<Course> courses;

	public Student(String email, @NonNull String name, @NonNull String password) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Student [email=" + email + ", name=" + name + ", password=" + password + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password);
	}
	@Override
	public int hashCode() {
		return Objects.hash(email, name, password);
	}

	public void addCourse(Course c) {
		// TODO Auto-generated method stub
		   courses.add(c);
	        c.getStudents().add(this);
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
}
