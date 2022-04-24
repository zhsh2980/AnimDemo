#!/bin/bash

#相同的库存在多个版本时，gradle 会自动使用最高版本的库，上图中 后面带有 “(*)” 的库就表示这个库有被覆盖过。

echo 'start 查看依赖树, 请在项目根目录执行'

 ./gradlew :app:dependencies >log.txt

echo 'finish 依赖树查询完毕, 到根目录 log.txt 中查看'






