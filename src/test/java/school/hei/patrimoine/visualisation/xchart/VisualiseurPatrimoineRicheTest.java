package school.hei.patrimoine.visualisation.xchart;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.TestFileGetter;
import school.hei.patrimoine.modele.EvolutionPatrimoine;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.GroupePossession;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.visualisation.AreImagesEqual;

import java.time.LocalDate;
import java.util.Set;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.time.Month.MAY;
import static java.time.Month.NOVEMBER;
import static java.util.Calendar.JUNE;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VisualiseurPatrimoineRicheTest {
  private final Visualiseur visualiseur = new Visualiseur();
  private final AreImagesEqual areImagesEqual = new AreImagesEqual();
  private final TestFileGetter testFileGetter = new TestFileGetter();

  private Patrimoine patrimoine() {
    var ilo = new Personne("Ilo");
    var au13mai24 = LocalDate.of(2024, MAY, 13);
    var compteCourant = new Argent("BP", au13mai24, 13_410);
    var salaire = new FluxArgent(
        "Salaire",
        compteCourant,
        LocalDate.of(2023, JANUARY, 1),
        LocalDate.of(2026, DECEMBER, 31),
        4_800,
        3);
    var trainDeVie = new GroupePossession(
        "Train de vie",
        au13mai24,
        Set.of(
            new FluxArgent(
                "Loyer",
                compteCourant,
                LocalDate.of(2023, JANUARY, 1),
                LocalDate.of(2026, DECEMBER, 31),
                -1_450,
                27),
            new FluxArgent(
                "Courses",
                compteCourant,
                LocalDate.of(2023, JANUARY, 1),
                LocalDate.of(2026, DECEMBER, 31),
                -1_100,
                1)
        ));

    var voiture = new GroupePossession(
        "Voiture",
        au13mai24,
        Set.of(new FluxArgent(
                "Achat voiture",
                compteCourant,
                LocalDate.of(2025, JUNE, 4),
                LocalDate.of(2025, JUNE, 4),
                -22_450,
                4),
            new Materiel(
                "Mercedes",
                LocalDate.of(2025, JUNE, 4),
                20_000,
                LocalDate.of(2025, JUNE, 4),
                -0.4)));

    var mac = new Materiel(
        "MacBook Pro",
        au13mai24,
        2_000,
        au13mai24,
        -0.9);

    return new Patrimoine(
        ilo,
        au13mai24,
        Set.of(compteCourant, trainDeVie, voiture, mac));

  }

  @Test
  void visualise_sur_quelques_annees() {
    var patrimoine = new EvolutionPatrimoine(
        "Dummy",
        patrimoine(),
        LocalDate.of(2024, MAY, 12),
        LocalDate.of(2026, NOVEMBER, 5));

    var imageGeneree = visualiseur.apply(patrimoine);

    assertTrue(areImagesEqual.apply(
        testFileGetter.apply("patrimoine-riche-sur-quelques-annees.png"),
        imageGeneree));
  }
}