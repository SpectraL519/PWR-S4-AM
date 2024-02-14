<script type="text/javascript"
  src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.0/MathJax.js?config=TeX-AMS_CHTML">
</script>
<script type="text/x-mathjax-config">
  MathJax.Hub.Config({
    tex2jax: {
      inlineMath: [['$','$'], ['\\(','\\)']],
      processEscapes: true},
      jax: ["input/TeX","input/MathML","input/AsciiMath","output/CommonHTML"],
      extensions: ["tex2jax.js","mml2jax.js","asciimath2jax.js","MathMenu.js","MathZoom.js","AssistiveMML.js", "[Contrib]/a11y/accessibility-menu.js"],
      TeX: {
      extensions: ["AMSmath.js","AMSsymbols.js","noErrors.js","noUndefined.js"],
      equationNumbers: {
      autoNumber: "AMS"
      }
    }
  });
</script>

# Semestr 4 - Aplikacje Mobilne

<br />

### Lista nr 0

1. Zainstaluj język Kotlin oraz uruchom i przeanalizuj kilka przykładów ze strony link.

```
$ sudo pacman -S kotlin
$ cat << EOF >hello.kt
fun main(args: Array<String>) {
    println("Hello, World!")
}
EOF
$ kotlinc hello.kt -include-runtime -d hello.jar
$ java -jar hello.jar

# uruchomienie REPL
$ kotlinc-jvm
Welcome to Kotlin version 1.8.10 (JRE 11.0.18+10)
Type :help for help, :quit for quit
>>> 2+2
res0: kotlin.Int = 4
>>>
```

2. Zainstaluj Android Studio. Uruchom przykładowe "Hello World" w emulatorze i/lub na urządzeniu z Androidem (telefon, tablet).

3. Zainstaluj scrcpy albo inny program do udostępniania ekranu urządzenia z Androidem. W przypadku korzystania TYLKO z emulatora pokaż okno emulatora dla wybranego urządzenia.

<br />
<br />
<br />

### Lista nr 1

1. (2pt) Przepisz i uruchom prostą grę napisaną w Kotlinie na wykładzie pierwszym. Zrób dodatkowo taką samą grę w Javie.

2. (3pt) W tym zadaniu jest duża możliwość wyboru. Proszę napisać dowolną aplikację w systemie Android w Javie i Kotlinie z następującymi ograniczeniami:

   * aplikacja zawiera co najmniej cztery widgety na ekranie co najmniej dwóch rodzajów np. TextView, EditView
   * aplikacja powinna odpowiadać na co najmniej dwa zdarzenia np. dwa przyciski
   * aplikacja powinna zmieniać dowolny aspekt wyglądu np. textview ma większe i grubsze czcionki
   * aplikacja powinna dostosowywać się do wielkość ekranu (różne telefony i rozdzielczości)
   * nie może to być aplikacja z zadania 1

    Sugestie:
    * Zgadywanie liczby. Komputer losuje liczbę np. 1 do 100, a użytkownik próbuje zgadywać liczbę z podpowiedziami komputera, czy liczba jest większa czy mniejsza od aktualnie podanej.
    * Zgadywanie słowa. Komputer podaje słowo (na początku zasłonięte), a użytkownik próbuje zgadnąć jakie to słowo w najmniejszej liczbę odsłanianych liter.
    * Papier-kamień-nożyczki. Gra z komputerem (który wybiera losowo). Śledź rozgrywkę człowieka i komputera podając kto wygrał ile partii.

<br />
<br />
<br />

### Lista nr 2

> Celem tej listy jest opanowanie bardziej zaawansowanych układów graficznych (niż te z poprzedniej listy) oraz wykorzystanie większej liczby widgetów oraz widoków w interfejsie graficznym użytkownika. Od tej listy wszystkie aplikacje piszemy w języku Kotlin.

1. (4pt) Napisz klasyczną grę Saper (Minesweeper), gdzie wypełniamy siatkę 9x9 polami które na początku są zakryte następnie odkrywamy je w taki sposób aby nie natrafić na minę. Na każdym odkrytym polu dodatkowo wypisywana jest liczba odkrytych min, które stykają się z danym polem. Możemy też zabezpieczyć dane pole flagą. Jeśli gracz odkryje wszystkie pola i zaznaczy flagami wszystkie miny, to wygrywa. Wykorzystaj odpowiedni Layout do rozłożenia przycisków i zrób tak aby przyciski wypełniały odpowiednio ekran oraz czcionka była odpowiednio duża (możesz to przetestować na emulatorze dla różnych rozdzielczości ekranów).

2. (3pt) Napisz aplikację "Wisielec", która wyświetla po kolei obrazek wisielca oraz słowo które gracz próbuje zgadnąć. Słowo wybierane jest losowo z dostępnego słownika. Oczywiście cały czas wyświetlane jest słowo z prawidłowo zgadniętymi literami np. dla słowa komputer jeśli gracz zgadł prawidłowo litery o, m i e, słowo będzie wyglądało mniej więcej tak ?om???e?. Do utworzenia obrazków wykorzystaj np. program GIMP nazwij je odpowiednio wisielec0.png, wisielec1.png i tak dalej. Do wyświetlania obrazków wykorzystaj ImageView. Do przechowywania słownika wykorzystaj plik strings.xml.
   
    ```
    <string-array name="words">
        <item>komputer</item>
        <item>zgadywanka</item>
        ...
    </string-array>
    ```
    Jak pobierać zasoby zobacz link. Słownik możesz wypełnić dowolnymi słowami np. ściągniętymi z internetu przez prosty skrypt.

<br />
<br />
<br />

### Lista nr 3

> Uwagi: (doprecyzowujące):
> 
> Zadanie pierwsze liczone jest za 5pt jeśli zostanie wykonane w wersji podstawowej. Dodatkowe punkty są za pomysły rozszerzające aplikację np. wykorzystanie biblioteki ciekawie rozbudowującej aplikacje (patrz wykład 5). Proponuje zastosować kilka takich pomysłów.

1. (5-10pt) Napisz aplikacje typu "Calendar", czyli kalendarz i listę wydarzeń. Proszę wzorować się na innych aplikacjach tego typu i zobaczyć jakie mają funkcjonalności. Tutaj naprawdę niebanalnych pomysłów jest dużo!. Minimalne wymagania to możliwość przeglądania kalendarza i dodawania wydarzeń w danym dniu (oraz usuwania). W aplikacji należy wykorzystać RecyclerView i co najmniej dwie aktywności z komunikacją przez intencje. Zrób też tak, aby aplikacja pamiętała kalendarz i listę wydarzeń po rotacji ekranu, czyli po zmianie z portrait na landscape i odwrotnie. Tutaj należy wykorzystać tylko onSaveInstanceState i onRestoreInstanceState. Uwaga: W tym zadaniu aplikacja powinna być w pełni funkcjonalna. Uwzględniana będzie też wygoda i użyteczność aplikacji.

2. *(5pt) Wykorzystaj lokalną bazę danych do przechowywania danych aplikacji przez użycie Room'a czyli warstwę abstrakcji na SQLite.

3. *(5pt) Zrób w aplikacji powiadomienie użytkownika, że zbliża się termin wydarzenia.

<br />
<br />
<br />

### Lista nr 4

> Celem tej listy jest napisanie aplikacji, która ma wiele aktywności, prawidłowo obsługuje stany aplikacji onPause, onStop, itp. przechowuje dane, poprawnie wykorzystuje intencje (intent) oraz wykorzytuje fragmenty.

1. (5pt) Napisz prostą grę w kółko i krzyżyk wykorzystującą Jetpack Compose. Aplikacja powinna mieć możliwość wyboru rozmiaru rozgrywki w zakresie co najmniej od 3x3 do 20x20. Wystarczy sama podstawowa rozgrywka między dwoma użytkownikami reguły też mogą być różne. W tym zadaniu głównym celem jest wykorzystanie Jetpacka do generowania i obsługi UI z kodu. Wykorzystaj też ViewModel do przechowywania i zarządzania danymi związanymi z interfejsem użytkownika w sposób "lifecycle-aware".

2. (5pt) Napisz aplikacje (galeria) przechowującą zdjęcia np. ludzi, krajobrazów, zwierząt, ... i każde zdjęcie dodatkowo zawiera krótki opis. Po uruchomieniu aplikacji, na początku pokazuje ona dostępne zdjęcia. Użytkownik może wybrać dowolną pozycję, aby zobaczyć większe zdjęcie i opis. Na ekranie dodatkowo, mamy możliwość ocenienia zdjęcia przez np. "gwiazdki" (zobacz RatingBar ). Proszę pamiętać, że na tym etapie poznania Androida nie ma to być w pełni funkcjonalna aplikacja np. nie potrzeba tworzyć kont dla użytkowników lub nie potrzeba przechowywać nowych zdjęć. Aplikacja powinna natomiast obsługiwać:
    * co najmniej dwie aktywności
    * przekazywać informacje z jednej aktywności do drugiej wykorzystując intencje (tutaj proszę przemyśleć jak to zrobić, dane wysyłane w intencji mają ograniczony rozmiar)
    * druga aktywność powinna wracać informacje do pierwszej o liczbie gwiazdek, po czym w pierwszej aktywności obrazki zostają odpowiednio posortowane po liczbie gwiazdek
    * poprawnie obsługiwać cykl życia aktywności tzn. onCreate, onStart, onResume, onPause, onStop, onDestroy, ... (te które są potrzebne)
    * wykorzystywać fragmenty przy zmianie orientacji ekranu
    * zapamiętywać swój stan po zmianie orientacji ekranu

3. *(5pt) Zmień powyższą aplikacje na "tab layout" (ViewPager2) zamiast dwóch aktywności.

4. *(5pt) Uzupełnij poprzednie zadanie o możliwość robienia zdjęć np. z dostępnych podstawowych bibliotek i dodawania do kolekcji.

<br />
<br />
<br />

### Lista nr 5

> Celem tej listy jest napisanie aplikacji, która umożliwia prostą animacje i na stałe przechowuje dane.

1. (5pt) Zaprogramuj widok niestandardowy (custom view) z pełną funkcjonalnością oraz przykładami wykorzystania. Do wyboru jest okrągły pasek postępu lub animowany wykres:
    * Okrągły pasek postępu (Circular progress bar), który wyświetla okrągły pasek postępu i animuje go, gdy użytkownik zbliża się do celu.
    * Animowany wykres (Animated chart), który wyświetla wykres i animuje dane podczas ich aktualizacji

2. (5pt) Napisz prostą wersje gry 'Space Invaders'. Celem gry jest kontrola statku kosmicznego i zestrzelenie obcych przed osiągnięciem dołu ekranu. Wykorzystaj SurfaceView lub GLSurfaceView który będzie odpowiedzialny za rysowanie gry. Gra powinna być skalowalna do rozmiarów ekranu. Wszystkie informacje o stanie rozgrywki np. logowanie gracza, tabela wyników itp. mają być przechowywane w bazie SQLite z wykorzystaniem Room.

3. *(5pt) Dodaj do gry z zadania poprzedniego, komunikację przez sieć aby umożliwić grę dwóch użytkowników - dwa statki na dole ekranu sterowane niezależnie przez dwóch użytkowników na różnych urządzeniach (gracze widzą ten sam ekran). Do komunikacji wykorzystaj bazę czasu rzeczywistego Firebase.

<br />
<br />
<br />

### Lista nr 6

1. (5pt) Celem tego zadania jest wykorzystanie bazy danych czasu rzeczywistego oraz autentykacji Firebase. Można wykorzystać je w następujących aplikacjach (jedna do wyboru):
    * Dopisz funkcjonalność "gry przez sieć" do aplikacji kółko i krzyżyk. Wystarczy tutaj klasyczna rozgrywka na planszy 3x3, czyli na początku mamy możliwość rejestracji lub jeśli mamy już konto to logowania. Po czym możemy wybrać osobę do gry (która jest też zalogowana do systemu). Następnie prowadzimy grę na zmianę przez sieć.
    * Program do rozmowy (chat) przez sieć z wykorzystaniem Firebase. Logowanie i rozmowa w czasie rzeczywistym z zalogowanymi użytkownikami.
    * Dopisz możliwość gry przez sieć do gry Pong. Logowanie, tabela wyników i oczywiście sama gra prowadzona przez dwie osoby na różnych urządzeniach.

    Logowanie należy przeprowadzić z wykorzystaniem Firebase Authentication SDK.

2. (5pt) Napisz aplikacje wykorzystującą REST API do prostych obliczeń matematycznych Newton API. W implementacji wykorzystaj bibliotekę Retrofit. Napisz prosty interfejs do wprowadzania podstawowych funkcji np. w EditText wprowadzamy wyrażenie i na dole mamy Buttony które odpowiadają wszystkim dostępnym operacją simplify, factor, derive, ... po czym dostajemy wynik obliczeń.

    ```
    # Lokalna instalacja Newton API (jeśli chcemy pracować i/lub testować lokalnie)

    $ sudo pacman -S nodejs npm
    $ git clone https://github.com/aunyks/newton-api.git
    $ cd newton-api
    $ npm install --no-audit
    $ node app.js

    # Mamy serwer na http://localhost:3000
    ```

<br />
<br />
<br />

### Lista nr 7 

1. (5pt) Napisz prostą aplikacje "Task-Manager", czyli listę zadań do zrobienia. Aplikacja zawiera RecyclerView z potencjalną listą zadań. Na początku lista jest pusta, ale na dole aktywności znajduje się Button (Dodaj), gdzie użytkownik może uruchomić drugą aktywność aby wprowadzić opis nowego zadania. Do przechowywania danych należy wykorzystać bibliotkę SQLDelight.

2. (5pt) Utwórz aplikację pogodową "Weather App", która pobiera dane pogodowe z dowolnego API i zapewnia użytkownikom aktualizacje pogody w czasie rzeczywistym. Zaimplementuj usługę (service), która okresowo pobiera informacje o pogodzie i wyświetla je jako powiadomienie (notifications) dla użytkownika. Aplikacja powinna umożliwiać użytkownikom wybór preferowanej lokalizacji, a usługa powinna pobierać dane pogodowe dla tej lokalizacji w regularnych odstępach czasu. Powiadomienie powinno zawierać istotne szczegóły pogodowe, takie jak temperatura, wilgotność i krótki opis warunków pogodowych.

3. (5pt) Napisz prostą aplikację do odtwarzania muzyki "Music Player App", która pozwala użytkownikom odtwarzać i kontrolować swoją bibliotekę muzyczną. Zaimplementuj usługę (service), która zarządza funkcjami odtwarzania w tle, nawet gdy aplikacja nie jest aktywnie uruchomiona. Aplikacja powinna mieć powiadomienie (notifications) z przyciskami odtwarzania/pauzy, które umożliwiają użytkownikom sterowanie odtwarzaniem muzyki bez otwierania aplikacji.

4. CDN