package com.mahait.gov.in.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.customfilter.CustomUserDetailsService;
import com.mahait.gov.in.entity.MstRoleEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.repository.UserInfoRepo;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements CustomUserDetailsService /* implements UserDetailsService */ {

	@Autowired
	private UserInfoRepo userInfoDAO;

	public OrgUserMst getUserIdbyUserName(String userName) {
		OrgUserMst userInfo = userInfoDAO.getUserIdbyUserName(userName);
		return userInfo;
	}

	@Override
	public UserDetails loadUserByUsernameAndDomain(String userName, int appCode) throws UsernameNotFoundException {
	
		OrgUserMst userInfo = userInfoDAO.getActiveUser(userName, appCode);
		if (userInfo == null) {
			throw new UsernameNotFoundException(
					String.format("Username not found for AppicationName, userName=%s, ApplicationName=%s", userName, appCode));
		}
		MstRoleEntity roleEntity = userInfoDAO.getRoleByRoleId(userInfo.getMstRoleEntity().getRoleId());

		GrantedAuthority authority = new SimpleGrantedAuthority((roleEntity).getRoleName());
		User user = new User((userInfo).getUserName(), (userInfo).getPassword(), Arrays.asList(authority));
		UserDetails userDetails = (UserDetails) user;
		return userDetails;

	}
	
	public Object getNameAndDesignation(int user_id) {
		String userInfo = userInfoDAO.getNameAndDesignation(user_id);
		return userInfo;
	}

	public OrgUserMst getUserByUserId(int updatedUserId) {
		OrgUserMst orgEmpMst = userInfoDAO.getUserByUserId(updatedUserId);
		return orgEmpMst;
	}
}
