package org.spring.user_service.service;

import org.spring.user_service.dto.UserDto;
import org.spring.user_service.entity.User;
import org.spring.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    // DTO -> Entity 변환
    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    // Entity -> DTO 변환
    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    // 유저 인증
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword())); // 암호화된 비밀번호 비교
    }

    // 모든 유저 조회
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    // 특정 유저 조회
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto); // 엔티티를 DTO로 변환
    }

    // 유저 생성 시 비밀번호 암호화
    public UserDto createUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    // 유저 업데이트
    public Optional<UserDto> updateUser(Long id, UserDto updatedUserDto) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUserDto.getUsername());
            user.setEmail(updatedUserDto.getEmail());
            user.setPassword(passwordEncoder.encode(updatedUserDto.getPassword())); // 암호화된 비밀번호 설정
            user.setRole(updatedUserDto.getRole());
            User savedUser = userRepository.save(user);
            return convertToDto(savedUser);
        });
    }

    // 유저 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
