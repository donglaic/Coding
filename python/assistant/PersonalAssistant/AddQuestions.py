#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @File  : AddQuestions.py
# @Author: Donglai Chen
# @Date  : 2018/9/23
# @Desc  : 批量增加问题

import csv
from questionsDbMbms import QuestionsDb
import pprint

if __name__ == '__main__':
    db = QuestionsDb('XFQuestionsLib.db')
    # db.addTables()
    with open("quesInput.csv", "r") as csvFile:
        dict_reader = csv.DictReader(csvFile)
        for i in dict_reader:
            if '' != i['question'] and '' != i['answer']:
                print('input question {} answer {}'.format(i['question'],i['answer']))
                db.insertAQues(i['question'],i['answer'])
    print('Questions:')
    pprint.pprint(db.tableData('Questions'))
    # print('\nTests:')
    # pprint.pprint(db.tableData('Tests'))
