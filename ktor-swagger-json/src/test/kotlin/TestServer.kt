import com.rarnu.ksj.annotation.*
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.junit.Test
import java.io.Serializable

class ApplicationTest {

    @Test
    fun start() {
        testServer()
    }
}

fun testServer() {
    embeddedServer(Netty, 8080) {
        routing {
            sample()
        }
    }.start(wait = true)
}

data class MyData(
    @ApiModelProperty(name = "name", value = "名称", required = true)
    val name: String? = "",
    @ApiModelProperty(name = "greeting", value = "招呼", required = true)
    val greeting: String? = ""
): Serializable

data class RetData(
        @ApiModelProperty(name = "sayhello", value = "招呼用语", required = true)
        val sayhello: String
): Serializable

@Api(value = "测试用的", tag = "Test")
fun Routing.sample() {

    @ApiOperation(value = "测试接口", httpMethod = "GET")
    get("/hello") {
        @ApiParam(name = "name", value = "名称", required = true, type = "string", example = "2333")
        val name = call.parameters["name"] ?: ""
        call.respondText { "hello: $name" }
    }

    @ApiOperation(value = "测试POST接口", httpMethod = "POST")
    post<MyData>("/h1") {
        @ApiParam(name = "data", value = "数据", required = true, type = "MyData")
        val data = call.receive<MyData>()
        call.respond(
                @ApiResponse(value = "测试返回", type = "RetData")
                RetData("${data.greeting} ${data.name}")
        )
    }
}