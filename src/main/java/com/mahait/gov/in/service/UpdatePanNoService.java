package com.mahait.gov.in.service;

import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.UpdatePanNoModel;

public interface UpdatePanNoService {

	List<UpdatePanNoModel> findAllEmployee(String userName);

	int saveupdateMobNo(@Valid UpdatePanNoModel updatePanNoModel, OrgUserMst messages);

	
}
