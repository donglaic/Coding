#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @File  : AddAccounts.py
# @Author: Donglai Chen
# @Date  : 2018/10/1
# @Desc  : 批量增加账户信息

import csv
from financialDbMbms import FinancialDb
import pprint

if __name__ == '__main__':
    db = FinancialDb('FinancialDb.db')
    db.addTableAccounts()
    with open("account.csv", "r") as csvFile:
        dict_reader = csv.DictReader(csvFile)
        for i in dict_reader:
            # pprint.pprint(i['资产类型'])
            type = i['资产类型']
            if type == '外汇':
                moneyType = 'DOLLAR'
            elif type == '股票':
                moneyType = '股票数'
            else:
                moneyType = 'RMB'
            count = i['金额']
            position = i['资产位置']
            startDate = i['起息日']
            endDate = i['结息日']
            interest = i['年化收益率']
            risk = i['风险']
            comments = i['备注']
            db.insertAccount(type,moneyType,count,position,startDate,endDate,interest,risk,comments)
    print('Accounts:')
    pprint.pprint(db.tableData('Accounts'))
    # print('\nTests:')
    # pprint.pprint(db.tableData('Tests'))
