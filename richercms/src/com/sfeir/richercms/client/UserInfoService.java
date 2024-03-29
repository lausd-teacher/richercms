package com.sfeir.richercms.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.shared.BeanUser;
import com.sfeir.richercms.shared.BeanUserInfo;

/**
 * All services needed to handle user
 * @author homberg.g
 *
 */
@RemoteServiceRelativePath("loginService")
public interface UserInfoService extends RemoteService {
	
	/**
	 * return informations about the user
	 * @param requestUri => necessary for using google-login Agent
	 * @return information about user and if it was connected
	 */
	public BeanUserInfo login(String requestUri);
	
	/**
	 * Test the user, if he was in the CMS datastore
	 * @param usrInfo : returned by google after and identification on it
	 * @return null if the user is not register, a BeanUser if this user is registered
	 */
	public BeanUser isAutorized(BeanUserInfo usrInfo);
	
	/**
	 * Modify an User
	 * @param user : the bean containing the modification
	 */
	public void modifyUser(BeanUser user);
	
	/**
	 * Add a new user in the datastore.
	 * Just the mail is necessary. After the first connection, 
	 * others information are updated
	 * @return id of the new User; null if the user is already in the datastore
	 */
	public Long addUser(String email);
	
	/**
	 * Delete a specific user into the datastore
	 * @param userId : id of the specific user
	 */
	public void deleteUser(Long userId);
	
	/**
	 * this method is use when a user logout.
	 * its necessary to update his state in dataBase for other user
	 * @param id : the id's user
	 */
	public void logOutUser(Long id);
	
	/**
	 * Update the user admin field
	 * @param id : the user's id
	 * @param admin : false : if its not an admin, true either.
	 */
	public void UserAdminChange(Long id, boolean admin);
	
	/**
	 * Return all users 
	 * @return list with all users
	 */
	public List<BeanUser> getUsers();
	
	/**
	 * return an user
	 * @param id : id of the user
	 * @return null if user no exist, his bean either
	 */
	public BeanUser getUser(Long id);
}
