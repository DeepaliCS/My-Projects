import pandas as pd
#import numpy as np
#import os
import pyodbc
from sqlalchemy import create_engine
from datetime import date
#from match_by_producer_as_module import matching_by_producer


# connection to sql server credentials
username = 'Access'
password = 'windows10'
server_con = 'deepali-hpenvyx\sqlexpress'
db_name = 'myEnvironment'
server_name_alchemy = 'SQL+Server'
server_name_pyodbc = 'SQL Server'

# connection urls - pyodbc & sqlalchemy
sql_connect = "Driver={};""Server={};""Database={};""uid={};pwd={}".format(server_name_pyodbc, server_con, db_name, username, password)
engine = create_engine("mssql+pyodbc://{}:{}@{}/{}?driver={}".format(username, password, server_con, db_name, server_name_alchemy))

dbc = pyodbc.connect(sql_connect)
query = pd.read_sql_query("select * from myEnvironment.dbo.FamilyDetail",dbc)

print(query)
today = date.today()
print('---')
print(today)



