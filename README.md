# GfxLab


## Nameštanje

- Uključite JAR fajlove iz lib foldera u projekat, ako se to ne desi automatski.
- Potrebno je da vaš projekat koristi JavaFX biblioteke za vaš OS.
  - Najlakši način da to namestite je da koristite BellSoft Liberica JDK, koji, za razliku od većine drugih JDK-ova, dolazi sa ugrađenim JavaFX modulima.
    - Ako koristite IntelliJ, ovo je lako namestiti: File > Project Structure... > Project > SDK > Add SDK > Download JDK... > Vendor: BellSoft Liberica JDK 19.0.1.
    - Alternativno, sami preuzmite JDK sa [https://bell-sw.com/pages/downloads/](https://bell-sw.com/pages/downloads/#/java-19-current). Izaberite vaš OS, poslednju verziju, i Full JDK (jedino Full JDK uključuje JavaFX). Kada instalirate/raspakujete JDK, namestite u IDE-u da projekat koristi baš taj JDK.
  - Ako nećete da koristite BellSoft Liberica JDK, snađite se da preuzmete odgovarajuće biblioteke na neki način (direktni download svih potrebnih jar-fajlova, Maven, ...). Potrebni su vam javafx-base, javafx-controls, javafx-graphics, i javafx-swing.
  

## Šta-gde

- Pokrećete klasu `gui.App`.
- Nameštate šta želite da prikažete u klasi `playground.GfxLab`.
- Sve što budemo razvijali u toku kursa biće u paketu `graphics3d`.
