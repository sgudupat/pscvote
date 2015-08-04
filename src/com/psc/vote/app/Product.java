package com.psc.vote.app;

public class Product {
	
	private String pname;
	private String pvalue;
	private String pdescription;
	
	
	public Product(String pname, String pvalue, String pdescription) {
		super();
		this.pname = pname;
		this.pvalue = pvalue;
		this.pdescription = pdescription;
	}

	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	public String getPdescription() {
		return pdescription;
	}
	public void setPdescription(String pdescription) {
		this.pdescription = pdescription;
	}
	
	
	@Override
	public String toString() {
		return "Product [pname=" + pname + ", pvalue=" + pvalue
				+ ", pdescription=" + pdescription + "]";
	}
		

}
