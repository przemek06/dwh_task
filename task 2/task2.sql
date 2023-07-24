with wszystkie_rekordy as (
	select klient_id, "status",
	count(*) over (
		partition by klient_id
	) as liczba_kontaktow,
	row_number() over (
		partition by klient_id
		order by kontakt_ts desc

	) as kolejnosc
	from statuses
)
select klient_id, "status"
from wszystkie_rekordy
where liczba_kontaktow >= 3
and kolejnosc = 1;