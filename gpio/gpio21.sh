#!/bin/sh
echo 21 > /sys/class/gpio/export
echo out > /sys/class/gpio/gpio21/direction

for i in {1..10};
do
		echo 1 > /sys/class/gpio/gpio21/value
		sleep 1
		echo 0 > /sys/class/gpio/gpio21/value
		sleep 1
done
echo 21 > /sys/class/gpio/unexport
