package com.mahait.gov.in.entity;

import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "componet_mapping")
public class ComponetMappingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	 @ManyToOne
	    @JoinColumn(name = "component_parameter_id", referencedColumnName = "Component_Parameter_id")
	    private ComponentParameterMstEntity componentParameterMstEntity;
	 
	 @ManyToOne
	    @JoinColumn(name = "component_mst_id", referencedColumnName = "Component_id")
	   ComponentMstEntity componentMstEntity;
      
	 private Boolean isMandatory;


}
