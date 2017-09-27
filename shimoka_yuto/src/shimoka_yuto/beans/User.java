package shimoka_yuto.beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String account;
	private String password;
	private String name;
	private String branch_name = null;
	private String department_name = null;
	private int branch_id ;
	private int department_id;
	private boolean is_actived;
	private Date insertDate;
	private Date updateDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBranchName() {
		return branch_name;
	}

	public void setBranchName(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getDepartmentName() {
		return department_name;
	}

	public void setDepartmentName(String department_name) {
		this.department_name = department_name;
	}

	public int getBranchId() {
		return branch_id;
	}

	public void setBranchId(int branch_id) {
		this.branch_id = branch_id;
	}

	public int getDepartmentId() {
		return department_id;
	}

	public void setDepartmentId(int department_id) {
		this.department_id = department_id;
	}

	public boolean getIsActived() {
		return is_actived;
	}

	public void setIsActived(boolean is_actived) {
		this.is_actived = is_actived;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
