#!/usr/sbin/init

#数据库默认密码
password=Darth2020.

passwordTemp=`grep "temporary password" /var/log/mysqld.log | awk '{ print $NF}'`
echo "初始化数据库密码"
mysqladmin -uroot -p$passwordTemp password $password
echo "数据库默认密码: Darth2020. "
echo "开启数据库远程连接"
mysql -uroot -p$password -e "use mysql;update user set host = '%' where user = 'root ';flush privileges;"
