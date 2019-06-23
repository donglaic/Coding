# encoding: utf-8
"""
@author: donglaic 
@contact: 652559480@qq.com

@version: 1.0
@file: ebbinghaus.py
@time: 2019/1/6 22:07

Note: 
"""

from Assistant.processer import Processer


class Ebbinghaus(Processer):
    def __init__(self,name):
        Processer.__init__(self,name)

    def Init(self):
        pass

    def Receive(self,msg):
        self.logger.info('Processer {} rec {}'.format(self.name,msg))

if __name__ == '__main__':
    pass

