
-- =============================================
-- Author:		Deepali
-- Create date: 20/08/2019
-- Description: SCRIPT to calcualte weights and matching power (incrementally) from wine matching
-- =============================================

drop table if exists #tmp_wine_matching
drop table if exists #tmp_wine_matching_row
drop table if exists #tmp_wine_matching_skip

declare @skip_v as int = 20000 --round 5
declare @select_top as int = 3589 ----
declare @select_all as int = 23589

--select count(*) from wine_matching 
-- RESULT: 23589

select * into #tmp_wine_matching_skip from (
SELECT TOP(@select_top) clean_name FROM wine_matching
WHERE clean_name not in (SELECT TOP(@skip_v) clean_name From wine_matching order by clean_name)
order by clean_name
) alias

select * from #tmp_wine_matching_skip order by clean_name
select top (@select_all) clean_name from wine_matching order by clean_name

-----------

select * into #tmp_wine_matching from 
(select top (@select_top) clean_name from #tmp_wine_matching_skip order by clean_name) alias

select * into #tmp_wine_matching_row from
(select ROW_NUMBER() OVER(ORDER BY clean_name ASC) AS row, * from #tmp_wine_matching) alias

--select * from #tmp_wine_matching_row

declare @count_of_item_outer as int = (select count(clean_name) from #tmp_wine_matching_row)


---select * from #tmp_wine_matching_row

declare @count_of_item_start as int = 1
declare @count_of_weights as int = 0

declare @count_of_item_start_outer as int = 1

drop table if exists #wines_and_weights
create table #wines_and_weights (wine_id int, clean_name varchar(1000), weight int);
	

WHILE @count_of_item_start_outer < @count_of_item_outer+1
	BEGIN

	drop table if exists #new_split
	drop table if exists #new_split_row
	--declare @count_of_item_start int  = 1

		declare @current_item as varchar(500)
		select top 1 @current_item = clean_name from #tmp_wine_matching_row where row like @count_of_item_start_outer
		
		--print(@current_item)

		--select @current_item as current_item

		declare @wine_id as int = (select top 1 wine_id from wine_matching where clean_name like @current_item)
		
		--declare @split_potential_wine_match as varchar(500)
		select * into #new_split from fnSplitStringNew(@current_item, ' ')

		--select * from #new_split
		
		--print(@current_item)
		
		declare @count_of_item_new_split as int = (select count(*) from #new_split)


		select * into #new_split_row from
		(select ROW_NUMBER() OVER(ORDER BY item ASC) AS row, * from #new_split) alias
				

			WHILE @count_of_item_start < @count_of_item_new_split+1
			BEGIN

				declare @split_potential_wine_match as varchar(500)
				select top 1 @split_potential_wine_match = item from #new_split_row where Row = @count_of_item_start


				declare @current_item_count as int
				select @current_item_count = count(clean_name) from wine_matching where ' '+clean_name+' ' like '% ' +@split_potential_wine_match+ ' %'

				-- adding up the number of weights for each item (as iteration goes along)
				set @count_of_weights = @count_of_weights + @current_item_count

				--print(@split_potential_wine_match + ' ' + cast(@count_of_weights as varchar(500)))

				--select @count_of_item_start as count_of_item_start, @split_potential_wine_match as split_potential_wine_match, @current_item_count as current_item_count
				
				--set @count_of_item_start = @count_of_item_start +1

				--select @split_potential_wine_match as split_potential_wine_match, @count_of_weights as count_of_weights
				SET @current_item_count = 0
									
				--select 'count of item starttt' as count_of_item_start


				set @current_item_count = 0
				set @count_of_item_start = @count_of_item_start + 1
				
			END
			print('----')

			insert into #wines_and_weights(wine_id, clean_name, weight)
			values(@wine_id, @current_item, @count_of_weights)

			--select @wine_id as wine_id, @current_item as current_item, @count_of_weights as count_of_weights
			--print(@split_potential_wine_match + ' ' + cast(@count_of_weights as varchar(500)))

			set @count_of_weights = 0
			set @count_of_item_start = 1

			drop table if exists #new_split_row

			set @count_of_item_start_outer = @count_of_item_start_outer +1
END


select * from #wines_and_weights order by clean_name

--select count(*) from #wines_and_weights

