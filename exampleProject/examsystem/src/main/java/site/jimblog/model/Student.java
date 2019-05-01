package site.jimblog.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * <p>Title: Student</p>  
 * <p>Description: </p>  
 * @author Jim
 * @date Mar 6, 2018  
 * 
 */
@Entity
@Table(name="t_student")
public class Student {
	private String id;
	private String name;
	private String password;
	private String gender;
	private String prefession;
	private String cardNo;
	
	private String flag="2";
	
	private List<Exam> examList=new ArrayList<>();
	@OneToMany(mappedBy="student")
	@Cascade(CascadeType.DELETE)
	public List<Exam> getExamList() {
		return examList;
	}

	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}

	@Id
	@Column(name="id",unique=true,nullable=false,length=40)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name="name",length=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name="password",length=20)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="gender",length=5)
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name="prefession",length=40)
	public String getPrefession() {
		return prefession;
	}

	public void setPrefession(String prefession) {
		this.prefession = prefession;
	}
	@Column(name="cardNo",length=50)
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	@Transient
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
