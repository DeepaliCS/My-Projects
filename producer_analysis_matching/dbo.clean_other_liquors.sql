

-- =============================================
-- Author:      Deepali
-- Create date: 2019-07-25
-- Description: Prepare NJ Wine Data for matching (Script)
-- =============================================

  select * from Matching_DB.dbo.domaine_storage_25072019

  delete from Matching_DB.dbo.domaine_storage_25072019 where producer like ''

-- add new columns in prep for matching processes
alter table Matching_DB.dbo.domaine_storage_25072019 add incoming_wine_name varchar(1000);
alter table Matching_DB.dbo.domaine_storage_25072019 add wo_id varchar(500);
alter table Matching_DB.dbo.domaine_storage_25072019 add not_matching varchar(500);
alter table Matching_DB.dbo.domaine_storage_25072019 add not_matching_reason varchar(500);
alter table Matching_DB.dbo.domaine_storage_25072019 add clean_name varchar(500);

update Matching_DB.dbo.domaine_storage_25072019 
set incoming_wine_name = Producer + ' ' +  WineName + ' ' + Designation;

-- update to remove if the producer is the name and WineName and if Designation includes 'none'
update Matching_DB.dbo.domaine_storage_25072019
set incoming_wine_name = case 
when WineName like producer + '%' then WineName else Producer + ' ' +  WineName 
end 
+ case when Designation <> 'none' then ' ' + Designation else ''
end

update Matching_DB.dbo.domaine_storage_25072019 
set not_matching_reason = 'Spirit, Empty, or Not Wine'
where
	(WineName like '% gin %'
	or WineName like '%whisky%'
	or WineName like '%cognac%'
	or WineName like '% rum %'
	or WineName like '%vodka%'
	--or WineName like '%brory%'
	or WineName like '%empty%'
	or WineName like '% tequila %'
	or WineName like '% beer %'
	or WineName like '%Unreadable%'
	or WineName like '%Cider%'
	or WineName like '%olive%'
	or WineName like '%none%'
)

update Matching_DB.dbo.domaine_storage_25072019 set not_matching_reason = case when WineName like '% gin %' then 'gin'
									 when WineName like '%whisky%' then 'whisky'
									 when WineName like '%cognac%' then 'cognac'
									 when WineName like '% rum %' then 'rum'
									 when WineName like '%vodka%' then 'vodka'
									 when WineName like '%empty%' then 'empty'
									 when WineName like '% tequila %' then 'tequila'
									 when WineName like '% beer %' then 'beer'
									 when WineName like '% Unreadable %' then 'Unreadable'
									 when WineName like '%Cider%' then 'Cider'
									 when WineName like '%olive%' then 'olive'
									 when WineName like '%none%' then 'none'
								end
-- Show all results
select * from Matching_DB.dbo.domaine_storage_25072019

-- Show without blank lines, spirits, or empty/olive oil/ none type bottles
select * from Matching_DB.dbo.domaine_storage_25072019
where not_matching_reason is not null

--select incoming_wine_name, count(1) from #temp GROUP BY incoming_wine_name
--order by count(1) desc
