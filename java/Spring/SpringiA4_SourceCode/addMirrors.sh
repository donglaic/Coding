#!/bin/bash

files=$(find -name build.gradle)

for f in $files
	do
		sed -i '4r aliyunMirror' $f
	done
