1. Make sure matching_call and match_by_producer_as_module.py is in the same folder
2. Add credentials on matching_call.py to connect to SQL server
3. Make sure the right table for incoming wine name and compare table is set
4. Run matching
5. Results should be in the matching_db table, (whatever it is named in from the match_by_producer_as_module.py table)
	- To find out the name of the table, search 'to_sql' on the match_by_producer_as_module.py table 
	- WARNING: The script is designed to overwrite the table on sql server if the procedure is run more than once with the same table name.
