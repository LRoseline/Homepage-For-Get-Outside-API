package com.tfriends.domain;

import lombok.Data;

@Data
public class CovidLocationVO {
	private int locno;
	private String lname;
	private String locnameen;
	private String todayoverflow;
	private String todaylocalocc;
	private String todaydef;
	private String todayisoling;
	private String todayisoclear;
	private String todaydeath;
	private String todayinc;
	private String yesterdayoverflow;
	private String yesterdaylocalocc;
	private String yesterdaydef;
	private String yesterdayisoling;
	private String yesterdayisoclear;
	private String yesterdaydeath;
	private String yesterdayinc;
}