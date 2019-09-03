

-- =============================================
-- Author:		Deepali
-- Create date: 27/08/2019
-- Description: SCRIPT - update incoming wine names after being matched (and manual analysis)
-- =============================================

-- manually update wine names if analysis says its correct 
declare @incoming_wine_name as varchar(500) = 'Taylor Fladgate Vintage'

-- check distinct wines that are to be matched
select incoming_wine_name, count(1) from Matching_DB.[dbo].[Irongate_data_20190730] 
where wo_id is null and not_matching is null 
group by incoming_wine_name
having count(1) > 3
order by count(1) desc

-- review wines that were matched by analysing the clean_name and incoming wine name
select DISTINCT ir.[Wine Name] as excel_wine_name -- excel_wine_name needs to be an updated version because some wines have been maatched
, ir.[Column 1] as wine_id 
, ird.wo_id
--, wm.wine_id
, wm.clean_name
, wm.producer_id
, ird.incoming_wine_name
, ird.not_matching
, ird.not_matching_reason
, ird.wine_producer_id
from matching_db.dbo.irongate_distinct_matched_data_240819 ir
join wine_matching wm on ir.[Column 1] = wm.wine_id
join matching_db.dbo.[Irongate_data_20190730] ird on ir.[Wine Name] = ird.incoming_wine_name
where ir.[Column 1] like '%[0-9]%'

-- updating the rows given the incmoing_wine_name is correctly matched with a clean_name (update - wine_id)
UPDATE Matching_db.dbo.irongate_data_20190730
set wo_id = ird.[Column 1]
FROM Matching_db.dbo.irongate_data_20190730 ir
join matching_db.dbo.irongate_distinct_matched_data_240819 ird on ir.incoming_wine_name = ird.[Wine Name]
join wine_matching wm on ird.[Column 1] = wm.wine_id
where ird.[Column 1] like '%[0-9]%'
and ir.incoming_wine_name like @incoming_wine_name

/*

-- correct wines and their number of rows that were updated
2 - 4  = correct - 50 rows
6 = correct - 53 rows
7-8 correct - 78 rows
10 = correct - 54 rows
12-13 correct - 64
17-19 correct - 116
20-22 correct - 73
23-24 correct - 58
25 correct - 48
26-27 correct - 65

total: 659 rows updated

*/







