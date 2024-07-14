package com.mahait.gov.in.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DisplayInnerReportModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private int paybillgenerationtrnid;
	private String billdescription;
	
	//start
	private String deptalldetNm;  // edp desc
	private int type;
	private int deptallowdeducid;
	private String tempvalue; // insted of subdetail head and headofaccountcode
	private String tempempty;
	private String description ;
	
	private List headerRow = new ArrayList();
	private List orderdataList = new ArrayList();
	private List slno= new ArrayList();
	private List allowance = new ArrayList();
	private List lstdedectionag = new ArrayList();
	private List lstdeducother = new ArrayList();
	private List lstdeductrsy = new ArrayList();
	private List alnetamt = new ArrayList();
	private List algrosstotal = new ArrayList();
	private List algrosssale = new ArrayList();
	private List algrossamt = new ArrayList();
	private List deductionamt = new ArrayList();
	private List aldeduct_adj_try = new ArrayList();
	private List aldeduct_adj_otr = new ArrayList();
	private List abcloans = new ArrayList();
	private List faloans = new ArrayList();
	private List caloans = new ArrayList();
	private List valoans = new ArrayList();
	private List hbaloans = new ArrayList();
	private List dcpsgpsno = new ArrayList();
	
	
	
    List<DisplayInnerReportModel> lstDisplayInnerReportModel= new ArrayList();
		
	
	//end
	public int getPaybillgenerationtrnid() {
		return paybillgenerationtrnid;
	}
	public void setPaybillgenerationtrnid(int paybillgenerationtrnid) {
		this.paybillgenerationtrnid = paybillgenerationtrnid;
	}
	public String getBilldescription() {
		return billdescription;
	}
	public void setBilldescription(String billdescription) {
		this.billdescription = billdescription;
	}
	public String getDeptalldetNm() {
		return deptalldetNm;
	}
	public void setDeptalldetNm(String deptalldetNm) {
		this.deptalldetNm = deptalldetNm;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDeptallowdeducid() {
		return deptallowdeducid;
	}
	public void setDeptallowdeducid(int deptallowdeducid) {
		this.deptallowdeducid = deptallowdeducid;
	}
	public String getTempvalue() {
		return tempvalue;
	}
	public void setTempvalue(String tempvalue) {
		this.tempvalue = tempvalue;
	}
	public String getTempempty() {
		return tempempty;
	}
	public void setTempempty(String tempempty) {
		this.tempempty = tempempty;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List getHeaderRow() {
		return headerRow;
	}
	public void setHeaderRow(List headerRow) {
		this.headerRow = headerRow;
	}
	public List getOrderdataList() {
		return orderdataList;
	}
	public void setOrderdataList(List orderdataList) {
		this.orderdataList = orderdataList;
	}
	public List getSlno() {
		return slno;
	}
	public void setSlno(List slno) {
		this.slno = slno;
	}
	public List getAllowance() {
		return allowance;
	}
	public void setAllowance(List allowance) {
		this.allowance = allowance;
	}
	public List getLstdedectionag() {
		return lstdedectionag;
	}
	public void setLstdedectionag(List lstdedectionag) {
		this.lstdedectionag = lstdedectionag;
	}
	public List getLstdeducother() {
		return lstdeducother;
	}
	public void setLstdeducother(List lstdeducother) {
		this.lstdeducother = lstdeducother;
	}
	public List getLstdeductrsy() {
		return lstdeductrsy;
	}
	public void setLstdeductrsy(List lstdeductrsy) {
		this.lstdeductrsy = lstdeductrsy;
	}
	
	public List getAlnetamt() {
		return alnetamt;
	}
	public void setAlnetamt(List alnetamt) {
		this.alnetamt = alnetamt;
	}
	public List getAlgrosstotal() {
		return algrosstotal;
	}
	public void setAlgrosstotal(List algrosstotal) {
		this.algrosstotal = algrosstotal;
	}
	public List getAlgrosssale() {
		return algrosssale;
	}
	public void setAlgrosssale(List algrosssale) {
		this.algrosssale = algrosssale;
	}
	public List getAlgrossamt() {
		return algrossamt;
	}
	public void setAlgrossamt(List algrossamt) {
		this.algrossamt = algrossamt;
	}
	
	public List getDeductionamt() {
		return deductionamt;
	}
	public void setDeductionamt(List deductionamt) {
		this.deductionamt = deductionamt;
	}
	
	
	public List getAldeduct_adj_try() {
		return aldeduct_adj_try;
	}
	public void setAldeduct_adj_try(List aldeduct_adj_try) {
		this.aldeduct_adj_try = aldeduct_adj_try;
	}
	public List getAldeduct_adj_otr() {
		return aldeduct_adj_otr;
	}
	public void setAldeduct_adj_otr(List aldeduct_adj_otr) {
		this.aldeduct_adj_otr = aldeduct_adj_otr;
	}
	
	public List getAbcloans() {
		return abcloans;
	}
	public void setAbcloans(List abcloans) {
		this.abcloans = abcloans;
	}
	
	public List getDcpsgpsno() {
		return dcpsgpsno;
	}
	public void setDcpsgpsno(List dcpsgpsno) {
		this.dcpsgpsno = dcpsgpsno;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public List getFaloans() {
		return faloans;
	}
	public void setFaloans(List faloans) {
		this.faloans = faloans;
	}
	
	
	public List getCaloans() {
		return caloans;
	}
	public void setCaloans(List caloans) {
		this.caloans = caloans;
	}
	
	public List getValoans() {
		return valoans;
	}
	public void setValoans(List valoans) {
		this.valoans = valoans;
	}
	
	
	public List getHbaloans() {
		return hbaloans;
	}
	public void setHbaloans(List hbaloans) {
		this.hbaloans = hbaloans;
	}
	
	
	public List<DisplayInnerReportModel> getLstDisplayInnerReportModel() {
		return lstDisplayInnerReportModel;
	}
	public void setLstDisplayInnerReportModel(List<DisplayInnerReportModel> lstDisplayInnerReportModel) {
		this.lstDisplayInnerReportModel = lstDisplayInnerReportModel;
	}
	@Override
	public String toString() {
		return "DisplayInnerReportModel [paybillgenerationtrnid=" + paybillgenerationtrnid + ", billdescription="
				+ billdescription + ", deptalldetNm=" + deptalldetNm + ", type=" + type + ", deptallowdeducid="
				+ deptallowdeducid + ", tempvalue=" + tempvalue + ", tempempty=" + tempempty + ", description="
				+ description + ", headerRow=" + headerRow + ", orderdataList=" + orderdataList + ", slno=" + slno
				+ ", allowance=" + allowance + ", lstdedectionag=" + lstdedectionag + ", lstdeducother=" + lstdeducother
				+ ", lstdeductrsy=" + lstdeductrsy + ", alnetamt=" + alnetamt + ", algrosstotal=" + algrosstotal
				+ ", algrosssale=" + algrosssale + ", algrossamt=" + algrossamt + ", deductionamt=" + deductionamt
				+ ", aldeduct_adj_try=" + aldeduct_adj_try + ", aldeduct_adj_otr=" + aldeduct_adj_otr + ", abcloans="
				+ abcloans + ", faloans=" + faloans + ", caloans=" + caloans + ", valoans=" + valoans + ", hbaloans="
				+ hbaloans + ", dcpsgpsno=" + dcpsgpsno + ", lstDisplayInnerReportModel=" + lstDisplayInnerReportModel
				+ "]";
	}
	
}
