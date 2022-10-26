<p class="lead">Zadanie 2. časť</p>

<ol>
<li>Upravte aplikáciu tak, že v nej budete mať len jednu Aktivitu a viacero Fragmentov a pre rozloženie budete používať ConstraintLayout. Pre navigáciu v aplikácii medzi obrazovkami-fragmentami použite knižnicu <a href="https://developer.android.com/guide/navigation">Navigation</a>.</li>
<li>Do aplikácie pridajte ďalší fragment, v ktorom si môžete zadať svoje meno, názov podniku (bar, reštaurácia, kaviareň,...) a jeho GPS súradnice, v ktorom nápoj z animácie podávajú.</li>
<li>Dole na obrazovke, kde budete zadávať údaje pridajte aj tlačidlo potvrdiť, ktoré vás presunie na daľší Fragment.</li>
<li>Na tom ďaľšom fragmente bude vypísané meno, názov podniku, animácia (z predchádzajúceho zadania) a tlačidlo "ukáž na mape".</li>
<li>Po kliknutí na tlačidlo, otvoríte inú aplikáciu v telefóne, ktorá dokáže ukázať GPS polohu na mape. </li>
</ol>

<p>Vyplnené údaje z jedného fragmentu do druhého pošlite cez <a href="https://developer.android.com/guide/navigation/navigation-pass-data">argumenty</a>. Vyplnené údaje nie je potrebné uchovávať po ukončení aplikácie.
Na otvorenie mapovej aplikácie použite Intent. Do aplikácie môžete pridať aj link na stránku, kde po klinutí sa Vám otvorí webový prehliadač so stránkou, prípadne to urobiť aj s telefónnym číslom.</p>

<p class="lead">Zadanie 3. časť</p>
<ol>
<li>Vytvorte nový Fragment v aplikácií, v ktorom zobrazíte zoznam podnikov v skrolovateľnom zozname - RecyclerView.</li>
<li>Zoznam podnikov načítate z JSON súboru : <a href="data/pubs.json">pubs.json</a>. Jednotlivé podniky sa nachádzajú v atribúte elements, kde majú svoje id ako aj GPS polohu.</li>

<li>V jednotlivých riadkoch v zozname, vypisujte názov podniku (v "tags"-&gt;"name") a prípadne aj iný užitočný dostupný údaj.</li>
<li>Po kliknutí na riadok sa Vám otvorí nový Fragment (podobný tomu z minulého cvičenia), v ktoromu bude: názov podniku, a dodatočné dostupné informácie ( internetová stránka, telefónne číslo, otváracie hodiny,....).</li>
<li>Pridajte do Fragmentu s detailom aj tlačidlo vymazať zo zoznamu. ( vymaže sa len z kolekcie v kóde, pri otvorení aplikácii sa znovu načítajú všetky z jsonu)</li>
<li>Vo Fragmente so zoznamom pridajte možnosť na zoradenie podľa názvu podniku.</li>  
</ol>
<p>Pre načítanie z json súboru môžete použiť knižnicu GSON. <a href="https://github.com/google/gson"> GSON </a></p>