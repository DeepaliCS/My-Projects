# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

import pandas as pd
import pyodbc
from sqlalchemy import create_engine
import os

# connection to sql server credentials
username = 'Access'
password = 'windows10'
server_con = 'deepali-hpenvyx\sqlexpress's
db_name = 'myEnvironment'
server_name_alchemy = 'SQL+Server'
server_name_pyodbc = 'SQL Server'

def connection_to_sql_server(username,password,server_con,db_name,server_name_alchemy,server_name_pyodbc):
    # connection urls - pyodbc & sqlalchemy
    sql_connect = "Driver={};""Server={};""Database={};""uid={};pwd={}".format(server_name_pyodbc, server_con, db_name, username, password)
    engine = create_engine("mssql+pyodbc://{}:{}@{}/{}?driver={}".format(username, password, server_con, db_name, server_name_alchemy))

    dbc = pyodbc.connect(sql_connect)
    
    query1 = pd.read_sql_query("select Name, Age from myEnvironment.dbo.FamilyDetail",dbc)

    return(query1, engine)

# ------------------
#test df
df, engine = connection_to_sql_server(username,password,server_con,db_name,server_name_alchemy,server_name_pyodbc)

# ------------------
def import_pd_to_sql(dataframe, engine, name):
    dataframe.to_sql(name, con=engine, if_exists='replace', index=False)
    
import_pd_to_sql(df, engine, 'name')

# ------------------
def export_to_xlsx(df, path, sheetName):
    
    path_format = 'r' + "{}".format(path)
    print(path_format)
    
    writer = pd.ExcelWriter(sheetName, engine='xlsxwriter')
    df.to_excel(writer, sheet_name=sheetName)
    writer.save()
    os.startfile(sheetName)

path_string = r'C:\Users\deepa\Desktop\excelDump\results.xlsx'
export_to_xlsx(df, path_string, 'Sheet2')
# excel App location: C:\ProgramData\Microsoft\Windows\Start Menu\Programs



