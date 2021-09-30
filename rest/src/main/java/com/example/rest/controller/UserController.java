package com.example.rest.controller;

import com.example.rest.dto.ResponseDto;
import com.example.rest.dto.UserDto;
import com.example.rest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<?>> users() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto<>(HttpStatus.OK.value(), userService.findAll()));
    }

    // 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> user(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), userService.findById(id)));
    }

    // 회원 등록
    @PostMapping
    public ResponseEntity<ResponseDto<?>> insert(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), userService.insert(userDto)));
    }

    // 회원 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> modify(@PathVariable Long id, @RequestBody UserDto userDto) {
        userService.modify(id, userDto);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "update success"));
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> remove(@PathVariable Long id) {
        userService.remove(id);
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "delete success"));
    }
}
