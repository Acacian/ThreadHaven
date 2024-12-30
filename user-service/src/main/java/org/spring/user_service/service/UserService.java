package org.spring.user_service.service;

import org.spring.user_service.dto.UserDto;
import org.spring.user_service.entity.User;
import org.spring.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    // 유저 생성
    public UserDto createUser(UserDto userDto) {
        User user = convertToEntity(userDto); // DTO를 엔티티로 변환
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser); // 저장 후 엔티티를 DTO로 변환
    }

    // 유저 업데이트
    public Optional<UserDto> updateUser(Long id, UserDto updatedUserDto) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUserDto.getUsername());
            user.setEmail(updatedUserDto.getEmail());
            user.setPassword(passwordEncoder.encode(updatedUserDto.getPassword()));
            user.setRole(updatedUserDto.getRole());
            User savedUser = userRepository.save(user);
            return convertToDto(savedUser); // 업데이트 후 엔티티를 DTO로 변환
        });
    }

    // 유저 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 엔티티를 DTO로 변환
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        // password는 보안상 클라이언트로 보내지 않음
        return dto;
    }

    // DTO를 엔티티로 변환
    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return user;
    }
}
