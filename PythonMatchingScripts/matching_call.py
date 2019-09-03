import pandas as pd
#import numpy as np
#import os
import pyodbc
from sqlalchemy import create_engine
from datetime import datetime
from match_by_producer_as_module import matching_by_producer


# connection to sql server credentials
username = ''
password = ''
server_con = '172.23.252.130'
db_name = 'Matching_DB'
server_name_alchemy = 'SQL+Server'
server_name_pyodbc = 'SQL Server'

# connection urls - pyodbc & sqlalchemy
sql_connect = "Driver={};""Server=tcp:{};""Database={};""uid={};pwd={}".format(server_name_pyodbc, server_con, db_name, username, password)
engine = create_engine("mssql+pyodbc://{}:{}@{}/{}?driver={}".format(username, password, server_con, db_name, server_name_alchemy))

# table and column names required (for incoming wine names)
incoming_wine_table_name = 'name_list'
incoming_wine_column_name = 'top 1 clean_name'
schema = 'dbo'

# table and column names required (for compare data)
compare_data_table_name = 'sql_python_full_wine_matching'
wine_id = 'wine_id'
producer_name = 'producer_name'
clean_name = 'clean_name'
weight = 'weight'
matching_power = 'matching_power'


# select statement calls
incoming_wine_names = "select {} from {}.{}.{}".format(incoming_wine_column_name, db_name, schema, incoming_wine_table_name)
compare_data = "select {}, {}, {}, {}, {} from {}.{}.{}".format(wine_id, producer_name, clean_name, weight, matching_power, db_name, schema, compare_data_table_name)
distinct_producer_name = "select {} from {}.{}.{}".format(producer_name, db_name, schema, compare_data_table_name)



# function: sql connection
def check_sql_queries(sql_connect, engine, incoming_wine_names, compare_data, distinct_producer_name):
    print('CALL TO: check_sql_queries.fn')
    try:
        
        wo = pyodbc.connect(sql_connect)
        read_incoming_wine_names = pd.read_sql_query(incoming_wine_names,wo)
        read_compare_data = pd.read_sql_query(compare_data, wo)
        read_distinct_producer_name = pd.read_sql_query(distinct_producer_name, wo)
        
        count_read_incoming_wine_names = len(read_incoming_wine_names)
        count_read_compare_data = len(read_compare_data)
        count_read_distinct_producer_name = len(read_distinct_producer_name)
        
        print('pyodbc connection made to SQL Server')
        print('SQL alchemy engine: ' +str(engine))
        print('')
        print('Starting Matching Process ..')
        print('Total number of incoming Wine Names to Match: ' + str(count_read_incoming_wine_names))
        print('Total number of rows in ' + compare_data_table_name + ' is ' + str(count_read_compare_data))
        print('Distinct number of producers in ' + compare_data_table_name + ' is ' + str(count_read_distinct_producer_name))
        startTime = datetime.now()
        print('Start Date/Time: ' + str(startTime))
        
        # write call to start matching process here
        
        matching_by_producer(wo, engine, read_incoming_wine_names, read_compare_data, read_distinct_producer_name)
        
    except:
# =============================================================================
#         print("Unsuccessfull connection for: " + sql_connect)
#         print('or')
#         print("Unsuccessfull connection for: " + str(engine))
# =============================================================================
        
        print('Unsuccessful connection...')
      
    
        
# function call: to check connection
check_sql_queries(sql_connect, engine, incoming_wine_names, compare_data, distinct_producer_name)
    







