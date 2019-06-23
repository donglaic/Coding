# encoding: utf-8
"""
@author: donglaic 
@contact: 652559480@qq.com

@version: 1.0
@file: test.py
@time: 2019/1/6 21:31

Note: 
"""
import importlib

if __name__ == '__main__':
    # import Assistant.processer
    aa = importlib.import_module('Assistant.processer')
    inp_func = 'Processer'
    f = getattr(aa, inp_func,None)
    print(f())
    # __import__('Assistant.processer', fromlist=True)
    # from Assistant.processer import Processer
    # print(dir(Assistant.processer.Processer))


