package com.watermelon.beatckc.controller;

import com.watermelon.beatckc.entity.Fetchers;
import com.watermelon.beatckc.entity.Tables;
import com.watermelon.beatckc.entity.dto.GetView;
import com.watermelon.beatckc.validation.NoQQEmailPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
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
    public ResponseEntity<GetView> getUserByEmail(@RequestParam @Email @NoQQEmailPattern(message = "自定义邮箱错误") String email) {
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


}
