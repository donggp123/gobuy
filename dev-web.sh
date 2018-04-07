
#!/bin/sh
export BUILD_ID=dev-web
echo 'after test'
# 项目地址
PROJECT_PATH=/opt/workspaces/dev/web/target
echo "项目地址 " ${PROJECT_PATH}
# 项目名称
PROJECT_NAME=web
echo "项目名称 " ${PROJECT_NAME}
# tomcat 地址
TOMCAT_HOME=/usr/local/tomcat-web
echo "tomcat 地址=" ${TOMCAT_HOME}
# 备份地址
BACK_UP_PATH=/opt/workspaces/backup
echo "备份地址 " ${BACK_UP_PATH}
if [ ! -d "${BACK_UP_PATH}" ]; then
        echo 'BACK_UP_PATH is not  real  path '
        echo '-----------------------------'
        # mkdir
        mkdir ${BACK_UP_PATH}
fi
if [ ! -d "${PROJECT_PATH}" ]; then
        echo 'PROJECT_PATH is not  real  project path'
        echo '-----------------------------'
        # exit
        exit 1
fi
cd $PROJECT_PATH
echo "cd" ${PROJECT_PATH}
# $TOMCAT_HOME
if [ ! -d "${TOMCAT_HOME}" ]; then
        echo 'TOMCAT_HOME is not a really path!'
        echo '-----------------------------'
    # exit
        exit 1
fi
# 新建备份路径
BACK_UP_DATE="$(date +%Y%m%d%H%m%S)"
echo create bakup dictory ${BACK_UP_DATE}
cd ${BACK_UP_PATH}
mkdir ${BACK_UP_DATE}
cd ${TOMCAT_HOME}/bin
echo ${TOMCAT_HOME}/bin
#run
./shutdown.sh
echo run ./shutdown.sh
sleep 10
cd ${TOMCAT_HOME}/webapps
echo cd ${TOMCAT_HOME}/webapps
# 备份
OLD_WAR=`ls  ${TOMCAT_HOME}/webapps | grep war`
echo "备份 " ${OLD_WAR}
cp ${OLD_WAR}  ${BACK_UP_PATH}/${BACK_UP_DATE}
# 删除以前的打包文件
rm -rf ${PROJECT_NAME}*
echo rm -rf ${PROJECT_NAME}*
rm -rf ${TOMCAT_HOME}/webapps/ROOT
echo rm -rf ${TOMCAT_HOME}/webapps/ROOT
# 项目
cd ${PROJECT_PATH}
WAR=`ls ${PROJECT_PATH} | grep war`
echo "新的war包" ${WAR}
if [ ! -f "${WAR}" ]; then
        echo 'this are not have war here '
        echo ${WAR}
        exit 1
fi
cp ${WAR} ${TOMCAT_HOME}/webapps
# 开启项目
cd ${TOMCAT_HOME}/bin
echo cd ${TOMCAT_HOME}/bin "开启项目"
#run
./startup.sh
echo run ./shartup.sh
