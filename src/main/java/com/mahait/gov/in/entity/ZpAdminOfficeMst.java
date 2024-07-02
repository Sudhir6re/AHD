package com.mahait.gov.in.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ZP_ADMIN_OFFICE_MST")
//@EqualsAndHashCode(of = "ofcId")
public class ZpAdminOfficeMst implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="OFC_ID")
	private Long ofcId;
	

    @Column(name = "OFFICE_NAME", length = 50)
    private String officeName;

    @Column(name = "OFFICE_CODE", length = 10)
    private String officeCode;

    @Column(name = "LANG_ID", precision = 10, scale = 0)
    private Long langId;

    @Column(name = "CREATED_BY", precision = 10, scale = 0)
    private Long createdBy;

    @Column(name = "CREATED_DATE", length = 20)
    private Timestamp createdDate;

    @Column(name = "UPDATED_BY", precision = 10, scale = 0)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", length = 20)
    private Timestamp updatedDate;

    @Column(name = "CREATED_BY_POST", precision = 10, scale = 0)
    private Long createdByPost;


    @Column(name = "scheme_code", length = 20)
    private String schemeCode;

    
    @Column(name = "DCPS_OFFICE_NAME", length = 150)
    private String dcpsOffName;
    
    
    @Column(name = "is_active")
    private Integer isActive;
    
    
    
    
    

	
    
}
