#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @File  : assistant.py
# @Author: Donglai Chen
# @Date  : 2018/9/22
# @Desc  : 私人小助手

import threading
import itchat
from pprint import pprint
from examiner import Examiner
from itchat.content import *
import schedule
import time
import logging
from secretary import Secretary
from cleaner import Cleaner
from questionsDbMbms import QuestionsDb

class Assistant(object):
    def __init__(self):
        self.initLogger()
        self.examiner = Examiner()
        self.sec = Secretary()
        self.clr = Cleaner()
        self.questionsDb = QuestionsDb('XFQuestionsLib.db')
        self.login()
        self.init()

    def init(self):
        self.proc = 'ProcNull'
        self.examiner.init()
        self.sec.clear()
        self.sendYq('System init!')

    def login(self):
        itchat.auto_login()
        #itchat.auto_login(hotReload=True)
        self.yq = itchat.search_friends(name='陈有钱')[0]

    def initLogger(self):
        self.logger = logging.getLogger('Assistant')
        self.logger.setLevel(level=logging.INFO)
        handler = logging.FileHandler("log.txt",encoding='utf-8')
        handler.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(filename)s - %(levelname)s - %(funcName)s - %(message)s')
        handler.setFormatter(formatter)
        console = logging.StreamHandler()
        console.setLevel(logging.INFO)
        formatter = logging.Formatter('%(asctime)s - %(name)s - %(filename)s - %(levelname)s - %(funcName)s - %(message)s')
        console.setFormatter(formatter)
        self.logger.addHandler(console)
        self.logger.addHandler(handler)

    def sendYq(self, msg):
        if str(msg) != '':
            self.logger.info('sendYq: '+str(msg))
            self.yq.send(str(msg))

    def command(self,cmd):
        self.logger.info('rec cmd: '+cmd)
        if cmd == 'exam':
            self.proc = 'ProcExam' #开始测试
            self.sendYq('Begin exam：')
            self.sendYq(self.examiner.ask())
        elif cmd == 'createQuestions':
            self.sendYq('Begin creating questions')
            self.proc = 'ProcCreateQuestions' #编辑问题
        elif cmd == 'init':
            self.init()
        elif cmd.startswith('deleteQuestions '):
            self.clr.deleteAQuestion(cmd.replace('deleteQuestions ',''))
            self.sendYq('question deleted.')
        elif cmd.startswith('questionRecord '):
            self.sendYq(self.clr.quesRecord(cmd.replace('questionRecord ','')))
        elif cmd == 'questionCount':
            self.sendYq(self.questionsDb.questionCount())
        else:
            self.logger.error('Sorry, I don`t known the meaning of: '+ cmd)
            self.yq.send('Sorry, I don`t known the meaning of: ' + cmd)

    def exam(self):
        q = self.examiner.ask()
        if 'Examiner: There is NO question to ask!' != q:
            self.sendYq(self.examiner.ask())
            self.status = 'EXAM'
        else:
            self.status = 'NULL'

    def process(self,msg):
        if self.proc == 'ProcExam':
            self.sendYq(self.examiner.recMsg(msg))
        elif self.proc == 'ProcCreateQuestions':
            self.sendYq(self.sec.recMsg(msg))

    def receiveMsg(self,msg):
        self.logger.info('Received {} from {}: '.format(msg.text,msg['User']['NickName']))
        if msg['User']['NickName'] == '陈有钱':
            if msg.text.startswith('cmd:'):
                self.command(msg.text.replace('cmd:', ''))
            else:
                self.process(msg.text)

if __name__ == '__main__':
    ass = Assistant()

    @itchat.msg_register(TEXT)
    def receive_text_msg(msg):
        ass.receiveMsg(msg)

    itchat.run()
