<h1 align="center" style="border-bottom: none">
    <a href="https://arbess.tiklab.net/" target="_blank"><img alt="Arbess" src="https://image.tiklab.net/img/homes/g1/1e38ad72325ab017"></a><br>Arbess
</h1>

# Arbess - 开源的持续集成/持续交付 (CI/CD) 工具

Arbess 是一款强大的开源 CI/CD 工具，旨在帮助开发团队高效管理构建、测试和部署流程。它提供了丰富的功能，支持多种主流开发工具和平台，是现代软件开发流程中不可或缺的利器。

## 主要功能
通过流水线设计、任务管理、流水线执行、测试报告和统计分析五个方面来简单了解Arbess的主要功能。

### 流水线设计

用户灵活的设计流水线，支持串行和并行执行，触发设置，全局变量，后置处理等功能以适应多样化的工作流程需求。

- 支持串行任务，并行任务

支持创建并行或串行任务，使得用户可以根据具体需求灵活选择任务执行方式，从而优化流水线的效率和响应速度。

![img](https://community.tiklab.net/api/image/9dd0a79a3ae2d640)


- 支持配置触发设置

​     提供灵活的触发配置选项，包括 定时触发 和 周期触发，便于自动化任务的精准调度。

![img](https://community.tiklab.net/api/image/487c4c8d21413fb4)

- 配置变量

​     提供灵活的变量配置功能，允许用户定义全局变量并在整个流水线中复用，不同任务中可以使用相同的变量，减少出错风险。

﻿

![img](https://community.tiklab.net/api/image/8d96b75c794d805c)

- 配置后置处理

​     提供强大的后置处理功能，允许用户在任务执行完成后进行消息通知和自定义脚本处理。

![img](https://community.tiklab.net/api/image/b60e35ab990fb8af)

### 任务管理

流水线支持几十种不同的任务，源码，代码扫描，测试，部署等全方面覆盖。

- 源码

流水线支持多种源码，支持是市面上常用版本管理工具，并且集成了GitLab、GitHub、Gitee、GitPuk等。

| 支持类型 | 说明        |
| -------- | ----------- |
| 通用Git  | 支持Git协议 |
| Gitee    | 集成Gitee   |
| GitHub   | 集成GitHub  |
| GitLab   | 集成GitLab  |
| GitPuk   | 集成GitPuk  |
| Svn      | 支持Svn协议 |

﻿

- 代码扫描

流水线支持多种代码扫描，并且内置了spotbugs代码扫描，无需额外配置，可直接使用。

| 支持类型              | 说明                          |
| --------------------- | ----------------------------- |
| SpotBugs-Java代码扫描 | 内置SpotBugs-Java代码扫描工具 |
| SonarQube             | 集成SonarQube代码扫描         |

﻿

- 测试

集成了TestHubo自动化测试，并对单元测试结果进行格式化处理。

| 支持类型           | 说明                   |
| ------------------ | ---------------------- |
| TestHubo自动化测试 | 集成TestHubo自动化测试 |
| 单元测试           | 支持Mavne单元测试      |

﻿

- 构建

集成了市面上主要语言的使用的构建工具，Maven，npm，docker等。

| 支持类型 | 说明           |
| -------- | -------------- |
| Maven    | 支持Maven构建  |
| npm      | 支持npm构建    |
| Docker   | 支持Docker构建 |

﻿

- 拉取制品

可以拉取多种语言的制品，同时集成了多种第三方制品管理工具。

| 支持类型 | 说明                 |
| -------- | -------------------- |
| Maven    | 支持Maven拉取        |
| npm      | 支持npm拉取          |
| Docker   | 支持Docker拉取       |
| Hadess   | 支持拉取Hadess的制品 |
| Nexus    | 支持拉取Nexus的制品  |
| SSH      | 支持SSH拉取          |

﻿

- 推送制品

可以推送多种语言的制品，同时集成了多种第三方制品管理工具。

| 支持类型 | 说明             |
| -------- | ---------------- |
| Maven    | 支持Maven推送    |
| npm      | 支持npm推送      |
| Docker   | 支持Docker推送   |
| Hadess   | 支持推送到Hadess |
| Nexus    | 支持推送到Nexus  |
| SSH      | 支持SSH推送      |

﻿

- 部署

多种部署方式支持，同时支持，Docker部署，K8s集群部署。

| 支持类型    | 说明                   |
| ----------- | ---------------------- |
| 自定义部署  | 支持自定义部署         |
| 主机部署    | 支持部署到主机上       |
| Docker部署  | 支持部署到Docker环境中 |
| K8s集群部署 | 支持部署到K8s集群中    |

﻿

- 部署策略

多种部署策略支持，蓝绿发布，滚动发布，金丝雀发布等，这些策略能够有效减少系统升级对用户的影响，保证部署过程的平稳和高效。

| 支持类型   | 说明               |
| ---------- | ------------------ |
| 蓝绿部署   | 支持蓝绿部署模式   |
| 滚动部署   | 支持滚动模式部署   |
| 金丝雀模式 | 支持金丝雀模式部署 |

﻿

### 流水线执行

选择不同的Agent执行流水线，同时支持实时查看流水线的运行信息。

- 分布式执行

流水线支持配置不同的Agent在不同主机上执行不同的流水线，能够应对高负载的业务需求，确保系统在高并发场景下依然保持稳定和高效。



![img](https://community.tiklab.net/api/image/bff9e9c02d699bc1)

- 查看历史

可以在历史界面查看流水线的运行记录，同时支持个维度来快速定位所需历史，如：名称模糊查询，执行人，执行方式，运行状态等。

﻿

![img](https://community.tiklab.net/api/image/75c340ac524d83f5)

- 查看运行状态

让用户可以随时查看流水线中每个任务的运行信息，任务时间，运行方式，执行人，以及任务的最终运行结果等关键数据，能帮助团队快速识别问题并进行调整，确保整体流程的顺利进行。

![img](https://community.tiklab.net/api/image/f7913832ec315007)

- 查看日志详情

支持查看每个任务的详细日志输出，帮助用户实时监控任务执行的过程和结果。通过直观的日志界面，可以快速获取任务的运行状态、错误信息和调试信息，从而及时发现并解决问题。

﻿

![img](https://community.tiklab.net/api/image/3a16ad52f5afbab9)

### 测试报告

涵盖代码质量分析、代码扫描、单元测试及自动化测试结果，帮助用户实时掌握代码的健康状况，满足不同用户对代码可靠性和稳定性的需求。

- 代码扫描

集成代码质量分析工具，对代码进行全面扫描，识别潜在问题和优化空间，可以检测出潜在的漏洞、编码规范的偏差和性能瓶颈等问题。

![img](https://community.tiklab.net/api/image/f084c360c066f46f)

- 单元测试

支持查看每个单元测试的详细信息，包括测试用例的执行结果、具体用例的执行信息，成功，失败，错误的用例等，帮助开发者精确定位问题。


![img](https://community.tiklab.net/api/image/8ed56de84885338c)

- 自动化测试

集成 TestHubo 自动化测试平台，实现多维度的测试覆盖。TestHubo 支持功能测试、性能测试等多种类型等。

![img](https://community.tiklab.net/api/image/936cc9bac26997b4)

### 统计分析

Arbess支持流水线运行统计以及结果统计查看，方便管理者查看流水线的统计信息。

- 运行统计

可以从多个方面查看流水线的运行统计信息，如时间段，平均执行时长等，同时支持查看近7天，25天，30天，90天的统计信息。

![img](https://community.tiklab.net/api/image/850aa1f71e1b908b)

- 结果统计

可以从多个维度查看流水线的结果统计信息，如结果数量，结果概率等，同时支持查看近7天，25天，30天，90天的统计信息。

![img](https://community.tiklab.net/api/image/e10c87efe8cd55ac)

## 产品优势
通过简单的优势来了解Arbess，快速了解其独特之处和应用价值。

### 任务多样性
几十种任务支持

源码，代码扫描，测试，构建，拉取制品，推送制品，部署等都支持。

支持并行串行任务

支持任务并行串行执行。

任务自由组合

任务随意组合，没有任何限制。

支持任务单独运行

每个任务可以单独运行，随意使用。


### 简洁易用
界面简单

界面简洁，结构清晰，一目了然。

安装配置简单

下载安装即可使用，不需要任何额外配置。

### 安全可靠
多层级权限控制

应用级权限，系统级权限，项目级权限，全方面保护应用的安全。

日志审计

实时记录任何变动，追溯到个人，什么时间操作了什么。

自动备份与恢复

数据误删，数据损害，定时备份，实时恢复。


### 多版本，多终端
多版本支持

提供公有云和私有云版本。

多平台支持

支持Windows、Mac、Linux、Docker等平台。


### 开源免费
Arbess提供了开源的源码，可以在Gitee,GitHub上面直接下载，使用。

开源地址：

Gitee： https://gitee.com/tiklab-project/tiklab-arbess

GitHub： https://github.com/tiklab-project/tiklab-arbess

## 安装

### 系统要求
- Java 16+
- Maven 3.4+

### 克隆仓库
```bash
git clone https://github.com/tiklab-project/tiklab-arbess.git
cd tiklab-arbess
```

### 构建项目

#### 配置MAVEN仓库
配置maven的setrings.xml文件的远程仓库为一下内容

```
<mirror>
    <id>hadess</id>
    <name>hadess</name>
    <url>https://mirror.tiklab.net/repository/tiklab-maven</url>  
    <mirrorOf>*</mirrorOf>
</mirror>
```
#### 构建

- **MAC系统**：mvn clean package -P system-mac,env-dev
- **Linux系统**：mvn clean package -P system-linux,env-dev
- **Windows系统**：mvn clean package -P system-windows,env-dev

### 使用示例
1. 使用IDEA运行ArbessApplication启动。  
2. 打开浏览器，访问 http://localhost:8090
3. 登录信息，用户名：admin 密码：123456


### 贡献
我们欢迎社区的贡献！如果你有好的建议或发现了问题，请通过以下方式联系我：

[联系我们](https://tiklab.net/account/workOrder/add)

邮箱: tiklab@163.com

如需了解更多信息，请访问我们的官方网站或加入我们的社区讨论：

[官方网站](https://www.tiklab.net)

邮箱: tiklab@163.com

#### 立即体验 Arbess，解锁高效的自动化构建、测试和部署流程！

