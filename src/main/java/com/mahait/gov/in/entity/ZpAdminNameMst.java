package com.mahait.gov.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Zp_Admin_Name_Mst")
public class ZpAdminNameMst {
	
	@Id
    @Column(name = "id", precision = 10, scale = 0)
    private Long id;

    @Column(name = "admin_code", length = 50)
    private String adminCode;
    
    
    @Column(name = "admin_name", length = 50)
    private String adminName;


}
