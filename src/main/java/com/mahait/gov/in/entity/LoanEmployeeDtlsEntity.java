package com.mahait.gov.in.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="loan_employee_dtls",schema="public")
public class LoanEmployeeDtlsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="loan_employee_adv_id")
	private Integer loanemployeeadvid;
	
	@Column(name="employee_id")
	private BigInteger employeeid;
	
	@Column(name="sevaarth_id")
	private String sevaarthid;
	
	@Column(name="department_allowdeduc_code")
	private Integer departmentallowdeduccode ;
	
	@Column(name="loan_type_id")
	private BigInteger loantypeid ;
	
	@Column(name="loan_prin_amt")
	private BigInteger loanprinamt ;
	
	@Column(name="loan_interest_amt")
	private BigInteger loaninterestamt ;
	
	@Column(name="loan_prin_inst_no")
	private Integer loanprininstno ;
	
	@Column(name="loan_int_inst_no")
	private Integer loanintinstno;
	
	@Column(name="loan_emi_amt")
	private BigInteger  loanemiamt ;
	
	@Column(name="loan_account_no")
	private String loanaccountno ;
	
	@Column(name="loan_date")
	private Date  loandate ;
	
	@Column(name="trn_counter")
	private Integer trncounter ;
	
	@Column(name="loan_int_emi_amt")
	private BigInteger loanintemiamt ;
	
	@Column(name="loan_prin_emi_amt")
	private BigInteger loanprinemiamt;
	
	@Column(name="loan_sanc_order_no")
	private String loansancorderno ;
	
	@Column(name="loan_activate_flag")
	private Integer loanactivateflag ;
	
	@Column(name="odd_inst_no")
	private BigInteger oddinstno;
	
	@Column(name="odd_inst_amt")
	private BigInteger oddinstamt;
	
	@Column(name="is_approved")
	private Integer isapproved ;
	
	@Column(name="voucher_no")
	private String voucherno;
	
	@Column(name="voucher_date")
	private Date voucherdate;
	
	@Column(name="loan_sanc_order_date")
	private Date loansancorderdate;
	
	@Column(name="loan_rec_type")
	private Integer loanrectype ;
	
	@Column(name="loan_rec_remarks")
	private String loanrecremarks;
	
	@Column(name="multi_inst_recvd")
	private Integer multiinstrecvd ;
	
	@Column(name="multi_amt_recvd")
	private BigInteger multiamtrecvd ;
	
	@Column(name="loan_update_order_no")
	private String loanupdateorderno;
	
	@Column(name="loan_update_order_date")
	private Date loanupdateorderdate ;
	
	@Column(name="start_date")
	private Date startdate ;
	
	@Column(name="end_date")
	private Date enddate ;
	
	@Column(name="created_by")
	private BigInteger createdby ;
	
	@Column(name="created_by_post")
	private BigInteger createdbypost ;
	
	@Column(name="created_date")
	private Date createddate  ;
	
	@Column(name="updated_by")
	private BigInteger updatedby;
	
	@Column(name="updated_by_post")
	private BigInteger updatedbypost ;
	
	@Column(name="updated_date")
	private Date updateddate ;

	@Column(name="total_recovered_amt")
	private BigInteger totalRecoveredAmt ;
	
	@Column(name="total_recovered_inst")
	private int totalRecoveredInst ;
	
	@Column(name="gpf_app_no")
	private int gpfAppNo ;
	
	@Column(name="sanc_amt_gpf_II")
	private BigInteger sancAmtGpfII ;

	@Column(name="sanc_inst_gpf_II")
	private Integer sancInstGpfII ;

	@Column(name="inst_amt_gpf_II")
	private BigInteger instAmtgpfII;
	
	@Column(name="total_recovered_amt_gpf_II")
	private BigInteger totalRecoveredAmtGpfII ;
	
	@Column(name="total_recovered_inst_gpf_II")
	private Integer totalRecoveredInstGpfII ;

	public BigInteger getTotalRecoveredAmt() {
		return totalRecoveredAmt;
	}

	public void setTotalRecoveredAmt(BigInteger totalRecoveredAmt) {
		this.totalRecoveredAmt = totalRecoveredAmt;
	}

	public int getTotalRecoveredInst() {
		return totalRecoveredInst;
	}

	public void setTotalRecoveredInst(int totalRecoveredInst) {
		this.totalRecoveredInst = totalRecoveredInst;
	}

	public Integer getLoanemployeeadvid() {
		return loanemployeeadvid;
	}

	public void setLoanemployeeadvid(Integer loanemployeeadvid) {
		this.loanemployeeadvid = loanemployeeadvid;
	}

	public BigInteger getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(BigInteger employeeid) {
		this.employeeid = employeeid;
	}

	public String getSevaarthid() {
		return sevaarthid;
	}

	public void setSevaarthid(String sevaarthid) {
		this.sevaarthid = sevaarthid;
	}

	public Integer getDepartmentallowdeduccode() {
		return departmentallowdeduccode;
	}

	public void setDepartmentallowdeduccode(Integer departmentallowdeduccode) {
		this.departmentallowdeduccode = departmentallowdeduccode;
	}

	public BigInteger getLoantypeid() {
		return loantypeid;
	}

	public void setLoantypeid(BigInteger loantypeid) {
		this.loantypeid = loantypeid;
	}

	public BigInteger getLoanprinamt() {
		return loanprinamt;
	}

	public void setLoanprinamt(BigInteger loanprinamt) {
		this.loanprinamt = loanprinamt;
	}

	public BigInteger getLoaninterestamt() {
		return loaninterestamt;
	}

	public void setLoaninterestamt(BigInteger loaninterestamt) {
		this.loaninterestamt = loaninterestamt;
	}

	public Integer getLoanprininstno() {
		return loanprininstno;
	}

	public void setLoanprininstno(Integer loanprininstno) {
		this.loanprininstno = loanprininstno;
	}

	public Integer getLoanintinstno() {
		return loanintinstno;
	}

	public void setLoanintinstno(Integer loanintinstno) {
		this.loanintinstno = loanintinstno;
	}

	public BigInteger getLoanemiamt() {
		return loanemiamt;
	}

	public void setLoanemiamt(BigInteger loanemiamt) {
		this.loanemiamt = loanemiamt;
	}

	public String getLoanaccountno() {
		return loanaccountno;
	}

	public void setLoanaccountno(String loanaccountno) {
		this.loanaccountno = loanaccountno;
	}

	public Date getLoandate() {
		return loandate;
	}

	public void setLoandate(Date loandate) {
		this.loandate = loandate;
	}

	public Integer getTrncounter() {
		return trncounter;
	}

	public void setTrncounter(Integer trncounter) {
		this.trncounter = trncounter;
	}

	public BigInteger getLoanintemiamt() {
		return loanintemiamt;
	}

	public void setLoanintemiamt(BigInteger loanintemiamt) {
		this.loanintemiamt = loanintemiamt;
	}

	public BigInteger getLoanprinemiamt() {
		return loanprinemiamt;
	}

	public void setLoanprinemiamt(BigInteger loanprinemiamt) {
		this.loanprinemiamt = loanprinemiamt;
	}

	public String getLoansancorderno() {
		return loansancorderno;
	}

	public void setLoansancorderno(String loansancorderno) {
		this.loansancorderno = loansancorderno;
	}

	public Integer getLoanactivateflag() {
		return loanactivateflag;
	}

	public void setLoanactivateflag(Integer loanactivateflag) {
		this.loanactivateflag = loanactivateflag;
	}

	public BigInteger getOddinstno() {
		return oddinstno;
	}

	public void setOddinstno(BigInteger oddinstno) {
		this.oddinstno = oddinstno;
	}

	public BigInteger getOddinstamt() {
		return oddinstamt;
	}

	public void setOddinstamt(BigInteger oddinstamt) {
		this.oddinstamt = oddinstamt;
	}

	public Integer getIsapproved() {
		return isapproved;
	}

	public void setIsapproved(Integer isapproved) {
		this.isapproved = isapproved;
	}

	public String getVoucherno() {
		return voucherno;
	}

	public void setVoucherno(String voucherno) {
		this.voucherno = voucherno;
	}

	public Date getVoucherdate() {
		return voucherdate;
	}

	public void setVoucherdate(Date voucherdate) {
		this.voucherdate = voucherdate;
	}

	public Date getLoansancorderdate() {
		return loansancorderdate;
	}

	public void setLoansancorderdate(Date loansancorderdate) {
		this.loansancorderdate = loansancorderdate;
	}

	public Integer getLoanrectype() {
		return loanrectype;
	}

	public void setLoanrectype(Integer loanrectype) {
		this.loanrectype = loanrectype;
	}

	public String getLoanrecremarks() {
		return loanrecremarks;
	}

	public void setLoanrecremarks(String loanrecremarks) {
		this.loanrecremarks = loanrecremarks;
	}

	public Integer getMultiinstrecvd() {
		return multiinstrecvd;
	}

	public void setMultiinstrecvd(Integer multiinstrecvd) {
		this.multiinstrecvd = multiinstrecvd;
	}

	public BigInteger getMultiamtrecvd() {
		return multiamtrecvd;
	}

	public void setMultiamtrecvd(BigInteger multiamtrecvd) {
		this.multiamtrecvd = multiamtrecvd;
	}

	public String getLoanupdateorderno() {
		return loanupdateorderno;
	}

	public void setLoanupdateorderno(String loanupdateorderno) {
		this.loanupdateorderno = loanupdateorderno;
	}

	public Date getLoanupdateorderdate() {
		return loanupdateorderdate;
	}

	public void setLoanupdateorderdate(Date loanupdateorderdate) {
		this.loanupdateorderdate = loanupdateorderdate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public BigInteger getCreatedby() {
		return createdby;
	}

	public void setCreatedby(BigInteger createdby) {
		this.createdby = createdby;
	}

	public BigInteger getCreatedbypost() {
		return createdbypost;
	}

	public void setCreatedbypost(BigInteger createdbypost) {
		this.createdbypost = createdbypost;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public BigInteger getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(BigInteger updatedby) {
		this.updatedby = updatedby;
	}

	public BigInteger getUpdatedbypost() {
		return updatedbypost;
	}

	public void setUpdatedbypost(BigInteger updatedbypost) {
		this.updatedbypost = updatedbypost;
	}

	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}

	public int getGpfAppNo() {
		return gpfAppNo;
	}

	public void setGpfAppNo(int gpfAppNo) {
		this.gpfAppNo = gpfAppNo;
	}

	public BigInteger getSancAmtGpfII() {
		return sancAmtGpfII;
	}

	public void setSancAmtGpfII(BigInteger sancAmtGpfII) {
		this.sancAmtGpfII = sancAmtGpfII;
	}

	public Integer getSancInstGpfII() {
		return sancInstGpfII;
	}

	public void setSancInstGpfII(Integer sancInstGpfII) {
		this.sancInstGpfII = sancInstGpfII;
	}

	public BigInteger getInstAmtgpfII() {
		return instAmtgpfII;
	}

	public void setInstAmtgpfII(BigInteger instAmtgpfII) {
		this.instAmtgpfII = instAmtgpfII;
	}

	public BigInteger getTotalRecoveredAmtGpfII() {
		return totalRecoveredAmtGpfII;
	}

	public void setTotalRecoveredAmtGpfII(BigInteger totalRecoveredAmtGpfII) {
		this.totalRecoveredAmtGpfII = totalRecoveredAmtGpfII;
	}

	public Integer getTotalRecoveredInstGpfII() {
		return totalRecoveredInstGpfII;
	}

	public void setTotalRecoveredInstGpfII(Integer totalRecoveredInstGpfII) {
		this.totalRecoveredInstGpfII = totalRecoveredInstGpfII;
	}


	
	
}
