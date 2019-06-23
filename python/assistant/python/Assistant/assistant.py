# encoding: utf-8
"""
@author: donglaic 
@contact: 652559480@qq.com

@version: 1.0
@file: assistant.py
@time: 2019/1/6 19:46

Note:   personal assistant
"""
import logging
import itchat
from itchat.content import *

from Assistant.processer import Processer
from Config.config import Config
import importlib

CMD_CMDINIT = "CMD:Init"
CMD_CMDLIST = "CMD:List"
USR_NAME = '陈有钱'

class Assistant(object):
    __processer_running = None
    __chenyouqian = None
    __state = None

    def __init__(self):
        self.__name = 'Assistant'
        self.__logger = self.CreatLogger()
        self.__logger.info('{} init!'.format(self.__name))
        self.LoginWechat()

    def Init(self):
        self.DelProcesserRunning()
        self.__state = None
        self.Send('System Init!')

    def LoginWechat(self):
        itchat.auto_login()
        self.__chenyouqian = itchat.search_friends(name=USR_NAME)[0]

    def Run(self):
        itchat.run()

    def CmdList(self):
        index = 1
        result = ''
        for c in Config(self.__name+'_config.json').config:
            if index > 1:
                result = result + '\n'
            result = result + '{} {}'.format(index,c['Commnet'])
            index = index + 1
        return result

    def SetProcsser(self, class_name):
        # 删除当前
        self.DelProcesserRunning()
        pkg = importlib.import_module('Assistant.'+str(class_name).lower())
        c = getattr(pkg, class_name, None)
        self.__processer_running = c('{}'.format(str(class_name)))
        assert isinstance(self.__processer_running,Processer)

    def DelProcesserRunning(self):
        if None != self.__processer_running:
            self.__processer_running.Init()
            del self.__processer_running

    def RecMsg(self,msg):
        self.__logger.debug('RecMsg from {}：{} '.format(msg['User']['NickName'],msg.text))
        if msg['User']['NickName'] == USR_NAME:
            if msg.text == CMD_CMDINIT:
                self.Init()
            elif self.__processer_running != None:
                self.Send(self.__processer_running.Receive(msg.text))
            elif msg.text == CMD_CMDLIST:
                # 用户请求命令列表
                self.Send(self.CmdList())
                # 将状态转入到等待用户选择要process
                self.__state = 'WaitCmdSelection'
            elif self.__state == 'WaitCmdSelection':
                config = Config(self.__name+'_config.json').config
                # 判断用户选择的process序号合法
                if int(msg.text) > 0 and int(msg.text) <= len(config):
                    self.SetProcsser(config[int(msg.text) - 1]['ClassName'])
                    self.Send('{} 启动'.format(config[int(msg.text)-1]['Commnet']))
                    # 清除状态
                    self.__state = None
            else:
                self.__logger.warning('DiscardMsg: {}'.format(msg.text))
                self.Send('对不起，我不懂你的意思。')
                return

    def Send(self,msg):
        if '' != msg and None != msg:
            self.__chenyouqian.send(str(msg))

    @property
    def chenyouqian(self):
        return self.__chenyouqian
    @chenyouqian.setter
    def chenyouqian(self,value):
        self.__chenyouqian = value

    def CreatLogger(self):
        # create logger with 'spam_application'
        logger = logging.getLogger('Assistant')
        logger.setLevel(logging.DEBUG)
        # create file handler which logs even debug messages
        fh = logging.FileHandler('assistant.log',mode='w',encoding='gbk')
        fh.setLevel(logging.DEBUG)
        # create console handler with a higher log level
        ch = logging.StreamHandler()
        ch.setLevel(logging.INFO)
        # create formatter and add it to the handlers
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
        fh.setFormatter(formatter)
        ch.setFormatter(formatter)
        # add the handlers to the logger
        logger.addHandler(fh)
        logger.addHandler(ch)
        return logger


if __name__ == '__main__':
    assistant = Assistant()

    @itchat.msg_register(TEXT)
    def receive_text_msg(msg):
        assistant.RecMsg(msg)

    assistant.Run()

