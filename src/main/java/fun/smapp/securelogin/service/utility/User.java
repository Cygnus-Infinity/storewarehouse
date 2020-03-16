package fun.smapp.securelogin.service.utility;

import fun.smapp.securelogin.model.auth.SystemUser;

public interface User {

    /**
     * This method will create a new user to the system.
     * @param user
     */
    public void save(SystemUser user);

    /**
     * This method will find a user by username
     * @param userName
     * @return
     */
    public SystemUser findByUserName(String userName);
}
