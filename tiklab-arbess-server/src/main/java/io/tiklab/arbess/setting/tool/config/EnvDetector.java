package io.tiklab.arbess.setting.tool.config;

import io.tiklab.arbess.setting.tool.model.ToolInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class EnvDetector {

    public static EnvDetector findInstance() {
        return new EnvDetector();
    }

    public ToolInfo findEnv(String type) {
        switch (type) {
            case "git" -> {
                return detectGit();
            }
            case "svn" -> {
                return detectSvn();
            }
            case "jdk" -> {
                return detectJava();
            }
            case "maven" -> {
                return detectMaven();
            }
            case "node" -> {
                return detectNode();
            }
            case "go" -> {
                return detectGo();
            }
            case "c_add" -> {
                return detectCpp();
            }
            case "python" -> {
                return detectPython();
            }
            case "php" -> {
                return detectPhp();
            }
            case "net_core" -> {
                return detectDotnet();
            }
            case "k8s" -> {
                return detectKubectl();
            }
            default -> {
                throw new RuntimeException("未知的环境类型。");
            }
        }
    }


    private static ToolInfo detectGit() {
        try {
            ProcessBuilder builder = new ProcessBuilder("git", "--version");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && line.toLowerCase().contains("git version")) {
                    String version = line.replace("git version", "").trim();
                    String path = getCommandPath("git");
                    return new ToolInfo(true, version, path);
                }
            }
        } catch (IOException ignored) {}
        return new ToolInfo(false, null, null);
    }

    private static ToolInfo detectJava() {
        try {
            ProcessBuilder builder = new ProcessBuilder("java", "-version");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && line.toLowerCase().contains("version")) {
                    String version = line.split("\"")[1];
                    String path = getCommandPath("java");
                    return new ToolInfo(true, version, path);
                }
            }
        } catch (IOException ignored) {}
        return new ToolInfo(false, null, null);
    }

    private static ToolInfo detectMaven() {
        try {
            ProcessBuilder builder = new ProcessBuilder("mvn", "-v");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && line.toLowerCase().contains("apache maven")) {
                    String version = line.replace("Apache Maven", "").trim().split(" ")[0];
                    String path = getCommandPath("mvn");
                    return new ToolInfo(true, version, path);
                }
            }
        } catch (IOException ignored) {}
        return new ToolInfo(false, null, null);
    }

    private static ToolInfo detectNode() {
        try {
            ProcessBuilder builder = new ProcessBuilder("node", "--version");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && line.startsWith("v")) {
                    String version = line.substring(1);
                    String path = getCommandPath("node");
                    return new ToolInfo(true, version, path);
                }
            }
        } catch (IOException ignored) {}
        return new ToolInfo(false, null, null);
    }

    private static ToolInfo detectGo() {
        try {
            ProcessBuilder builder = new ProcessBuilder("go", "version");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && line.toLowerCase().contains("go version")) {
                    String version = line.split(" ")[2]; // go1.21.0
                    String path = getCommandPath("go");
                    return new ToolInfo(true, version, path);
                }
            }
        } catch (IOException ignored) {}
        return new ToolInfo(false, null, null);
    }

    private static ToolInfo detectSvn() {
        try {
            ProcessBuilder builder = new ProcessBuilder("svn", "--version");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && line.toLowerCase().contains("svn")) {
                    String version = line.replaceAll("[^0-9.]", "").trim();
                    String path = getCommandPath("svn");
                    return new ToolInfo(true, version, path);
                }
            }

            process.waitFor();
        } catch (IOException | InterruptedException ignored) {}

        return new ToolInfo(false, null, null);
    }

    private static ToolInfo detectCpp() {
        List<String[]> commands = Arrays.asList(
                new String[]{"g++", "--version"},
                new String[]{"clang++", "--version"},
                new String[]{"cl"} // Windows
        );

        for (String[] cmd : commands) {
            ToolInfo info = tryCommand(cmd);
            if (info.isInstalled()) return info;
        }

        return new ToolInfo(false, null, null, null);
    }

    private static ToolInfo tryCommand(String[] cmd) {
        try {
            ProcessBuilder builder = new ProcessBuilder(cmd);
            builder.redirectErrorStream(true);
            Process p = builder.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()))) {
                String output = reader.readLine();
                if (output != null) {
                    String version = output.trim();
                    String type = cmd[0];
                    String path = getCommandPath(type);
                    return new ToolInfo(true, type, version, path);
                }
            }
        } catch (IOException ignored) {}
        return new ToolInfo(false, null, null, null);
    }

    private static ToolInfo detectDotnet() {
        try {
            ProcessBuilder builder = new ProcessBuilder("dotnet", "--version");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && line.matches("\\d+\\.\\d+(\\.\\d+)?")) {
                    String version = line.trim();
                    String path = getCommandPath("dotnet");
                    return new ToolInfo(true, version, path);
                }
            }
        } catch (IOException ignored) {}
        return new ToolInfo(false, null, null);
    }

    private static ToolInfo detectPhp() {
        try {
            ProcessBuilder builder = new ProcessBuilder("php", "-v");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && line.toLowerCase().contains("php")) {
                    String version = line.replace("PHP", "").trim();
                    String path = getCommandPath("php");
                    return new ToolInfo(true, version, path);
                }
            }
        } catch (IOException ignored) {}
        return new ToolInfo(false, null, null);
    }

    public static ToolInfo detectKubectl() {
        try {
            // 执行 kubectl version --client --short 获取版本
            ProcessBuilder builder = new ProcessBuilder("kubectl", "version", "--client", "--short");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                if (line != null && line.toLowerCase().contains("client version")) {
                    // 输出示例：Client Version: v1.28.1
                    String version = line.split(":")[1].trim();
                    String path = getCommandPath("kubectl");
                    return new ToolInfo(true, version, path);
                }
            }

            process.waitFor();
        } catch (IOException | InterruptedException ignored) {}

        return new ToolInfo(false, null, null);
    }
    /** 跨平台检测 Python 安装状态 */
    private static ToolInfo detectPython() {
        List<String[]> commands = Arrays.asList(
                new String[]{"python3", "--version"},
                new String[]{"python", "--version"},
                new String[]{"py", "--version"}
        );

        for (String[] cmd : commands) {
            ToolInfo info = tryPythonCommand(cmd);
            if (info.isInstalled()) {
                return info;
            }
        }

        return new ToolInfo(false, null, null);
    }

    /** 执行命令并解析结果 */
    private static ToolInfo tryPythonCommand(String[] cmd) {
        try {
            ProcessBuilder builder = new ProcessBuilder(cmd);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String output = reader.readLine();
                if (output != null && output.toLowerCase().contains("python")) {
                    // 获取版本信息
                    String version = output.replace("Python", "").trim();

                    // 再获取 Python 路径（可选）
                    String path = getCommandPath(cmd[0]);

                    return new ToolInfo(true, version, path);
                }
            }

            process.waitFor();
        } catch (IOException | InterruptedException ignored) {}

        return new ToolInfo(false, null, null);
    }


    private static String getCommandPath(String cmd) {
        String[] whichCmd = System.getProperty("os.name").toLowerCase().contains("win")
                ? new String[]{"where", cmd}
                : new String[]{"which", cmd};
        try {
            Process p = new ProcessBuilder(whichCmd).start();
            try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line = r.readLine();
                return (line != null && !line.isEmpty()) ? line.trim() : null;
            }
        } catch (IOException ignored) {}
        return null;
    }


}
