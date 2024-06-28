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
import com.mahait.gov.in.entity.OrgEmpMst;
import com.mahait.gov.in.repository.UserInfoRepo;

/*@Service*/
@Service("userDetailsService")
public class UserDetailsServiceImpl implements CustomUserDetailsService /* implements UserDetailsService */ {

	@Autowired
	private UserInfoRepo userInfoDAO;

//	private final Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * @Override public UserDetails loadUserByUsername(String userName) throws
	 * UsernameNotFoundException {
	 * 
	 * UserInfo userInfo = userInfoDAO.getActiveUser(userName); MstRoleEntity
	 * roleEntity =
	 * userInfoDAO.getRoleByRoleId(Integer.valueOf((userInfo).getRole_id()));
	 * 
	 * GrantedAuthority authority = new
	 * SimpleGrantedAuthority((roleEntity).getRoleName());
	 * 
	 * User user = new
	 * User((userInfo).getUserName(),(userInfo).getPassword(),Arrays.asList(
	 * authority)); UserDetails userDetails = (UserDetails)user; return userDetails;
	 * }
	 */

	/* public UserInfo getUserIdbyUserName(String userName,int appCode) */
	public OrgEmpMst getUserIdbyUserName(String userName) {
		OrgEmpMst userInfo = userInfoDAO.getUserIdbyUserName(userName);
		// int userId = userInfo.getUser_id();

		return userInfo;
	}

	@Override
	public UserDetails loadUserByUsernameAndDomain(String userName, int appCode) throws UsernameNotFoundException {
		/*
		 * if (StringUtils.isAnyBlank(username, domain)) { throw new
		 * UsernameNotFoundException("Username and domain must be provided"); } User
		 * user = userInfoDAO.findUser(username, domain); if (user == null) { throw new
		 * UsernameNotFoundException(
		 * String.format("Username not found for domain, username=%s, domain=%s",
		 * username, domain)); } return user;
		 * 
		 */

		/*if (StringUtils.isAnyBlank(userName, appCode)) {
			throw new UsernameNotFoundException("Username and domain must be provided");
		}*/

		OrgEmpMst userInfo = userInfoDAO.getActiveUser(userName, appCode);
		if (userInfo == null) {
			throw new UsernameNotFoundException(
					String.format("Username not found for AppicationName, userName=%s, ApplicationName=%s", userName, appCode));
		}
		MstRoleEntity roleEntity = userInfoDAO.getRoleByRoleId(Integer.valueOf((userInfo).getRole_id()));

		GrantedAuthority authority = new SimpleGrantedAuthority((roleEntity).getRoleName());
		User user = new User((userInfo).getUserName(), (userInfo).getPassword(), Arrays.asList(authority));
		UserDetails userDetails = (UserDetails) user;
		return userDetails;

	}
	
	public Object getNameAndDesignation(int user_id) {
		String userInfo = userInfoDAO.getNameAndDesignation(user_id);
		// int userId = userInfo.getUser_id();

		return userInfo;
	}

	public OrgEmpMst getUserByUserId(int updatedUserId) {
		OrgEmpMst orgEmpMst = userInfoDAO.getUserByUserId(updatedUserId);
		return orgEmpMst;
	}
}
