public class User {
    protected String userId, username, role;
    protected int failedLogins = 0;
    protected boolean isLocked = false;
// simulation password
    public boolean login(String password) {
        // Simulated login check
        if (isLocked) return false;
        if (password.equals("password123")) {
            failedLogins = 0;
            return true;
        } else {
            failedLogins++;
            if (failedLogins >= 5) isLocked = true;
            return false;
        }
    }
}
