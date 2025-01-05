/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i = 0; i < users.length; i++){
            if (users[i] != null){ // if there is a slot with a user, check if it is the user we want
                String user_name = users[i].getName();
                if (user_name.equals(name)){
                    return users[i];
                }
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        for (int i = 0; i < users.length; i++){
            if (userCount == users.length) { // checks if there is any room left in the network
                return false;
            }
            if (users[i] != null){ //checks if there is already a user with that name
                String user_name = users[i].getName();
                if (user_name.equals(name)){
                    return false;
                }
            }
            if (users[i] == null) { // if there is an empty slot, add the user
                users[i] = new User(name);
                userCount++;
                return true;
            }
        }
        return false;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        User user1 = getUser(name1);
        User user2 = getUser(name2);

        if (getUser(name1) == null || getUser(name2) == null){
            return false;
        }
        if (user1.follows(name2) == true){
            return false;
        }
        else{
            user1.addFollowee(name2);
            return true;
        }
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        String max = "";
        int count = 0;
        User user = getUser(name);
        for (int i = 1; i < users.length; i++){
            if (users[i - 1] != null && users[i] != null){
                if (count < user.countMutual(users[i])){ // checks maximum mutual count
                    max = users[i].getName();
                    count = user.countMutual(users[i]);
                }
            }
        }
        return max;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int max = 0;
        String max_user = "";
        for (int i = 0; i < users.length; i++){
            if (users[i] != null){
                String user1 = users[i].getName();
                if (followeeCount(user1) > max){
                    max = followeeCount(user1);
                    max_user = user1;
                }
            }
        }
        return max_user;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < users.length; i++){
            if (users[i] != null){
                if (users[i].follows(name)){
                    count++;
                    continue;
                }
            }
        }
        return count;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String ans = "";
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) { 
                String name = users[i].getName();
                ans += name + " -> "; 
                String[] follows = users[i].getfFollows();
                for (int j = 0; j < follows.length; j++) {
                    if (follows[j] != null){
                        ans += " " + follows[j];
                    }
                    else {
                        break;
                    }

                }
                ans += "\n"; 
            }
            else {
                break;
            }
        }
        return ans;
    }
}
