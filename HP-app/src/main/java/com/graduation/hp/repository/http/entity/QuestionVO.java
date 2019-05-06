package com.graduation.hp.repository.http.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(value = "题目答案类")
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO extends QuestionPO{

	/*
	 * 选项
	 */
	private String option;
	private String option2;
	private String option3;
	private String option4;
	private String option5;
	
}
