# encoding: utf-8
"""
@author: donglaic 
@contact: 652559480@qq.com

@version: 1.0
@file: processer.py
@time: 2018/12/23 17:56

Note:
"""
import logging
from abc import ABCMeta,abstractmethod

from Config.config import Config


class Processer(metaclass=ABCMeta):
    def __init__(self,name):
        self.name = name
        self.state = None
        self.logger = logging.getLogger('Assistant.'+str(self.name))
        self.Init()

    def mame(self):
        return self.name

    def CmdList(self):
        index = 1
        result = ''
        for c in Config(self.name+'_config.json').config:
            if index > 1:
                result = result + '\n'
            result = result + '{} {}'.format(index,c['Commnet'])
            index = index + 1
        return result

    @abstractmethod
    def Init(self):
        pass

    @abstractmethod
    def Receive(self,msg):
        pass

if __name__ == '__main__':
    pass

