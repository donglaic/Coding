#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @File  : myLogger.py
# @Author: Donglai Chen
# @Date  : 2018/9/23
# @Desc  : log系统

import logging

class MyLogger(object):
    def __init__(self):
        self.logger = logging.getLogger('Logger')
        self.logger.setLevel(level=logging.INFO)
        handler = logging.FileHandler("log.txt")
        handler.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(filename)s - %(levelname)s - %(lineno)s - %(message)s')
        handler.setFormatter(formatter)
        console = logging.StreamHandler()
        console.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(filename)s - %(levelname)s - %(lineno)s - %(message)s')
        console.setFormatter(formatter)
        self.logger.addHandler(console)
        self.logger.addHandler(handler)



if __name__ == '__main__':
    myLogger = MyLogger()
    myLogger.logger.info('test')
