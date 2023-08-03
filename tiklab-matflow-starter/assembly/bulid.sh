
#jenkins项目目录
jenkins_name=tiklab-matflow
#应用名称
app_name=tiklab-matflow

Environment=test
system_type=linux

#不同环境ip地址
dev_ip=172.10.1.12
test_ip=172.11.1.18
demo_ip=172.12.1.18
#部署位置
build_address=/usr/local/apps
#历史文件夹
history_address=/usr/local/history


#制品文件地址
tar_file=
#制品文件
app_tar_file=
#解压后文件
app_file=
#ip
ip=

app_package(){

    #应用根目录
    app_root_path=/root/.jenkins/workspace/${jenkins_name}

    #获取制品路径
    app_root_starter=${app_root_path}/${app_name}-starter/target

    #进入目录
    cd ${app_root_path}

    #打包
    mvn clean package -U -DskipTests=true -P env-${Environment} -P system-${system_type}

    #获取制品包
    tar_file=$(find ${app_root_path} -name ${app_name}*.tar.gz)

    if [ ! -n "${tar_file}" ]; then
      echo "无法获取到制品文件！"
      exit 0
    fi

    #获取文件解后的文件明显
    app_tar_file=${tar_file#${app_root_starter}/}
    app_file=${app_tar_file%.tar.gz}

    if [ ! -n "${app_file}" ]; then
      echo "获取到制品解压文件名称失败！"
      exit 0
    fi
}

build_dev(){
    app_package
    echo "制品位置："${tar_file}
    echo "制品解压后文件名称："${app_file}
    ip=${dev_ip}
}

build_test(){
    app_package
    echo "制品位置："${tar_file}
    echo "制品解压后文件名称："${app_file}
    ip=${test_ip}
}

build_demo(){
    app_package
    echo "制品位置："${tar_file}
    echo "制品解压后文件名称："${app_file}
    ip=${demo_ip}
}

build_prd(){
    app_package
    echo "制品位置："${tar_file}
    echo "制品解压后文件名称："${app_file}
}

build_aliyun(){
    app_package
    echo "制品位置："${tar_file}
    echo "制品解压后文件名称："${app_file}
}

case ${system_type} in
  "linux")
    echo "当前系统类型为：linux"
    ;;
  "windows")
    echo "暂不支持当前系统类型构建！"
    exit 0
    ;;
  "mac")
    echo "暂不支持当前系统类型构建！"
    exit 0
    ;;
  *)
    echo "暂不支持当前系统类型构建！请选择：linux,windows,mac中的一个环境！"
    exit 0
    ;;
esac

case ${Environment} in
  "dev")
    echo "当前环境未配置执行程序！"
    exit 0
    ;;
  "test")
    build_test
    ;;
  "demo")
    build_demo
    ;;
  "prd")
    echo "当前环境未配置执行程序！"
    exit 0
    ;;
  "aliyun")
    echo "当前环境未配置执行程序！"
    exit 0
    ;;
  *)
    echo "没有此环境！请选择：dev,test,demo,prd,aliyun中的一个环境！"
    exit 0
    ;;
esac

if [ ! -n "${ip}" ]; then
    echo "无法获取到ip地址！"
    exit 0
fi

mkdirs="source /etc/profile;mkdir -p ${history_address};mkdir -p ${build_address}"

#执行远程命令
ssh root@${ip} ${mkdirs}

#发送文件
scp -r ${tar_file} ${ip}:${history_address}

#解压文件
tar="tar -xvf ${history_address}/${app_tar_file} -C ${build_address}"

#启动命令
#start="cd ${build_address}/${app_file}/bin;source /etc/profile;sh shutdown.sh;nohup sh startup.sh > /dev/null 2>&1 &"
start="cd ${build_address}/${app_file}/bin;source /etc/profile;sh shutdown.sh;sh startup.sh "

#解压文件
ssh root@${ip} ${tar}

#启动
ssh root@${ip} ${start}
