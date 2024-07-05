package com.mahait.gov.in.entity;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "HR_PAY_ORDER_MST", schema = "IFMS")
public class HrPayOrderMst implements Serializable {

    @Id
    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "ORDER_NAME", length = 100)
    private String orderName;

    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_BY_POST")
    private Long createdByPost;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_BY_POST")
    private Long updatedByPost;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "TRN_COUNTER")
    private Integer trnCounter;

    @Column(name = "LOCATION_CODE", length = 20)
    private String locationCode;

    @Column(name = "LANG_ID")
    private Short langId;

    @Column(name = "ATTACHMENT_ID")
    private Long attachmentId;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "GR_TYPE", precision = 11, scale = 0)
    private BigDecimal grType;

    // Constructors, getters, and setters

    public HrPayOrderMst() {
        // Default constructor
    }

    // Getters and setters for all fields

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedByPost() {
        return createdByPost;
    }

    public void setCreatedByPost(Long createdByPost) {
        this.createdByPost = createdByPost;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedByPost() {
        return updatedByPost;
    }

    public void setUpdatedByPost(Long updatedByPost) {
        this.updatedByPost = updatedByPost;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getTrnCounter() {
        return trnCounter;
    }

    public void setTrnCounter(Integer trnCounter) {
        this.trnCounter = trnCounter;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

	public Short getLangId() {
		return langId;
	}

	public void setLangId(Short langId) {
		this.langId = langId;
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getGrType() {
		return grType;
	}

	public void setGrType(BigDecimal grType) {
		this.grType = grType;
	}

   
}
