# deploy 1
call mvn deploy:deploy-file -DrepositoryId=cgiser-local -Durl=http://192.168.2.7:8080/nexus/content/repositories/cgiser-local/ -DgroupId=com.cgiser -DartifactId=cgiser-ssoserver-client -Dversion=1.0 -Dpackaging=jar -Dfile=cgiser-ssoserver-parent/cgiser-ssoserver-client/target/cgiser-ssoserver-client-1.0.jar -e

call mvn deploy:deploy-file -DrepositoryId=cgiser-local -Durl=http://192.168.2.7:8080/nexus/content/repositories/cgiser-local/ -DgroupId=com.cgiser -DartifactId=cgiser-ssoserver-client -Dversion=1.0 -Dpackaging=jar -Dclassifier=sources -Dfile=cgiser-ssoserver-parent/cgiser-ssoserver-client/target/cgiser-ssoserver-client-1.0-sources.jar 

call mvn deploy:deploy-file -DrepositoryId=cgiser-local -Durl=http://192.168.2.7:8080/nexus/content/repositories/cgiser-local/ -DgroupId=com.cgiser -DartifactId=cgiser-ssoserver-biz -Dversion=1.0 -Dpackaging=jar -Dfile=cgiser-ssoserver-parent/cgiser-ssoserver-biz/target/cgiser-ssoserver-biz-1.0.jar -e

call mvn deploy:deploy-file -DrepositoryId=cgiser-local -Durl=http://192.168.2.7:8080/nexus/content/repositories/cgiser-local/ -DgroupId=com.cgiser -DartifactId=cgiser-ssoserver-biz -Dversion=1.0 -Dpackaging=jar -Dclassifier=sources -Dfile=cgiser-ssoserver-parent/cgiser-ssoserver-biz/target/cgiser-ssoserver-biz-1.0-sources.jar 