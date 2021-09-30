package com.example.rest.service;

import com.example.rest.dto.UserDto;
import com.example.rest.entity.User;
import com.example.rest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(user -> UserDto.entityToDto(user)).collect(Collectors.toList());
    }

    // 회원 조회
    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return UserDto.entityToDto(user);
    }
    
    // 회원 등록
    @Transactional
    public UserDto insert(UserDto userDto) {
        User saveUser = UserDto.dtoToEntity(userDto);
        User saveUserInfo = userRepository.save(saveUser);
        log.info("saveUserInfo : {}", saveUserInfo);

        return UserDto.entityToDto(saveUserInfo);
    }

    // 회원 수정
    @Transactional
    public void modify(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEmail(userDto.getEmail());
        user.setAge(user.getAge());
    }

    // 회원 삭제
    @Transactional
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}
