

-- =============================================
-- Author:		Deepali
-- Create date: 09/08/2019
-- Description: SCRIPT to test matches using producer name
--				Check if the DISTINCT of producer name is enough rows to find the match, or ALL of the rows 
-- =============================================

-- select a random clean_name to test from this list
--select top 100 clean_name from wine_matching

-- Testing for wine name that is already in the database - to see if its found with distinct lists
declare @set_test_name as varchar(500) = 'Albert Bichot Long Depaquit Chablis Grand Cru Blanchots'
--'Vinedos Alonso del Yerro Ribera del Duero DO'


-- Finding the potential match
--select * from wine_matching where
--clean_name like '%Albert%'
--or clean_name like '%Bichot%'
--or clean_name like '%Long%'
--or clean_name like '%Depaquit%'
--or clean_name like '%Chablis%'
--or clean_name like '%Grand%'
--or clean_name like '%Cru%'
--or clean_name like '%Blanchots%'

-- This is probably the best match for the inc_test_name (this wine should be checked if its in the distinct lists)
declare @pot_wine_name as varchar(500) = 'Albert Bichot Domaine Long-Depaquit Blanchots Chablis Grand Cru'

select @set_test_name as name

drop table if exists #list_to_compare
drop table if exists #temp_split
select * into #temp_split from fnSplitStringNew(@set_test_name, ' ')

drop table if exists #temp_split_row
select * into #temp_split_row from (select ROW_NUMBER() OVER(ORDER BY Item ASC) AS Row, * from #temp_split) alias
--select * from #temp_split_row
DECLARE @cnt INT = 0;
DECLARE @cnt_total INT = (select count(*) from #temp_split)
create table #list_to_compare(wine_id int, producer_id int, producer_name varchar(500), clean_name varchar(500))

--producer name check loop - get all potential matches given the producer name is found in the name
WHILE @cnt < @cnt_total
BEGIN
   
   declare @item as varchar(500) = (select Item from #temp_split_row where Row like @cnt)

   --select * from wine_matching where producer_name like '%' + @item + '%'

   insert into #list_to_compare(wine_id, producer_id, producer_name, clean_name)
   select wine_id, producer_id, producer_name, clean_name from wine_matching where producer_name like '%' + @item + '%'
   
   SET @cnt = @cnt + 1;

END;

select * from #list_to_compare order by clean_name asc
--select distinct wine_id from #list_to_compare

--select * from wine_matching where producer_name like (
--select distinct producer_name from #list_to_compare
--)

-- getting the distinct wine names (using wine_id) so that duplicates are removed
drop table if exists #list_to_compare_distinct
select * into #list_to_compare_distinct from (
select distinct wine_id from #list_to_compare
) alias

--select * from #list_to_compare_distinct ld
--join wine_matching wm on ld.wine_id = wm.wine_id
----214 rows

-- Finding a match using the DISTINCT producer name (THIS ONE)
--select distinct producer_name from #list_to_compare_distinct ld
--join wine_matching wm on ld.wine_id = wm.wine_id
--where clean_name like @pot_wine_name
--order by producer_name asc
--76 rows

--select distinct ld.wine_id from #list_to_compare_distinct ld
--join wine_matching wm on ld.wine_id = wm.wine_id
--214 rows

-- Finding a match using ALL rows found from producer name check loop above (THIS ONE)
--select * from #list_to_compare_distinct ld
--join wine_matching wm on ld.wine_id = wm.wine_id
--where clean_name like @pot_wine_name
--order by clean_name asc

--214 rows

--select * from wine_matching where clean_name like @set_test_name