package com.graduation.hp.repository.http.entity.vo;

import com.graduation.hp.repository.http.entity.pojo.QuestionPO;

public class QuestionVO extends QuestionPO {

	/*
	 * 选项
	 */
	private String option;
	private String option2;
	private String option3;
	private String option4;
	private String option5;

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getOption5() {
		return option5;
	}

	public void setOption5(String option5) {
		this.option5 = option5;
	}
}
