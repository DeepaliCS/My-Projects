
-- =============================================
-- Author:		Deepali
-- Create date: 20/08/2019
-- Description: SCRIPT to remove duplicate wine names on the wines and weights table
-- =============================================

drop table if exists #alt
drop table if exists #alt_row
drop table if exists #ww_row
drop table if exists #ww
drop table if exists #ww_all 
drop table if exists #ww_all_row
drop table if exists #new_temp


select * from Matching_DB.dbo.wo_wines_and_weights_190819

select * into #alt from (
select clean_name as dupe_cnt
from wine_matching group by clean_name
having count(clean_name) > 1
--order by count(clean_name) desc
) alias

select * into #alt_row from
(select ROW_NUMBER() OVER(ORDER BY dupe_cnt ASC) AS Row, * from #alt) alias
-------------------------
--select * from #alt
--select * from #alt order by dupe_cnt

--select wine_id from wine_matching where clean_name like 
--(select top 1 dupe_cnt from #alt)

--select * from #alt_row

DECLARE @cnt INT = 1;
declare @cnt_total varchar(500)= (select count(*) from #alt)

WHILE @cnt < @cnt_total
BEGIN
	declare @clean_name2 as varchar(500)
	select top 1 @clean_name2 = dupe_cnt from #alt_row where Row = @cnt
	--select @clean_name2 as clean_name
	--select wine_id from wine_matching where clean_name like @clean_name2
   SET @cnt = @cnt + 1;
END;


-----------------

select * into #ww_all from (
select * from Matching_DB.dbo.wo_wines_and_weights_190819
) alias

--select * from #ww_all

select * into #ww_all_row from
(select ROW_NUMBER() OVER(ORDER BY clean_name ASC) AS Row, * from #ww_all
) alias

-- showing the repetitive wines
select * into #ww from (
select clean_name as clean_name
from Matching_DB.dbo.wo_wines_and_weights_190819 group by clean_name
having count(clean_name) > 1
--order by count(clean_name) desc
) alias

select * into #ww_row from
(select ROW_NUMBER() OVER(ORDER BY clean_name ASC) AS Row, * from #ww
) alias

select * from #ww_row

----------------

drop table if exists #save_all_ww
create table #save_all_ww (row int, wine_id varchar(500), clean_name varchar(1000), weight varchar(500), matching_power varchar(500));
DECLARE @cnt1 INT = 1;
declare @clean_name3 as varchar(500)
DECLARE @cnt_total1 INT = 10;
set @cnt_total1 = (select count(*) from #alt)
--set @cnt_total1 = 10

WHILE @cnt1 < @cnt_total1
BEGIN

	-- get the top clean name from ww_row
	select top 1 @clean_name3 = clean_name from #ww_row where Row = @cnt1
	--select @clean_name3 as clean_name

	-- save the wine_ids from wine_matching where it matched the clean name found from ww_row
	select * into #save_wine_ids from (
	select wine_id from wine_matching where clean_name like @clean_name3
	) alias
	
	-- getting the lowest wine_id from #we_all (to prepare to delete)
	--select * from #ww_all_row where wine_id = (select top 1 wine_id from #save_wine_ids order by wine_id asc)

	insert into #save_all_ww(row, wine_id, clean_name, weight, matching_power)
	select * from #ww_all_row where wine_id = (select top 1 wine_id from #save_wine_ids order by wine_id asc)

	drop table #save_wine_ids
   SET @cnt1 = @cnt1 + 1;
END;

select * from #save_all_ww

delete from #save_all_ww
where row not in (select max(row) from #save_all_ww group by clean_name)

--select * from #save_all_ww
--select wine_id, clean_name, weight, matching_power from #save_all_ww

--select * from #ww_all

delete wa from #ww_all wa
join #save_all_ww sa on wa.clean_name = sa.clean_name
where sa.clean_name = wa.clean_name

--select * from #ww_all

insert into #ww_all (wine_id, clean_name, weight, matching_power)
(select wine_id, clean_name, weight, matching_power from #save_all_ww)

--select * from #ww_all
select wine_id, clean_name, weight, matching_power from #ww_all

--select distinct clean_name from #ww_all

