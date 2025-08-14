package com.watermelon.beatckc.controller;

import com.watermelon.beatckc.entity.Fetchers;
import com.watermelon.beatckc.entity.Tables;
import com.watermelon.beatckc.entity.dto.AddDto;
import com.watermelon.beatckc.entity.dto.GetView;
import com.watermelon.beatckc.entity.dto.PatchDto;
import com.watermelon.beatckc.entity.dto.PutDto;
import com.watermelon.beatckc.validation.NoQQEmailPattern;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

        jSqlClient.save(user);
        return new ResponseEntity<>("添加成功", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable @Positive Integer id) {
        var result = jSqlClient.deleteById(GetView.class, id);
        System.out.println(result);
        if (result.getTotalAffectedRowCount() == 0) {
            return new ResponseEntity<String>("删除失败,ID不存在", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("删除成功", HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<String> putUserById(
            @RequestBody @Valid PutDto user) {
        var id = user.getId();
        if (id == null || id <= 0) {
            return new ResponseEntity<String>("id缺失或无效id", HttpStatus.BAD_REQUEST);
        }
        var existing = jSqlClient.findById(GetView.class, user.getId());
        if (existing == null) {
            return new ResponseEntity<String>("Id不存在", HttpStatus.NOT_FOUND);
        }

        var result = jSqlClient.update(user);
        if (result.getTotalAffectedRowCount() == 0) {
            return new ResponseEntity<>("更新失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("更新成功", HttpStatus.OK);
    }

    @PatchMapping()
    @Transactional
    public ResponseEntity<String> updateUserPartial(
            @RequestBody @Valid PatchDto updates) throws Exception {
        var id = updates.getId();
        if (id == null || id <= 0) {
            return new ResponseEntity<String>("id缺失或无效id", HttpStatus.BAD_REQUEST);
        }
        var existing = jSqlClient.findById(GetView.class, id);
        if (existing == null) {
            return new ResponseEntity<>("更新失败，ID不存在", HttpStatus.NOT_FOUND);
        }
        var result = jSqlClient.update(updates);
        return result.getTotalAffectedRowCount() > 0
                ? new ResponseEntity<>("部分更新成功", HttpStatus.OK)
                : new ResponseEntity<>("更新失败", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //TODO Spring Security basic auth jwts
}
