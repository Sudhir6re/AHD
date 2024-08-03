package com.mahait.gov.in.service;

import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.UpdateUIDModel;

public interface UpdateUIDService {

	List<UpdateUIDModel> findAllEmployee(String userName);


	int saveuidNo(@Valid UpdateUIDModel updateUIDModel, OrgUserMst messages);

}
