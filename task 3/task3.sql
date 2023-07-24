with rekordy_z_datami as (
	select s.*, convert(date, s.kontakt_ts) as "data"
	from statuses s
) 
select "data", 
(select count(*) from
	(
	select *,
	row_number() over (
		partition by klient_id
		order by kontakt_ts desc

	) as kolejnosc
	from rekordy_z_datami rzd2 
	where rzd1."data" = rzd2."data"
	) as rekordy_z_kolejnoscia
where kolejnosc = 1
and "status" = 'zainteresowany'
) as sukcesy,
(select count(*) from
	(
	select *,
	row_number() over (
		partition by klient_id
		order by kontakt_ts desc

	) as kolejnosc
	from rekordy_z_datami rzd2 
	where rzd1."data" = rzd2."data"
	) as rekordy_z_kolejnoscia
where kolejnosc = 1
and "status" = 'niezainteresowany' 
) as utraty,
(select count(*) from
	(
	select *,
	row_number() over (
		partition by klient_id
		order by kontakt_ts desc

	) as kolejnosc
	from rekordy_z_datami rzd2 
	where rzd1."data" = rzd2."data"
	) as rekordy_z_kolejnoscia
where kolejnosc = 1
and "status" in ('poczta_głosowa', 'nie_ma_w_domu')
) as do_ponowienia,
(select count(*) from
	(
	select *,
	row_number() over (
		partition by klient_id
		order by kontakt_ts desc

	) as kolejnosc
	from rekordy_z_datami rzd2 
	where rzd1."data" = rzd2."data"
	and exists (
		select * 
		from rekordy_z_datami rzd3 
		where rzd3.kontakt_ts < rzd2.kontakt_ts 
		and rzd3."status" = 'niezainteresowany' 
		and rzd3.klient_id = rzd2.klient_id
		)
	) as rekordy_z_kolejnoscia
where kolejnosc = 1
and "status" = 'zainteresowany'
) as niezainteresowani_sukcesy,
(select count(*) from
	(
	select *,
	row_number() over (
		partition by klient_id
		order by kontakt_ts desc

	) as kolejnosc
	from rekordy_z_datami rzd2 
	where rzd1."data" = rzd2."data"
	and exists (
		select * 
		from rekordy_z_datami rzd3 
		where rzd3.kontakt_ts < rzd2.kontakt_ts 
		and rzd3."status" = 'zainteresowany' 
		and rzd3.klient_id = rzd2.klient_id
		)
	) as rekordy_z_kolejnoscia
where kolejnosc = 1
and "status" = 'niezainteresowany'
) as zainteresowani_utraty
from rekordy_z_datami rzd1
group by rzd1."data";
