package com.training.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "same")
public class Data {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "invoiceNumber", unique = true)
	private String invoiceNumber;
	@Column(name = "custoPO")
	private String custoPO;
	@Column(name = "invoiceDate")
	private String invoiceDate;
	@Column(name = "address")
	private String address;
	@Column(name = "totalInvoice")
	private String totalInvoice;

    //	Getters and Setters

	
	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}


	/**
	 * @return the custoPO
	 */
	public String getCustoPO() {
		return custoPO;
	}


	/**
	 * @param custoPO the custoPO to set
	 */
	public void setCustoPO(String custoPO) {
		this.custoPO = custoPO;
	}


	/**
	 * @return the invoiceDate
	 */
	public String getInvoiceDate() {
		return invoiceDate;
	}


	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * @return the totalInvoice
	 */
	public String getTotalInvoice() {
		return totalInvoice;
	}


	/**
	 * @param totalInvoice the totalInvoice to set
	 */
	public void setTotalInvoice(String totalInvoice) {
		this.totalInvoice = totalInvoice;
	}
	}
