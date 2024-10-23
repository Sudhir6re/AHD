package com.mahait.gov.in.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "component_parameter_mst")
public class ComponentParameterMstEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Component_Parameter_id")
	private Long id;
	private String CompntParameterName;
	
	@OneToMany(mappedBy = "componentParameterMstEntity", cascade = CascadeType.ALL)
    private Set<ComponetMappingEntity> componetMappingEntity;
}
