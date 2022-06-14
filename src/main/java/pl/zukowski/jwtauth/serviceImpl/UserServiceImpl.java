package pl.zukowski.jwtauth.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.zukowski.jwtauth.dto.UserDto;
import pl.zukowski.jwtauth.entity.Role;
import pl.zukowski.jwtauth.entity.User;
import pl.zukowski.jwtauth.repository.RoleRepository;
import pl.zukowski.jwtauth.repository.UserRepository;
import pl.zukowski.jwtauth.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User saveUser(UserDto newUser) {
        User user = new User(null, newUser.getLogin(), bCryptPasswordEncoder.encode(newUser.getPassword()), new ArrayList<>());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String login, String roleName) {
        User user = userRepo.findByLogin(login);
        user.getRoles().add(roleRepo.findByName(roleName));
        userRepo.save(user);
    }


    @Override
    public User getUser(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepo.findAll().stream()
                .map(user -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto convertEntityToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(login);
        if (user == null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach((role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }
}
