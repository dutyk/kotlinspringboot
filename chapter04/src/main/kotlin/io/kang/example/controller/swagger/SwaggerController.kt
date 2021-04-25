package io.kang.example.controller.swagger

import io.kang.example.entity.User
import io.kang.example.repository.UserRepository
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class SwaggerController {
    @Autowired
    lateinit var userRepository: UserRepository

    @ApiOperation(value="获取用户列表", notes="")
    @GetMapping("/all")
    fun getUserList(): List<User> {
        return userRepository.findAll().toList()
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PostMapping("/add")
    fun postUser(@RequestBody user: User): String {
        userRepository.save(user)
        return "success"
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @GetMapping("/find/{id}")
    fun getUser(@PathVariable id: Long): User? {
        return userRepository.findById(id).get()
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
        ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    )
    @PutMapping("/update/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: User) {
        val user = User(id, user.userName, user.password, user.email, user.age, user.height, user.address, user.education, user.income)
        userRepository.save(user)
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userRepository.deleteById(id)
    }
}