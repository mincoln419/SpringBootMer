package com.mermer.sample;

public class Sample {
	
	private String prefix;
	
	private String name;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Sample [prefix=" + prefix + ", name=" + name + "]";
	}
	
	

}
