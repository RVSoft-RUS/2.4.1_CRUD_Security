package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDaoImp;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private UserDaoImp userDao;

   @Transactional
   @Override
   public boolean addUser(User user) {
      userDao.addUser(user);
      return  true;
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> getAllUsers() {
      return userDao.getAllUsers();
   }

   @Transactional
   @Override
   public boolean deleteUser(User user) {
      userDao.deleteUser(user);
      return true;
   }

   @Transactional(readOnly = true)
   @Override
   public User getUserById(long id) {
      return userDao.getUserById(id);
   }

   @Transactional
   @Override
   public boolean updateUser(User user) {
      userDao.updateUser(user);
      return true;
   }

   @Transactional(readOnly = true)
   @Override
   public User getUserByLogin(String login) {
      return userDao.getUserByLogin(login);
   }

   @Transactional(readOnly = true)
   @Override
   public boolean isExistLogin(String login) {
      return userDao.isExistLogin(login);
   }
}