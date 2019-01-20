package com.test.kcb.bean;

import java.io.Serializable;

public class Course implements Serializable{
	public Course(String id, String sid, String name, String teacher,
			String day, String start, String end, String room) {
		super();
		this.id = id;
		this.sid = sid;
		this.name = name;
		this.teacher = teacher;
		this.day = day;
		this.start = start;
		this.end = end;
		this.room = room;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", sid=" + sid
				+ ", teacher=" + teacher + ", day=" + day + ", start=" + start
				+ ", end=" + end + ", room=" + room + "]";
	}

	public String id;
	public String name;
	public String sid;
	public String teacher;
	public String day;
	public String start;
	public String end;
	public String room;

}
