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

@Entity
@Data
@NoArgsConstructor
@Table(name = "component_mst")
public class ComponentMstEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="component_id")
	public Long id;

	@Column(unique = true , name ="component_name")
    public String componentName;
	
	@OneToMany(mappedBy = "componentMstEntity", cascade = CascadeType.ALL)
    private Set<ComponetMappingEntity> componetMappingEntity;

}

