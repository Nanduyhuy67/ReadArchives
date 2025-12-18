import model.User;

import javax.swing.*;

public class MainControl {
    private static JFrame frame;

    // Instance untuk semua halaman
    private static WelcomePage welcomePage;
    private static RegisterPage registerPage;
    private static Dashboard dashboard; // Nama class Dashboard (bukan DashboardPage)
    private static ProfilePage profilePage;
    private static EditProfilePage editProfilePage;

    public static void main(String[] args) {
        frame = new JFrame("ReadArchives");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showWelcome();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void showWelcome() {
        if (welcomePage == null) {
            welcomePage = new WelcomePage();
        }
        frame.setContentPane(welcomePage.mainPanel);
        frame.revalidate();
        frame.setTitle("ReadArchives - Welcome");
    };


    public static void showRegister() {
        if (registerPage == null) {
            registerPage = new RegisterPage();
        }
        frame.setContentPane(registerPage.mainPanel);
        frame.revalidate();
        frame.setTitle("ReadArchives - Register");
    }

    public static void showDashboard() {
        if (dashboard == null) {
            dashboard = new Dashboard(); // Nama class: Dashboard
        }
        frame.setContentPane(dashboard.getMainPanel());
        frame.revalidate();
        frame.setTitle("ReadArchives - Dashboard");
    }

    // ===== TAMBAHAN UNTUK PROFILE PAGE =====

    public static void showProfilePage() {
        if (profilePage == null) {
            profilePage = new ProfilePage();
        }
        if (currentUser != null) {
            profilePage.setUserData(
                    currentUser.getDisplayName(),
                    currentUser.getEmail(),
                    currentUser.getPassword()
            );
        }

        frame.setContentPane(profilePage.getProfilePagePanel());
        frame.revalidate();
        frame.setTitle("ReadArchives - Profile");

        
    }

    public static void showEditProfilePage() {
        if (editProfilePage == null) {
            editProfilePage = new EditProfilePage();
        }

        if (profilePage != null) {
            String currentDisplayName = profilePage.getDisplayName();
            String currentEmail = profilePage.getEmail();
            String currentPassword = profilePage.getPassword(); // Ambil password saat ini

            // Pastikan Anda memanggil setCurrentData dengan 3 parameter (termasuk password)
            editProfilePage.setCurrentData(currentDisplayName, currentEmail, currentPassword);
        }

        frame.setContentPane(editProfilePage.getEditProfilePanel());
        frame.revalidate();
        frame.setTitle("ReadArchives - Edit Profile");
    }

    // Method untuk update data dari EditProfilePage ke ProfilePage
    public static void updateUserProfile(String newDisplayName, String newEmail) {
        if (profilePage != null) {
            if (newDisplayName != null) {
                profilePage.setDisplayName(newDisplayName);
            }
            if (newEmail != null) {
                profilePage.setEmail(newEmail);
            }
        }
    }

    public static void updateUserPassword(String newPassword) {
        // Pastikan profilePage tidak null sebelum memanggil method di dalamnya
        if (profilePage != null && newPassword != null) {
            profilePage.setPassword(newPassword);
        }
    }

    // Getter untuk halaman (optional)
    public static ProfilePage getProfilePage() {
        if (profilePage == null) {
            profilePage = new ProfilePage();
        }
        return profilePage;
    }

    public static EditProfilePage getEditProfilePage() {
        if (editProfilePage == null) {
            editProfilePage = new EditProfilePage();
        }
        return editProfilePage;
    }
}