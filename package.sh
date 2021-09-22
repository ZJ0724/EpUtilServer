#!/bin/sh

appName="ep-util"

# 前端
# shellcheck disable=SC2164
cd ui
chmod 777 package.sh && ./package.sh
# shellcheck disable=SC2103
cd ..
rm -rf src/main/resources/static/*
cp -r ui/build/* src/main/resources/static

# 后端
rm -rf build
chmod 777 gradlew && ./gradlew build -x test

mkdir -p build/${appName}
cp -r build/libs/ep-util.jar build/${appName}
cp -r bin build/${appName}

# shellcheck disable=SC2164
cd build
zip -q -r ${appName}.zip ${appName}
tar -zcvf ${appName}.tar.gz ${appName}
cd ..