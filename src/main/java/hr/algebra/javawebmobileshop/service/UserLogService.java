package hr.algebra.javawebmobileshop.service;

import hr.algebra.javawebmobileshop.model.UserLog;
import hr.algebra.javawebmobileshop.repo.UserLogRepository;
import hr.algebra.javawebmobileshop.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserLogService {
    @Autowired
    private UserLogRepository userLogRepository;

    public void logActivity(String username, String ipAddress) {
        UserLog userLog = new UserLog(
                null,
                username,
                ipAddress,
                DateUtils.format(LocalDateTime.now())
        );
        saveUserLog(userLog);
    }

    private void saveUserLog(UserLog userLog) {
        userLogRepository.save(userLog);
    }

    public List<UserLog> findAllUserLogs() {
        return userLogRepository.findAll();
    }

    public UserLog findUserLogById(Long id) {
        return userLogRepository.findById(id).orElse(null);
    }
}
