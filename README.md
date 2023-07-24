# Zadania z hurtowni danych

## Zadanie 1
Żeby uruchomić aplikację z zadania 1, należy zbudować projekt w folderze *task 1*  z użyciem Maven. Następnie trzeba uruchomić powstały plik .jar podając jeden command-line argument. Ten argument to ścieżka do pliku CSV, który zostanie utworzony po udanym uruchomieniu aplikacji.

Program najpierw wczytuje plik JSON i mapuje go na obiekty Status z pomocą biblioteki Gson i mojej klasy JSONParser. Następnie statusy są filtrowane i sortowane z użyciem komparatora StatusComparator. Na koniec obiekt klasy CSVParser mapuje listę statusów na plik CSV i zapisuje go w lokacji podanej przez użytkownika. Jeśli program napotka jakieś błędy, zwróci exit code 10 i wyświetli powód błędu w konsoli.

## Zadanie 2
W zadaniu drugim korzystam z CTE oraz partycjonowania. Użyłem partycji, żeby uniknąć skomplikowanego grupowania. Głównym zastosowaniem partycji w tym zadaniu jest zdobycie najnowszych rekordów dla każdego klienta. Poza tym partycje użyte są do policzenia łącznej liczby kontaktów dla każdego klienta.

## Zadanie 3

W zadaniu trzecim najpierw tworzę CTE rekordy_z_datami, które po prostu zawiera dodatkową kolumnę z datą. W głównej części zapytania korzystam z tego CTE, żeby po kolei definiować każdą wymaganą kolumnę. Mimo że nie ma takiej sytuacji w podanym zbiorze danych, trzeba wziąć pod uwagę sytuację, kiedy danego dnia ten sam klient dwukrotnie dostał telefon. Przez to znowu musiałem użyć podzapytań oraz partycji, żeby zawsze dla danej daty i danego klienta mieć tylko najnowszy rekord. Zapytanie ogólnie wygląda na skomplikowane, ale jest podzielone na niezależne bloki, które same w sobie są dosyć czytelne. Przykładowo w ten sposób zdobywam kolumnę sukcesy:

```
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
) as sukcesy
```

W dwóch ostatnich kolumnach mam dodatkowe podzapytanie, które jest konieczne, żeby zdeterminować czy dany klient miał jakikolwiek rekord z określonym statusem (zainteresowant lub niezainteresowany w zależności od przypadku) przed najnowszym rekordem z danego dnia. 



## Zadanie 4
W zadaniu czwartym użyłem rekomendowanych bloczków i połączyłem je w odpowiedniej kolejności. Poza tym tylko wypełniłem mapowanie oraz zdefiniowałem po jakich kolumnach będzie się odbywało filtrowanie i sortowanie.  
