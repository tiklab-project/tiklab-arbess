package io.tiklab.arbess.setting.k8s.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tiklab.arbess.setting.k8s.model.KubectlNode;
import io.tiklab.arbess.setting.k8s.model.KubectlVersion;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.exception.ApplicationException;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KubectlConfig {

    private String kubeConfigPath;

    private String kubePath;

    public KubectlConfig() {
    }

    public KubectlConfig(String kubePath) {
        this.kubePath = kubePath;
    }

    public KubectlConfig(String kubeConfigPath, String kubePath) {
        this.kubeConfigPath = kubeConfigPath;
        this.kubePath = kubePath;
    }

    public static KubectlConfig instance( String kubePath){
        return new KubectlConfig(kubePath);
    }

    public static KubectlConfig instance(String kubeConfigPath, String kubePath){
        return new KubectlConfig(kubeConfigPath,kubePath);
    }

    // 执行 kubectl 命令的通用方法
    private String runKubectlCommand(String... args) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();

        int systemType = PipelineUtil.findSystemType();
        if (systemType == 1){
            command.add(".\\kubectl.exe");
        }else{
            command.add("./kubectl");
        }
        command.add("--kubeconfig=" + kubeConfigPath);
        command.addAll(Arrays.asList(args));

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true); // 合并输出流
        pb.directory(new File(kubePath));
        Process process = pb.start();
        boolean finished = process.waitFor(10, TimeUnit.SECONDS);

        if (!finished) {
            process.destroy();
            throw new RuntimeException("命令执行超时：" + String.join(" ", command));
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return output.toString();
        }
    }

    /**
     * 1. 验证 kubeconfig 文件是否存在且格式基本正确
     */
    private boolean validateConfigFile() {
        File file = new File(kubeConfigPath);
        return file.exists() && file.length() > 0;
    }

    /**
     * 获取版本信息
     * @return 版本信息
     */
    public KubectlVersion findK8sVersion() {
        KubectlVersion kubectlVersion = new KubectlVersion();

        boolean b = validateConfigFile();
        if (!b) {
            throw new ApplicationException("无法识别当前的配置k8s文件!");
        }

        try {
            // 1. 获取版本信息
            ProcessBuilder versionPb = new ProcessBuilder("./kubectl", "version", "--kubeconfig", kubeConfigPath, "--output=yaml");
            versionPb.directory(new File(kubePath));
            Process versionProcess = versionPb.start();

            String versionOutput = new BufferedReader(new InputStreamReader(versionProcess.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));

            // 使用 SnakeYAML 解析
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(versionOutput);

            Map<String, Object> clientVersion = (Map<String, Object>) data.get("clientVersion");
            Map<String, Object> serverVersion = (Map<String, Object>) data.get("serverVersion");

            if (clientVersion != null && clientVersion.get("gitVersion") != null) {
                kubectlVersion.setClientVersion(clientVersion.get("gitVersion").toString());
            }

            if (serverVersion != null && serverVersion.get("gitVersion") != null) {
                kubectlVersion.setServerVersion(serverVersion.get("gitVersion").toString());
            }

            // 2. 获取 server 地址
            ProcessBuilder configPb = new ProcessBuilder("./kubectl", "config", "view", "--minify", "--kubeconfig", kubeConfigPath, "--output=yaml");
            configPb.directory(new File(kubePath));
            Process configProcess = configPb.start();
            String configOutput = new BufferedReader(new InputStreamReader(configProcess.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));

            Pattern serverPatternAddr = Pattern.compile("^\\s*server:\\s*(https?://\\S+)", Pattern.MULTILINE);

            Matcher serverMatcherAddr = serverPatternAddr.matcher(configOutput);
            if (serverMatcherAddr.find()) {
                kubectlVersion.setServerAddress(serverMatcherAddr.group(1));
            }

        } catch (Exception e) {
            throw new ApplicationException("获取版本失败:"+e.getMessage());
        }

        return kubectlVersion;
    }

    public KubectlVersion findK8sClientVersion() {
        KubectlVersion kubectlVersion = new KubectlVersion();

        try {
            // 1. 获取版本信息
            ProcessBuilder versionPb = new ProcessBuilder("./kubectl", "version", "--output=yaml");
            versionPb.directory(new File(kubePath));
            Process versionProcess = versionPb.start();

            String versionOutput = new BufferedReader(new InputStreamReader(versionProcess.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));

            // 使用 SnakeYAML 解析
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(versionOutput);

            Map<String, Object> clientVersion = (Map<String, Object>) data.get("clientVersion");


            if (clientVersion != null && clientVersion.get("gitVersion") != null) {
                kubectlVersion.setClientVersion(clientVersion.get("gitVersion").toString());
            }
        } catch (Exception e) {
            throw new ApplicationException("获取版本失败:"+e.getMessage());
        }

        return kubectlVersion;
    }

    /**
     * 4. 获取所有节点信息
     */
    public List<KubectlNode> findAllNodes() {
        try {
            String json = runKubectlCommand("get", "nodes", "-o", "json");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            List<KubectlNode> result = new ArrayList<>();
            for (JsonNode item : root.get("items")) {
                KubectlNode node = new KubectlNode();
                node.setNodeName(item.get("metadata").get("name").asText());
                node.setNodeStatus(getNodeStatus(item));
                node.setNodeRole(getNodeRoles(item));
                node.setOsImage(item.at("/status/nodeInfo/osImage").asText());
                node.setKernelVersion(item.at("/status/nodeInfo/kernelVersion").asText());
                node.setContainerRuntime(item.at("/status/nodeInfo/containerRuntimeVersion").asText());
                node.setNodeIp(findAddress(item, "InternalIP"));
                node.setExternalIp(findAddress(item, "ExternalIP"));
                String isoTime = item.get("metadata").get("creationTimestamp").asText(); // 例：2024-05-13T01:02:03Z

                ZonedDateTime zdt = ZonedDateTime.parse(isoTime);
                String formatted = zdt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                node.setAcg(formatted);
                result.add(node);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("解析节点 JSON 失败: " + e.getMessage(), e);
        }
    }

    public void execYaml(String yamlPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "./kubectl", "apply", "-f", yamlPath, "--kubeconfig", kubeConfigPath
            );
            pb.directory(new File(kubePath)); // 设置 kubectl 所在目录
            pb.redirectErrorStream(true); // 合并 stderr 和 stdout

            Process process = pb.start();

            // 读取输出
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // 或者写入日志
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new ApplicationException("kubectl apply 执行失败，退出码: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            throw new ApplicationException("执行失败: " + e.getMessage(), e);
        }
    }

    private String getNodeStatus(JsonNode item) {
        for (JsonNode condition : item.at("/status/conditions")) {
            if ("Ready".equals(condition.get("type").asText())) {
                return condition.get("status").asText().equals("True") ? "Ready" : "NotReady";
            }
        }
        return "Unknown";
    }

    private String getNodeRoles(JsonNode item) {
        JsonNode labels = item.get("metadata").get("labels");
        for (Iterator<String> it = labels.fieldNames(); it.hasNext(); ) {
            String key = it.next();
            if (key.startsWith("node-role.kubernetes.io/")) {
                return key.replace("node-role.kubernetes.io/", "");
            }
        }
        return "none";
    }

    private String findAddress(JsonNode item, String type) {
        for (JsonNode addr : item.at("/status/addresses")) {
            if (type.equals(addr.get("type").asText())) {
                return addr.get("address").asText();
            }
        }
        return null;
    }




}
