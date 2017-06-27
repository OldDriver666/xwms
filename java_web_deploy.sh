NULL=/dev/null
TARGET_NAME="managesvr.war"
DEV_TOMCAT_PATH="/home/fise/apache-tomcat-8.0.32"
CURRENT_DATE=`date +%Y%m%d%H%M%S`
MY_PID_FILE="./java_web_deploy.pid"

check_run()
{
		# 同时只能有一个部署进程在执行
		touch $MY_PID_FILE
		MY_PID=`cat $MY_PID_FILE`

		if [ ! -z "$MY_PID" ] ; then
			IS_EXIST=`ps ax | awk '{ print $1 }' | grep -e "^${MY_PID}$"`
			if [ ! -z "$IS_EXIST" ] ; then	
				echo "骚等！有锅锅正在发布！不信你看："
				ps aux | grep -e "^${MY_PID}$"
				exit 6
			fi
		fi
		echo $$ > $MY_PID_FILE
}

git_pull()
{
		git_last_commit=`git log -1`;
		git clean -fd > $NULL;
		git pull > $NULL;
		echo 'will install from:' ${git_last_commit}
}

exit_deploy()
{
	rm -rf $MY_PID_FILE
	exit 0
}

build_target()
{
	if [ $1 == "dev" ] ; then
		echo "will build project use dev config"
		mvn install -Pdev -DskipTests
	else
		echo "will build project use produce config"
		mvn install -Pproduce -DskipTests
	fi
	
	if [ ! -z "$TARGET_NAME" ] ; then
		echo "build success will deploy\n"
	else
		echo "build fail please check code"
		exit 8
	fi
}



delopy_project()
{
	if [ $1 == "dev" ] ; then
		#停止目前的服务
		$DEV_TOMCAT_PATH/bin/shutdown.sh
		#备份原来版本
		cp $DEV_TOMCAT_PATH/webapps/$TARGET_NAME $DEV_TOMCAT_PATH/webapps/${TARGET_NAME}.${CURRENT_DATE}
		mv ./target/$TARGET_NAME $DEV_TOMCAT_PATH/webapps/
		#启动最新的服务
		$DEV_TOMCAT_PATH/bin/startup.sh
		tail -f $DEV_TOMCAT_PATH/logs/vernonshopLog-1.0
	else
		echo "test delopy to produce"
	fi
}

#开始真正的编译打包操作
check_run

git_pull
build_target dev
delopy_project dev

exit_deploy

