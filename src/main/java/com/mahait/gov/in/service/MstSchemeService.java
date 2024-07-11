package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.MstScheme;
import com.mahait.gov.in.model.MstSchemeModel;

public interface MstSchemeService {

	public List<MstSchemeModel> findAllScheme(String string);

}
