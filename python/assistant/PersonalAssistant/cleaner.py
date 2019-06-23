#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @File  : cleaner.py
# @Author: Donglai Chen
# @Date  : 2018/9/27
# @Desc  : db cleaner


from questionsDbMbms import QuestionsDb
import logging

class Cleaner(object):
    def __init__(self):
        self.initLogger()
        self.db = QuestionsDb('XFQuestionsLib.db')

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

    def deleteAQuestion(self,ques_id):
        self.logger.info('delete question id {}!'.format(ques_id))
        self.db.deleteAQues(ques_id)

    def quesRecord(self,ques_id):
        return self.db.questionRecords(ques_id)

if __name__ == '__main__':
    clr = Cleaner()
    clr.deleteAQuestion(190)
