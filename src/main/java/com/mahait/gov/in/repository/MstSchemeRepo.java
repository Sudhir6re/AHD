package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.MstScheme;
import com.mahait.gov.in.model.MstSchemeModel;

public interface MstSchemeRepo {

	List<MstScheme> findAllScheme();

	List<MstSchemeModel> findAllSchemename(String username);

	List<MstSchemeModel> findAllSchemename();

}
