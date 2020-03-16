package fun.smapp.securelogin.service.security;

public interface Security {
    /**
     * Need To Implement to get Current Logged In user
     * @return String
     */
    public String findLoggedInUserName();

    /**
     * Need to Implement this method to Auto Login In many case
     * @param userName
     * @param password
     */
    public void autoLogin(String userName, String password);
}
