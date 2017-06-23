package com.huiming.emeng.bo;

import com.huiming.emeng.model.Banner;
import com.huiming.emeng.model.Passage;

public class PassageWithBanner {

	private Passage passage;
	private Banner banner;

	public PassageWithBanner(Banner banner) {
		super();
		this.banner = banner;
	}

	public Passage getPassage() {
		return passage;
	}

	public void setPassage(Passage passage) {
		this.passage = passage;
	}

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	public PassageWithBanner() {
		super();
	}

	public PassageWithBanner(Passage passage, Banner banner) {
		super();
		this.passage = passage;
		this.banner = banner;
	}

}
