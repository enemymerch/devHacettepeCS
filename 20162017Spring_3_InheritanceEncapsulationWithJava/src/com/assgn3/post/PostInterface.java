package com.assgn3.post;

import java.util.Date;
import java.util.UUID;

public interface PostInterface {
	public void setText(String text);
	public String getText();
	public UUID getID();
	public Date getDate();
}
