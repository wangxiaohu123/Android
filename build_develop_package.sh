#!/usr/bin/bash
set -o nounset
set -o errexit
export ANDROID_HOME="/usr/local/share/android-sdk"
nowdate=`date +%m%d%H%M`
#用当前时间作为版本的build号
echo $nowdate

echo "https://api.develop.ykx100.com/" > YKX/baselibs/src/main/res/raw/url
cd  YKX
./gradlew clean
./gradlew -P suffix=$nowdate assembledebug