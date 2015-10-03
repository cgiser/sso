# sso
passport

///////////////// 研发环境 ////////////////////////////////////////
清空环境：
mvn clean -DAPP_ENV=dev

建立Eclipse环境：
mvn eclipse:eclipse -DAPP_ENV=dev

清空Eclipse环境：
mvn eclipse:clean -DAPP_ENV=dev

只编译：
mvn compile -DAPP_ENV=dev

只打包：
mvn package -DAPP_ENV=dev  -Dmaven.test.skip=true

只装配：
mvn process-resources -DAPP_ENV=dev

打包+装配+安装二进制：
mvn install -DAPP_ENV=dev -Dmaven.test.skip=true

///////////////// 测试环境 ////////////////////////////////////////

清空环境：
mvn clean -DAPP_ENV=test

打包+装配+安装二进制：
mvn install -DAPP_ENV=test -Dmaven.test.skip=true

mvn clean install -DAPP_ENV=idc_test -Dmaven.test.skip=true

///////////////// 生产环境 ////////////////////////////////////////

清空环境：
mvn clean -DAPP_ENV=prod

打包+装配+安装二进制：
mvn install -DAPP_ENV=prod -Dmaven.test.skip=true