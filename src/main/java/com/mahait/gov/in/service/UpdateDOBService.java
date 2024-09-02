package com.mahait.gov.in.service;

import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.UpdateDOBModel;

public interface UpdateDOBService {

	List<UpdateDOBModel> findAllEmployee(String userName);

	int saveupdatedob(@Valid UpdateDOBModel updateDOBModel, OrgUserMst messages);

}
