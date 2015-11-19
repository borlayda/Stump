package hu.nullpointerexception.stump.service
import hu.nullpointerexception.stump.exception.EntityAlreadyExistsException
import hu.nullpointerexception.stump.exception.EntityNotFoundException
import hu.nullpointerexception.stump.exception.StumpException
import hu.nullpointerexception.stump.model.Role
import hu.nullpointerexception.stump.model.User
import hu.nullpointerexception.stump.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
/**
 * Author: Márton Tóth
 */
@Service
class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.userRepository = repository
        this.passwordEncoder = passwordEncoder
    }

    List<User> getAllUsers() {
        userRepository.findAll().asList()
    }

    /**
     * This method adds a new user to the database.
     * It also encodes the users password.
     *
     * @param user
     * @return the persisted user
     */
    def addUser(User user) throws EntityAlreadyExistsException {
        user.password = passwordEncoder.encode(user.password)
        try {
            userRepository.save(user)
        } catch (DuplicateKeyException e) {
            throw new EntityAlreadyExistsException(e);
        }

    }

    def changePassword(String userId, String oldPassword, String newPassword) {
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User not found.")
        }
        if (!passwordEncoder.matches(oldPassword, user.password)) {
            throw new StumpException("Old password is incorrect.")
        }
        user.password = passwordEncoder.encode(newPassword)
        userRepository.save(user)
    }

    def changeRole(String userId, String newRole) {
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User not found.")
        }
        user.role = Role.valueOf(newRole)
        userRepository.save(user)
    }

    def deleteUser(String userId) {
        def user = userRepository.findOne(userId)
        if (user == null) {
            throw new EntityNotFoundException("User not found.")
        }
        userRepository.delete(user)
    }

    User getUser(String userId) {
        userRepository.findOne(userId)
    }
}
