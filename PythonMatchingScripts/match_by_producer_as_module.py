
def matching_by_producer(wo, engine, incoming_wine_names, compare_data, distinct_producer_name):
    
    import pandas as pd
    #import numpy as np
# =============================================================================
#     import os
#     import pyodbc
# =============================================================================
    
    from datetime import datetime
    startTime = datetime.now()

    print("Step 1: Reading in Datasets")
     
    # Assinging crucial variables from matching call to start process
    incomingwn = incoming_wine_names  
    wo_selected_columns=compare_data     
    wo_disinct_producer_name = distinct_producer_name
    
    get_producer_name = 'producer_name'
    get_wine_id = 'wine_id'
    get_potential_wine_match = 'clean_name'
    get_matching_power = 'matching_power'
    get_weight = 'weight'
    
    distinct_producer_name = wo_disinct_producer_name[get_producer_name].str.lower() #wo_producer_name
    incomingwn = incomingwn['clean_name'].tolist()
    
    datetime.now() - startTime
    #print("Finished at:" + str(datetime.now() - startTime))
    
    #####
    
    item_match_count = 0
    yes_list = []
    unmatched_wines = []
    matched = []
    full_row = []
    most_likely_prod = []
    full_row_df = pd.DataFrame(columns=['incoming_wine_name', 'wine_id', 'potential_wine_name', 'max_count','weight', 'matching_power', 'confidence_score'])
    unmatched_wines_df = pd.DataFrame(columns=['wine_name', 'matched_or_not_matched', 'not_matched_reason'])
    
    print("Step 2: Running Main Script")
    
    for i in range(len(incomingwn)): #
        print('--------')
        print(str(i+1)+ '.' + ' Matching: ' + incomingwn[i])
        
    
        inc_name_as_list_lowercase = [inc_name_as_list_lowercase.lower() for inc_name_as_list_lowercase in (str(incomingwn[i]).split(" "))]
        
        inc_name_as_list_lowercase_df_column_name = 'words'
        inc_name_as_list_lowercase_df = (pd.DataFrame(inc_name_as_list_lowercase).rename(columns={0: inc_name_as_list_lowercase_df_column_name}))
    
        for j in range(len(distinct_producer_name)):
            
            producer_words_column_name = 'producer_words'
            #producer_name_words_df_rename = (pd.DataFrame(distinct_producer_name).rename(columns={0: producer_words_column_name}))
            found_producer_name = distinct_producer_name.iloc[j]
    
            found_producer_name = str(found_producer_name)
            found_producer_name_after = found_producer_name.split(" ")
            found_producer_name_after = found_producer_name_after
            
            new_found_producer_list = list(found_producer_name_after)
            new_found_producer_list_df = pd.DataFrame(new_found_producer_list)
            
            
            new_found_producer_list_df_rename = new_found_producer_list_df.rename(columns={0: producer_words_column_name})
            producer_word_count = new_found_producer_list_df.count().iloc[0]
            result = pd.merge(inc_name_as_list_lowercase_df, new_found_producer_list_df_rename, left_on=inc_name_as_list_lowercase_df_column_name, right_on=producer_words_column_name, how='left')
            result_count = result.dropna()
                    
            # if all of the words of the producer name exists in the incomnig wine name, compare the weights and mp for best match
            # what if there are more than 1 producers / words that could relate to the incoming wine name - why only select the first one found that matches the count?
            if (producer_word_count == (result_count.count()).iloc[1]):
        
                #print('producer found: ' + found_producer_name)
                
                most_likely_prod_input = found_producer_name
                most_likely_prod.append(most_likely_prod_input)
    
                # get all wine name with the name of the found producer
                producer_list_df = found_producer_name
                get_potential_wine_names = (wo_selected_columns.loc[wo_selected_columns[get_producer_name].str.lower() == producer_list_df])[get_potential_wine_match]
    
                # loop to compare for each potential wine name
                for l in range(len(get_potential_wine_names)):
                    #print('inside l loop')
                    
                    result = pd.merge(inc_name_as_list_lowercase_df, new_found_producer_list_df_rename, left_on=inc_name_as_list_lowercase_df_column_name, right_on=producer_words_column_name, how='left')
                    nans = lambda result: result[result.isnull().any(axis=1)]
                    only_nans_df = pd.DataFrame((nans(result))[inc_name_as_list_lowercase_df_column_name])
                    
                    result2_icwn_name = 'incwn'
                    result2 = pd.merge(((pd.DataFrame(str(get_potential_wine_names.iloc[l]).split(" "))).rename(columns={0: result2_icwn_name})).apply(lambda x: x.astype(str).str.lower()), only_nans_df, left_on=result2_icwn_name, right_on=inc_name_as_list_lowercase_df_column_name, how='left')
                    item_match_count = (result2.count()).iloc[1]
                    
                    yes_list_input = {'incoming_wine_name': incomingwn[i],
                      'potential_wine_name': get_potential_wine_names.iloc[l],
                      'wine_id': (wo_selected_columns.loc[wo_selected_columns[get_potential_wine_match] == get_potential_wine_names.iloc[l]])[get_wine_id].iloc[0],
                      'matching_power': (wo_selected_columns.loc[wo_selected_columns[get_potential_wine_match] == get_potential_wine_names.iloc[l]])[get_matching_power].iloc[0],
                      'weight': (wo_selected_columns.loc[wo_selected_columns[get_potential_wine_match] == get_potential_wine_names.iloc[l]])[get_weight].iloc[0],
                      'max_count': (item_match_count+1),
                      'confidence_score': ((producer_word_count+item_match_count)/len(inc_name_as_list_lowercase)*100)}
                    
                    yes_list.append(yes_list_input)
                    matched_input = incomingwn[i]
                    matched.append(matched_input)
                    
                
            # Saved in separate list if potential wine name not found by producer name
            if ((j == len(distinct_producer_name) -1) # if the iteration for producer name has reached
               & (producer_word_count != (result_count.count()).iloc[1]) # if the producer count doesn't equal to the result_count (producer words matched)
               & (incomingwn[i] not in matched)): # and the current incoming wine name is not in the matched list
                 #print('true')
                 unmatched_wines_input = {'wine_name': incomingwn[i],
                                          'matched_or_not_matched': 'N',
                                          'not_matched_reason':'Producer not found'}
                 unmatched_wines.append(unmatched_wines_input)
                 
    if (unmatched_wines != []):
            unmatched_wines_df = pd.DataFrame(unmatched_wines)
    
    #####
        
    if (yes_list != []):
        yes_list_df = pd.DataFrame(yes_list)
        yes_list_df_max = yes_list_df.groupby(['incoming_wine_name'], as_index=False)['max_count'].max()
    
        #yes_list_df_max_full_data = []
        for m in range(len(yes_list_df_max)):
            full_row_data = yes_list_df.loc[((yes_list_df['incoming_wine_name'] == (yes_list_df_max.loc[m].iloc[0])) & (yes_list_df['max_count'] == (yes_list_df_max.loc[m].iloc[1])))].head(1)
            full_row_input = {'incoming_wine_name': full_row_data.iloc[0].loc['incoming_wine_name'],
                              'wine_id': full_row_data.iloc[0].loc['wine_id'],
                              'potential_wine_name': full_row_data.iloc[    0].loc['potential_wine_name'],
                              'max_count':  full_row_data.iloc[0].loc['max_count'],
                              'weight': full_row_data.iloc[0].loc['weight'],
                              'matching_power': full_row_data.iloc[0].loc['matching_power'],
                              'confidence_score':full_row_data.iloc[0].loc['confidence_score']}
            full_row.append(full_row_input)
        full_row_df = pd.DataFrame(full_row)
        
        
    full_row_df.to_sql('matched_irongate_300819', con=engine, if_exists='replace', index=False)
    unmatched_wines_df.to_sql('unmatched_irongate_300819', con=engine, if_exists='replace', index=False)
    

# =============================================================================
#     # write df's to excel
#     writer = pd.ExcelWriter(r'C:\Users\deepa\Desktop\results.xlsx', engine='xlsxwriter')
#     full_row_df.to_excel(writer, sheet_name='matched_data')
#     unmatched_wines_df.to_excel(writer, sheet_name='unmatched_wines')
#     
#     writer.save()
#     os.startfile(r'C:\Users\deepa\Desktop\results.xlsx')
#     
#     print("Finished at: " + str(datetime.now() - startTime))
#     print("Step 3: Exec Excel to View Results")
# =============================================================================
    
    # Print end time 
    datetime.now() - startTime
    print("Finished: " + str(datetime.now() - startTime))