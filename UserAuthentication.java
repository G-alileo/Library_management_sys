import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserAuthentication {
    private Map<String, User> users;
    private String usersFilePath;
    
    public UserAuthentication(String usersFilePath) {
        this.usersFilePath = usersFilePath;
        this.users = new HashMap<>();
        initializeUsers();
    }
    
    private void initializeUsers() {
        File file = new File(usersFilePath);
        
        try {
            if (file.createNewFile()) {
                System.out.println("Users file created: " + file.getName());
                // Create default admin and user accounts
                addDefaultUsers();
            } else {
                System.out.println("Users file exists. Loading users...");
                loadUsersFromFile();
            }
        } catch (IOException e) {
            System.out.println("Error initializing users file: " + e.getMessage());
        }
    }
    
    private void addDefaultUsers() {
        // Add a default librarian account
        User librarian = new User("admin", "admin123", UserRole.LIBRARIAN);
        users.put(librarian.getUsername(), librarian);
        
        // Add a default user account
        User user = new User("user", "user123", UserRole.REGULAR);
        users.put(user.getUsername(), user);
        
        saveToFile();
    }
    
    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(usersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String username = parts[0].trim();
                        String password = parts[1].trim();
                        UserRole role = UserRole.valueOf(parts[2].trim());
                        
                        User user = new User(username, password, role);
                        users.put(username, user);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing user: " + line + " - " + e.getMessage());
                }
            }
            System.out.println("Successfully loaded " + users.size() + " users.");
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
    }
    
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usersFilePath))) {
            for (User user : users.values()) {
                writer.write(user.getUsername() + "," + 
                             user.getPassword() + "," + 
                             user.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }
    
    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public boolean addUser(String username, String password, UserRole role) {
        if (users.containsKey(username)) {
            return false; 
        }
        
        User newUser = new User(username, password, role);
        users.put(username, newUser);
        saveToFile();
        return true;
    }
}