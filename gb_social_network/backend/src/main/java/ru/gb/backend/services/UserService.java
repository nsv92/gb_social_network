package ru.gb.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.backend.dto.UserDto;
import ru.gb.backend.entity.Role;
import ru.gb.backend.entity.User;
import ru.gb.backend.exceptions.AppError;
import ru.gb.backend.exceptions.ResourceNotFoundException;
import ru.gb.backend.exceptions.ValidationException;
import ru.gb.backend.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    public List<UserDto> findAllUsers() {
        return convertUsers(userRepository.findAll());
    }

    public UserDto createOrUpdate(UserDto user) {
        return convertUserToDto(userRepository.save(convertDtoToUser(user)));
    }

    public Optional<UserDto> findById(Long id) {
        return Optional.of(convertUserToDto(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"))));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserForId(Long id) {
        return userRepository.findById(id);
    }

    public User getUserForIds(Long id){
        return userRepository.findById(id)
                .orElse(new User());
    }


    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickName(nickname);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User user = findByNickname(nickname).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", nickname)));
        return new org.springframework.security.core.userdetails.User(user.getNickName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    private UserDto convertUserToDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName(), user.getNickName(), "", user.getPhone(), user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    }

    private User convertDtoToUser(UserDto userDto) {
        return new User(userDto.getId(), passwordEncoder.encode(userDto.getPassword()), userDto.getName(), userDto.getNickname(), userDto.getEmail(), userDto.getPhone(), roleService.getRolesByName(userDto.getRoles()));
    }

    private List<UserDto> convertUsers(List<User> users) {
        return users.stream().map(this::convertUserToDto).collect(Collectors.toList());
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
