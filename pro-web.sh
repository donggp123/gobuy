
#!/bin/sh
export BUILD_ID=pro-web
TOMCAT_HOME=/usr/local/tomcat
PROJECT=web
SAVE_DIR=/opt/workspaces/war/web/target
#shutdown tomcat
$TOMCAT_HOME/bin/shutdown.sh
echo "tomcat shutdown"
#publish project
echo "$PROJECT publishing"
rm -rf $TOMCAT_HOME/webapps/$PROJECT*
cp $SAVE_DIR/$PROJECT.war $TOMCAT_HOME/webapps/$PROJECT.war
#bak project
BAK_DIR="$SAVE_DIR"/bak/$PROJECT/`date +%Y%m%d`
mkdir -p "$BAK_DIR"
cp $TOMCAT_HOME/webapps/$PROJECT.war $BAK_DIR/"$PROJECT"_`date +%H%M%S`.war
#remove tmp
rm -rf "$SAVE_DIR"/$PROJECT*.war
#start tomcat
$TOMCAT_HOME/bin/startup.sh
echo "tomcat is starting,please try to access $PROJECT conslone url" 1
