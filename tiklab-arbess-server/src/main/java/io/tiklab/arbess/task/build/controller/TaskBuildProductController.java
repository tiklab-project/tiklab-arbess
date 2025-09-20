package io.tiklab.arbess.task.build.controller;

import io.tiklab.arbess.support.agent.model.AgentMessage;
import io.tiklab.arbess.task.build.model.TaskBuildProduct;
import io.tiklab.arbess.task.build.model.TaskBuildProductQuery;
import io.tiklab.arbess.task.build.service.TaskBuildProductService;
import io.tiklab.arbess.ws.server.SocketServerHandler;
import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @pi.protocol: http
 * @pi.groupName: 实例制品控制器
 * @pi.url:/instance/product
 */
@RestController
@RequestMapping("/instance/artifact")
@Api(name = "实例制品控制器",desc = "实例制品管理")
public class TaskBuildProductController {

    @Autowired
    private TaskBuildProductService buildProductService;

    @Value("${external.url}")
    private String externalUrl;

    /**
     * @pi.name:查询实例制品列表
     * @pi.url:/findBuildProductList
     * @pi.methodType:post
     * @pi.request-type: json
     * @pi.param: model=io.tiklab.arbess.task.build.model.TaskBuildProductQuery;
     */
    @RequestMapping(path="/findBuildProductList",method = RequestMethod.POST)
    @ApiMethod(name = "查询实例制品列表",desc = "查询实例制品列表")
    @ApiParam(name = "productQuery",desc = "查询条件",required = true)
    public Result<List<TaskBuildProduct>> findBuildProductList(@RequestBody @Valid @NotNull TaskBuildProductQuery productQuery){
        List<TaskBuildProduct> buildProductList = buildProductService.findBuildProductList(productQuery);
        return Result.ok(buildProductList);
    }


    @GetMapping("/download/*")
    public void download(HttpServletResponse response, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String path = requestURI.substring(requestURI.indexOf("/download/") + "/download/".length());
        TaskBuildProductQuery productQuery = new TaskBuildProductQuery();
        productQuery.setInstanceId(path);
        List<TaskBuildProduct> buildProductList = buildProductService.findBuildProductList(productQuery);
        if (Objects.isNull(buildProductList) || buildProductList.isEmpty()){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        TaskBuildProduct taskBuildProduct = buildProductList.get(0);
        String agentId = "local-default";
        String filePath = taskBuildProduct.getValue();

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + new File(filePath).getName());
        if(!StringUtils.isEmpty(taskBuildProduct.getFileSize())){
            response.setHeader("Content-Length", taskBuildProduct.getFileSize());
        }
        // 请求ID
        String requestId = UUID.randomUUID().toString();

        try (ServletOutputStream out = response.getOutputStream()) {

            // 注册一个回调（当 WebSocket 收到分片时写入 out）
            FileTransferSession session = new FileTransferSession(requestId, out);
            FileTransferManager.register(requestId,session);

            AgentMessage agentMessage = new AgentMessage();
            agentMessage.setType("file");
            agentMessage.setMessage(filePath);
            agentMessage.setSessionId(requestId);
            SocketServerHandler.instance().sendHandleMessage(agentId,agentMessage);

            // 阻塞直到传输完成（可加超时）
            session.awaitCompletion();

        } catch (IOException e) {
            // 🚨 用户可能取消了下载（连接关闭）
            System.out.println("用户取消下载: " + e.getMessage());
            // 清理 session
            FileTransferManager.remove(requestId);
            // 这里还可以通知 Agent 停止传输
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}












