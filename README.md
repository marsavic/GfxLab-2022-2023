# GfxLab


## Nameštanje

- Uključite JAR fajlove iz lib foldera u projekat, ako se to ne desi automatski.
- Potrebne je da vaš projekat koristi JavaFX biblioteke za vaš OS.
  - Najlakši način da to namestite je da koristite BellSoft Liberica JDK, koji, za razliku od većine drugih JDK-ova, dolazi sa ugrađenim JavaFX modulima.
    - Ako koristite IntelliJ, ovo je lako namestiti: File > Project Structure... > Project > SDK > Add SDK > Download JDK... > Vendor: BellSoft Liberica JDK 19.0.1.
  - Ako nećete tako, snađite se da preuzmete odgovarajuće biblioteke na neki način. Potrebni su vam javafx-base, javafx-controls, javafx-graphics, i javafx-swing.
  

## Šta-gde

- Pokrećete klasu `gui.App`.
- Nameštate šta želite da prikažete u klasi `playground.GfxLab`.
- Sve što budemo razvijali u toku kursa biće u paketu `graphics3d`.
