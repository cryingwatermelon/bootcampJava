package com.watermelon.beatckc.controller;

import com.watermelon.beatckc.entity.Basicinfo;
import com.watermelon.beatckc.entity.BasicinfoDraft;
import com.watermelon.beatckc.entity.Fetchers;
import com.watermelon.beatckc.entity.Tables;
import com.watermelon.beatckc.entity.dto.AddDto;
import com.watermelon.beatckc.entity.dto.GetView;
import com.watermelon.beatckc.validation.NoQQEmailPattern;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
@Validated
public class UserInfoController implements Tables, Fetchers {
    private final JSqlClient jSqlClient;

    public UserInfoController(JSqlClient jSqlClient) {
        this.jSqlClient = jSqlClient;
    }

    @GetMapping
    public ResponseEntity<List<GetView>> getUserList() {
        List<GetView> result = jSqlClient.createQuery(BASICINFO_TABLE)
                .select(
                        BASICINFO_TABLE.fetch(GetView.class)

                ).execute();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetView> getUserById(@PathVariable @Positive Integer id) {
        GetView result = jSqlClient.findById(GetView.class, id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 不允许QQ邮箱的自定义校验
    @GetMapping("/query")
    public ResponseEntity<GetView> getUserByEmail(@RequestParam @Email @NoQQEmailPattern(message = "注册时就不允许使用QQ邮箱,所以不要输入QQ邮箱进行查询") String email) {
        var result = jSqlClient.createQuery(BASICINFO_TABLE)
                .where(BASICINFO_TABLE.email().eq(email))
                .select(
                        BASICINFO_TABLE.fetch(GetView.class)
                ).execute();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        System.out.println(result);
        return new ResponseEntity<>(result.getFirst(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNewUser(@RequestBody @Valid AddDto user) {

        Basicinfo newUser = BasicinfoDraft.$.produce(d -> {
            d.setEmail(user.getEmail());
            d.setNickname(user.getNickname());
        });
        jSqlClient.save(newUser);
        return new ResponseEntity<>("添加成功", HttpStatus.OK);
    }
}
