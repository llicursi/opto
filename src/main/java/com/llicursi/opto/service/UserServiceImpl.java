package com.llicursi.opto.service;

import com.llicursi.opto.datatransferobject.mapper.UserMapper;
import com.llicursi.opto.dataaccessobject.UserRepository;
import com.llicursi.opto.domainobject.UserDO;
import com.llicursi.opto.exception.ResultNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Long DEFAULT_MAX_CALORIES = 600l;
    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDO> findAll(){
        return (List<UserDO>) userRepository.findAll();
    }

    @Override
    public UserDO findById(Long id) throws ResultNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new ResultNotFoundException("Could not find current user with id: " + id));
    }

    @Override
    @Transactional
    public UserDO create(UserDO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserDO userDO = userRepository.save(user);

        return userDO;
    }

    @Override
    @Transactional
    public void update(UserDO user, Long userId) throws ResultNotFoundException {
        UserDO foundUser = findById(userId);
        foundUser.setEmail(user.getEmail());
        foundUser.setName(user.getName());
        if (hasChangedPassword(user)) {
            foundUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        foundUser.setRole(user.getRole());
        userRepository.save(foundUser);
    }

    private boolean hasChangedPassword(UserDO user) {
        return !user.getPassword().equals(UserMapper.HIDDEN_PASSWORD);
    }

    @Override
    @Transactional
    public void delete(Long id) throws ResultNotFoundException {
        UserDO userDO = findById(id);
        userRepository.delete(userDO);
    }

}
