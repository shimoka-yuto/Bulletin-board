package shimoka_yuto.beans;

import java.io.Serializable;
import java.util.Date;

public class UserComment implements Serializable {
	private static final long serialVersionUID = 1L;

	private String account;
	private String name;
	private int branch_id;
	private int department_id;
	private int comment_id;
	private int user_id;
	private int message_id;
	private String text;
	private Date insertDate;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getCommentId() {
		return comment_id;
	}

	public void setCommentId(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getUserId() {
		return user_id;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}

	public int getMessageId() {
		return message_id;
	}

	public void setMessageId(int message_id) {
		this.message_id = message_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String[] getSplitedText() {
		return text.split("\n");
	}

}
