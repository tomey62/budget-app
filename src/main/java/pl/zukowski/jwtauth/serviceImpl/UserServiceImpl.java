package pl.zukowski.jwtauth.serviceImpl;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
    private final JavaMailSender javaMailSender;


    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public User saveUser(UserDto newUser) {

        User user = new User(null, newUser.getLogin(), bCryptPasswordEncoder.encode(newUser.getPassword()), newUser.getEmail(), new ArrayList<>());
        user.getRoles().add(roleRepo.findByName("ROLE_USER"));
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
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto convertEntityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User resetPassword(String email) {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        User user = userRepo.findByEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(randomPassword));
        try {
            sendMail(email, "Your new password", "Your new password is: " + randomPassword, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepo.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach((role -> authorities.add(new SimpleGrantedAuthority(role.getName()))));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    public void sendMail(String to,
                         String subject,
                         String text,
                         boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }

}
