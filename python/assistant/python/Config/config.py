# encoding: utf-8
"""
@author: donglaic 
@contact: 652559480@qq.com

@version: 1.0
@file: config.py
@time: 2019/1/6 20:14

Note: 
"""
import json


class Config(object):
    def __init__(self,file_name):
        self.__file_name = file_name
        self.__config = self.Load()

    def Load(self):
        f = open(self.__file_name,'rb')
        return json.load(f)

    @property
    def config(self):
        return self.__config

if __name__ == '__main__':
    c = Config('test.json')
    print(c.config)

