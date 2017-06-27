git_pull()
{
	git_last_commit=`git log -1`;
	git clean -fd;
	git pull;
	echo 'will install from $git_last_commit'
}

#开始真正的编译打包操作
git_pull
