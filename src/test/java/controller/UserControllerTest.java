package controller;

import id.ac.ui.cs.advprog.adproggameshop.controller.UserController;
import id.ac.ui.cs.advprog.adproggameshop.model.User;
import id.ac.ui.cs.advprog.adproggameshop.service.TransactionServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.service.UserServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.service.GameServiceImpl;
import id.ac.ui.cs.advprog.adproggameshop.utility.GameDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.TransactionDTO;
import id.ac.ui.cs.advprog.adproggameshop.utility.UserBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TransactionServiceImpl transactionService;

    @Mock
    private GameServiceImpl gameService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRegisterPage() {
        String viewName = userController.getRegisterPage(model);
        assertEquals("register", viewName);
    }

    @Test
    void testGetLoginPage() {
        String viewName = userController.getLoginPage(model);
        assertEquals("login", viewName);
    }

    @Test
    void testPersonalPage() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.personalPage(session, model);

        assertEquals("personal_page", viewName);
        verify(model).addAttribute("authenticated", user);
    }

    @Test
    void testProfilePage() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.profilePage(session, model);

        assertEquals("profile_page", viewName);
        verify(model).addAttribute("authenticated", user);
    }

    @Test
    void testEditProfilePage() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.editProfilePage(session, model);

        assertEquals("edit_profile", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testEditProfile() {
        User user = new User("testuser", "test@example.com", "password");
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        String viewName = userController.editProfile(user, session);

        assertEquals("redirect:/personal-page", viewName);
        verify(userService).save(user);
        verify(session).setAttribute("userLogin", user);
    }

    @Test
    void testGetProfilePage_UserExists() {
        User user = new UserBuilder("testuser", "test@example.com", "password").isSeller(false).build();
        when(userService.findUserById(1L)).thenReturn(user);

        String viewName = userController.getProfilePage(1L, model);

        assertEquals("other_user_profile", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testGetProfilePage_UserNotExists() {
        when(userService.findUserById(1L)).thenReturn(null);

        String viewName = userController.getProfilePage(1L, model);

        assertEquals("error_page", viewName);
    }

    @Test
    void testLogout() {
        // Arrange
        when(request.getSession(false)).thenReturn(session); // Mocking getSession() to return the session mock

        // Act
        String viewName = userController.logout(request);

        // Assert
        assertEquals("redirect:/login", viewName);
        verify(request).getSession(false); // Verify that getSession(false) was called
        verify(session).invalidate(); // Verify that invalidate() was called on the session mock
    }

    @Test
    void testChangeRoleSeller() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.changeRoleSeller(session, model);

        assertEquals("redirect:/personal-page", viewName);
        assertTrue(user.is_seller());
        verify(userService).save(user);
    }

    @Test
    void testTopUp() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.topUp(session, model);

        assertEquals("topUp", viewName);
    }

    @Test
    void testSubmitInteger() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);

        String viewName = userController.submitInteger(100, session);

        assertEquals("redirect:/personal-page", viewName);
        verify(userService).topUp(user, 100);
    }

    @Test
    void testTransactionHistory() {
        User user = new User("testuser", "test@example.com", "password");
        when(session.getAttribute("userLogin")).thenReturn(user);
        List<TransactionDTO> transactions = Collections.emptyList();
        when(transactionService.findAllByBuyerOrSeller(user, user)).thenReturn(transactions);

        String viewName = userController.transactionHistory(session, model);

        assertEquals("transactionHistory", viewName);
        verify(model).addAttribute("transactions", transactions);
    }
}
